package com.ehaoyao.opertioncenter.auditOrder.action;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.ehaoyao.opertioncenter.auditOrder.model.PAOrderDetailDO;
import com.ehaoyao.opertioncenter.auditOrder.model.PAOrderQueryParam;
import com.ehaoyao.opertioncenter.auditOrder.model.PAOrderResponse;
import com.ehaoyao.opertioncenter.auditOrder.service.AuditPAOrderService;
import com.ehaoyao.opertioncenter.common.DateUtil;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;

@Controller
@RequestMapping("/auditPAOrder.do")
public class AuditPAOrderController extends BaseAction{
	
	private static final Logger logger = Logger.getLogger(AuditPAOrderController.class);	
	//平安订单
	@Autowired
	private AuditPAOrderService auditPAOrderService;
	//用户信息
	@Autowired
	private UserServiceImpl userService;
	
	/**
	 * 菜单：平安健康订单管理
	 */
	@RequestMapping(params = ("method=goPAOrder"))
	public String goPAOrder(HttpServletRequest request,HttpSession session, ModelMap modelMap,String orderSn,String orderStatr,String orderType,String paymentType,String startTime,String endTime,String orderBy,String sort,String createAdmin){
		try {	
				User user = this.getCurrentUser();
				//客服编号
				request.getSession().setAttribute("custServCode", user != null ? user.getUserName():"");
				//客服姓名
				request.getSession().setAttribute("adminUserName", user.getName());
			this.getPAOrder(request,session, modelMap,orderType,startTime,endTime);
		} catch (Exception e) {
			logger.error("平安健康订单访问失败！",e);
			modelMap.put("mesg", "平安健康订单访问失败！");
			return "opcenter/custService/permissionTip";
		}
		return "opcenter/auditOrder/PA_order";
	}

