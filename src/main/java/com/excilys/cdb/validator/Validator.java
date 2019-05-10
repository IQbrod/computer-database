package com.excilys.cdb.validator;

import java.sql.Timestamp;

import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.*;

public class Validator {
	protected Logger logger;
	private static Validator instance;
	
	private Validator() {}
	
	public static Validator getInstance() {
		if (instance == null)
			instance = new Validator();
		return instance;
	}
	
	private void validateId(String id) {
		try {
			int localId = Integer.parseInt(id);
			if (localId < 0)
				throw new Exception();
		} catch (Exception e) {
			throw new InvalidIntegerException(id);
		}
	}
	
	private void required(String name, String element) {
		if (element == null) {
			throw new RequiredElementException(name);
		}
	}
	
	private void validateDate(String date) {
		if (date != null) {
			try {
				Timestamp.valueOf(date+" 12:00:00");
			} catch (Exception e) {
				RuntimeException exception = new InvalidDateValueException(date);
				this.logger.error(exception.getMessage());
				throw exception;
			}
		}
	}
	
	private void validateDateOrder(String before, String after) {
		if (before != null && after != null) {
			if (Timestamp.valueOf(before+" 12:00:00").after(Timestamp.valueOf(after+" 12:00:00"))) {
				RuntimeException exception = new InvalidDateOrderException(before, after);
				this.logger.error(exception.getMessage());
				throw exception;
			}
		}
	}
	
	public boolean validateCompanyDto(CompanyDto companyDto) {
		this.validateId(companyDto.getId());
		this.required("name", companyDto.getName());
		return true;
	}
	
	public boolean validateComputerDto(ComputerDto computerDto) {
		this.validateId(computerDto.getId());
		this.required("name", computerDto.getName());
		this.validateDate(computerDto.getIntroduction());
		this.validateDate(computerDto.getDiscontinued());
		this.validateDateOrder(computerDto.getIntroduction(), computerDto.getDiscontinued());
		this.validateId(computerDto.getCompanyId());
		return true;
	}
}
