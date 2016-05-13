package com.ehaoyao.opertioncenter.send.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.CharacterReplaceUtil;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.common.PropertiesUtil;
import com.ehaoyao.opertioncenter.send.model.HistoryTime;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.service.ShortMessageService;
import com.ehaoyao.opertioncenter.send.utils.ShortMessage;
import com.ehaoyao.opertioncenter.send.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.send.vo.RuleInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageLogVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;

/**
 * 
 * Title: OrderTask.java
 * 
 * Description: 订单查询定时任务
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:03:47
 */
@Service
public class OrderTask {
	private static final Logger logger = Logger.getLogger(OrderTask.class);

	@Autowired
	private ShortMessageService shortMessageService;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH");
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	//定时任务时间间隔
	String strTaskTIme=PropertiesUtil.getProperties("extend.properties", "send_mesg_task_time");
	
	public void run() {
		long st = System.currentTimeMillis();
		logger.info(">>>>>>订单查询定时任务：");
		//**按已启用的短信规则，查询订单，生成短信，存入数据库
		this.createSmLog();
		logger.info("总共耗时："+(System.currentTimeMillis()-st));
	}

	/**
	 * 按已启用的短信规则，查询订单，生成短信，存入数据库
	 */
	@SuppressWarnings("unchecked")
	private void createSmLog(){
		int totalPages = 0;
		int pageNo = 0;
		PageModel<RuleInfoVO> pageModel = null;
		ShortMessageRuleVO<RuleInfoVO> ruleVO = null;
		PageModel<OrderInfoVO> pageModel2 = null;
		List<RuleInfoVO> ruleList = null;
		
		/*
		 * ************* 按已启用的短信规则，查询订单，生成短信，存入数据库 ************************/
		logger.info("获取短信规则：");
		//分批获取短信规则
		do{
			pageNo++;
			pageModel = new PageModel<RuleInfoVO>();
			pageModel.setPageNo(pageNo);
			ruleVO = new ShortMessageRuleVO<RuleInfoVO>();
			ruleVO.setPageModel(pageModel);
			ruleVO.setEnable("1");//已启用
			
			//查询已启用的所有短信规则
//			ruleVO = shortMessageService.getRules(ruleVO);
			ruleVO = shortMessageService.getRuleInfoLs(ruleVO);
			pageModel = ruleVO.getPageModel();
			ruleList = pageModel.getList();
			
			if(ruleList!=null && ruleList.size()>0){
				//遍历短信规则
				for(RuleInfoVO r:ruleList){
					try {
						//订单标志和订单状态不能为空，且定单状态不能为4：已完成，5：已拆单
						if (r.getOrderFlag() == null || r.getOrderFlag().trim().length() <= 0
								|| r.getOrderStatus() == null || r.getOrderStatus().trim().length() <= 0 
								|| "4".equals(r.getOrderStatus())||"5".equals(r.getOrderStatus())) {
							continue;
						}
						
						//规则时间段不明确，不知何时发送短信，则不查询订单
						if(r.getStartHour()==null || r.getEndHour()==null){
							continue;
						}
						
						//时间外跳过不发
						if(r.getOutTimeFlag()!=null && "1".equals(r.getOutTimeFlag().trim())){
							//当前时间不在发送短信时间段内，则不查询订单
							if(!ShortMessage.betwenTime(r.getStartHour(), r.getEndHour())){
								continue;
							}
						}
						
						//获取订单查询条件
						ShortMessageRuleVO<OrderInfoVO> vo = this.getRuleVO(r);
						pageModel2 = new PageModel<OrderInfoVO>();
						int pNo = 0;
						int tp = 0;
						logger.info("获取短信：order_flag:"+r.getOrderFlag()+" status:"+r.getOrderStatus());
						//分批获取订单信息
						do{
							//时间外跳过不发
							if(r.getOutTimeFlag()!=null && "1".equals(r.getOutTimeFlag().trim())){
								//当前时间不在发送短信时间段内，则不查询订单
								if(!ShortMessage.betwenTime(r.getStartHour(), r.getEndHour())){
									continue;
								}
							}
							pNo++;
							pageModel2.setTotalRecords(0);
							pageModel2.setList(null);
							pageModel2.setPageNo(pNo);
							vo.setPageModel(pageModel2);
							if("6".equals(r.getOrderStatus())){
								vo.setsTime(vo.getStartTime()!=null?df.format(vo.getStartTime()):"");
							}
							//按短信规则查询订单
							pageModel2 = shortMessageService.getOrders(vo);
							//根据订单，批量生成短信并保存
							this.saveSMLogs(r, pageModel2.getList());
							
							tp=pageModel2.getTotalPages();
						}while(pNo<tp);
					} catch (Exception e) {
						logger.error("按短信规则：rule_id="+r.getId()+" order_flag="+r.getOrderFlag()+" 查询订单失败！",e);
					}
				}
			}
			totalPages = ruleVO.getPageModel().getTotalPages();
		}while(pageNo<totalPages);
	}
	
