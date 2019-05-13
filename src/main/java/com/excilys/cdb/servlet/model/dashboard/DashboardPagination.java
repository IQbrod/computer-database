package com.excilys.cdb.servlet.model.dashboard;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.servlet.model.ServletModel;

@Component
@Scope("prototype")
public class DashboardPagination extends ServletModel {
	private int page, maxPage, medianPage, size;
	private String search, orderBy;
	
	public DashboardPagination() {
		this.setDefault();
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setDefault() {
		this.page = 1;
		this.size = 10;
		this.search = " ";
		this.orderBy = "id";
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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
  		request.setAttribute("search", this.search);
	}	
}
