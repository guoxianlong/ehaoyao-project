package com.ehaoyao.opertioncenter.decoctOrder.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.decoctOrder.service.IDecoctOrderService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.haoyao.goods.action.BaseAction;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/decoctOrder.do")
public class DecoctOrderAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(DecoctOrderAction.class);
	@Autowired
	private IDecoctOrderService iDecoctOrderService;
	
	/**
	 * 获取订单信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=showOrderInfos"))
	public String showOrderInfos(HttpServletRequest request,HttpSession session, ModelMap modelMap,ThirdOrderAuditVO vo){
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("actionName", ac);
		
		if(vo==null){
			vo = new ThirdOrderAuditVO();
		}
		//开始时间
		String startDate = vo.getStartDate();
		if(startDate!=null && startDate.trim().length()>0){
			vo.setStartDate(startDate.trim()+" 00:00:00");
		}
		//截止时间
		String endDate = vo.getEndDate();
		if(endDate!=null && endDate.trim().length()>0){
			vo.setEndDate(endDate.trim()+" 23:59:59");
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
		PageModel<OrderInfo> pm = new PageModel<OrderInfo>();
		pm.setPageSize(this.getPageSize());
		pm.setPageNo(this.getPageno());
		try {
			//订单查询
			pm = iDecoctOrderService.getOrderInfos(pm,vo);
			List<OrderInfo> orderList = pm.getList();
			if (orderList != null && orderList.size() > 0) {
				modelMap.put("orderList", orderList);
			}
			modelMap.put("pageno", pm.getPageNo());
			modelMap.put("pageTotal", pm.getTotalPages());
			modelMap.put("pageSize", pm.getPageSize());
			modelMap.put("recTotal", pm.getTotalRecords());
		} catch (Exception e) {
			logger.error("订单查询异常！orderNumber:",e);
		}
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		modelMap.put("vo", vo);
		return "opcenter/decoctOrder/decoctOrderInfo";
	}
	
	/**
	 * 根据订单号查找商品明细
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=getOrderDetails"))
	public void getOrderDetails(HttpServletRequest request,PrintWriter printWriter,ThirdOrderAuditVO vo){
		List<OrderDetail> list;
		String result = "";
		try {
			list = iDecoctOrderService.getOrderDetails(vo);
			if(list!=null && list.size()>0){
				JSONArray jsonList= JSONArray.fromObject(list);
				result = jsonList.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printWriter.write(result);	
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 保存更新订单状态
	 * @param request
	 * @param printWriter
	 * @param vo
	 */
	@RequestMapping(params = ("method=updOrderStatus"))
	public void updOrderStatus(HttpServletRequest request,PrintWriter printWriter,ThirdOrderAuditVO vo){
		String[] orderNumberParam = request.getParameterValues("arrParam")!=null ? (String[])request.getParameterValues("arrParam") :null;
		String result  = "";
		try {
			if(orderNumberParam!=null){
				result  = iDecoctOrderService.updateOrderStatus(orderNumberParam);
			}else{
				result = "请选择处理结果！";
			}
		} catch (Exception e) {
			result = "程序处理异常！";
			e.printStackTrace();
		}
		printWriter.write(result);	
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * 获取订单详情页信息
	 * @param request
	 * @param session
	 * @param modelMap
	 * @param vo
	 * @return
	 */
	@RequestMapping(params = ("method=getOrderDetailPage"))
	public void getOrderDetailPage(HttpServletRequest request,PrintWriter printWriter,ThirdOrderAuditVO vo){
		String orderNumber = "";
		List<OrderDetail> list;
		String orderDetailsJsonArr = "";
		JSONObject json = new JSONObject();
		try {
			if(vo==null){
				vo = new ThirdOrderAuditVO();
			}
			orderNumber = vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"";
			OrderInfo orderInfo = (OrderInfo) iDecoctOrderService.getOrderInfosByOrderNums(vo);
			list = iDecoctOrderService.getOrderDetails(vo);
			if(list!=null && list.size()>0){
				JSONArray jsonList= JSONArray.fromObject(list);
				orderDetailsJsonArr = jsonList.toString();
				json.put("orderDetails", orderDetailsJsonArr);
			}
			if(orderInfo!=null){
				JSONObject orderInfoJson = (JSONObject) JSONObject.toJSON(orderInfo);
				json.put("orderInfo", orderInfoJson.toJSONString());
			}
			printWriter.write(json.toJSONString());
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			logger.error("查询订单异常！订单号："+orderNumber,e);
		}
	}
	
	/**
	 * 打印订单信息，可打印多单
	 * @param request
	 * @param session
	 * @param modelMap
	 * @param vo
	 * @return
	 */
	@RequestMapping(params = ("method=printOrders"))
	public void printOrders(HttpServletRequest request,HttpServletResponse response,ThirdOrderAuditVO vo){
		JSONArray jsonArr = new JSONArray();
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8"); 
			jsonArr = iDecoctOrderService.getOrderInfos(vo);
			String jsonStr = jsonArr.toString();
			response.getOutputStream().write(jsonStr.getBytes("UTF-8"));
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("打印订单异常！",e);
		}
	}
	
	
}
