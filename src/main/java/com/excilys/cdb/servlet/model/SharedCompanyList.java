package com.excilys.cdb.servlet.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.service.CompanyService;

public class SharedCompanyList extends ServletModel {
	private List<CompanyDto> companyList;
	private static SharedCompanyList instance = null;
	
	private SharedCompanyList() throws RuntimeException {
		this.refresh();
	}
	
	public static SharedCompanyList getInstance() throws RuntimeException {
		if (instance == null)
			instance = new SharedCompanyList();
		return instance;
	}
	
	public void refresh() throws RuntimeException {
		this.companyList = CompanyService.getInstance().listAllElements().stream().map(c -> CompanyMapper.getInstance().modelToDto(c)).collect(Collectors.toList());
	}
	
	@Override
	public void flush(HttpServletRequest request) {
		request.setAttribute("companyList", this.companyList);		
	}

}
