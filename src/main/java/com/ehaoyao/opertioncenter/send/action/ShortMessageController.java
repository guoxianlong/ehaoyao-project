package com.ehaoyao.opertioncenter.send.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehaoyao.opertioncenter.common.CharacterReplaceUtil;
import com.ehaoyao.opertioncenter.common.EntityUtil;
import com.ehaoyao.opertioncenter.common.JsonUtil;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.common.PropertiesUtil;
import com.ehaoyao.opertioncenter.send.model.ChannelRule;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.model.ShortMessageRule;
import com.ehaoyao.opertioncenter.send.service.ShortMessageService;
import com.ehaoyao.opertioncenter.send.timer.OrderTask;
import com.ehaoyao.opertioncenter.send.timer.SendMessageTask;
import com.ehaoyao.opertioncenter.send.utils.ShortMessage;
import com.ehaoyao.opertioncenter.send.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.send.vo.RuleInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageLogVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;
import com.haoyao.goods.util.SignUtils;

/**
 * 
 * Title: ShortMessageController.java
 * 
 * Description: 短信设置
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午5:56:34
 */
@Controller
@RequestMapping("/rule")
public class ShortMessageController {
	private static final Logger logger = Logger.getLogger(ShortMessageController.class);
	@Autowired
	private ShortMessageService shortMessageService;
	@Autowired
	private OrderTask orderTask;
	@Autowired
	@Qualifier("sendMessageTask")
	private SendMessageTask sendTask;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//短信发送接口加密标识
	private String smsSign = PropertiesUtil.getProperties("extend.properties", "smsSign");
	
	/**
	 * 规则设置页面
	 */
	@RequestMapping(value = "/setup")
	public String setup(HttpServletRequest request,HttpSession session, ModelMap modelMap) {
		ShortMessageRuleVO<ShortMessageRule> ruleVO = new ShortMessageRuleVO<ShortMessageRule>();
		PageModel<ShortMessageRule> pageModel = new PageModel<ShortMessageRule>();
		String orderStatus = request.getParameter("orderStatus");
		String conChannel = request.getParameter("conChannel");
		String pn = request.getParameter("pageNo");
		
		modelMap.put("orderStatus", orderStatus);
		if("all".equals(orderStatus)){
			orderStatus = null;
			modelMap.put("orderStatus", "all");
		}
		ruleVO.setOrderStatus(orderStatus);
		
		modelMap.put("conChannel", conChannel);
		if("all".equals(conChannel)){
			conChannel = null;
			modelMap.put("conChannel", "all");
		}
		ruleVO.setOrderFlag(conChannel);
		
		int pageNo = 1;
		if(pn!=null){
			try {
				pageNo = Integer.parseInt(pn.trim());
			} catch (Exception e) {
				pageNo = 1;
			}
		}
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(10);
		ruleVO.setPageModel(pageModel);
		ruleVO = shortMessageService.getRules(ruleVO);
		modelMap.put("ruleVO", ruleVO);
		//渠道
		modelMap.put("channelMap",EntityUtil.getAllChannel());
		return "opcenter/sms/sms_rules";
	}