	/**
	 * 获取订单查询条件
	 */
	private ShortMessageRuleVO<OrderInfoVO> getRuleVO(RuleInfoVO rule){
		ShortMessageRuleVO<OrderInfoVO> vo = new ShortMessageRuleVO<OrderInfoVO>();
		Date curTime = new Date();
		Date endTime = curTime;//截止时间
		vo.setEndTime(endTime);
		try {
			BeanUtils.copyProperties(rule, vo);
			
			if(rule.getCashDelivery()!=null && "1".equals(rule.getCashDelivery().trim())){
				vo.setCashDelivery("货到付款");
			}else{
				vo.setCashDelivery(null);
			}
			
			//获取整点时间，开始时间
			Integer sh = rule.getStartHour();
			Calendar sc = Calendar.getInstance();
			sc.set(Calendar.HOUR_OF_DAY, sh);
			sc.setTime(df1.parse(df1.format(sc.getTime())));
			
			//获取整点时间，截止时间
			Integer eh = rule.getEndHour();
			Calendar ec = Calendar.getInstance();
			ec.set(Calendar.HOUR_OF_DAY, eh);
			ec.setTime(df1.parse(df1.format(ec.getTime())));
			
			HistoryTime historyTime = new HistoryTime();
			historyTime.setOrderFlag(rule.getOrderFlag());
			historyTime.setOrderStatus(rule.getOrderStatus());
			historyTime.setCashDelivery(rule.getCashDelivery());
			
			//上次查询订单时间
			List<HistoryTime> htLs = shortMessageService.getHt(historyTime);
			HistoryTime ht = null;
			//有历史时间记录，更新截止时间
			if(htLs!=null && htLs.size()>0 && htLs.get(0)!=null){
				ht = htLs.get(0);
				Date end = ht.getEndTime();
				if(end!=null){
					//时间外跳过不发
					if(rule.getOutTimeFlag()!=null && "1".equals(rule.getOutTimeFlag().trim())){
						int taskTime = Integer.parseInt(strTaskTIme);
						/* 1.当天首次查询
						 * 2.上次查询截止时间在规则开始时间之前
						 * 以规则开始时间作为订单查询开始时间
						 */
						if((curTime.getTime()-sc.getTime().getTime())<taskTime 
								|| end.compareTo(sc.getTime())<0){
							ht.setStartTime(sc.getTime());
						}else{
							ht.setStartTime(end);
						}
					}else{//自动顺延
						/*
						 * 上次查询到当前时间超出一天，视为短信规则禁用后重新启用。
						 */
						if((curTime.getTime()-end.getTime())>1000*60*60*24){
							if(curTime.compareTo(sc.getTime())<0){//开始时间之前
								//前一天截止时间
								Calendar preEc = Calendar.getInstance();
								preEc.add(Calendar.DAY_OF_MONTH, -1);
								preEc.set(Calendar.HOUR_OF_DAY, rule.getEndHour());
								//获取整点时间
								preEc.setTime(df1.parse(df1.format(preEc.getTime())));
								ht.setStartTime(preEc.getTime());
							}else if(curTime.compareTo(ec.getTime())>0) {//截止时间之后
								ht.setStartTime(ec.getTime());
							}else{//规则时间段内
								ht.setStartTime(sc.getTime());
							}
						}else{
							ht.setStartTime(end);
						}
					}
				}else{//上次查询截止时间未知，把当前时间作为本次查询开始时间，避免重复查询
					ht.setStartTime(new Date());
				}
				ht.setEndTime(endTime);
				vo.setStartTime(ht.getStartTime());
				shortMessageService.updateHt(ht);
			}else{
				//无历史查询时间，规则首次启用首次查询，新增时间记录
				ht = new HistoryTime();
				ht.setOrderFlag(rule.getOrderFlag());
				ht.setOrderStatus(rule.getOrderStatus());
				ht.setCashDelivery(rule.getCashDelivery());
				//时间外跳过不发，首次启用首次查询，以规则开始时间作为订单查询开始时间
				if(rule.getOutTimeFlag()!=null && "1".equals(rule.getOutTimeFlag().trim())){
					ht.setStartTime(sc.getTime());
				}else{
					/*
					 * 自动顺延，规则首次启用首次查询：
					 * 1.当前时间在规则开始时间之前，订单查询开始时间设为短信规则前一天截止时间，截止时间为当前时间；
					 * 2.当前时间在规则时间之内，订单查询开始时间设为短信规则开始时间，截止时间为当前时间；
					 * 3.当前时间在规则截止时间之后，订单查询开始时间设为短信规则截止时间，截止时间为当前时间。
					 */
					if(curTime.compareTo(sc.getTime())<0){//开始时间之前
						//前一天截止时间
						Calendar preEc = Calendar.getInstance();
						preEc.add(Calendar.DAY_OF_MONTH, -1);
						preEc.set(Calendar.HOUR_OF_DAY, rule.getEndHour());
						//获取整点时间
						preEc.setTime(df1.parse(df1.format(preEc.getTime())));
						ht.setStartTime(preEc.getTime());
					}else if(curTime.compareTo(ec.getTime())>0) {//截止时间之后
						ht.setStartTime(ec.getTime());
					}else{//规则时间段内
						ht.setStartTime(sc.getTime());
					}
				}
				
				ht.setEndTime(endTime);
				vo.setStartTime(ht.getStartTime());
				shortMessageService.saveHt(ht);
			}
			return vo;
		} catch (Exception e) {
			logger.error("短信规则：id="+rule.getId()+"获取订单查询条件失败！",e);
			vo.setStartTime(curTime);
		}
		return vo;
	}
	
