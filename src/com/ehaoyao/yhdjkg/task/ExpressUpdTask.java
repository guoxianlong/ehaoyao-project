package com.ehaoyao.yhdjkg.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.yhdjkg.utils.BaseMap;
import com.ehaoyao.yhdjkg.utils.DBUtil;
import com.ehaoyao.yhdjkg.utils.DateUtil;
import com.ehaoyao.yhdjkg.utils.PostClient;
/**
 * 
 * @author wls
 * @date 2016-04-08
 *
 */
public class ExpressUpdTask extends TimerTask{
	static String orderFlag=BaseMap.getValue("channel_yhdcfy");
	static Logger logger = Logger.getLogger(ExpressUpdTask.class);
	
	/**
	 * 一号店发货任务
	 */
	public void run() {
		Date start_Time = new Date();
		logger.info("***"+orderFlag+" 物流信息回写1号店平台开始时间:"+DateUtil.formatCurrentDateToStandardDate()+"***");
		Map<String, String> map = BaseMap.getMap();
		map.put("method", "yhd.logistics.order.shipments.update");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long order_time = 0l;
		Integer order_count = 0;
		try {
			con = DBUtil.getConnection();
			String sql = "select express.order_number,express.express_com_id,express.express_id from express_info express join order_info info on express.order_number = info.order_number where info.order_status = 's01'  and info.order_flag = '"+orderFlag+"' ";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			String tid = "";
			while (rs.next()) {
				try{
					tid = rs.getString("order_number");//订单编号
					String express_com_id = rs.getString("express_com_id").trim();//物流公司编号
					String express_id = rs.getString("express_id").trim();//运单号
					if(express_com_id.equals("1748")){express_com_id="1760";}
					if(express_com_id.equals("470")){express_com_id="1757";}
					if(express_com_id.equals("463")){express_com_id="1755";}
					map.put("orderCode", tid);
					map.put("deliverySupplierId", express_com_id);
					map.put("expressNbr", express_id);
//					logger.info(tid + "发货成功");
					String result = PostClient.sendByPost(BaseMap.ROUTER_URL, map,BaseMap.SECRET_KEY);
					if( result == null || "".equals(result)  ){
						logger.info(" 一号店发货接口出现异常：" + "订单号:" + tid + "，配送商ID:" + express_com_id + "，运单号：" + express_id);
						continue;
					}
					JSONObject resultJson = JSON.parseObject(result);
					int flag = result.indexOf("订单状态异常");
					if( resultJson != null ){
						int resultCount = resultJson.getJSONObject("response").getIntValue("updateCount");
						if( resultCount > 0 || flag != -1 ){
							sql = "update order_info set order_status = 's02' where order_number = '" + tid + "' and order_flag = '"+orderFlag+"' ";
							ps = con.prepareStatement(sql);
							Date now_time = new Date();
							int count = ps.executeUpdate();
							order_time += new Date().getTime() - now_time.getTime();
							order_count++;
							if( count > 0 ){
								logger.info("订单号 " + tid + "发货成功，更改数据库状态s02成功");
							}else{
								logger.info("订单号 " + tid + "发货成功，更改数据库状态s02失败");
							}
						}else{
							logger.error("订单号:" + tid + "发货失败，" + "配送商ID:" + express_com_id + "，运单号：" + express_id + "错误信息:" + resultJson.toString());
						}
					}
				}catch( Exception e ){
					e.printStackTrace();
					logger.error("订单号:" + tid + " 发货时出现异常，跳过此条信息，请检查订单状态");
					continue;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("一号店发货定时任务出现异常，请参考异常信息");
		} finally {
			DBUtil.closeAll(con, ps, rs);
		}
		if( order_count > 0 ){
			logger.info("共更新了" + order_count + "条数据,每条数据约耗时" + ( order_time / order_count ) + "ms" );
		}
		logger.info("***"+orderFlag+" 物流信息回写1号店平台结束时间:"+DateUtil.formatCurrentDateToStandardDate()+",共耗时:"+(new Date().getTime() - start_Time.getTime())+"ms***");
	}

	
	/**
	 * 获取1号店合作物流公司信息（1号店自配送、3PL等）
	 * 目前不用此方法
	 */
	/*
	public void saveLogistics() {
		Map<String, String> map = BaseMap.getMap();
		map.put("method", "yhd.logistics.deliverys.company.get");

		String json = PostClient.sendByPost(BaseMap.ROUTER_URL, map,
				BaseMap.SECRET_KEY);
		JSONObject jo = JSON.parseObject(json);
		JSONArray ja = jo.getJSONObject("response").getJSONObject(
				"logisticsInfoList").getJSONArray("logisticsInfo");
		int count = ja.size();
		for (int i = 0; i < count; i++) {
			JSONObject lo = ja.getJSONObject(i);
			String id = lo.getString("id");
			String logistics_name = lo.getString("companyName");
			logistics_name = logistics_name.substring(0, logistics_name
					.length() - 4);
			String sql = "INSERT logistics (logistics_id,logistics_name,shopAlias) values("
					+ id + ",'" + logistics_name + "','yhd')";
			// DBUtil.executeUpdate(sql);
		}
	}
	*/
}
