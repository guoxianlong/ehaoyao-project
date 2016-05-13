package com.ehaoyao.opertioncenter.custServiceCenter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * Title: CustServFilter.java
 * 
 * Description: 客服访问 过滤器
 * 
 * @author xcl
 * @version 1.0
 * @created 2014年9月10日 下午1:03:34
 */
public class CustServFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String uri = request.getRequestURI();
		String ac = uri.substring(uri.lastIndexOf("/")+1);
		String met = request.getParameter("method");
		//呼叫中心访问主界面，不验证
		if("outScreen.do".equals(ac) && "getInfo".equals(met) //去电弹屏
				|| "callerScreen.do".equals(ac) && "getInfo".equals(met)//来电弹屏
				|| "callerScreen.do".equals(ac) && "goOfficialOrder".equals(met)//官网订单管理，查询历史订单
				|| "callerScreen.do".equals(ac) && "goAddOrder".equals(met)//客服代下单
				|| "nowTaskReport.do".equals(ac) && "queryTaskList".equals(met)//今日任务
				|| "user2.do".equals(ac) && "checkUserById".equals(met)//
				){
			chain.doFilter(req, res);
		} else{
			HttpSession session = request.getSession();
			Object obj = session.getAttribute("custServCode");
			if(obj==null || obj.toString().trim().length()<=0){
				request.setAttribute("mesg", "请重新访问！");
				request.getRequestDispatcher("/WEB-INF/view/opcenter/custService/permissionTip.jsp").forward(req,res);
			}else{
				chain.doFilter(req, res);
			}
		}
		
	}

	@Override
	public void destroy() {

	}

}