	/**
	 * 根据订单，批量生成短信并保存
	 */
	private void saveSMLogs(RuleInfoVO r,List<OrderInfoVO> orderList){
		if(orderList!=null && orderList.size()>0){
			ShortMessageLog smLog = null;
			String expComName = "";
			ShortMessageLogVO sml = null;
			for(OrderInfoVO o:orderList){
				//已发货状态(订单中心订单状态：s01 s02)有多种，保存前判断短信是否已存在，避免重复
				//6：运单已复核 ，与发货状态相同
				if("3".equals(r.getOrderStatus()) || "6".equals(r.getOrderStatus())){
					sml = new ShortMessageLogVO();
					sml.setOrderFlag(o.getOrderFlag());
					sml.setOrderNumber(o.getOrderNumber());
					sml.setCashDelivery(r.getCashDelivery());
					sml.setOrderStatus(r.getOrderStatus());
					sml.setExpressNo(o.getExpressId());//运单号，拆单后有多个，运单复核每个运单一个通知

//					//检查N天内是否有该状态的短信
//					Calendar ca = Calendar.getInstance();
//					ca.add(Calendar.DAY_OF_MONTH, -5);
//					try {
//						sml.setStatusTime(df2.parse(df2.format(ca.getTime())));
//					} catch (Exception e1) {
//					}
					
					//判断短信是否已存在
					List<ShortMessageLog> list = shortMessageService.getSml(sml);
					if(list!=null && list.size()>0){
//						logger.info("短信已存在！短信ID:"+list.get(0).getId()+" order_flage="+o.getOrderFlag()+" order_number="+o.getOrderNumber()
//								+ " order_status="+r.getOrderStatus()+" cash_delivery="+r.getCashDelivery());
						continue;
					}
				}
				
				try {
					smLog = new ShortMessageLog();
					smLog.setOrderNumber(o.getOrderNumber());
					smLog.setCustomerName(o.getReceiver());
					smLog.setPhoneNumber(o.getMobile());
					
					//下单、配货、退款中没有快递信息
					if("1".equals(r.getOrderStatus()) || "2".equals(r.getOrderStatus()) || "7".equals(r.getOrderStatus())){
						smLog.setExpressNo(o.getExpressId());
						smLog.setExpressComName(o.getExpressComName());
						
						if("7".equals(r.getOrderStatus())){//退款金额
							smLog.setRefundMoney(o.getRefundMoney());
						}
					}else{
						smLog.setExpressNo(o.getExpressId());
						//获取快递公司名称
						if(o.getExpressComName()!=null && o.getExpressComName().trim().length()>0){
							expComName = o.getExpressComName().trim();
						}else if(o.getExpressComCode()!=null && o.getExpressComCode().trim().length()>0){
							//快递公司名称编码
							expComName = PropertiesUtil.getProperties("orderCodeToKD100Code.properties", o.getExpressComCode().trim());
							if(expComName!=null && expComName.trim().length()>0){
								expComName = PropertiesUtil.getProperties("express.properties", expComName);
							}
						}else if(o.getExpressComId()!=null && o.getExpressComId().trim().length()>0){
							//快递公司名称编码
							expComName = PropertiesUtil.getProperties("expressIDtoKD100Code.properties", o.getExpressComId().trim());
							if(expComName!=null && expComName.trim().length()>0){
								expComName = PropertiesUtil.getProperties("express.properties", expComName);
							}
						}else{//拆单
							OrderInfoVO vo = new OrderInfoVO();
							vo.setOrderFlag(o.getOrderFlag());
							vo.setOrderNumber(o.getOrderNumber());
							List<OrderInfoVO> ls = shortMessageService.getExpressInfoRemoves(vo);
							if(ls!=null && ls.size()>0){
								String expIds = "";
								for(OrderInfoVO exp:ls){
									//快递单号
									expIds += (exp.getExpressId()!=null?exp.getExpressId().trim():"")+",";
									String cName="";
									//获取快递公司名称
									if(exp.getExpressComName()!=null && exp.getExpressComName().trim().length()>0){
										expComName += exp.getExpressComName().trim()+",";
									}else if(exp.getExpressComCode()!=null && exp.getExpressComCode().trim().length()>0){
										//快递公司名称编码
										cName = PropertiesUtil.getProperties("orderCodeToKD100Code.properties", exp.getExpressComCode().trim());
										if(cName!=null && cName.trim().length()>0){
											cName = PropertiesUtil.getProperties("express.properties", cName);
										}
									}else if(exp.getExpressComId()!=null && exp.getExpressComId().trim().length()>0){
										//快递公司名称编码
										cName = PropertiesUtil.getProperties("expressIDtoKD100Code.properties", exp.getExpressComId().trim());
										if(cName!=null && cName.trim().length()>0){
											cName = PropertiesUtil.getProperties("express.properties", cName);
										}
									}
									expComName += (cName!=null?cName.trim():"") + ",";
								}
								
								//快递单号
								expIds = expIds.length()>0?expIds.substring(0,expIds.length()-1):null;
								smLog.setExpressNo(expIds);
								
								expComName = expComName.length()>0?expComName.substring(0,expComName.length()-1):null;
							}
						}
						smLog.setExpressComName(expComName);
					}
					
					smLog.setOrderFlag(o.getOrderFlag());
					//短信规则条件
					smLog.setOrderStatus(r.getOrderStatus());
					smLog.setCashDelivery(r.getCashDelivery());
					
					smLog.setSendFlag("0");//未发送
					
					if(r.getOrderStatus()!=null){
						//订单状态更新时间
						try {
							if("1".equals(r.getOrderStatus().trim())){//已下单
								if(o.getToOrdercenterTime()!=null){
									smLog.setStatusTime(o.getToOrdercenterTime());
								}else{
									smLog.setStatusTime(o.getLastTime());
								}
							}else if("2".equals(r.getOrderStatus().trim())){//配货中
								if(o.getToErpTime()!=null){
									smLog.setStatusTime(df.parse(o.getToErpTime().trim()));
								}else{
									smLog.setStatusTime(o.getLastTime());
								}
							}else if("6".equals(r.getOrderStatus().trim())){//运单已复核
								if(o.getDeliveryDate()!=null){
									smLog.setStatusTime(df2.parse(o.getDeliveryDate().trim()));
								}else{
									smLog.setStatusTime(o.getLastTime());
								}
							}else if("7".equals(r.getOrderStatus().trim())){// 已退款
								if(o.getRefundTime()!=null){
									smLog.setStatusTime(df.parse(o.getRefundTime().trim()));
								}else{
									smLog.setStatusTime(o.getLastTime());
								}
							}else {// 3:已发货 
								smLog.setStatusTime(o.getLastTime());
							}
						} catch (Exception e) {
							smLog.setStatusTime(o.getLastTime());
						}
					}
					
					smLog.setLastTime(new Date());
					//短信内容
					if(r!=null && r.getContent()!=null && r.getContent().trim().length()>0){
						smLog.setContent(CharacterReplaceUtil.replace(r.getContent().trim(),smLog));
					}
					//保存短信
					shortMessageService.saveSmLog(smLog);
				} catch (Exception e) {
					logger.error("新增短信失败！order_flag="+o.getOrderFlag()+"  order_number="+o.getOrderNumber(),e);
				}
			}
		}
	}
	
}
