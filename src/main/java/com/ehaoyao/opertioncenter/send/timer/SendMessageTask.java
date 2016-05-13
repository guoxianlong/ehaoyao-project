package com.ehaoyao.opertioncenter.send.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.common.PropertiesUtil;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.service.ShortMessageService;
import com.ehaoyao.opertioncenter.send.utils.ShortMessage;
import com.ehaoyao.opertioncenter.send.vo.RuleInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;

/**
 * 
 * Title: SendMessageTask.java
 * 
 * Description: 短信发送定时任务
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:04:03
 */
@Service
public class SendMessageTask {
	private static final Logger logger = Logger.getLogger(SendMessageTask.class);

	@Autowired
	private ShortMessageService shortMessageService;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	//定时任务时间间隔
	String strTaskTime=PropertiesUtil.getProperties("extend.properties", "send_mesg_task_time");
	
	public void run() {
		long st = System.currentTimeMillis();
		logger.info(">>>>>>短信定时任务：");
		// 查询短信记录，并发送
		this.sendSM();
		logger.info("总共耗时："+(System.currentTimeMillis()-st));
	}
	
	/**
	 * 按短信规则查询并发送短信
	 */
	@SuppressWarnings("unchecked")
	private void sendSM(){
		int totalPages = 0;
		int pageNo = 0;
		PageModel<RuleInfoVO> pageModel = null;
		ShortMessageRuleVO<RuleInfoVO> ruleVO = null;
		List<RuleInfoVO> ruleList = null;
		//分批获取短信规则
		do{
			pageNo++;
			pageModel = new PageModel<RuleInfoVO>();
			ruleVO = new ShortMessageRuleVO<RuleInfoVO>();
			pageModel.setPageNo(pageNo);
			ruleVO.setPageModel(pageModel);
			ruleVO.setEnable("1");//已启用
			//当前时间，小时
			ruleVO.setCurHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
			
			//查询当前时间可以发送短信的短信规则
			ruleVO = shortMessageService.getRuleInfoLs(ruleVO);
			pageModel = ruleVO.getPageModel();
			ruleList = pageModel.getList();
			
			if(ruleList!=null && ruleList.size()>0){
				List<ShortMessageLog> logList = null;
				PageModel<ShortMessageLog> pmLog = null;
				ShortMessageRuleVO<ShortMessageLog> rVO = null;
				//遍历短信规则
				for(RuleInfoVO r:ruleList){
					if (r.getOrderFlag() == null || r.getOrderFlag().trim().length() <= 0
							|| r.getOrderStatus() == null || r.getOrderStatus().trim().length() <= 0) {
						continue;
					}
					
					if(r.getStartHour()!=null&&r.getEndHour()!=null){
						//判断当前时间是否在指定时间段内
						if(!ShortMessage.betwenTime(r.getStartHour(),r.getEndHour())){
							break;
						}
					}else{//发送短信的时间段不明确，则不发送
						break;
					}
					
					//短信查询条件
					rVO = this.getSMLogRuleVO(r);
					
					pmLog = new PageModel<ShortMessageLog>();
					int pNo = 0;
					int tp = 0;
					//分批获取短信
					do{
						//每次查询，判断有效时间
						if(r.getStartHour()!=null&&r.getEndHour()!=null){
							//判断当前时间是否在指定时间段内
							if(!ShortMessage.betwenTime(r.getStartHour(),r.getEndHour())){
								break;
							}
						}else{//发送短信的时间段不明确，则不发送
							break;
						}
						
//						pNo++;
						//每次都查询第一页，发送短信后，发送标志改变，再次查询时这些数据不会被查询到
						pNo=1;
						pmLog.setPageNo(pNo);
						pmLog.setTotalRecords(0);
						pmLog.setList(null);
						rVO.setPageModel(pmLog);
						//按短信规则查询短信记录
						pmLog = shortMessageService.getSMLog(rVO);
						logList = pmLog.getList();
						String res = null;
						if(logList!=null && logList.size()>0){
							for(ShortMessageLog sl:logList){
								try {
									//短信发送
									res = ShortMessage.sendSM(sl.getPhoneNumber(),sl.getContent(),"8");
									if(res!=null && "1".equals(res.trim())){
										sl.setSendFlag("1");
									}else{
										sl.setSendFlag("2");//发送失败
										sl.setRemark(res);
									}
								} catch (Exception e) {
									logger.error("短信发失败！",e);
									sl.setSendFlag("2");
									String mesg = e.getMessage();
									if(mesg!=null && mesg.length()>200){
										mesg = mesg.substring(0, 200);
									}
									sl.setRemark(mesg);
								}
								if(!"1".equals(sl.getSendFlag())){
									logger.info("订单号："+sl.getOrderNumber() + " order_flag:"+sl.getOrderFlag()+" 短信ID："+sl.getId()+" 短信发送失败！");
								}
								//修改短信发送标志
								shortMessageService.updateSMLogSendFlag(sl);
							}
						}
						tp=pmLog.getTotalPages();
					}while(pNo<tp);
				}
			}
			totalPages = ruleVO.getPageModel().getTotalPages();
		}while(pageNo<totalPages);
		
	}
	
