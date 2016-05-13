package com.ehaoyao.opertioncenter.reportForm.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.opertioncenter.common.PageModel;
import com.ehaoyao.opertioncenter.reportForm.service.IThirdOrderAuditReportService;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderDetail;
import com.ehaoyao.opertioncenter.thirdOrderAudit.model.OrderInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.OrderMainInfo;
import com.ehaoyao.opertioncenter.thirdOrderAudit.vo.ThirdOrderAuditVO;
import com.haoyao.goods.action.BaseAction;

import net.sf.json.JSONArray;


@Controller
@RequestMapping({"/thirdOrderAuditReport.do"})
public class ThirdOrderAuditReportAction extends BaseAction {

	
	private static final Logger logger = Logger.getLogger(ThirdOrderAuditReportAction.class);
	@Autowired
	private IThirdOrderAuditReportService iThirdOrderAuditReportService;
	
	/**
	 * 获取订单信息
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
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
		try {
			//订单查询
			pm = iThirdOrderAuditReportService.getOrderInfos(pm,vo);
			List<OrderMainInfo> orderList = pm.getList();
			if (orderList != null && orderList.size() > 0) {
				modelMap.put("orderList", orderList);
			}
			modelMap.put("pageno", pm.getPageNo());
			modelMap.put("pageTotal", pm.getTotalPages());
			modelMap.put("pageSize", pm.getPageSize());
			modelMap.put("recTotal", pm.getTotalRecords());
		} catch (Exception e) {
			logger.error("订单查询异常！异常信息:",e);
		}
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		modelMap.put("vo", vo);
		return "opcenter/reportForm/thirdOrderAuditReport";
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
			list = iThirdOrderAuditReportService.getOrderDetails(vo);
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
	 * 导出三方订单审核报表
	 * @param request
	 * @param printWriter
	 * @param vo
	 */
	@RequestMapping(params = ("method=exportExcel"))
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,ThirdOrderAuditVO vo){
		 // 创建工作簿对象  
		try {
			HSSFWorkbook workbook2003 = new HSSFWorkbook();  
			workbook2003= iThirdOrderAuditReportService.exportExcel(vo,workbook2003);
			String filename="三方订单审核报表.xls";
			OutputStream os = response.getOutputStream();
			response.reset();
			String agent=request.getHeader("user-agent");
			response.setContentType("application/vnd.ms-excel");
			if(agent.contains("Firefox")){
				response.addHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes("GB2312"),"ISO-8859-1"));
			}else {
				response.setHeader("Content-disposition","attachment;filename="+URLEncoder.encode(filename, "UTF-8"));
			}
			workbook2003.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
