package com.excilys.cdb.servlet.model.editComputer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.servlet.model.ServletModel;

@Component
@Scope("prototype")
public class EditComputerValues extends ServletModel {
	private ComputerDto compDto;
	
	public void setComputer(ComputerDto compDto) {
		this.compDto = compDto;
	}

	@Override
	public void flush(HttpServletRequest request) {
  		request.setAttribute("computer", this.compDto);
	}	
}
