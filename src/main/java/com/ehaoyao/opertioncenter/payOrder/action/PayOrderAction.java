package com.ehaoyao.opertioncenter.payOrder.action;

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

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.payOrder.service.IPayOrderService;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderInfoDetailVO;
import com.ehaoyao.opertioncenter.payOrder.vo.OrderShowInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.haoyao.goods.action.BaseAction;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("/payOrder.do")
public class PayOrderAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(PayOrderAction.class);
	@Autowired
	private IPayOrderService iPayOrderService;
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(params=("method=showOrderPay"))
	public String showOrderPay(HttpServletRequest request,HttpSession session, ModelMap modelMap,OrderInfoDetailVO vo){
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("actionName", ac);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(vo == null){
			new OrderInfoDetailVO();
		}
		//开始时间
		String startDate = vo.getStartDate();
		if(startDate==null || startDate.trim().length()==0){
			Date dNow = new Date(); //当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(dNow);//把当前时间赋给日历
			calendar.add(calendar.DATE, -3); //设置为前4月
			dBefore = calendar.getTime(); //得到前4月的时间
			startDate = sdf.format(dBefore);
		}
		vo.setStartDate(startDate.trim()+" 00:00:00");
		//截止时间
		String endDate = vo.getEndDate();
		if(endDate==null || endDate.trim().length()==0){
			endDate=sdf.format(new Date());
		}
		vo.setEndDate(endDate.trim()+" 23:59:59");
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
		PageModel<OrderShowInfo> pm = new PageModel<OrderShowInfo>();
		pm.setPageSize(this.getPageSize());
		pm.setPageNo(this.getPageno());
		try {
			pm=iPayOrderService.getOrderInfos(pm, vo);
			List<OrderShowInfo> orderList=pm.getList();
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
		
		return "opcenter/payOrder/payOrder";
	}
	
	/**
	 * 根据订单号查找商品明细
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = ("method=getOrderDetails"))
	public void getOrderDetails(HttpServletRequest request,PrintWriter printWriter,OrderInfoDetailVO vo){
		List<OrderDetail> list;
		String result = "";
		try {
			list = iPayOrderService.getOrderDetails(vo);
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
	 * 获取数据库存在的渠道
	 * 
	 */
	@RequestMapping(params = ("method=getOrderFlag"))
	public void getOrderFlag(HttpServletRequest request,PrintWriter printWriter, ModelMap modelMap,OrderInfoDetailVO vo){
		List<OrderShowInfo> list;
		String result = "";
	
		try {
			list = iPayOrderService.getOrderFlag();
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
	
}
