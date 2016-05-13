package com.ehaoyao.opertioncenter.send.dao;

import java.util.List;

import com.ehaoyao.opertioncenter.send.model.ChannelRule;
import com.ehaoyao.opertioncenter.send.model.ShortMessageRule;
import com.ehaoyao.opertioncenter.send.vo.RuleInfoVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;
import com.haoyao.goods.dao.BaseDao;

/**
 * 
 * Title: ShortMessageRuleDao.java
 * 
 * Description: 短信规则
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午5:58:56
 */
public interface ShortMessageRuleDao extends BaseDao<ShortMessageRule, Long> {

	/**
	 * 获取短信规则列表
	 */
	public List<ShortMessageRule> getRules(
			ShortMessageRuleVO<ShortMessageRule> ruleVO);

	/**
	 * 获取短信规则记录总数
	 */
	public int getRulesCount(ShortMessageRuleVO<ShortMessageRule> ruleVO);

	/**
	 * 控制短信规则的开关
	 */
	public void onOrOffRule(ShortMessageRule rule);

	/**
	 * 判断是否存在此规则
	 */
	public List<RuleInfoVO> getIsExistRule(RuleInfoVO cr);
	
	/**
	 * 根据规则ID查询规则
	 */
	public List<RuleInfoVO> getRuleByRuleId(String ruleId);

	/**
	 * 添加渠道规则信息
	 */
	public void addChannelRule(ChannelRule cr);

	/**
	 * 按规则ID删除渠道规则关联信息
	 */
	public void delChannelByRuleId(Long ruleId);
	
	/**
	 * 按渠道规则关系表ID删除渠道规则关联信息
	 */
	public void delChannelById(Long id);
	
	/**
	 * 查询渠道规则信息列表
	 */
	public List<RuleInfoVO> getRuleInfoLs(ShortMessageRuleVO<RuleInfoVO> ruleVO);

	/**
	 * 查询渠道规则信息总数
	 */
	public int getRuleInfoLsCount(ShortMessageRuleVO<RuleInfoVO> ruleVO);

	/**
	 * 按规则ID查询渠道
	 */
	public List<ChannelRule> getChannelByRuleId(Long ruleId);

}