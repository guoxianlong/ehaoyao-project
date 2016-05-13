package com.ehaoyao.opertioncenter.send.dao;

import java.util.List;
import java.util.Map;

import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLogHis;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageLogVO;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageRuleVO;
import com.haoyao.goods.dao.BaseDao;

/**
 * 
 * Title: ShortMessageLogDao.java
 * 
 * Description: 短信记录
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年11月18日 下午5:58:37
 */
public interface ShortMessageLogDao extends BaseDao<ShortMessageLog, Long> {
	
	/**按短信规则获取短信*/
	public List<ShortMessageLog> getSMLogByRule(ShortMessageRuleVO<ShortMessageLog> vo);
	
	/**按短信规则获取短信总数*/
	public int getSMLogCountByRule(ShortMessageRuleVO<ShortMessageLog> vo);

	/**按短信信息获取短信*/
	public List<ShortMessageLog> getSml(ShortMessageLogVO sml);
	
	/** 保存短信历史记录 */
	public void saveSmLogHis(ShortMessageLogHis smlHis);
	
	/** 删除短信记录 */
	public void delSmLogById(Long id);

	/**
	 * 统计各平台已发短信数量
	 */
	public List<Map<String, Object>> countSms(String startDate, String endDate);
	
}
