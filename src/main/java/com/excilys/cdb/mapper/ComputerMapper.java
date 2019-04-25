package com.excilys.cdb.mapper;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.model.Computer;

public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	private static ComputerMapper instance = new ComputerMapper();	
	private Logger logger = (Logger) LogManager.getLogger(this.getClass());
	
	private ComputerMapper() {}	
	
	public static ComputerMapper getInstance() {
		return instance;
	}
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) throws RuntimeException {
		int id = this.idToInt(dtoObject.getId());
		String name = dtoObject.getName();
		Timestamp t1, t2;
		t1 = this.castTimestamp(dtoObject.getIntroduction());
		t2 = this.castTimestamp(dtoObject.getDiscontinued());
		int cid = this.idToInt(dtoObject.getCompany());
			
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
	public ComputerDto modelToDto(Computer modelObject) {
		return new ComputerDto(
			Integer.toString(modelObject.getId()),
			modelObject.getName(),
			(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString(),
			(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString(),
			(modelObject.getManufacturer() <= 0) ? "0" : Integer.toString(modelObject.getManufacturer())
		);
	}
	
}
