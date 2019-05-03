package com.excilys.cdb.servlet.model.dashboard;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.servlet.model.ServletModel;

public class DashboardPagination extends ServletModel {
	private int page, maxPage, medianPage, size;
	
	private static DashboardPagination instance = null;
	
	private DashboardPagination() {}

	public static DashboardPagination getInstance() {
		if (instance == null)
			instance = new DashboardPagination();
		return instance;
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
	
	@Override
	public void flush(HttpServletRequest request) {
  		request.setAttribute("page", this.page);
  		request.setAttribute("size", this.size);
  		request.setAttribute("maxPage", this.maxPage);
  		request.setAttribute("medianPage", this.medianPage);
	}	
}
