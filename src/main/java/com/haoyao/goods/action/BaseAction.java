package com.haoyao.goods.action;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseAction implements ServletContextAware{
	 /** 公共字段* */
    public Integer recTotal = 0;// 总记录数
    public Integer pageTotal = 0;// 总页数
    public Integer pageSize;// 每页显示多少条
    public Integer pageno = 1;// 当前页
    public ServletContext servletContext;// servlet容器对象
    public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	/**
     * @description 获取spring容器上下文
     * 
     */
	@SuppressWarnings("unchecked")
    public WebApplicationContext getWac() {
	return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
    /**
     * @description 获取spring中bean的实例
     * 
     */
    public Object getBeanService(String beanName) {
    	WebApplicationContext w = getWac();
    	return w.getBean(beanName);
        }  
    
    
    public Integer getRecTotal() {
    	return recTotal;
        }
    public void setRecTotal(Integer recTotal) {
    	this.recTotal = recTotal;
        }
    public Integer getPageTotal() {
    	return pageTotal;
        }
    public void setPageTotal(Integer pageTotal) {
    	if (pageno > pageTotal)
    	    pageno = pageTotal;
    	this.pageTotal = pageTotal;
        } 
    public Integer getPageSize() {

    	return pageSize == null || pageSize < 1 ? 20 : pageSize;
    }
    public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
    }
    public Integer getPageno() {
    	return pageno == null || pageno < 1 ? 1 : pageno;
        }
    public void setPageno(Integer pageno) {
    	this.pageno = pageno == null || pageno < 1 ? 1 : pageno;
        }
}
