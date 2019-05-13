package com.excilys.cdb.servlet.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.service.CompanyService;

@Component
public class SharedCompanyList extends ServletModel {
	private List<CompanyDto> companyList;
	private final CompanyService companyService;
	private final CompanyMapper companyMapper;
	
	public SharedCompanyList(CompanyService companyService, CompanyMapper companyMapper) throws RuntimeException {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
		this.refresh();
	}
	
	public void refresh() throws RuntimeException {
		this.companyList = this.companyService.listAllElements().stream().map(c -> this.companyMapper.modelToDto(c)).collect(Collectors.toList());
	}
	
	@Override
	public void flush(HttpServletRequest request) {
		request.setAttribute("companyList", this.companyList);		
	}

}
