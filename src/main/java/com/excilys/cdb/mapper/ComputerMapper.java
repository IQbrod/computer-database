package com.excilys.cdb.mapper;

import java.sql.*;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

@Component
public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	private final CompanyMapper companyMapper;
	private final CompanyService companyService;
	
	public ComputerMapper(CompanyMapper companyMapper, CompanyService companyService) {
		this.companyMapper = companyMapper;
		this.companyService = companyService;
	}
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) {
		int id = this.idToInt(dtoObject.getId());
		String name = dtoObject.getName();
		Timestamp t1;
		Timestamp t2;
		t1 = (dtoObject.getIntroduction() == null) ? null : Timestamp.valueOf(dtoObject.getIntroduction()+" 12:00:00");
		t2 = (dtoObject.getDiscontinued() == null) ? null : Timestamp.valueOf(dtoObject.getDiscontinued()+" 12:00:00");
		int cid = this.idToInt(dtoObject.getCompanyId());
			
		return new Computer(id,name,t1,t2,(cid == 0) ? null : cid);
	}

	@Override
	public ComputerDto modelToDto(Computer modelObject) {
		CompanyDto companyDto = (modelObject.getCompany_id() <= 0) ? new CompanyDto("0","_") : this.companyMapper.modelToDto(this.companyService.read(modelObject.getCompany_id()));
		
		return new ComputerDto(
			Integer.toString(modelObject.getId()),
			modelObject.getName(),
			(modelObject.getIntroduced() == null) ? "" : modelObject.getIntroduced().toString().substring(0,modelObject.getIntroduced().toString().length()-11),
			(modelObject.getDiscontinued() == null) ? "" : modelObject.getDiscontinued().toString().substring(0,modelObject.getDiscontinued().toString().length()-11),
			companyDto.getId(),
			companyDto.getName()
		);
	}
	
}
