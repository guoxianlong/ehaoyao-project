package com.ehaoyao.opertioncenter.reportForm.action;

import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehaoyao.framework.util.StringUtil;
import com.ehaoyao.opertioncenter.reportForm.service.CommunicationReportService;
import com.ehaoyao.opertioncenter.reportForm.vo.CommuInfoVo;
import com.haoyao.goods.action.BaseAction;
import com.haoyao.goods.model.User;
import com.haoyao.goods.service.UserServiceImpl;
/**
 * 沟通记录报表
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/communicateReport.do")
public class CommunicationReportAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(CommunicationReportAction.class);
	//沟通记录
	@Autowired
	private CommunicationReportService commuService;
	//用户信息
	@Autowired
	private UserServiceImpl userService;

	/**
	 * 沟通记录查询
	 */
	@RequestMapping(params = ("method=getCommunicationList"))
	public String getCommunicationList(HttpServletRequest request, ModelMap modelMap, CommuInfoVo ci){
		String flag=request.getParameter("flag");
		try{
			if(flag==null){
				//设置查询条件:当前客服工号,客服姓名
				setCustServ(ci);
			}
			String endDate=ci.getEndDate();
			if(ci!=null){
				if(ci.getEndDate()!=null && ci.getEndDate().trim().length()>0){
					ci.setEndDate(ci.getEndDate().trim()+" 23:59:59");
				}
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
					this.setPageSize(5);
				}else{
					this.setPageSize(Integer.parseInt(pageSize));
				}
				//将查询条件存入session中
				request.getSession().setAttribute("ci", ci);
				recTotal = commuService.queryCommuCount(ci);
				pageTotal = recTotal / this.getPageSize();
				pageTotal = recTotal % this.getPageSize() == 0 ? pageTotal : pageTotal + 1;
				if( this.getPageno() > pageTotal ){
					this.setPageno(1);
				}
				List<CommuInfoVo> commuList = commuService.queryCommuList((this.getPageno()-1)*this.getPageSize(), this.getPageSize(), ci);
				modelMap.put("commuList", commuList);
				
				modelMap.put("pageno", this.getPageno());
				modelMap.put("pageTotal", pageTotal);
				modelMap.put("pageSize", this.getPageSize() );
				modelMap.put("recTotal", recTotal);
				modelMap.put("tel_show", commuService.controlShow("ROLE_TEL_SHOW"));
				ci.setEndDate(endDate);
				modelMap.put("ci",ci);
			}
		}catch(Exception e){
			logger.error("查询沟通记录出错，错误信息为：" + e);
			e.printStackTrace();
		}
		if(flag==null){
			return "opcenter/reportForm/commuInfo_report";
		}
		return "opcenter/reportForm/inner_commu_rep";
		
	}
	/**
	 * 获取select数据
	 */
	@RequestMapping(params = ("method=getSelectList"))
	public void getSelectList(HttpServletRequest request,HttpServletResponse response){
		String parentName=request.getParameter("parentName");
		if(StringUtil.isNotBlank(parentName)){
			//xml路径
			URL url;
			//返回的数据
			StringBuffer classNames;
			String xPath;
			try {
				url = new URL("http://api.goodscenter.ehaoyao.com:8081//interface-data/GetFrontClass?type=1");
				classNames=new StringBuffer();
				SAXReader sReader = new SAXReader(); 
				Document doc = (Document) sReader.read(url);
				xPath="//classs[parentName='"+parentName+"']/className";
				if(parentName.equals("null")){
					xPath="//classs[parentId=0]/className";
				}
				List<?> classNodes = doc.selectNodes(xPath);
				for(Iterator<?> classItr=classNodes.iterator();classItr.hasNext();){
					Element classElement=(Element)classItr.next();
					String className=classElement.getText();
					//去除以"_de"结尾的数据
					if(!className.endsWith("_de")){
						if(classNames.length()>0){
							classNames.append(","+className);
						}else{
							classNames.append(className);
						}
					}
				}
				response.getWriter().print(classNames);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 *获取excel
	 */
	@RequestMapping(params = ("method=getExcel"))
	private void getExcel(HttpServletRequest request,HttpServletResponse response){
		
        CommuInfoVo ciVo=(CommuInfoVo) request.getSession().getAttribute("ci");
        if(ciVo!=null){
        	if(ciVo.getEndDate()!=null && ciVo.getEndDate().trim().length()>0){
				ciVo.setEndDate(ciVo.getEndDate().trim()+" 23:59:59");
			}
        }
        // 创建工作簿对象  
        HSSFWorkbook workbook2003 = new HSSFWorkbook();  
       workbook2003= commuService.writeToExcel(ciVo,workbook2003);
		String filename="沟通记录.xls";
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
	
	//设置客服工号,客服姓名查询条件
	private void setCustServ(CommuInfoVo ci){
		//用户信息
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		if(aut!=null){
			Object principal = aut.getPrincipal();
			if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				User user = userService.checkByUserName(userName);
				ci.setCustServCode(user.getUserName());
				ci.setName(user.getName());
			}
		}
	}
}
