package com.ehaoyao.opertioncenter.reportForm.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehaoyao.opertioncenter.custServiceCenter.service.ReservationService;
import com.ehaoyao.opertioncenter.custServiceCenter.util.PropertiesUtil;
import com.ehaoyao.opertioncenter.custServiceCenter.vo.ReservationVO;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;
import com.haoyao.goods.util.SignUtils;
/**
 * 今日任务
 * @author Administrator
 *
 */
@Controller
@RequestMapping({"/nowTaskReport.do","/nowTaskReport2.do"})
public class NowTaskReportAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(NowTaskReportAction.class);
	//电销客服访问时用的请求加密标志
	private String nowTaskSign = PropertiesUtil.getProperties("extend.properties", "outScreenSign");
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//今日任务
	@Autowired
	private ReservationService reservationService;
	//用户信息
	@Autowired
	private UserServiceImpl userService;
	/**
	 * 查询今日任务
	 */
	@RequestMapping(params = ("method=queryTaskList"))
	public String queryTaskList(HttpServletRequest request,HttpSession session, ModelMap modelMap){

		//客服编号
		String custServCode = request.getParameter("custServCode");
		//会员手机号
		/*String phoneNo = request.getParameter("phoneNo");
		//健康顾问
		String custServName = request.getParameter("custServName");
		//客户名称
		String custName = request.getParameter("custName");*/
		//校验码
		String reqSign = request.getParameter("sign");
		if(custServCode==null || custServCode.trim().length()<=0){
			modelMap.put("mesg", "客服编号为空！");
			return "opcenter/custService/permissionTip";
		}
		/**对请求加密*/
		Map<String, String> map = new HashMap<String, String>();
		//参与签名的参数
		map.put("custServCode",custServCode);
		/*map.put("custName",custName);
		map.put("phoneNo",phoneNo);
		map.put("custServName",custServName);*/
		HashMap<String, String> signMap = SignUtils.sign(map, nowTaskSign);
		String sign = signMap.get("appSign");
		if(!sign.equals(reqSign)){
			modelMap.put("mesg", "参数签名验证失败！");
			return "opcenter/custService/permissionTip";
		}
		session.setAttribute("custServCode", custServCode);
		ReservationVO vo = new ReservationVO();
		vo.setCustServiceNo(custServCode);
		return getTaskList(request, session, modelMap,vo);
	}
	/**
	 * 查询今日任务
	 */
	@RequestMapping(params = ("method=getTaskList"))
	public String getTaskList(HttpServletRequest request,HttpSession session, ModelMap modelMap,ReservationVO cvo){
		try{
			String uri = request.getRequestURI();
			String ac = uri.substring(uri.lastIndexOf("/")+1);
			//客服编号(链接地址中的客服编号)
			String custServCode = (String) session.getAttribute("custServCode");
			/*//会员手机号
			//客服编号(输入的客服编号)
			String custServCd = request.getParameter("custServCd");
			String phoneNo = request.getParameter("phoneNo");
			//健康顾问
			String custServName = request.getParameter("custServName");
			//客户名称
			String custName = request.getParameter("custName");*/
			String flag = request.getParameter("flag");
			if(flag != null && "1".equals(flag.trim())){
				custServCode = cvo.getCustServiceNo();
			}else{
				if("nowTaskReport2.do".equals(ac)){
					if(isNull(custServCode) && isNull(cvo.getCustServiceNm())){
						User user = this.getCurrentUser();
						custServCode = user!=null?user.getUserName():null;
						cvo.setCustServiceNm(user!=null?user.getName():null);
						session.setAttribute("custServCode", custServCode);
					}
				}
			}
			cvo.setCustServiceNo(custServCode);
			String endStr = "";
			if("nowTaskReport2.do".equals(ac)){
				endStr = "2";
			}
			modelMap.put("endStr", endStr);
			//将查询条件放入session
			request.getSession().setAttribute("cvo", cvo);
			String pageno = request.getParameter("pageno");
			String pageSize = request.getParameter("pageSize");
			if( pageno == null || "".equals(pageno) ){
				this.setPageno(1);
			}else{
				this.setPageno(Integer.parseInt(pageno));
				if( this.getPageno() < 1 ){
					this.setPageno(1);
				}
			}
			if( pageSize == null || "".equals(pageSize) || "".equals(pageSize) ){
				this.setPageSize(10);
			}else{
				this.setPageSize(Integer.parseInt(pageSize));
			}
			
			recTotal = reservationService.getReservaCount(cvo);
			pageTotal = recTotal / this.getPageSize();
			pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
			if( this.getPageno() > pageTotal ){
				this.setPageno(1);
			}
			List<ReservationVO> taskList = reservationService.getReservaList((this.getPageno()-1)*this.getPageSize(), this.getPageSize(), cvo);
			modelMap.put("taskList", taskList);
			modelMap.put("pageno", this.getPageno());
			modelMap.put("pageTotal", pageTotal);
			modelMap.put("pageSize", this.getPageSize() );
			modelMap.put("recTotal", recTotal);
			modelMap.put("cvo",cvo);
		}catch(Exception e){
			logger.error("查询今日任务出错，错误信息为：" + e);
			e.printStackTrace();
		}
		return "opcenter/reportForm/task";
	}
	/**
	 * 将查询结果导出excel
	 */
	@RequestMapping(params=("method=getExcel"))
	public void getExcel(HttpServletRequest request,HttpServletResponse response){
		// 创建工作簿对象  
	    HSSFWorkbook workbook2003 = new HSSFWorkbook(); 
	    reservationService.writeToExcel(request,workbook2003);
	    String filename="今日任务.xls";
	    try {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 判断字符串是否为null或者空
	 * @param str
	 * @return
	 */
	private boolean isNull(String str){
		boolean ret = false;
		if(str == null || "".equals(str.trim())){
			ret =  true;
		}
		return ret;
	}
	
	/**
	 * 完成
	 */
	@RequestMapping(params = ("method=complete"))
	public void complete(HttpServletResponse response,HttpServletRequest request,int id){
		try{
			String message = "";
			PrintWriter out = response.getWriter();
			reservationService.complete(id);
			message = "修改成功！";
			out.println(message);
			out.flush(); 
		    out.close();
		}catch(Exception e){
			logger.error("修改今日任务完成状态出错，错误信息为：" + e);
			e.printStackTrace();
		}
	}
	
	//首页-统计今日任务
	@RequestMapping(params = ("method=todayTask"))
	@ResponseBody
	public String todayTask(HttpServletResponse response,HttpServletRequest request,ReservationVO vo){
		User user = getCurrentUser();
		if(user!=null){
			vo.setCustServiceNo(user.getUserName());
		}
		JSONObject json = new JSONObject();
		//查询时间
		json.put("queryTime",df.format(new Date()) );
		List<Map<String, Integer>> ls = reservationService.countTodayReserva(vo);
		if(ls!=null && ls.size()>0){
			json.put("dataLs", JSONArray.fromObject(ls));
		}else{
			json.put("dataLs", "[]");
		}
		return json.toString();
	}
	
	/**
	 * 获取当前用户
	 * @return User 
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
}
