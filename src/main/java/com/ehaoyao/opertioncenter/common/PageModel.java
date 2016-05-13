package com.ehaoyao.opertioncenter.common;

import java.util.List;

public class PageModel<E> {

	// 保存当前页的数据
	private List<E> list;

	// 每页显示的数据条数，0：查询全部
	private int pageSize = 20;

	private int totalRecords;// 总记录数

	@SuppressWarnings("unused")
	private int firstResult = 0;// 从第几条数据开始查找

	@SuppressWarnings("unused")
	private int totalPages;
	private int pageNo = 1;// 当前第几页

	// 获取总页数
	public int getTotalPages() {
		if (this.pageSize == 0) {
			return this.totalRecords>0?1:0;
		}
		return (this.totalRecords + this.pageSize - 1) / this.pageSize;
	}

	// 最后一页
	public int getBottomPage() {
		return this.getTotalPages();
	}

	// 第一页
	public int getFirstPage() {
		return 1;
	}

	// 上一页
	public int getPrePage() {
		if (this.pageNo <= 1) {
			return 1;
		}
		return this.pageNo - 1;
	}

	// 下一页
	public int getNextPage() {
		if (this.pageNo >= this.getBottomPage()) {
			return this.getBottomPage();
		}
		return this.pageNo + 1;
	}

	public List getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getFirstResult() {
		if (pageNo <= 1) {
			return 0;
		}
		return (this.pageNo - 1) * this.pageSize;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getPageNo() {
		if (this.getTotalPages() < 1) {
			return 0;
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
