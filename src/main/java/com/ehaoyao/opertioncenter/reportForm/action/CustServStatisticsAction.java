package com.ehaoyao.opertioncenter.reportForm.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.opertioncenter.reportForm.service.CustServStatisticsService;
import com.ehaoyao.opertioncenter.reportForm.vo.CustServStatisticsVo;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;
/**
 * 沟通记录报表
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/custServStatistics.do")
public class CustServStatisticsAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(CustServStatisticsAction.class);
	//客服统计
	@Autowired
	private CustServStatisticsService custService;
	
	//用户信息
	@Autowired
	private UserServiceImpl userService;

	/**
	 * 客服统计查询
	 */
	@RequestMapping(params = ("method=getStatisticsList"))
	public String getStatisticsList(HttpServletRequest request, ModelMap modelMap, CustServStatisticsVo cs){
		String flag=request.getParameter("flag");
		try{
			if(flag==null || "".equals(cs.getConsultDate().trim()) || cs.getConsultDate()==null){
				//第一次查询
				cs.setConsultDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(cs!=null){
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
				//将查询条件存入session中
				request.getSession().setAttribute("cs", cs);
				recTotal = custService.queryStatisticsCount(cs);
				pageTotal = recTotal / this.getPageSize();
				pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
				if( this.getPageno() > pageTotal ){
					this.setPageno(1);
				}
				List<CustServStatisticsVo> statisticsList = custService.queryStatisticsList((this.getPageno()-1)*this.getPageSize(), this.getPageSize(), cs);
				CustServStatisticsVo statisticSum = custService.querySum(cs);
				modelMap.put("statisticsList", statisticsList);
				modelMap.put("statisticSum",statisticSum);
				
				modelMap.put("pageno", this.getPageno());
				modelMap.put("pageTotal", pageTotal);
				modelMap.put("pageSize", this.getPageSize() );
				modelMap.put("recTotal", recTotal);
				
				modelMap.put("cs",cs);
			}
		}catch(Exception e){
			logger.error("查询客服统计出错，错误信息为：" + e);
			e.printStackTrace();
		}
		if(flag==null){
			return "opcenter/reportForm/custServ_statistics";
		}
		return "opcenter/reportForm/inner_custServ_statistics";
		
	}

	/**
	 * 获取excel
	 */
   @RequestMapping(params=("method=getExcel"))
   public void getExcel(HttpServletRequest request,HttpServletResponse response){
	   //从session中取出vo作为查询条件
	   CustServStatisticsVo cs=(CustServStatisticsVo) request.getSession().getAttribute("cs");
	   //查询人
	   Authentication aut = SecurityContextHolder.getContext().getAuthentication();
	   String name=null;
	   if(aut!=null){
			Object principal = aut.getPrincipal();
			if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				User user = userService.checkByUserName(userName);
				name=user.getName();
			}
		}
       // 创建工作簿对象  
       HSSFWorkbook workbook2003 = new HSSFWorkbook();  
       custService.writeToExcel(request,cs,name,workbook2003);
		String filename="产品咨询日报.xls";
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
	

}
