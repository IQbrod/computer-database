package com.excilys.cdb.mapper;

import java.sql.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.validator.ComputerValidator;

public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	private static ComputerMapper instance;	
	
	private ComputerMapper() {
		super(ComputerValidator.getInstance());
	}	
	
	public static ComputerMapper getInstance() {
		if (instance == null)
			instance = new ComputerMapper();		
		return instance;
	}
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) throws RuntimeException {
		this.validator.validate(dtoObject);
		
		int id = this.idToInt(dtoObject.getId());
		String name = dtoObject.getName();
		Timestamp t1, t2;
		t1 = Timestamp.valueOf(dtoObject.getIntroduction());
		t2 = Timestamp.valueOf(dtoObject.getDiscontinued());
		int cid = this.idToInt(dtoObject.getCompanyId());
			
		Computer computer = new Computer(id,name,t1,t2,cid);
		return computer;
	}

	@Override
	public ComputerDto modelToDto(Computer modelObject) throws RuntimeException {
		CompanyDto companyDto = (modelObject.getManufacturer() <= 0) ? new CompanyDto("0","_") : CompanyService.getInstance().read(Integer.toString(modelObject.getManufacturer()));
		
		return new ComputerDto(
			Integer.toString(modelObject.getId()),
			modelObject.getName(),
			(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString().substring(0,modelObject.getDateIntro().toString().length()-2),
			(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString().substring(0,modelObject.getDateDisc().toString().length()-2),
			companyDto.getId(),
			companyDto.getName()
		);
	}
	
}
