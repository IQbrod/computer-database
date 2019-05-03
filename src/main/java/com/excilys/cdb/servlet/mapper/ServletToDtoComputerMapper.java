package com.excilys.cdb.servlet.mapper;

import com.excilys.cdb.dto.ComputerDto;

public class ServletToDtoComputerMapper extends ServletToDtoMapper<ComputerDto> {
	private static ServletToDtoComputerMapper instance = null;
	
	private ServletToDtoComputerMapper() {}
	
	public static ServletToDtoComputerMapper getInstance() {
		if (instance == null)
			instance = new ServletToDtoComputerMapper();
		return instance;
	}
	
	@Override
	public ComputerDto convertFields(ComputerDto dtoObject) {
		dtoObject.setIntroduction(this.convertDate(dtoObject.getIntroduction()));
		dtoObject.setDiscontinued(this.convertDate(dtoObject.getDiscontinued()));
		return dtoObject;
	}
	
	private String convertDate(String date) {
		// Empty date input
		if (date.equals("")) {
			return null;
		// Might be a date input
		} else if (date.length() == 10) {
			return date.replace("/", "-") + " 12:00:00";
		// Might be a text input
		} else {
			return date;
		}
	}

}
