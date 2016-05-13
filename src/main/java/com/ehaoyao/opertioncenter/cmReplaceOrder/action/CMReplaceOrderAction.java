package com.ehaoyao.opertioncenter.cmReplaceOrder.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.ehaoyao.opertioncenter.cmReplaceOrder.service.ICMReplaceOrderService;
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.haoyao.goods.action.BaseAction;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/CMReplaceOrder.do")
public class CMReplaceOrderAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(CMReplaceOrderAction.class);
	@Autowired
	private ICMReplaceOrderService iCMReplaceOrderService;
	
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(vo==null){
			vo = new ThirdOrderAuditVO();
		}
		//开始时间
		String startDate = vo.getStartDate();
		if(startDate!=null && startDate.trim().length()>0){
			vo.setStartDate(startDate.trim()+" 00:00:00");
		}else{
			startDate = sdf.format(new Date());
			vo.setStartDate(startDate + " 00:00:00");
		}
		//截止时间
		String endDate = vo.getEndDate();
		if(endDate!=null && endDate.trim().length()>0){
			vo.setEndDate(endDate.trim()+" 23:59:59");
		}else{
			endDate = sdf.format(new Date());
			vo.setEndDate(endDate + " 23:59:59");
		}
		//审核状态
		if(vo.getAuditStatus() == null || vo.getAuditStatus().length()==0){
			vo.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_WAIT);
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
			pm = iCMReplaceOrderService.getOrderInfos(pm,vo);
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
		return "opcenter/CMReplaceOrder/CMReplaceOrderInfo";
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
			list = iCMReplaceOrderService.getOrderDetails(vo);
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
			OrderInfo orderInfo = (OrderInfo) iCMReplaceOrderService.getOrderInfosByOrderNums(vo);
			list = iCMReplaceOrderService.getOrderDetails(vo);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(params = ("method=printOrders"))
	public void printOrders(HttpServletRequest request,PrintWriter printWriter,ThirdOrderAuditVO vo){
		String orderNumbers = "";
		List<OrderInfo> orderInfoList;
		JSONObject json = new JSONObject();
		try {
			if(vo==null){
				vo = new ThirdOrderAuditVO();
			}
			orderNumbers = vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"";
			orderInfoList = (List<OrderInfo>) iCMReplaceOrderService.getOrderInfosByOrderNums(vo);
			JSONArray jsonList= JSONArray.fromObject(orderInfoList);
			json.put("orderInfo", jsonList.toString());
			printWriter.write(json.toJSONString());
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			logger.error("打印订单异常！订单号："+orderNumbers,e);
		}
	}
	
	/**
	 * 订单审核
	 * @param request
	 * @param session
	 * @param modelMap
	 * @param vo
	 * @return
	 */
	@RequestMapping(params = ("method=auditOrder"))
	public void auditOrder(HttpServletRequest request,PrintWriter printWriter,ThirdOrderAuditVO vo){
		try {
			if(vo==null){
				vo = new ThirdOrderAuditVO();
			}
			String rtnStr = iCMReplaceOrderService.updateAuditStatus(vo);
			printWriter.write(rtnStr);
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			logger.error("审核订单异常！订单号："+vo.getOrderNumber(),e);
		}
	}
	
}
