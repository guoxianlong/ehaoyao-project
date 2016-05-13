package com.ehaoyao.opertioncenter.send.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.send.dao.HistoryTimeDao;
import com.ehaoyao.opertioncenter.send.dao.OrderInfoDao;
import com.ehaoyao.opertioncenter.send.dao.ShortMessageLogDao;
import com.ehaoyao.opertioncenter.send.dao.ShortMessageRuleDao;
import com.ehaoyao.opertioncenter.send.model.ChannelRule;
import com.ehaoyao.opertioncenter.send.model.HistoryTime;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLogHis;
import com.ehaoyao.opertioncenter.send.model.ShortMessageRule;
import com.ehaoyao.opertioncenter.send.service.ShortMessageService;
import com.ehaoyao.opertioncenter.send.vo.RuleInfoVO;
import com.ehaoyao.opertioncenter.send.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageLogVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;
/**
 * 
 * Title: ShortMessageServiceImpl.java
 * 
 * Description: 短信平台
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:03:06
 */
@Service
public class ShortMessageServiceImpl implements ShortMessageService{

	@Autowired
	private ShortMessageRuleDao ruleDao;
	@Autowired
	private ShortMessageLogDao logDao;
	@Autowired
	private OrderInfoDao orderDao;
	@Autowired
	private HistoryTimeDao htDao;
	
	//按ID查询短信规则
	public ShortMessageRule findRuleById(Long id) {
		return ruleDao.get(id);
	}

	//获取短信规则列表
	public ShortMessageRuleVO<ShortMessageRule> getRules(ShortMessageRuleVO<ShortMessageRule> ruleVO) {
		//记录总数
		int count = ruleDao.getRulesCount(ruleVO);
		ruleVO.getPageModel().setTotalRecords(count);
		//短信规则列表
		List<ShortMessageRule> rules = ruleDao.getRules(ruleVO);
		ruleVO.getPageModel().setList(rules);
		return ruleVO;
	}
	
	//删除短信规则
	public void deleteRule(Long id) {
		if(id!=null){
			ruleDao.delete(id);
			ruleDao.delChannelByRuleId(id);
		}
	}
	/**
	 * 控制短信规则的开关
	 */
	public void onOrOffRule(ShortMessageRule rule){
		if(rule != null && rule.getId() != null && !"".equals(rule.getId())){
			ruleDao.onOrOffRule(rule);
		}
	}
	/**
	 * 添加短息记录
	 */
	public int saveSmLog(ShortMessageLog smLog) {
		smLog.setLastTime(new Date());
		logDao.save(smLog);
		return 1;
	}
	
	/** 
	 * 获得订单列表
	 */
	public PageModel<OrderInfoVO> getOrders(ShortMessageRuleVO<OrderInfoVO> vo) {
		int count = 0;
		List<OrderInfoVO> ls = null;
		//退款
		if("7".equals(vo.getOrderStatus())){
			count = orderDao.getOrderRefundsCount(vo);
			ls = orderDao.getOrderRefunds(vo);
		}else{
			//查询总数
			count = orderDao.getOrdersCount(vo);
			//查询订单
			ls = orderDao.getOrders(vo);
		}
		vo.getPageModel().setTotalRecords(count);
		vo.getPageModel().setList(ls);
		return vo.getPageModel();
	}
	
	//修改短息发送标志
	public void updateSMLogSendFlag(ShortMessageLog smLog) {
		smLog.setLastTime(new Date());
		logDao.update(smLog);
	}

	/*
	 * 按短信规则获取短信记录
	 */
	@Override
	public PageModel<ShortMessageLog> getSMLog(ShortMessageRuleVO<ShortMessageLog> vo) {
		PageModel<ShortMessageLog> pageModel = vo.getPageModel();
		//查询短信
		int count = logDao.getSMLogCountByRule(vo);
		pageModel.setTotalRecords(count);
		List<ShortMessageLog> ls  = logDao.getSMLogByRule(vo);
		pageModel.setList(ls);
		return pageModel;
	}

	@Override
	public List<HistoryTime> getHt(HistoryTime ht) {
		return htDao.getHt(ht);
	}
	
	@Override
	public void updateHt(HistoryTime ht) {
		htDao.update(ht);
	}
	
	@Override
	public void saveHt(HistoryTime ht) {
		htDao.save(ht);
	}

