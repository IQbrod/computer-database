package com.excilys.cdb.servletmodel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.ComputerDto;

public class DashboardComputerList extends ServletModel {
	private List<ComputerDto> list;
	private int nbComputer;
	
	private static DashboardComputerList instance = null;
	
	private DashboardComputerList() {}

	public static DashboardComputerList getInstance() {
		if (instance == null)
			instance = new DashboardComputerList();
		return instance;
	}

	public void setList(List<ComputerDto> list) {
		this.list = list;
	}
	
	public void setNbComputer(int nbComputer) {
		this.nbComputer = nbComputer;
	}

	@Override
	public void flush(HttpServletRequest request) {
  		request.setAttribute("computerList", list);
  		request.setAttribute("nbComputer", this.nbComputer);
	}	
}
