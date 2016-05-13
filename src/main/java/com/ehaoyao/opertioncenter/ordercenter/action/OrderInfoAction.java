package com.ehaoyao.opertioncenter.ordercenter.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.common.PropertiesUtil;
import com.ehaoyao.opertioncenter.ordercenter.orderModel.OrderDetail;
import com.ehaoyao.opertioncenter.ordercenter.service.OrderInfoService;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderDataVO;
import com.ehaoyao.opertioncenter.ordercenter.vo.OrderParamVO;
import com.haoyao.goods.action.BaseAction;

/**
 * 
 * Title: OrderInfoAction.java
 * 
 * Description: 订单中心订单查询
 * 
 * @author xcl
 * @version 1.0
 * @created 2015年1月22日 下午4:37:21
 */
@Controller
@RequestMapping("/orderInfo.do")
public class OrderInfoAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(OrderInfoAction.class);
	
	/**极速达订单发货接口*/
	private String JSD_FH_URL = PropertiesUtil.getProperties("extend.properties", "JSD_FH_URL");
	/**极速达订单驳回接口*/
	private String JSD_BH_URL = PropertiesUtil.getProperties("extend.properties", "JSD_BH_URL");
	
	private RestTemplate restTemplate = new RestTemplate();
	
	//订单信息
	@Autowired
	private OrderInfoService orderInfoService; 

	/**
	 * 查询订单相关信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=getInfo"))
	public String getInfo(HttpServletRequest request, ModelMap modelMap,OrderParamVO orderParam) {
		if(orderParam==null){
			orderParam = new OrderParamVO();
		}
		//截止时间
		String endDate = orderParam.getEndDate();
		if(endDate!=null && endDate.trim().length()>0){
			orderParam.setEndDate(endDate.trim()+" 23:59:59");
		}
		String pageno = request.getParameter("pageno");
		String pageSize = request.getParameter("pageSize");
		try {
			if (pageno == null || "".equals(pageno)) {
				this.setPageno(1);
			} else {
				this.setPageno(Integer.parseInt(pageno));
				if (this.getPageno() < 1) {
					this.setPageno(1);
				}
			}
			if (pageSize == null || "".equals(pageSize)) {
				this.setPageSize(5);
			} else {
				this.setPageSize(Integer.parseInt(pageSize));
			}
		} catch (Exception e) {
			this.setPageno(1);
			this.setPageSize(5);
		}
		PageModel<OrderDataVO> pm = new PageModel<OrderDataVO>();
		pm.setPageSize(this.getPageSize());
		pm.setPageNo(this.getPageno());
		try {
			//订单查询
			pm = orderInfoService.getOrderData(pm,orderParam);
			List<OrderDataVO> orderInfoLs = pm.getList();
			if (orderInfoLs != null && orderInfoLs.size() > 0) {
				modelMap.put("orderInfoLs", orderInfoLs);
			}
			modelMap.put("pageno", pm.getPageNo());
			modelMap.put("pageTotal", pm.getTotalPages());
			modelMap.put("pageSize", pm.getPageSize());
			modelMap.put("recTotal", pm.getTotalRecords());
		} catch (Exception e) {
			logger.error("极速达订单信息查询异常！orderNumber:"+orderParam.getOrderNumber(),e);
		}
		
		orderParam.setEndDate(endDate);
		modelMap.put("orderData", orderParam);
		return "opcenter/orderInfo/orderInfo";
	}
	
	/**
	 * 查询订单相关信息
	 */
	@RequestMapping(params = ("method=getDetail"))
	@ResponseBody
	public String getDetail(HttpServletRequest request, ModelMap modelMap,OrderParamVO orderParam) {
		if(orderParam!=null && orderParam.getOrderNumber()!=null && orderParam.getOrderNumber().trim().length()>0){
			try {
				//订单详情
				List<OrderDetail> orderDetailLs = orderInfoService.getOrderDetails(orderParam);
				if (orderDetailLs != null && orderDetailLs.size() > 0) {
					modelMap.put("detailLs", orderDetailLs);
					JSONArray ja = JSONArray.fromObject(orderDetailLs);
					return ja.toString();
				}
			} catch (Exception e) {
				logger.error("极速达订单详情查询异常！orderNumber:"+orderParam.getOrderNumber(),e);
			}
		}
		return null;
	}
	
	/**
	 * 极速达订单发货
	 */
	@RequestMapping(params = ("method=deliver"))
	@ResponseBody
	public Map<String,String> deliver(HttpServletRequest request, ModelMap modelMap) {
		Map<String,String> map = new HashMap<String,String>();
		String orderNumber = request.getParameter("orderNumber");
		String expressNo = request.getParameter("expressNo");
		String district = request.getParameter("district");
		if(orderNumber==null || (orderNumber=orderNumber.trim()).length()==0 
				|| expressNo==null || (expressNo=expressNo.trim()).length()==0){
			map.put("mesg", "订单号或运单号为空");
			return map;
		}
		String url = JSD_FH_URL + orderNumber+"/"+expressNo+"/"+district; 
		String result = "";
		try {
			/*
			 * 调用发货接口
			 * {"errorInfo":"发货失败","isSuccess":false}
			 */
			result = restTemplate.postForObject(url, null, String.class);
		} catch (Exception e) {
			logger.error("极速达订单发货接口调用失败：",e);
			map.put("mesg", "极速达订单发货接口访问失败！");
			return map;
		}
		
		try {
			if(result!=null && result.trim().length()>0){
				JSONObject js = JSONObject.fromObject(result.trim());
				if(js!=null){
					Object isSuccess = js.get("isSuccess");
					if(isSuccess!=null && "true".equals(isSuccess.toString())){
						map.put("mesg", "success");
						return map;
					}else if(js.get("errorInfo")!=null && js.get("errorInfo").toString().trim().length()>0){
						map.put("mesg", js.get("errorInfo").toString().trim());
						return map;
					}
				}
			}
		} catch (Exception e){
			logger.error("result:"+result+"  "+e.getMessage());
		}
		map.put("mesg", "error");
		return map;
	}
	
	/**
	 * 极速达订单驳回
	 */
	@RequestMapping(params = ("method=reject"))
	@ResponseBody
	public Map<String,String> reject(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			String orderNumber = request.getParameter("orderNumber");
			if(orderNumber==null || (orderNumber=orderNumber.trim()).length()==0){
				map.put("mesg","订单号为空");
				return map;
			}
			String url = JSD_BH_URL + orderNumber;
			String result = "";
			try {
				/*
				 * 调用订单驳回接口
				 * 失败返回：{"result":"fail"}
				 * 成功返回: {"result":"success"}
				 */
				result = restTemplate.postForObject(url, null, String.class);
			} catch (Exception e) {
				logger.error("极速达订单驳回接口调用失败：",e);
				map.put("mesg", "极速达订单驳回接口调用失败");
				return map;
			}
			
			try {
				if(result!=null && result.trim().length()>0){
					JSONObject js = JSONObject.fromObject(result.trim());
					if(js!=null){
						Object res = js.get("result");
						if(res!=null && res.toString().equals("success")){
							map.put("mesg", "success");
							return map;
						}
					}
				}
			} catch (Exception e){
				logger.error(e.getMessage());
			}
		} catch (Exception e) {
		}
		map.put("mesg", "error");
		return map;
	}
	
	/**
	 * 极速达新订单数量
	 */
	@RequestMapping(params = ("method=newOrder"))
	@ResponseBody
	public String newOrder(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		int count=0;
		try {
			//查询新订单数量
			count = orderInfoService.getNewOrderCount();
		} catch (Exception e) {
		}
		JSONObject json = new JSONObject();
		json.put("count", count);
		return json.toString();
	}
	
}
