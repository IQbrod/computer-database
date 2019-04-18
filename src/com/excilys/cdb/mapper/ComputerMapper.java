package com.excilys.cdb.mapper;

import java.sql.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.model.Computer;

public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	
	public ComputerMapper() {}	
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) throws Exception {
		if (dtoObject == null) {
			return null;
		} else {
			int id = this.idToInt(dtoObject.getId());
			String name = dtoObject.getName();
			Timestamp t1, t2;
			t1 = this.castTimestamp(dtoObject.getIntro());
			t2 = this.castTimestamp(dtoObject.getDiscon());
			Computer c = new Computer(id,name,t1,t2,idToInt(dtoObject.getComp()));
				return c;
		}
	}
	
	private Timestamp castTimestamp(String s) throws Exception {
		try {
			return (s == null) ? null : Timestamp.valueOf(s);
		} catch (NumberFormatException e) {
			throw new InvalidDateValueException(s);
		}
	}

	@Override
	public ComputerDto modelToDto(Computer modelObject) {
		if (modelObject == null) {
			return null;
		} else {
			return new ComputerDto(
				Integer.toString(modelObject.getId()),
				modelObject.getName(),
				(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString(),
				(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString(),
				(modelObject.getManufacturer() <= 0) ? "0" : Integer.toString(modelObject.getManufacturer())
			);
		}
	}
	
}
