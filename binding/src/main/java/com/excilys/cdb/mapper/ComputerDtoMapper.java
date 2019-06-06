package com.excilys.cdb.mapper;

import java.sql.*;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

@Component
public class ComputerDtoMapper extends DtoMapper<ComputerDto, Computer>{
	private final CompanyDtoMapper companyMapper;
	private final CompanyService companyService;
	
	public ComputerDtoMapper(CompanyDtoMapper companyMapper, CompanyService companyService) {
		this.companyMapper = companyMapper;
		this.companyService = companyService;
	}
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) {
		long id = dtoObject.getId();
		String name = dtoObject.getName();
		Timestamp t1;
		Timestamp t2;
		t1 = (dtoObject.getIntroduction() == null) ? null : Timestamp.valueOf(dtoObject.getIntroduction()+" 12:00:00");
		t2 = (dtoObject.getDiscontinued() == null) ? null : Timestamp.valueOf(dtoObject.getDiscontinued()+" 12:00:00");
		long cid = dtoObject.getCompanyId();
			
		return new Computer(id,name,t1,t2,(cid == 0) ? null : cid);
	}

	@Override
	public ComputerDto modelToDto(Computer modelObject) {
		CompanyDto companyDto = (modelObject.getCompanyId() == null || modelObject.getCompanyId() <= 0) ? new CompanyDto(0L,"_") : this.companyMapper.modelToDto(this.companyService.read(modelObject.getCompanyId()));
		
		return new ComputerDto(
			modelObject.getId(),
			modelObject.getName(),
			(modelObject.getIntroduced() == null) ? "" : modelObject.getIntroduced().toString().substring(0,modelObject.getIntroduced().toString().length()-11),
			(modelObject.getDiscontinued() == null) ? "" : modelObject.getDiscontinued().toString().substring(0,modelObject.getDiscontinued().toString().length()-11),
			companyDto.getId(),
			companyDto.getName()
		);
	}
	
}
