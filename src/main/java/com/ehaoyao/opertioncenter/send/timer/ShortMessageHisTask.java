package com.ehaoyao.opertioncenter.send.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.opertioncenter.common.PropertiesUtil;
import com.ehaoyao.opertioncenter.send.model.ShortMessageLog;
import com.ehaoyao.opertioncenter.send.service.ShortMessageService;
import com.ehaoyao.opertioncenter.send.vo.ShortMessageLogVO;

/**
 * 
 * Title: ShortMessageHis.java
 * 历史短信
 * @author xcl
 * @version 1.0
 * @created 2014年11月4日 下午5:17:06
 */
@Service
public class ShortMessageHisTask {
	private static final Logger logger = Logger.getLogger(ShortMessageHisTask.class);

	@Autowired
	private ShortMessageService shortMessageService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//保留短信日志的天数
	private String retain_log_day=PropertiesUtil.getProperties("extend.properties", "retain_log_day");
	//备份失败的短信条数
	public static int errNum=0;
	
	public void setShortMessageService(ShortMessageService shortMessageService) {
		this.shortMessageService = shortMessageService;
	}

	/**
	 * 历史短信移入历史表中
	 */
	public void backupShortMessage(){
		Date st = new Date();
		logger.info("备份历史短信开始>>>>"+st);
		ShortMessageLogVO smlog = new ShortMessageLogVO();
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(df.parse(df.format(ca.getTime())));
			if(retain_log_day!=null && !"".equals(retain_log_day)){
				Integer day = Integer.parseInt(retain_log_day);
				//保留多少天内的短信记录
				ca.add(Calendar.DAY_OF_MONTH, -day);
			}
		} catch (Exception e) {
		}
		smlog.setLastTime(ca.getTime());
		smlog.setPageSize(100);
		//查询某段时间之前的短信
		List<ShortMessageLog> ls = null;
		do{
			smlog.setFirstResult(errNum);//备份失败的记录不再查询
			try {
				ls = shortMessageService.getSml(smlog);
			} catch (Exception e) {
				ls = null;
			}
			Long id = 0L;
			if((ls!=null && ls.size()>0)){
				for(ShortMessageLog sl:ls){
					id = sl.getId();
					try {
						shortMessageService.updateBackSmLog(sl);
					} catch (Exception e) {
						errNum++;
						logger.error("短信日志备份异常：id:"+id +" error:"+e.getMessage());
					}
				}
			}
		}while(ls!=null && ls.size()>0 && ls.size()==smlog.getPageSize());
		logger.info(errNum);
		Date et = new Date();
		logger.info("备份历史短信结束>>>> "+et +" 总耗时 "+(et.getTime()-st.getTime())+" ms");
	}
	
}