	//获取短信查询条件
	private ShortMessageRuleVO<ShortMessageLog> getSMLogRuleVO(RuleInfoVO rule){
		ShortMessageRuleVO<ShortMessageLog> vo = new ShortMessageRuleVO<ShortMessageLog>();
		//当前时间
		Date curTime = new Date();
		Date endTime = curTime;
		String sTime = df1.format(curTime);
		vo.setEndTime(endTime);
		try {
			BeanUtils.copyProperties(rule, vo);
			Integer sh = rule.getStartHour();
			Integer eh = rule.getEndHour();
			
			//获取规则时间段开始时间，整点
			Calendar sc = Calendar.getInstance();
			sc.setTime(df1.parse(sTime));
			sc.set(Calendar.HOUR_OF_DAY, sh);
			
			String outTimeFlag = rule.getOutTimeFlag();
			/*
			 * 拆单、运单复核
			 * 订单中心的表没有记录拆单时间，所以拆单时间只能是物流拆单表的“发货日期”字段，该时间只有年月日，没有时分秒，
			 * 所以，查询短信的开始时间只能从零点开始
			 */
			if(vo.getOrderStatus()!=null && ("5".equals(vo.getOrderStatus()) || "6".equals(vo.getOrderStatus()))){
				//开始时间
				//outTimeFlag 0:时间外自动顺延 	1：时间外跳过不发
				if(outTimeFlag!=null && outTimeFlag.trim().equals("0")){
					//前一天
					Calendar ec = Calendar.getInstance();
					try {
						ec.setTime(df1.parse(df1.format(new Date())));
					} catch (ParseException e) {}
					ec.add(Calendar.DAY_OF_MONTH, -1);
					vo.setStartTime(ec.getTime());
				}else{
					//当天日期
					try {
						vo.setStartTime(df1.parse(df1.format(new Date())));
					} catch (ParseException e) {}
				}
			}else{
				//开始时间
				//outTimeFlag 0:时间外自动顺延 	1：时间外跳过不发
				if(outTimeFlag!=null && outTimeFlag.trim().equals("0")){
					//前一天截止时间
					Calendar ec = Calendar.getInstance();
					ec.setTime(df1.parse(sTime));
					ec.add(Calendar.DAY_OF_MONTH, -1);
					ec.set(Calendar.HOUR_OF_DAY, eh);
					
					try {
						/*
						 * 前一天截止时间再往前推一个定时任务的时间，
						 * 避免规则截止时间之前的部分订单短信漏发
						 */
						Long t = Long.parseLong(strTaskTime);
						if(t!=null){
							int m = (int) (t/(1000*60));
							ec.add(Calendar.MINUTE, -m);
						}
					} catch (Exception e) {
					}
					vo.setStartTime(ec.getTime());
				}else{
					vo.setStartTime(sc.getTime());
				}
			}
			
		} catch (Exception e) {
			logger.error("短信规则：id="+rule.getId()+" 获取短信查询条件失败！",e);
			vo.setStartTime(curTime);
		}
		return vo;
	}
	

}