	/**
	 * 获取当前登陆用户
	 * 
	 */
	private User getCurrentUser(){
		//用户信息
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		if(aut!=null){
			Object principal = aut.getPrincipal();
			if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				User user = userService.checkByUserName(userName);
				return user;
			}
		}
		return null;
	}
	
	/**
	 * 获取平安订单
	* @Description:
	* @param @param request
	* @param @param session
	* @param @param modelMap
	* @param @param orderType  订单类型 ：    0：全部订单；1：普通订单：2：未审核处方药订单；3：审核通过处方药订单；4：审核未通过处方药订单；							
	* @param @param startTime  	订单创建日期 开始 
	* @param @param endTime		订单创建日期 结束
	* @param @return
	* @param @throws ParseException
	* @return String
	* @throws
	 */
	@RequestMapping(params = ("method=getPAOrder"))
	public String getPAOrder(HttpServletRequest request,HttpSession session, ModelMap modelMap,String orderType,String startTime,String endTime) throws ParseException{
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		modelMap.put("auditAction", ac);
		//获取时间标示
		String dateString = DateUtil.getDateToString();
		modelMap.put("dateString", dateString);
		if("callerScreen.do".equals(ac)){
			modelMap.put("outAction","outScreen.do");
		}else{
			modelMap.put("outAction","outScreen2.do");
		}
		//日期格式化工具和日期算法工具
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		//订单结束日期
		String endCreated;
		if(null == endTime || "".equals(endTime)){
		//如果结束日期为空，这结束日期为当天		
			endTime=sdf2.format(calendar.getTime());			
		}		
		endCreated=	endTime+" 23:59:59";
		//订单开始日期
		String startCreated;
		if(null == startTime ||"".equals(startTime)){
			//如果开始日期为空，则开始日期为系统日期的三个月之前
			calendar.add(Calendar.MONTH, -3);
			startTime= sdf2.format(calendar.getTime());
		} 
		startCreated=startTime+" 00:00:00";
		//订单类型
		if(null==orderType && !"".equals(orderType)){
			orderType="2";
		}
		//第几页
		String pageIndex = request.getParameter("pageIndex");
		//每页显示的条数
		String pageSize = request.getParameter("pageSize");
		if( pageno == null || "".equals(pageno) ){
			this.setPageno(1);
		}else{
			if(null != pageIndex && !"".equals(pageIndex)){
				this.setPageno(Integer.parseInt(pageIndex));
			} else {
				this.setPageno(1);
			}
			if( this.getPageno() < 1 ){
				this.setPageno(1);
			}			
		}
		if( pageSize == null || "".equals(pageSize)){
			this.setPageSize(5);
		}else{
			this.setPageSize(Integer.parseInt(pageSize));
		}
		//平安订单数据包装类
		PAOrderResponse paOrderResponse=null;
		try {
			/**创建订单查询条件，并封装参数*/
			PAOrderQueryParam paOrderQueryParam=new PAOrderQueryParam();
			//订单开始时间
			paOrderQueryParam.setStartCreated(startCreated);
			//订单结束时间
			paOrderQueryParam.setEndCreated(endCreated);
			//订单类型
			paOrderQueryParam.setOrderType(orderType);
			//当前页	
			paOrderQueryParam.setPageNo(this.getPageno());
			//每页显示记录数
			paOrderQueryParam.setPageSize(this.getPageSize());
			/** 调用业务层方法获取平安订单数据*/
			paOrderResponse = auditPAOrderService.getPAOrderResponse(paOrderQueryParam);
		} catch (Exception e) {
			logger.error("调用平安健康订单列表接口失败"+e.getMessage());
			modelMap.put("orderMessage", "调用平安健康订单列表接口失败");
		}	
		if(null != paOrderResponse){
			//将订单集合转化成json字符串，传入到页面中
			List<PAOrderDetailDO> orderDetailDOList =paOrderResponse.getOrderDetailDOList();
			String OrderDetailDOListJson = JSONArray.toJSONString(orderDetailDOList);
			//订单总记录数
			int recTotal =paOrderResponse.getTotalCount();
			pageTotal = recTotal / this.getPageSize();
			pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
		    modelMap.put("OrderDetailDOList", orderDetailDOList);
			modelMap.put("pageno", this.getPageno());
			modelMap.put("pageTotal", pageTotal);
			modelMap.put("pageSize", this.getPageSize());
			modelMap.put("recTotal", recTotal);		
			modelMap.put("orderType", orderType);
			modelMap.put("startTime", startTime);
			modelMap.put("endTime", endTime);
			modelMap.put("OrderDetailDOListJson",OrderDetailDOListJson);
		}
		return "opcenter/auditOrder/PA_order";
	}
	/**
	 * 审核订单
	 * @param request
	 * @param printWriter
	 */
	@RequestMapping(params = ("method=auditOrder"))
	public void auditOrder(HttpServletRequest request,PrintWriter printWriter,String type,String orderSn,String auditRemark,String remark){
		/** 调用审核接口的参数*/
		//1、订单号 
		long bizOrderId =Long.parseLong(orderSn);
		//2、订单状态 1:审核通过  2:审核不通过
		int result =Integer.parseInt(type);
		//3、 审核信息 
		String resultMsg = auditRemark;
		//4、 备注信息 ,由于调用审核接口时，此参数不能为空字符串，就随便赋值一个字符串
		remark="11";
		/**审核订单并返回的消息*/
		String  auditOrderReturnMsg= null;
		try {
			auditOrderReturnMsg = auditPAOrderService.auditOrder(bizOrderId, result, resultMsg, remark);
		} catch (Exception e) {
			if(type.equals("1")){
				auditOrderReturnMsg="审核失败!!调用审核通过接口出错,请联系开发人员！！！";
				logger.error("调用审核通过接口出错，失败单号为"+bizOrderId,e);
			}
			if(type.equals("2")){
				auditOrderReturnMsg="审核失败!!调用审核不通过接口出错,请联系开发人员！！！";
				logger.error("调用审核不通过接口出错,失败单号为"+bizOrderId,e);
			}
		}
		if(auditOrderReturnMsg == null){
			auditOrderReturnMsg = "success";
		}
		printWriter.write(auditOrderReturnMsg);
		printWriter.flush();
		printWriter.close();
	}
}
