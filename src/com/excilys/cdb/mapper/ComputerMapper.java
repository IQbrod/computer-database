package com.excilys.cdb.mapper;

import java.sql.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;

public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	
	public ComputerMapper() {}	
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) {
		if (dtoObject == null) {
			return null;
		} else {			
			return new Computer(
				this.idToInt(dtoObject.getId()),
				dtoObject.getName(),
				(dtoObject.getIntro() == null) ? null : Timestamp.valueOf(dtoObject.getIntro()),
				(dtoObject.getDiscon() == null) ? null : Timestamp.valueOf(dtoObject.getDiscon()),
				this.idToInt(dtoObject.getComp())				
			);
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
