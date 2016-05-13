package com.haoyao.goods.action;

public class Page {
	private int cruuentPage;
	private int startRecord;
	private int endRecord;
	private int recordCount;
	private int nextPage;
	private int previousPage;
	private int totalPage;
	private int pageSize;

	public Page() {
		cruuentPage = 1;
		pageSize = 4;
	}

	public void init(int recordCount) {
		this.recordCount = recordCount;
		startRecord = (cruuentPage - 1) * pageSize + 1;
		if (startRecord > recordCount) {
			startRecord = recordCount;
		}
		endRecord = cruuentPage * pageSize;
		if (endRecord > recordCount) {
			endRecord = recordCount;
		}
		totalPage = (recordCount % pageSize == 0) ? (recordCount / pageSize)
				: (recordCount / pageSize + 1);
		nextPage = cruuentPage + 1;
		if (nextPage > totalPage) {
			nextPage = totalPage;
		}
		previousPage = cruuentPage - 1;

		if (previousPage < 1) {
			previousPage = 1;
		}

	}

	public int getCruuentPage() {
		return cruuentPage;
	}

	public void setCruuentPage(int cruuentPage) {
		this.cruuentPage = cruuentPage;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