	/**
	 * 新增规则
	 * @param crule
	 * @return
	 */
	@RequestMapping(value = "/addRule",  method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> addRule(RuleInfoVO crule) {
		Map<String,String> map = new HashMap<String,String>();
		if(crule!=null){
			//验证渠道规则是否已存在
			if(crule.getOrderFlag()!=null && crule.getOrderFlag().trim().length()>0){
				List<RuleInfoVO> ls = shortMessageService.getIsExistRule(crule);
				String orderStatus = EntityUtil.getOrderStatusChange(crule.getOrderStatus());
				if(ls != null && ls.size()>0){
					String channelNames = "";
					String ids = "";
					for(RuleInfoVO r:ls){
						if(channelNames.length()>0){
							channelNames += "、";
						}
						channelNames += EntityUtil.getOrderFlagChange(r.getOrderFlag());
						
						if(ids.length()>0){
							ids += "、";
						}
						ids += r.getRuleId();
					}
					String message = "渠道["+channelNames+"]，已存在订单状态为[" + orderStatus + "]";
					if(crule.getCashDelivery() != null && !"".equals(crule.getCashDelivery()) && !"0".equals(crule.getCashDelivery())){
						message += ",其他条件为[仅针对货到付款]";
					}
					message += "的短信规则！规则编号："+ids;
					map.put("error", message);
					return map;
				}
			}
			
			//添加规则及相关渠道对应信息
			shortMessageService.addChannelRule(crule);
		}
		map.put("status", "success");
		return map;
	}
	
	/***
	 * 根据id查询短信规则信息
	 */
	@RequestMapping(value = "/getRuleByid", method = RequestMethod.POST)
	@ResponseBody
	public String getRuleByid(String id){
		RuleInfoVO rule=null;
		try {
			List<RuleInfoVO> ruleLs = shortMessageService.getRuleByRuleId(id);
			rule = null;
			if(ruleLs!=null && ruleLs.size()>0){
				rule = ruleLs.get(0);
				String orderFlag = "";
				for(RuleInfoVO r : ruleLs){
					if(orderFlag!=null && orderFlag.length()>0){
						orderFlag += ",";
					}
					orderFlag += r.getOrderFlag(); 
				}
				rule.setOrderFlag(orderFlag);
			}
		} catch (Exception e1) {
			logger.error(e1);
		}
		String ruleInfo = null;
		try {
			ruleInfo = JsonUtil.getJSONString(rule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruleInfo;
	}
	
	/**
	 * 根据id修改短信规则
	 */
	@RequestMapping(value = "/updateRuleByid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateRuleByid(RuleInfoVO ruleInfo){
		Map<String,String> map = new HashMap<String,String>();
		if(ruleInfo!=null){
			List<String> addChannels = null;
			List<Long> delIds = null;
			if(ruleInfo.getOrderFlag()!=null && !"".equals(ruleInfo.getOrderFlag().trim())){
				String channelNames = "";
				String ids = "";
				//查询已存在的渠道规则
				List<RuleInfoVO> ls = shortMessageService.getIsExistRule(ruleInfo);
				if(ls != null && ls.size()>0){
					for(RuleInfoVO r:ls){
						//与当前规则ID不同，则该渠道存在重复规则
						if(r.getRuleId()!=ruleInfo.getRuleId()){
							if(channelNames!=null && channelNames.length()>0){
								channelNames += "、";
							}
							channelNames += EntityUtil.getOrderFlagChange(r.getOrderFlag());
							if(ids.length()>0){
								ids += "、";
							}
							ids += r.getRuleId();
						}
					}
				}
				//渠道下已有相同规则
				if(channelNames!=null && !"".equals(channelNames.trim())){
					String orderStatus = EntityUtil.getOrderStatusChange(ruleInfo.getOrderStatus());
					String message = "渠道["+channelNames+"]，已存在订单状态为[" + orderStatus + "]";
					if(ruleInfo.getCashDelivery() != null && !"".equals(ruleInfo.getCashDelivery()) && !"0".equals(ruleInfo.getCashDelivery())){
						message += "，其他条件为[仅针对货到付款]";
					}
					message += "的短信规则！规则编号："+ids;
					map.put("error", message);
					return map;
				}
			}
			
			List<String> newChannels = null;
			if(ruleInfo.getOrderFlag()!=null && ruleInfo.getOrderFlag().trim().length()>0){
				String[] arr = ruleInfo.getOrderFlag().trim().split(",");
				//新渠道列表
				newChannels = Arrays.asList(arr);
				addChannels = new ArrayList<String>();
				//新增渠道
				addChannels.addAll(newChannels);
			}
			
			//使用该规则的所有渠道
			List<ChannelRule> oldChanneLs = shortMessageService.getChannelByRuleId(ruleInfo.getRuleId());
			List<ChannelRule> delChannel = null;
			if(oldChanneLs!=null && oldChanneLs.size()>0){
				delChannel = new ArrayList<ChannelRule>();
				//取消的渠道
				delChannel.addAll(oldChanneLs);
			}
			if(oldChanneLs!=null && oldChanneLs.size()>0 && newChannels!=null && newChannels.size()>0){
				//取消和新增渠道
				for(ChannelRule cr:oldChanneLs){
					for(String c:newChannels){
						//移除未变化的渠道，剩下删除和新增的渠道
						if(c.equals(cr.getOrderFlag())){
							delChannel.remove(cr);
							addChannels.remove(c);
						}
					}
				}
			}else if(oldChanneLs!=null && oldChanneLs.size()>0){
				//旧渠道不为空，新渠道为空，删除所有渠道，没有新增渠道
				addChannels = null;
			}else if(newChannels!=null && newChannels.size()>0){
				//旧渠道为空，新渠道不为空，新增渠道，没有可删除渠道
				delChannel = null;
			}else{
				addChannels = null;
				delChannel = null;
			}
			
			//已取消的渠道的id
			if(delChannel!=null && delChannel.size()>0){
				delIds = new ArrayList<Long>();
				for(ChannelRule cr:delChannel){
					delIds.add(cr.getId());
				}
			}
			
			//查询当前规则
			ShortMessageRule rule = shortMessageService.findRuleById(ruleInfo.getRuleId());
			//启用状态不变
			ruleInfo.setEnable(rule.getEnable());
			BeanUtils.copyProperties(ruleInfo, rule, new String[]{"id","orderFlag","lastTime"});
			rule.setLastTime(new Date());
			
			//新增的渠道
			List<ChannelRule> crLs = null;
			if(addChannels!=null && addChannels.size()>0){
				crLs = new ArrayList<ChannelRule>();
				ChannelRule cr = null;
				for(String cha:addChannels){
					cr = new ChannelRule();
					cr.setRuleId(rule.getId());
					cr.setOrderFlag(cha);
					cr.setLastTime(new Date());
					crLs.add(cr);
				}
			}
			
			try {
				//修改规则信息，删除取消的渠道，保存新增的渠道
				shortMessageService.updateChannelRule(rule,delIds,crLs);
			} catch (Exception e) {
				logger.error("规则修改失败！",e);
				map.put("error", "规则修改失败！");
				return map;
			}
		}
		map.put("status", "success");
		return map;
	}
	
	/**
	 * 根据id删除短信规则
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delRuleByid",method = RequestMethod.POST)
	@ResponseBody
	public String delRuleByid(String id){
		Long rid = Long.parseLong(id);
		String result;
		try {
			shortMessageService.deleteRule(rid);
			result = "success";
		} catch (Exception e) {
			logger.error(e);
			result = "error";
		}
		return result;
	}
	/**
	 * 根据id开启或者关闭短信规则
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/onOrOffRule",method = RequestMethod.POST)
	@ResponseBody
	public String onOrOffRule(ShortMessageRule rule){
		String result;
		try {
			if("true".equals(rule.getEnable())){
				rule.setEnable("1");
			}else{
				rule.setEnable("0");
			}
			shortMessageService.onOrOffRule(rule);
			result = "success";
		} catch (Exception e) {
			result = "error";
		}
		return result;
	}
	/**
	 * 接收物流端推送过来的订单状态通知，发送通知短信
	 * @param type 订单状态/类型 1：已完成，2：拆单
	 */
	@RequestMapping(value = "/recOrderSta" ,method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String recOrderSta(String type,String orderFlag,String orderNumber,String expressNo,String expressComName){
		if(type==null || (type=type.trim()).equals("")){
			return "{\"code\":0,\"message\":\"type is null\"}";
		}
		
		if(orderFlag!=null && (orderFlag=orderFlag.trim()).length()>0 
				&& orderNumber!=null && (orderNumber=orderNumber.trim()).length()>0){
			String comName = "";
			if(expressComName!=null && (expressComName=expressComName.trim()).length()>0){
				String[] names = expressComName.split(",");
				String name = "";
				for(String cName:names){
					try {
						//根据快递公司代码查询快递公司名称
						name = PropertiesUtil.getProperties("express.properties", cName);
					} catch (Exception e) {
						name = "";
						logger.error("获取 "+cName+" 快递公司名称异常："+e.getMessage());
					}
					if(name==null || name.trim().length()<=0){
						logger.info("未找到 "+cName+"对应的快递公司名称");
					}
					
					comName += (name!=null?name:"")+",";
				}
				comName = comName.length()>0?comName.substring(0,comName.length()-1):"";
				if(comName==null || comName.trim().length()<=0){
					logger.info("未找到"+expressComName+"代码对应的快递公司");
					comName = expressComName;
				}
			}
			//订单信息
			OrderInfoVO vo = new OrderInfoVO();
			vo.setOrderFlag(orderFlag);
			vo.setOrderNumber(orderNumber);
			vo.setExpressComName(comName.trim());
			vo.setExpressId(expressNo);

			//查询订单
			List<OrderInfoVO> orderLs = shortMessageService.getOrder(vo);
			if(orderLs!=null && orderLs.size()>0){
				vo = orderLs.get(0);
				vo.setExpressComName(comName.trim());
				vo.setExpressId(expressNo);
				
				//查询订单，发送并保存短信
				if(type.equals("1")){
					//4：已完成
					this.saveSMLog(vo,"4");
				}else if(type.equals("2")){
					logger.info("拆单 order_number:"+vo.getOrderNumber()+"  order_flag:"+vo.getOrderFlag());
					Long start = System.currentTimeMillis();
					try {
						//5：拆单
						this.saveSMLog(vo,"5");
					} catch (Exception e) {
						logger.error("拆单短信保存失败: order_number:"+vo.getOrderNumber()+"  order_flag:"+vo.getOrderFlag(), e);
					}
					logger.info("拆单： order_number:"+vo.getOrderNumber()+"  order_flag:"+vo.getOrderFlag()+"短信发送耗时："+(System.currentTimeMillis()-start)+"ms");
					start = System.currentTimeMillis();
					try {
						//6：运单复核
						this.saveSMLog(vo,"6");
					} catch (Exception e) {
						logger.error("复核短信保存失败： order_number:"+vo.getOrderNumber()+" order_flag:"+vo.getOrderFlag(),e);
					}
					logger.info("复核： order_number:"+vo.getOrderNumber()+"  order_flag:"+vo.getOrderFlag()+"短信发送耗时："+(System.currentTimeMillis()-start)+"ms");
				}else{
					logger.info("未知的订单状态/类型参数值！type="+type);
					return "{\"code\":0,\"message\":\"error,unknown type! type="+type+"\"}";
				}
			}else{
				logger.info("订单不存在！"+vo.getOrderFlag()+"  "+vo.getOrderNumber());
				return "{\"code\":0,\"message\":\"have no order!\"}";
			}
			
		}else{
			logger.info("订单号或订单标志为空，无法查找订单 orderFlag:"+orderFlag + "  orderNumber:"+orderNumber);
			return "{\"code\":0,\"message\":\"orderFlag or orderNumber is null! orderFlag:"+orderFlag + "  orderNumber:"+orderNumber+"\"}";
		}
		return "{\"code\":1,\"message\":\"success!\"}";
	}
	
	/**
	 * 查询订单，生成、发送并保存短信
	 */
	@SuppressWarnings("unchecked")
	private void saveSMLog(OrderInfoVO vo,String orderStatus){
		if(vo==null){
			return;
		}
		
		ShortMessageRuleVO<RuleInfoVO> ruleVO = new ShortMessageRuleVO<RuleInfoVO>();
		PageModel<RuleInfoVO> pageModel = new PageModel<RuleInfoVO>();
		pageModel.setPageSize(0);//查询全部
		ruleVO.setPageModel(pageModel);
		ruleVO.setEnable("1");//已启用
		ruleVO.setOrderFlag(vo.getOrderFlag());//订单标识
		ruleVO.setOrderStatus(orderStatus);//订单状态
		
		//该订单是货到付款，查询“仅针对货到付款”和“非仅针对货到付款”的规则，两条规则都可发短信
		if(vo.getPayType()!=null && vo.getPayType().indexOf("货到付款")>=0){
			ruleVO.setCashDelivery(null);
		}else{//针对全部
			ruleVO.setCashDelivery("0");
		}
		
		ruleVO = shortMessageService.getRuleInfoLs(ruleVO);
		if(ruleVO!=null){
			pageModel = ruleVO.getPageModel();
			if(pageModel.getList()!=null && pageModel.getList().size()>0){
				List<RuleInfoVO> rules = pageModel.getList();
				//无针对该订单状态的规则
				if(rules==null || rules.size()<=0){
					return;
				}
				
				//保存短信
				for(RuleInfoVO r:rules){
					addSMLog(r,vo,orderStatus);
				}
			}
			
		}
	}
	//添加短信
	private void addSMLog(RuleInfoVO rule,OrderInfoVO vo,String orderStatus){
		try {
			//物流重复推送时，为避免重复发送短信，保存前要先判断是否已存在
			ShortMessageLogVO sml = new ShortMessageLogVO();
			sml.setOrderFlag(vo.getOrderFlag());
			sml.setOrderNumber(vo.getOrderNumber());
			sml.setCashDelivery(rule.getCashDelivery());
			sml.setOrderStatus(rule.getOrderStatus());
			
			/*
			 * 拆单：短信内容中有运单号，每个运单都可通知拆单；
			 * 		 没有运单号时，只通知用户订单拆单，跟运单无关
			 * 运单复核、完成\签收：每个运单的复核、签收都可发短信
			 */
			if("5".equals(orderStatus)){
				String content = rule.getContent();
				if(content!=null && content.indexOf("[expressNumber]")>=0){
					sml.setExpressNo(vo.getExpressId());//运单号
				}
			}else{//复核、签收
				sml.setExpressNo(vo.getExpressId());//运单号
			}
			
			//判断短信是否已存在
			List<ShortMessageLog> list = shortMessageService.getSml(sml);
			if(list!=null && list.size()>0){
				logger.info("短信已存在！短信ID:"+list.get(0).getId()+" order_flage="+vo.getOrderFlag()+" order_number="+vo.getOrderNumber()
						+ " order_status="+rule.getOrderStatus()+" cash_delivery="+rule.getCashDelivery()+"expressNo="+vo.getExpressId());
				return;
			}
		} catch (Exception e1) {
			logger.info("验证重复短信失败"+e1.getMessage());
		}
		
		try {
			ShortMessageLog smLog = new ShortMessageLog();
			smLog.setOrderNumber(vo.getOrderNumber());
			smLog.setCustomerName(vo.getReceiver());
			smLog.setPhoneNumber(vo.getMobile());
			smLog.setExpressNo(vo.getExpressId());
			smLog.setExpressComName(vo.getExpressComName());
			
			smLog.setOrderFlag(vo.getOrderFlag());
			//短信规则条件
			smLog.setOrderStatus(rule.getOrderStatus());
			smLog.setCashDelivery(rule.getCashDelivery());
			smLog.setSendFlag("0");//未发送
			
			smLog.setStatusTime(vo.getLastTime());
			smLog.setLastTime(new Date());
			
			//完成
			if("4".equals(orderStatus)){
				Date date = vo.getLastTime();
				if(date==null || (new Date().getTime()-date.getTime())>1000*60*60*24){
					logger.info("last_time:"+date +"  order_number:"+vo.getOrderNumber()+"  order_flag:"+vo.getOrderFlag());
					logger.info("日期为空，或超过一天，不发送");
					return;
				}
			}else if("5".equals(orderStatus)||"6".equals(orderStatus)){//拆单、运单复核
				String deliveryDate = "";
				Date d = null;
				//拆单物流信息
				List<OrderInfoVO> exps = shortMessageService.getExpressInfoRemoves(vo);
				if(exps!=null && exps.size()>0){
					for(OrderInfoVO exp:exps){
						try {
							if(exp!=null&&exp.getDeliveryDate()!=null){
								deliveryDate = exp.getDeliveryDate().trim();
								if(deliveryDate.length()>=10){//日期 2014-08-25
									//获取指定运单号的日期
									if(vo.getExpressId()!=null && vo.getExpressId().indexOf(exp.getExpressId())>=0){
										deliveryDate=deliveryDate.substring(0, 10);//日期
										d = df.parse(deliveryDate);
										break;
									}
								}
							}
						} catch (Exception e) {
							continue;
						}
					}
					//运单号为空，则取第一条记录的日期
					if(d==null){
						for(OrderInfoVO exp:exps){
							try {
								if(exp!=null&&exp.getDeliveryDate()!=null){
									deliveryDate = exp.getDeliveryDate().trim();
									if(deliveryDate.length()>=10){//日期 2014-08-25
										deliveryDate=deliveryDate.substring(0, 10);//日期
										d = df.parse(deliveryDate);
										break;
									}
								}
							} catch (Exception e) {
								continue;
							}
						}
					}
				}else{
					logger.info("未找到该订单的拆单物流信息 order_number:"+vo.getOrderNumber()+"  order_flag:"+vo.getOrderFlag());
				}
				smLog.setStatusTime(d);
				//超过一天，不发送
				if(d==null || (df.parse(df.format(new Date())).getTime()-d.getTime())>1000*60*60*24){
					logger.info("deliveryDate:"+df.format(d) +"  order_number:"+vo.getOrderNumber()+"  order_flag:"+vo.getOrderFlag());
					logger.info("日期为空，或超过一天，不发送");
					return;
				}
			}
			
			//短信内容
			if(rule!=null && rule.getContent()!=null && rule.getContent().trim().length()>0){
				smLog.setContent(CharacterReplaceUtil.replace(rule.getContent().trim(),smLog));
			}
			
			//当前时间可发短信
			if(ShortMessage.betwenTime(rule.getStartHour(),rule.getEndHour())){
				//发送短信
				try{
					String res = ShortMessage.sendSM(smLog.getPhoneNumber(), smLog.getContent(), "5");
					if(res!=null && "1".equals(res.trim())){
						smLog.setSendFlag("1");
					}else{
						smLog.setSendFlag("2");//发送失败
						smLog.setRemark(res);
					}
				} catch (Exception e) {
					logger.error("短信发失败！",e);
					smLog.setSendFlag("2");
					String mesg = e.getMessage();
					if(mesg!=null && mesg.length()>200){
						mesg = mesg.substring(0, 200);
					}
					smLog.setRemark(mesg);
				}
				if(!"1".equals(smLog.getSendFlag())){
					logger.info("订单号："+smLog.getOrderNumber() + " order_flag:"+smLog.getOrderFlag()+" 短信发送失败！");
				}
				//保存短信
				shortMessageService.saveSmLog(smLog);
			}else{
				//自动顺延，保存短信
				if(rule.getOutTimeFlag()!=null && "0".equals(rule.getOutTimeFlag())){
					//保存短信
					shortMessageService.saveSmLog(smLog);
				}
			}
		} catch (Exception e) {
			logger.error("新增短信失败！order_flag="+vo.getOrderFlag()+"  order_number="+vo.getOrderNumber(),e);
		}
	}
	
	/*@RequestMapping(value = "/run", method = RequestMethod.POST)
	@ResponseBody
	public String run(){
		orderTask.run();
		return "success";
	}
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public String send(){
		sendTask.run();
		return "success";
	}*/
	
	/**
	 * 短信发送接口
	 */
	@RequestMapping(value = "/sendMesg" ,method = {RequestMethod.POST, RequestMethod.GET})
	public void sendMesg(HttpServletRequest request,PrintWriter out) {
		String tel = request.getParameter("tel");
		String mesg = request.getParameter("mesg");
		String sign = request.getParameter("sign");
		
		JSONObject js = new JSONObject();
		if(tel==null || (tel=tel.trim()).equals("")){
			js.put("code", "2");
			js.put("tip", "手机号为空！");
			logger.info("手机号为空！ sign:"+sign+"tel:"+tel+" mesg:"+mesg);
		}else if(mesg==null || (mesg=mesg.trim()).equals("")){
			js.put("code", "2");
			js.put("tip", "短信内容为空！");
			logger.info("短信内容为空！sign:"+sign+"tel:"+tel+" mesg:"+mesg);
		}else{
			try {
				mesg = new String(mesg.getBytes(),"UTF-8");
				logger.info("sign:"+sign+" tel:"+tel+" mesg:"+mesg);
			} catch (Exception e1) {
			}
			/**对请求加密验证*/
			Map<String, String> map = new HashMap<String, String>();
			map.put("tel",tel);
			map.put("mesg",mesg);
			HashMap<String, String> signMap = SignUtils.sign(map, smsSign);
			String appSign = signMap.get("appSign");
			if(!appSign.equals(sign)){
				js.put("code", "2");
				js.put("tip", "验证失败！");
				logger.info("参数签名验证失败！sign:"+sign+" tel:"+tel+" mesg:"+mesg);
			}else{
				//短信信息
				ShortMessageLog smLog = new ShortMessageLog();
				smLog.setPhoneNumber(tel);
				smLog.setOrderFlag("TMJSD");
				smLog.setContent(mesg);
				String code = "";
				String tip = "success";
				try {
					String res = ShortMessage.sendSM(tel.trim(), mesg.trim(), "5");
					if("1".equals(res)){
						smLog.setSendFlag("1");
						code = "1";
					}else{
						smLog.setSendFlag("2");
						smLog.setRemark(res);
						code = "2";
						logger.info(res);
					}
				} catch (Exception e) {
					logger.error("短信发送失败！sign:"+sign+" tel:"+tel+" mesg:"+mesg, e);
					String err = e.getMessage();
					if(err!=null&&err.length()>200){
						err = err.substring(0, 200);
					}
					smLog.setRemark(err);
					smLog.setSendFlag("2");
					code="2";
				}
				//保存短信
				try {
					smLog.setLastTime(new Date());
					shortMessageService.saveSmLog(smLog);
				} catch (Exception e) {
					logger.error("极速达短信保存失败！sign:"+sign+" tel:"+tel+" mesg:"+mesg,e);
				}
				if(!"1".equals(code)){
					logger.info("短信发送失败！sign:"+sign+" tel:"+tel+" mesg:"+mesg);
					tip = "error";
				}
				js.put("code", code);
				js.put("tip", tip);
			}
		}
		out.println(js.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/smsReport")
	public String smsReport(HttpServletRequest request, ModelMap modelMap) {
		String flag = request.getParameter("flag");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		//默认查询当月1号开始至当天数据
		if("1".equals(flag)){
			if((startDate==null || startDate.trim().length()==0) && 
					(endDate==null || endDate.trim().length()==0)){
				endDate = df.format(new Date());//截止到当天
				startDate = endDate.substring(0,8)+"01";//开始当月1号
			}
		}
		startDate = startDate!=null?startDate.trim():null;
		endDate = endDate!=null?endDate.trim():null;
		modelMap.put("startDate", startDate);
		modelMap.put("endDate", endDate);
		//短信统计
		List<Map<String,Object>> ls = shortMessageService.countSms(startDate,endDate);
		int count = 0;
		String shopName = "";
		for(Map<String,Object> map:ls){
			count += Integer.parseInt(map.get("num").toString());
			if("TMJSD".equals(map.get("orderFlag").toString().toUpperCase())){
				map.put("shopName", "极速达订单");
			}else{
				shopName = EntityUtil.getChannelName(map.get("orderFlag").toString());
				if(shopName==null || shopName.length()==0){
					shopName = map.get("orderFlag").toString();
				}
				map.put("shopName", shopName);
			}
		}
		modelMap.put("smsList", ls);
		modelMap.put("count", count);
		return "opcenter/sms/sms_report";
	}

	// 导出Excel
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportData")
	public void exportData(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
    	try {
    		OutputStream os=response.getOutputStream();// 取得输出流   
    		// 清空输出流
			response.reset();
			SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df1.format(new Date());
			
			//统计查询
			this.smsReport(request, modelMap);
			String startDate = modelMap.get("startDate")==null?"":modelMap.get("startDate").toString();
			String endDate = modelMap.get("endDate")==null?"":modelMap.get("endDate").toString();
			
			String name = "平台短信统计";
			// 设定输出文件头
			response.setHeader("Content-disposition", "attachment; filename="+new String(name.getBytes("GB2312"),"8859_1")+".xls");
			// 定义输出类型
			response.setContentType("application/msexcel");
			// 建立excel文件
            WritableWorkbook wwb = Workbook.createWorkbook(os);
            
			//设置单元格的文字格式
            //标题样式
            WritableFont fontTitle = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
            WritableCellFormat wcfTitle = new WritableCellFormat(fontTitle);
            wcfTitle.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.GRAY_50); //BorderLineStyle边框
            wcfTitle.setVerticalAlignment(VerticalAlignment.CENTRE);
            wcfTitle.setAlignment(Alignment.CENTRE);
            
            //内容字体
            WritableFont font = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
            //内容样式，居中
            WritableCellFormat wcf = new WritableCellFormat(font);
            wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.GRAY_50);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf.setAlignment(Alignment.CENTRE);
            //内容样式，居右
            WritableCellFormat wcf2 = new WritableCellFormat(font);
            wcf2.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.GRAY_50);
            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf2.setAlignment(Alignment.RIGHT);
            
            //创建sheet表格
            WritableSheet sheet = wwb.createSheet("平台短信统计", 0);
            //设置表格的宽度
            sheet.setColumnView(0,20);
            sheet.setColumnView(1,40);
            sheet.setColumnView(2,30);
            //第一行的高度
            sheet.setRowView(0, 600);
            //合并单元格
            sheet.mergeCells(2, 0, 3, 0);
            
            sheet.addCell(new Label(0,0,"发送日期：",wcf));
			sheet.addCell(new Label(1,0,startDate+" 至 "+endDate,wcf));
			sheet.addCell(new Label(2,0,"导出时间："+date,wcf));
            
            //头部行数，1行
            int headRow = 1;
            
            // 设置excel表的列标题
            String[] column = {"序号", "店铺名称", "短信触发量（条）"};
            sheet.setRowView(headRow, 400);
            for(int i=0;i<column.length;i++){
            	// 列、行、内容
            	sheet.addCell(new Label(i,headRow,column[i],wcfTitle));
            }
            
            List<Map<String,Object>> ls = null;
            if(modelMap.get("smsList")!=null){
            	ls = (List<Map<String, Object>>)modelMap.get("smsList");
            }

        	Map<String, Object> map=null;
        	Object objShopName=null;
        	Object objNum=null;
        	String shopName=null;
        	String num=null;
        	
        	int countRow = headRow+1;
            if(!ls.isEmpty()){
            	//填充数据
            	for(int i=0;i<ls.size();i++){
            		map = ls.get(i);
            		objShopName = map.get("shopName").toString();
            		objNum = map.get("num").toString();
        			
        			if(objShopName!=null){
        				shopName=objShopName.toString();
        			}
        			if(objNum!=null){
        				num=objNum.toString();
        			}
        			sheet.setRowView(i+headRow+1, 400);
            		sheet.addCell(new Label(0,i+countRow,(i+1)+"",wcf));
        			sheet.addCell(new Label(1,i+countRow,shopName,wcf));
        			sheet.addCell(new Label(2,i+countRow,num,wcf2));
            	}
            	countRow += ls.size();
            }
            
            String count = "0";
            if(modelMap.get("count")!=null){
            	count = modelMap.get("count").toString();
            }
            
            sheet.setRowView(countRow, 400);
            sheet.addCell(new Label(0,countRow,"",wcfTitle));
			sheet.addCell(new Label(1,countRow,"合计",wcfTitle));
			sheet.addCell(new Label(2,countRow,count,wcf2));
			
			//写入数据
			wwb.write();
			//关闭文件
			wwb.close();
			
		} catch (Exception e) {
			logger.error("导出平台短信统计表失败！", e);
		}
    }
}
