package com.excilys.cdb.servlet.model.editComputer;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.servlet.model.ServletModel;

public class EditComputerValues extends ServletModel {
	private ComputerDto compDto;
	
	private static EditComputerValues instance = null;
	
	private EditComputerValues() {}

	public static EditComputerValues getInstance() {
		if (instance == null)
			instance = new EditComputerValues();
		return instance;
	}
	
	public void setComputer(ComputerDto compDto) {
		this.compDto = compDto;
	}

	@Override
	public void flush(HttpServletRequest request) {
  		request.setAttribute("computer", this.compDto);
	}	
}