	@Override
	public List<OrderInfoVO> getOrder(OrderInfoVO vo) {
		if(vo!=null){
			List<OrderInfoVO> ls = orderDao.getOrder(vo);
			return ls;
		}
		return null;
	}

	/**
	 * 判断是否存在此规则
	 */
	@Override
	public List<RuleInfoVO> getIsExistRule(RuleInfoVO cr) {
		return ruleDao.getIsExistRule(cr);
	}

	/**
	 * 获取拆单物流信息
	 */
	@Override
	public List<OrderInfoVO> getExpressInfoRemoves(OrderInfoVO vo) {
		if(vo!=null && vo.getOrderFlag()!=null && vo.getOrderNumber()!=null){
			List<OrderInfoVO> ls = orderDao.getExpressInfoRemoves(vo);
			return ls;
		}else{
			return null;
		}
	}

	/*
	 * 按短信信息获取短信
	 */
	@Override
	public List<ShortMessageLog> getSml(ShortMessageLogVO sml) {
		List<ShortMessageLog> ls = logDao.getSml(sml);
		return ls;
	}

	//备份历史短信
	@Override
	public void updateBackSmLog(ShortMessageLog sl) throws Exception {
		ShortMessageLogHis smlHis = new ShortMessageLogHis();
		BeanUtils.copyProperties(sl, smlHis);
		smlHis.setId(null);
		smlHis.setLogId(sl.getId());
		logDao.saveSmLogHis(smlHis);
		logDao.delete(sl.getId());
	}
	
	//按规则ID查询短信规则
	@Override
	public List<RuleInfoVO> getRuleByRuleId(String ruleId) {
		return ruleDao.getRuleByRuleId(ruleId);
	}

	//添加规则及渠道规则对应信息
	@Override
	public void addChannelRule(RuleInfoVO crule) {
		ShortMessageRule rule = new ShortMessageRule();
		BeanUtils.copyProperties(crule, rule, new String[]{"id","ruleId","orderFlag"});
		// 0:未启用，1：已启用，默认不启用
		rule.setEnable("1");
		rule.setLastTime(new Date());
		ruleDao.save(rule);
		
		String orderFlag = crule.getOrderFlag();
		if(orderFlag!=null && !"".equals(orderFlag.trim())){
			String channelArr[] = orderFlag.split(",");
			ChannelRule cr = null;
			for(String channel:channelArr){
				cr = new ChannelRule();
				cr.setOrderFlag(channel);
				cr.setRuleId(rule.getId());
				cr.setLastTime(new Date());
				ruleDao.addChannelRule(cr);
			}
		}
	}

	//修改规则信息，删除取消的渠道，保存新增的渠道
	@Override
	public void updateChannelRule(ShortMessageRule rule, List<Long> delIds,List<ChannelRule> crLs) throws Exception{
		rule.setLastTime(new Date());
		//保存规则信息
		ruleDao.update(rule);
		//删除已取消的渠道
		if(delIds!=null && delIds.size()>0){
			for(Long id : delIds){
				ruleDao.delChannelById(id);
			}
		}
		//保存新增的渠道
		if(crLs!=null && crLs.size()>0){
			for(ChannelRule cr:crLs){
				cr.setLastTime(new Date());
				ruleDao.addChannelRule(cr);
			}
		}
	}
	
	/*
	 * 查询渠道规则信息
	 */
	public ShortMessageRuleVO<RuleInfoVO> getRuleInfoLs(ShortMessageRuleVO<RuleInfoVO> ruleVO){
		int count = ruleDao.getRuleInfoLsCount(ruleVO);
		ruleVO.getPageModel().setTotalRecords(count);
		List<RuleInfoVO> crls = ruleDao.getRuleInfoLs(ruleVO);
		ruleVO.getPageModel().setList(crls);
		return ruleVO;
	}

	// 按规则ID查询渠道
	public List<ChannelRule> getChannelByRuleId(Long ruleId){
		return ruleDao.getChannelByRuleId(ruleId);
	}
	
	/*
	 * 统计各平台已发短信数量
	 */
	public List<Map<String, Object>> countSms(String startDate, String endDate){
		List<Map<String, Object>> ls = logDao.countSms(startDate,endDate);
		return ls;
	}
	
}
