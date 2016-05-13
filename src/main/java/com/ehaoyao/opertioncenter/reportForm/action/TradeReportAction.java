package com.ehaoyao.opertioncenter.reportForm.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.BuyRecordVO;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.util.SignUtils;

/**
 * 
 * Title: TradeReportAction.java
 * 
 * Description: 购买记录报表
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年10月11日 下午3:30:53
 */
@Controller
@RequestMapping("/tradeReport.do")
public class TradeReportAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(TradeReportAction.class);
	
	private RestTemplate restTemplate = new RestTemplate();
	private String buyUrl = PropertiesUtil.getProperties("extend.properties", "buyUrl");
	private String buySign = PropertiesUtil.getProperties("extend.properties", "buySign");
	
	/**
	 * 购买记录
	 */
	@RequestMapping(params = ("method=getTradeRecords"))
	public String getTradeRecords(HttpServletRequest request, ModelMap modelMap, BuyRecordVO buyVO) {
		try {
			String qFlag = request.getParameter("qFlag");
			if(!"1".equals(qFlag)){
				modelMap.put("qFlag", "0");
				return "opcenter/reportForm/trade_report";
			}else{
				modelMap.put("qFlag", "1");
			}
			
			String userName = "";
			String phone = "";
			String timeNum = "";
			String productNo = "";
			if(buyVO!=null){
				//用户名
				userName = buyVO.getUserName()!=null?buyVO.getUserName().trim():"";
				buyVO.setUserName(userName);
				//手机号
				phone = buyVO.getTelephoneNo()!=null?buyVO.getTelephoneNo().trim():"";
				buyVO.setTelephoneNo(phone);
				//期间 0:全部  ，  默认 3 
				timeNum = buyVO.getTimeNum()!=null&&buyVO.getTimeNum().trim().length()>0 ? buyVO.getTimeNum().trim():"3";
				buyVO.setTimeNum(timeNum);
				//商品编码
				productNo = buyVO.getProductNo()!=null?buyVO.getProductNo().trim():"";
				buyVO.setProductNo(productNo);
			}
			
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			String total = request.getParameter("recTotal");
			
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			
			if( pageSize == null || "".equals(pageSize)){
				this.setPageSize(20);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			if(total != null && !"".equals(total.trim())){
				int t = Integer.parseInt(total);
				int tPage = t / this.getPageSize();
				tPage = t % this.getPageSize() == 0 ? tPage : (tPage + 1);
				if( this.getPageno() > tPage ){
					this.setPageno(tPage);
				}
			}
			
			/**对请求加密*/
			Map<String, String> map = new HashMap<String, String>();
			//参与签名的参数
			map.put("userName",userName);
			map.put("telephoneNo", phone);
			map.put("timeNum", timeNum);
			map.put("productNo", productNo);
			map.put("pageNo", this.getPageno()+"");
			map.put("pageSize", this.getPageSize()+"");
			HashMap<String, String> signMap = SignUtils.sign(map, buySign);
			String sign = signMap.get("appSign");
			
			/**设定参数*/
			Map<String, String> Stringiables = new HashMap<String, String>(); 
			Stringiables.put("userName",userName);
			Stringiables.put("telephoneNo", phone);
			Stringiables.put("timeNum",timeNum);
			Stringiables.put("productNo", productNo);
			Stringiables.put("pageNo", this.getPageno()+"");
			Stringiables.put("pageSize", this.getPageSize()+"");
			Stringiables.put("sign", sign);
			
			try {
				//查询购买记录
				String res = "[]";
				res = restTemplate.getForObject(buyUrl, String.class, Stringiables);
				if(res!=null && res.trim().length()>0){
					JSONObject json = JSONObject.fromObject(res);
					Object ordersObj = json.get("orderList");
					if(ordersObj!=null){
						String s = ordersObj.toString();
						if(s.trim().length()>0){
							JSONArray orderList = JSONArray.fromObject(s);
							modelMap.put("bLs",orderList);
						}
					}
					//记录总数
					recTotal = json.getInt("totalRecords");
					pageTotal = recTotal / this.getPageSize();
					pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
					if( this.getPageno() > pageTotal ){
						this.setPageno(1);
					}
					modelMap.put("pageno", this.getPageno());
					modelMap.put("pageTotal", pageTotal);
					modelMap.put("pageSize", this.getPageSize());
					modelMap.put("recTotal", recTotal);
				}
			} catch (Exception e) {
				logger.error("获取购买记录失败：", e);
			}
		} catch (Exception e) {
			logger.error("获取购买记录失败：", e);
		}
		modelMap.put("bvo",buyVO);
		
		return "opcenter/reportForm/trade_report";
	}
}
