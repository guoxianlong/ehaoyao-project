package com.ehaoyao.opertioncenter.thirdOrderAudit.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.service.IThirdOrderAuditService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.haoyao.goods.action.BaseAction;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/thirdOrderAudit")
public class ThirdOrderAuditAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(ThirdOrderAuditAction.class);
	
	@Autowired
	private IThirdOrderAuditService iThirdOrderAuditService;
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(params=("method=showOrderInfos"))
	public String showOrderInfos(HttpServletRequest request,HttpSession session, ModelMap modelMap,ThirdOrderAuditVO vo){
		try {
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			modelMap.put("actionName", ac);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (vo == null) {
				vo = new ThirdOrderAuditVO();
			}
			//开始时间
			String startDate = vo.getStartDate();
			if(startDate==null || startDate.trim().length()==0){
				Date dNow = new Date(); //当前时间
				Date dBefore = new Date();
				Calendar calendar = Calendar.getInstance(); //得到日历
				calendar.setTime(dNow);//把当前时间赋给日历
				calendar.add(calendar.MONTH, -3); //设置为前3月
				dBefore = calendar.getTime(); //得到前3月的时间
				startDate = sdf.format(dBefore);
			}
			vo.setStartDate(startDate.trim()+" 00:00:00");
			//截止时间
			String endDate = vo.getEndDate();
			if(endDate==null || endDate.trim().length()==0){
				endDate=sdf.format(new Date());
			}
			vo.setEndDate(endDate.trim()+" 23:59:59");

			if(vo.getAuditStatus() == null || vo.getAuditStatus().length()==0){
				vo.setAuditStatus(OrderInfo.ORDER_AUDIT_STATUS_WAIT);
			}
			/*if(vo.getOrderFlag()==null || vo.getOrderFlag().length()==0){
				vo.setOrderFlag(OrderInfo.ORDER_ORDER_FLAG_TMCFY);
			}*/
			
			String sort = request.getParameter("sort")!=null&&request.getParameter("sort").trim().length()>0?request.getParameter("sort").trim():"";
			String orderBy = request.getParameter("orderBy")!=null&&request.getParameter("orderBy").trim().length()>0?request.getParameter("orderBy").trim():"";
			vo.setSort(sort);
			vo.setOrderBy(orderBy);
			
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
			PageModel<OrderMainInfo> pm = new PageModel<OrderMainInfo>();
			pm.setPageSize(this.getPageSize());
			pm.setPageNo(this.getPageno());
			pm = iThirdOrderAuditService.getOrderInfos(pm, vo);
			List<OrderInfo> orderList = pm.getList();
			if (orderList != null && orderList.size() > 0) {
				modelMap.put("orderList", orderList);
			}
			vo.setStartDate(startDate);
			vo.setEndDate(endDate);
			modelMap.put("pageno", pm.getPageNo());
			modelMap.put("pageTotal", pm.getTotalPages());
			modelMap.put("pageSize", pm.getPageSize());
			modelMap.put("recTotal", pm.getTotalRecords());
			modelMap.put("vo", vo);
		} catch (Exception e) {
			logger.error("订单查询异常！异常信息:",e);
			e.printStackTrace();
		}
		return "opcenter/thirdOrderAudit/thirdOrderAudit";
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
			list = iThirdOrderAuditService.getOrderDetails(vo);
			if(list!=null && list.size()>0){
				JSONArray jsonList= JSONArray.fromObject(list);
				result = jsonList.toString();
			}
		} catch (Exception e) {
			result = "查询订单异常，请联系管理员！订单号："+vo.getOrderNumber();
			logger.error("查询订单异常，请联系管理员！订单号："+vo.getOrderNumber(),e);
			e.printStackTrace();
		}finally{
			try {
				printWriter.write(result);
				printWriter.flush();
				printWriter.close();
			} catch (Exception e2) {
				logger.error("输出流输出及关闭异常信息：",e2);
			}
		}
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
		String result = "";
		try {
			if(vo==null){
				vo = new ThirdOrderAuditVO();
			}
			orderNumber = vo.getOrderNumber()!=null?vo.getOrderNumber().trim():"";
			
			OrderMainInfo orderMainInfo = iThirdOrderAuditService.getOrderMainInfo(vo);
			if(orderMainInfo!=null){
				JSONObject orderInfoJson = (JSONObject) JSONObject.toJSON(orderMainInfo);
				json.put("orderMainInfo", orderInfoJson.toJSONString());
			}
			
			list = iThirdOrderAuditService.getOrderDetails(vo);
			if(list!=null && list.size()>0){
				JSONArray jsonList= JSONArray.fromObject(list);
				orderDetailsJsonArr = jsonList.toString();
				json.put("orderDetails", orderDetailsJsonArr);
			}
			result = json.toJSONString();
		} catch (Exception e) {
			result = "查询订单异常，请联系管理员！订单号："+orderNumber;
			logger.error("查询订单异常，请联系管理员！订单号："+orderNumber,e);
		}finally{
			try {
				printWriter.write(result);
				printWriter.flush();
				printWriter.close();
			} catch (Exception e2) {
				logger.error("输出流输出及关闭异常信息：",e2);
			}
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
		String rtnStr = "";
		Object[] retObj = new Object[2];
		try {
			synchronized (this) {
				if(vo==null){
					vo = new ThirdOrderAuditVO();
				}
				retObj = iThirdOrderAuditService.updateAuditStatus(vo);
				if(!((boolean) retObj[0])){
					rtnStr = (String) retObj[1];
				}
			}
		} catch (Exception e) {
			rtnStr = e.getMessage();
			if(rtnStr==null){
				rtnStr = "审核订单异常，请联系管理员！订单号："+vo.getOrderNumber();
			}
			logger.error("审核订单异常，请联系管理员！订单号："+vo.getOrderNumber(),e);
		}finally{
			try {
				printWriter.write(rtnStr);
				printWriter.flush();
				printWriter.close();
			} catch (Exception e2) {
				logger.error("输出流输出及关闭异常信息：",e2);
			}
		}
	}
	
	/**
	 * 批量审核通过订单
	 * @param request
	 * @param printWriter
	 * @param vo
	 */
	@RequestMapping(params = ("method=updateAuditStatusBatch"))
	public void updateAuditStatusBatch(HttpServletRequest request,PrintWriter printWriter,ThirdOrderAuditVO vo){
		String[] orderNumberFlagsParam = request.getParameterValues("arrParam")!=null ? (String[])request.getParameterValues("arrParam") :null;
		String result  = "";
		try {
			synchronized (this) {
				if(orderNumberFlagsParam!=null){
					result  = iThirdOrderAuditService.updateAuditStatusBatch(orderNumberFlagsParam);
				}else{
					result = "请先选择批量审批的订单！";
				}
			}
		} catch (Exception e) {
			if(result.length()==0){
				result = "程序处理异常，请联系管理员！";
			}
			logger.error("批量审核订单异常，请联系管理员！",e);
			e.printStackTrace();
		}finally{
			try {
				printWriter.write(result);
				printWriter.flush();
				printWriter.close();
			} catch (Exception e2) {
				logger.error("输出流输出及关闭异常信息：",e2);
			}
		}
	}
}
