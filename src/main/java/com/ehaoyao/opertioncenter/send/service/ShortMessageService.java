package com.ehaoyao.opertioncenter.send.service;

import java.util.List;
import java.util.Map;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.send.model.ChannelRule;
import com.ehaoyao.opertioncenter.send.model.HistoryTime;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.model.ShortMessageRule;
import com.ehaoyao.opertioncenter.send.vo.RuleInfoVO;
import com.ehaoyao.opertioncenter.send.vo.OrderInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageLogVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;

/**
 * 
 * Title: ShortMessageService.java
 * 
 * Description: 短信设置
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午6:03:21
 */
public interface ShortMessageService{
	
	/**
	 * 判断是否存在此规则
	 * @param rule
	 * @return
	 */
	public List<RuleInfoVO> getIsExistRule(RuleInfoVO cr);

	/**
	 * 按ID获取一条短信规则
	 */
	public ShortMessageRule findRuleById(Long id);

	/**
	 * 获取短信规则
	 */
	public ShortMessageRuleVO<ShortMessageRule> getRules(ShortMessageRuleVO<ShortMessageRule> ruleVO);
	
	/**
	 * 删除短信规则
	 */
	public void deleteRule(Long id);
	
	/**
	 * 控制短信规则的开关
	 * @param log
	 * @return
	 */
	public void onOrOffRule(ShortMessageRule rule);
	
	/**
	 * 按短信规则获取订单
	 */
	public PageModel<OrderInfoVO> getOrders(ShortMessageRuleVO<OrderInfoVO> vo);
	
	/**
	 * 新增一条短信记录
	 */
	public int saveSmLog(ShortMessageLog smLog);
	
	/**
	 * 修改短信记录发送标志
	 */
	public void updateSMLogSendFlag(ShortMessageLog smLog);

	/**
	 * 按短信规则获取短信记录
	 */
	public PageModel<ShortMessageLog> getSMLog(ShortMessageRuleVO<ShortMessageLog> rVO);

	/**
	 * 获取历史时间
	 */
	public List<HistoryTime> getHt(HistoryTime ht);

	public void updateHt(HistoryTime ht);
	
	public void saveHt(HistoryTime ht);

	/**
	 * 获取订单
	 */
	public List<OrderInfoVO> getOrder(OrderInfoVO vo);
	
	/**
	 * 
	 * 获取拆单物流表信息
	 * @param vo
	 * @return
	 */
	public List<OrderInfoVO> getExpressInfoRemoves(OrderInfoVO vo);
	
	/**
	 * 按短信信息获取短信
	 */
	public List<ShortMessageLog> getSml(ShortMessageLogVO sml);
	
	/**
	 * 备份历史短信
	 */
	public void updateBackSmLog(ShortMessageLog sl) throws Exception;
	
	/**
	 * 根据规则ID获取短信规则
	 */
	public List<RuleInfoVO> getRuleByRuleId(String ruleId);
	
	/**
	 * 添加规则及渠道规则对应信息
	 */
	public void addChannelRule(RuleInfoVO crule);
	
	/**
	 * 修改规则信息，删除取消的渠道，保存新增的渠道
	 * @throws Exception 
	 */
	public void updateChannelRule(ShortMessageRule rule, List<Long> delIds,List<ChannelRule> crLs) throws Exception;
	
	/**
	 * 查询渠道规则信息
	 */
	public ShortMessageRuleVO<RuleInfoVO> getRuleInfoLs(ShortMessageRuleVO<RuleInfoVO> ruleVO);
	
	/**
	 * 按规则ID查询渠道
	 */
	public List<ChannelRule> getChannelByRuleId(Long ruleId);

	/**
	 * 统计各平台已发短信数量
	 */
	public List<Map<String, Object>> countSms(String startDate, String endDate);
}
