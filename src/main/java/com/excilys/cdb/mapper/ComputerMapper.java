package com.excilys.cdb.mapper;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	private static ComputerMapper instance;	
	private Logger logger = (Logger) LogManager.getLogger(this.getClass());
	
	private ComputerMapper() {}	
	
	public static ComputerMapper getInstance() {
		if (instance == null)
			instance = new ComputerMapper();		
		return instance;
	}
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) throws RuntimeException {
		int id = this.idToInt(dtoObject.getId());
		String name = dtoObject.getName();
		Timestamp t1, t2;
		t1 = this.castTimestamp(dtoObject.getIntroduction());
		t2 = this.castTimestamp(dtoObject.getDiscontinued());
		int cid = this.idToInt(dtoObject.getCompany().getId());
			
		Computer computer = new Computer(id,name,t1,t2,cid);
		return computer;
	}
	
	private Timestamp castTimestamp(String s) throws RuntimeException {
		try {
			return (s == null) ? null : Timestamp.valueOf(s);
		} catch (Exception e) {
			RuntimeException exception = new InvalidDateValueException(s);
			this.logger.error(exception.getMessage()+" caused by "+e.getMessage(),e);
			throw exception;
		}
	}

	@Override
	public ComputerDto modelToDto(Computer modelObject) throws RuntimeException {
		return new ComputerDto(
			Integer.toString(modelObject.getId()),
			modelObject.getName(),
			(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString().substring(0,modelObject.getDateIntro().toString().length()-2),
			(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString().substring(0,modelObject.getDateDisc().toString().length()-2),
			(modelObject.getManufacturer() <= 0) ? new CompanyDto("0","_") : CompanyService.getInstance().read(Integer.toString(modelObject.getManufacturer()))
		);
	}
	
}
