package com.ehaoyao.yhdjkg.task;

import java.util.Date;
import java.util.HashMap;
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
public class StockUpdTask extends TimerTask{
	static String orderFlag=BaseMap.getValue("channel_yhdcfy");
	private static String warehouseId =BaseMap.getValue("warehouseId");//仓库编号 
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StockUpdTask.class);

	/**
	 * 更新普通商品的库存
	 */
	public void run() {
		Date start_Time = new Date();
		logger.info("***"+orderFlag+" 库存回写开始时间:"+DateUtil.formatCurrentDateToStandardDate()+"***");
		int page = 1;
		int pageSize = 100;
		int pageCount = 0;
		Map<String, String> paramMap = BaseMap.getMap();
		paramMap.put("method", "yhd.general.products.search");
		//需要更新所有的库存
		//paramMap.put("canSale", "1");
		paramMap.put("pageRows", pageSize + "");
		do {
			try{
				String responseData =null;
				paramMap.put("curPage", page + "");
				responseData = PostClient.sendByPost(BaseMap.ROUTER_URL,
						paramMap, BaseMap.SECRET_KEY);
				if(null!=responseData && !"".equals(responseData)){
					JSONObject jo = new JSONObject();
					jo = JSON.parseObject(responseData).getJSONObject("response");
					int totalCount = jo.getIntValue("totalCount");
					logger.info(""+orderFlag+" 共有" + totalCount + "件商品信息");
					if (totalCount < 1)
						return;
					pageCount = totalCount / pageSize
							+ (totalCount % pageSize == 0 ? 0 : 1);
					JSONObject productList = jo.getJSONObject("productList");
					JSONArray ja = productList.getJSONArray("product");
					System.out.println("ja==="+ja.toJSONString());
					if(null!=ja && ja.size()>0){
						Map<String, String> stockMap = BaseMap.getMap();
						stockMap.put("method", "yhd.products.stock.update");
						stockMap.put("updateType", "1");
						
						StringBuilder stockString = new StringBuilder();
						for (int i = 1; i <= ja.size(); i++) {
							JSONObject product = ja.getJSONObject(i - 1);
							if (product == null)
								continue;
							String outerId = product.getString("outerId");
							
							if (outerId != null && !"".equals(outerId) && !"null".equals(outerId)) {
//								Product pro = (Product) VersionCachePool.getObject(outerId.trim()); //获取非处方药商品
								Product pro = null;
								Product[] productStockList = (Product[]) VersionCachePool.getObject("HYSGW_CFY");//获取处方药商品
								for (Product por2 : productStockList) {
									if (por2.getOuterid() != null&& por2.getOuterid().trim().equals(outerId.trim())) {
										pro = por2;
										break;
									}
								}
									
								//如果缓存里没有数据，打印日志
								if( pro == null ){
									logger.info("九州通商品ID: " + outerId + " 在缓存里没有获取到库存信息");
									//如果不是五十的倍数或者最后一条数据
									if( i % 50 != 0 || i != ja.size() )
										continue;
								}else{
									int stock = pro.getStock() > 999 ? 999 : pro.getStock();
									String outerStock = outerId.trim() + ":" + warehouseId + ":" + stock;
									stockString.append("," + outerStock);
									/*try{
										Map<String, String> outerStockMap = BaseMap.getMap();
										outerStockMap.put("method", "yhd.products.stock.update");
										outerStockMap.put("updateType", "1");
										if(null!=outerId && !"".equals(outerId)){
											
											outerStockMap.put("outerStockList", outerStock);
											String result = PostClient.sendByPost(BaseMap.ROUTER_URL,
													outerStockMap, BaseMap.SECRET_KEY);
											logger.info("调用一号店更新单个产品库存，outerId:" + outerId + ",outerStock:"+outerStock+"，处理结果:" + result);
										}
									}catch(Exception e){
										e.printStackTrace();
										logger.info("调用一号店更新单个产品库存出现异常！");
										continue;
									}
									*/
									
								}
								//如果是五十的倍数或者是最后一条数据而且库存信息部为空调用更新库存接口
								if( (i % 50 == 0 || i == ja.size()) && stockString.length() > 0 ){
									stockMap.put("outerStockList", stockString.toString().substring(1));
									logger.info("调用一号店更新库存接口处理数据信息:" + stockString.toString().substring(1));
									String res = null;
									try{//for循环内部捕获异常的目的，保证循环过程中有任意执行出现问题
										res = PostClient.sendByPost(BaseMap.ROUTER_URL,
												stockMap, BaseMap.SECRET_KEY);
										// 向1号店发送请求出现异常
										if(null!=res && "PostClient_Exception".equals(res)){
											logger.error("调用一号店批量更新库存接口，当前执行第" + page + "页，发送请求出现异常：" + res);
											continue;
										}
										logger.info("调用一号店批量更新库存接口，当前执行第" + page + "页返回信息:" + "--" + res);
										// 添加判断，如果返回结果中有处理失败信息，则执行单个更新库存
										JSONObject obj = JSONObject.parseObject(res);
										obj = (JSONObject) obj.get("response");
										// 获取指更新出错数量
										String errorCount = String.valueOf(obj.get("errorCount"));
										if(null!=errorCount && !"".equals(errorCount) && Integer.parseInt(errorCount)>0 ){
											singleStock(res, stockString.toString()); }
										
									}catch(Exception e){
										e.printStackTrace();
										logger.error("一号店批量更新库存异常信息e：" + e);
										// 添加判断，如果返回结果中有处理失败信息，则执行单个更新库存
										JSONObject obj = JSONObject.parseObject(res);
										obj = (JSONObject) obj.get("response");
										// 获取指更新出错数量
										String errorCount = String.valueOf(obj.get("errorCount"));
										if(null!=errorCount && !"".equals(errorCount) && Integer.parseInt(errorCount)>0 )
											singleStock(res, stockString.toString()); 
										logger.error("调用一号店批量更新库存接口出错，第" + page + "页，错误信息：" + e.toString());
									}
									stockString.delete(0,stockString.length());
									//stockMap.clear();
									// 每执行完成一页100条产品更新，进程休眠0.5秒
								}
							}
						}
						page++;
					}
				}else{
					logger.info(""+orderFlag+" 获取1号店第"+page+"页的产品数据，返回结果为空：" + responseData);
				}
			}catch( Exception e ){
				logger.error(""+orderFlag+" 更新库存出现未知异常----------------------------------------");
				e.printStackTrace();
				logger.error(""+orderFlag+" 更新库存出现未知异常:" + e);
				continue;
			}
		} while (page <= pageCount);
		logger.info("***"+orderFlag+" 库存回写结束时间:"+DateUtil.formatCurrentDateToStandardDate()+",共耗时:"+(new Date().getTime() - start_Time.getTime())+"ms***");
	}
	
	/**
	 * 单个更新库存
	 * res:批量处理库存更新失败返回信息
	 * 结构如（缩减版）：{'response':{'updateCount':50,'errorCount':50,'errInfoList':{'errDetailInfo':[{'pkInfo':'PKF203036X'},{'pkInfo':'PKF203035X'}]}}}；
	 * stockString：一号店要求封装的指更新库存数据信息，结构如：PKF203036X:7942:999,PKF203035X:7942:999
	 */
	public void singleStock(String res, String stockString){
		try{
			if(null!=res && !"".equals(res) && null!=stockString && !"".equals(stockString)){
				// 获取商品库存信息
				String[] stocks = stockString.split(",");
				Map<String, String> sMap = new HashMap<String, String>();
				if(null!=stocks && stocks.length>0){
					for(int j=0; j<stocks.length; j++){
						if(null!=stocks[j] && !"".equals(stocks[j])){
							String outerId_key = stocks[j].split(":").length>1?stocks[j].split(":")[0]:null;
							sMap.put(outerId_key, stocks[j]);
						}
					}
				}
				
				Map<String, String> stockMap = BaseMap.getMap();
				stockMap.put("method", "yhd.products.stock.update");
				stockMap.put("updateType", "1");
				
				// 解析要更新的商品信息，获取对应的库存信息，执行更新； 
				JSONObject obj = JSONObject.parseObject(res);
				obj = (JSONObject) obj.get("response");
				// 获取指更新出错数量
				String errorCount = String.valueOf(obj.get("errorCount"));
				if(null!=errorCount && !"".equals(errorCount) && Integer.parseInt(errorCount)>0){
					JSONObject errInfoList = (JSONObject)obj.get("errInfoList");
					if(null!=errInfoList){// 错误信息不为空
						JSONArray array = (JSONArray) errInfoList.get("errDetailInfo");
						if(null!=array && array.size()>0){
							for(int i=0; i<array.size(); i++){
								JSONObject pk = (JSONObject)array.get(i);
								
								String outerId = String.valueOf(pk.get("pkInfo")); //获取
								if(null!=outerId && !"".equals(outerId)){
									
									stockMap.put("outerStockList", sMap.get(outerId));
									String result = PostClient.sendByPost(BaseMap.ROUTER_URL,
											stockMap, BaseMap.SECRET_KEY);
									// 向1号店发送请求出现异常
									if(null!=result && "PostClient_Exception".equals(result)){
										logger.info("调用一号店批量更新库存接口，当前执行第" + result + "页，发送请求出现异常：" + res);
										continue;
										
									}
									logger.info("调用一号店更新单个产品库存，第" + (i+1) + "个/共" + array.size() + "个，" + sMap.get(outerId) + "的库存信息，处理结果:" + result);
								}
							}
						}else{
							logger.info("一号店单个更新库存，返回错误详情：errDetailInfo为null！");
						}
					}else{
						logger.info("一号店单个更新库存，返回错误信息：errInfoList为null！");
					}
				}
			}else{
				logger.info("一号店单个更新库存，参数为null！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("一号店单个更新库存，出现异常……" + e);
		}
	}

	/**
	 * 获取仓库信息，暂时不用
	 */
	public void getWarehouseId() {
		Map<String, String> paramMap = BaseMap.getMap();
		paramMap.put("method", "yhd.logistics.warehouse.info.get");
		String responseData = PostClient.sendByPost(BaseMap.ROUTER_URL,
				paramMap, BaseMap.SECRET_KEY);
		if (logger.isInfoEnabled()) {
			logger.info("getWarehouseId() - String responseData=" + responseData); //$NON-NLS-1$
		}
	}
}
