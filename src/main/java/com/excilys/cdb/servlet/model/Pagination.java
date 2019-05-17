package com.excilys.cdb.servlet.model;

public class Pagination {
	private int page;
	private int maxPage;
	private int medianPage;
	private int size;
	private String search;
	private String orderBy;
	private int nbComputer;
	
	public int getNbComputer() {
		return nbComputer;
	}

	public void setNbComputer(int nbComputer) {
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

	public int getMaxPage() {
		return maxPage;
	}

	public int getMedianPage() {
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

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public void setMedianPage(int medianPage) {
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
