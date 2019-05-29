package com.excilys.cdb.servlet.model;

public class Pagination {
	private int page;
	private long maxPage;
	private long medianPage;
	private int size;
	private String search;
	private String orderBy;
	private long nbComputer;
	
	public long getNbComputer() {
		return nbComputer;
	}

	public void setNbComputer(long nbComputer) {
		this.nbComputer = nbComputer;
	}

	public Pagination() {
		this.reset();
	}
	
	public void reset() {
		this.page = 1;
		this.maxPage = 5;
		this.medianPage = 3;
		this.size = 10;
		this.search = "";
		this.orderBy = "";
		this.nbComputer = 0;
	}

	public int getPage() {
		return page;
	}

	public long getMaxPage() {
		return maxPage;
	}

	public long getMedianPage() {
		return medianPage;
	}

	public int getSize() {
		return size;
	}

	public String getSearch() {
		return search;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setMaxPage(long maxPage) {
		this.maxPage = maxPage;
	}

	public void setMedianPage(long medianPage) {
		this.medianPage = medianPage;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
