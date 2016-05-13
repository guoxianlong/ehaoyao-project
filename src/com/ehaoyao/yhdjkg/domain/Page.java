package com.ehaoyao.yhdjkg.domain;
import java.util.List;

public class Page<T> {
	
	private int pageSize=10;      //每页条数
	private int count;          //总条数
	private int pageNum;      //总页数
	private int pageId=1;         //当前页数
	private int pageStrat;    // 起始条数
	private List<T> list;
	
	  
	/**   
	 * 创建一个新的实例 Page.  
	 * 默认为pageSize=10 count=0 pageId=1 
	 */
	public Page(){
		this(10,0,1);
	}
	
	public Page(int pageSize, int count, int pageId){
		this.pageSize=pageSize;
		this.count=count;
		this.pageNum=this.count%pageSize==0?this.count/pageSize:this.count/pageSize+1;
		this.pageId=pageId;if(this.pageId<=1){this.pageId=1;}if(this.pageId>=this.pageNum){this.pageId=this.pageNum;}
		this.pageStrat=(this.pageId-1)*this.pageSize;
	}

	 
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getPageStrat() {
		return pageStrat;
	}

	public void setPageStrat(int pageId) {
		this.pageStrat=(pageId-1)*this.pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}