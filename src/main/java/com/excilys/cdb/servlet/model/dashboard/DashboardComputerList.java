package com.excilys.cdb.servlet.model.dashboard;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.servlet.model.ServletModel;

@Component
@Scope("prototype")
public class DashboardComputerList extends ServletModel {
	private List<ComputerDto> list;
	private int nbComputer;

	public List<ComputerDto> getList() {
		return list;
	}

	public int getNbComputer() {
		return nbComputer;
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
