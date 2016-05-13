package com.ehaoyao.yhdjkg.task;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.yhdjkg.domain.Product;
import com.ehaoyao.yhdjkg.utils.BaseMap;
import com.ehaoyao.yhdjkg.utils.DateUtil;
import com.ehaoyao.yhdjkg.utils.PostClient;
import com.ehaoyao.yhdjkg.utils.VersionCachePool;
/**
 * 
 * @author wls
 * @date 2016-04-08
 *
 */
public class VirtualStockTask extends TimerTask{
	static String orderFlag=BaseMap.getValue("channel_yhdcfy");
	private static String warehouseId =BaseMap.getValue("warehouseId");//仓库编号 
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VirtualStockTask.class);
	
	
	public void run(){
		Date start_Time = new Date();
		logger.info("***"+orderFlag+" 库存回写开始时间:"+DateUtil.formatCurrentDateToStandardDate()+"***");
		int page = 1;
		int pageSize = 50;
		int pageCount = 0;
		Map<String, String> paramMap = BaseMap.getMap();
		paramMap.put("method", "yhd.serial.products.search");//需要更新所有的库存
		paramMap.put("canSale", "1");
		paramMap.put("pageRows", pageSize + "");
		do {
			try {
				pageCount=this.updateProductListStock(page, pageSize);
				if(pageCount<0)
					break;
				logger.info("第"+page+"页商品更新完成，30s后更新第下一页");
				page++;
				Thread.sleep(30*1000);//延迟30秒
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("出错了，错误信息："+e);	
			}
		} while (page <= pageCount);
		
		logger.info("***"+orderFlag+" 库存回写结束时间:"+DateUtil.formatCurrentDateToStandardDate()+",共耗时:"+(new Date().getTime() - start_Time.getTime())+"ms***");
	}
	
	
	/**
	 * 更新库存信息
	 * return  总页数
	 */
	private int updateProductListStock(int page,int pageSize) {
		int pageCount = 0;//总页数
		Map<String, String> paramMap = BaseMap.getMap();
		paramMap.put("method", "yhd.serial.products.search");//需要更新所有的库存
		paramMap.put("canSale", "1");
		paramMap.put("pageRows", pageSize + "");
		paramMap.put("curPage", page + "");
		String responseData = PostClient.sendByPost(BaseMap.ROUTER_URL,paramMap, BaseMap.SECRET_KEY);
		logger.info("查询商品信息返回结果："+responseData);
		if(null!=responseData && !"".equals(responseData)){
			JSONObject jo =JSON.parseObject(responseData).getJSONObject("response");
			int totalCount = jo.getIntValue("totalCount");
			logger.info(orderFlag+"   第"+page+"页。共有" + totalCount + "件商品信息");
			if(totalCount>0){
				JSONArray ja = jo.getJSONObject("serialProductList").getJSONArray("serialProduct");//获取返回结果中的 主商品信息列表
				if(null!=ja && ja.size()>0){
					for (int i = 0; i < ja.size(); i++) {
						try {
							JSONObject obj=ja.getJSONObject(i);
							String productId=obj.getString("productId");
							if(productId!=null&&!"".equals(productId)&&!"null".equals(productId.toLowerCase())){
								String updateStockMessage=this.getChildProdByProductId(productId);//通过主商品信息获取对应的所有子商品信息
								if(updateStockMessage.length()>0){
									this.updateStock(updateStockMessage.toString().substring(1),page,productId);//更新产品ID的所有子商品的库存  （ 去掉字符串前面的 ，）
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
								
						}
					}
				}
			}
			pageCount= totalCount / pageSize+ (totalCount % pageSize == 0 ? 0 : 1);
		}else{
			logger.error("查询第"+page+"页商品没有返回信息");
		}
		return pageCount; 
	}
	
	
	/**
	 * 通过主商品id，获取所有子商品
	 * reutrn  所有子商品的外部商品编号等拼接的字符串
	 */
	private String getChildProdByProductId(String productId){
//		if(!"44078281".equals(productId)){
//			return "";
//		}//测试 用 
		StringBuilder productsStock = new StringBuilder();
		Map<String, String> childMap = BaseMap.getMap();
		childMap.put("method", "yhd.serial.product.get");
		childMap.put("productId", productId);
		String childResult= PostClient.sendByPost(BaseMap.ROUTER_URL,childMap, BaseMap.SECRET_KEY);//发送请求  通过主商品获取所有子商品
		logger.info("主商品编号："+productId+"     对应的子商品信息："+childResult);
		JSONObject obj=JSON.parseObject(childResult).getJSONObject("response");
		JSONArray productList=obj.getJSONObject("serialChildProdList").getJSONArray("serialChildProd");  //或获取子商品的数组
		for (int i = 0; i < productList.size(); i++) {
			JSONObject product = productList.getJSONObject(i);
			String outerId=product.getString("outerId");
			if (outerId != null && !"".equals(outerId) && !"null".equals(outerId)) {
				String productMessage=this.getProdcutMessageByOuterID(outerId.trim());// 返回单个商品的信息
				if(productMessage!=null&&!"".equals(productMessage)){
					productsStock.append(productMessage);
				}
			}
		}
		return productsStock.toString();
	}
	
	/**
	 * 
	 * @Title: getProdcutMessageByOuterID
	 * @return 返回  外部商品编号库存等信息组合的字符串
	 */
	private String getProdcutMessageByOuterID(String outerId){
		StringBuilder stockString = new StringBuilder();
//		Product pro = (Product) VersionCachePool.getObject(outerId.trim());  //获取非处方药商品
		Product pro = null;
		Product[] productStockList = (Product[]) VersionCachePool.getObject("HYSGW_CFY");//获取处方药商品
		for (Product por2 : productStockList) {
			if (por2.getOuterid() != null&& por2.getOuterid().trim().equals(outerId.trim())) {
				pro = por2;
				break;
			}
		}
//		Product pro=new Product(); pro.setStock(100);//测试用
		
		//如果缓存里没有数据，打印日志
		if( pro == null ){
			logger.info("九州通商品ID: " + outerId + " 在缓存里没有获取到库存信息");
		}else{
			int stock = pro.getStock() > 999 ? 999 : pro.getStock();
			String outerStock = outerId+ ":" + warehouseId + ":" + stock;
			stockString.append("," + outerStock);
		}
		return stockString.toString();
	}
	
	private void updateStock(String message,int page,String productId){
		logger.info("批量更新虚拟库存的字符串信息："+message);
		Map<String, String> stockMap = BaseMap.getMap();
		stockMap.put("method", "yhd.serial.products.stock.update");
		stockMap.put("updateType", "1");
		stockMap.put("outerStockList", message);
		stockMap.put("productId", productId);
		String res = PostClient.sendByPost(BaseMap.ROUTER_URL,stockMap, BaseMap.SECRET_KEY);
//		String res="";//测试
		if(res==null||"".equals(res)){
			logger.info("批量更新虚拟商品第" + page + "页的主商品的productId为"+productId+"的商品，请求出现异常：" + res);
		}else{
			logger.info("批量更新虚拟商品第" + page + "页的主商品的productId为"+productId+"的商品，返回信息："+res);
			JSONObject obj = JSONObject.parseObject(res).getJSONObject("response");
			String errorCount = String.valueOf(obj.get("errorCount"));// 获取指更新出错数量
			if(null!=errorCount && !"".equals(errorCount) && Integer.parseInt(errorCount)>0 ){
				logger.info("批量更新虚拟商品第" + page + "页的主商品的productId为"+productId+"的商品，一共有"+errorCount+"个更新失败！");
			}
		}
	}
}
