package com.excilys.cdb.validator;

import java.sql.Timestamp;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.*;

@Component
public class Validator {
	private static final String DEFAULT_TIME_VALUE = " 12:00:00";
	protected Logger logger;
	
	private void validateId(Long id) {
		if (id < 0)
			throw new InvalidIntegerException(id.toString());
	}
	
	private void required(String name, String element) {
		if (element == null) {
			throw new RequiredElementException(name);
		}
	}
	
	private void validateDate(String date) {
		if (date != null) {
			try {
				Timestamp.valueOf(date+DEFAULT_TIME_VALUE);
			} catch (Exception e) {
				RuntimeException exception = new InvalidDateValueException(date);
				this.logger.error(exception.getMessage());
				throw exception;
			}
		}
	}
	
	private void validateDateOrder(String before, String after) {
		if (before != null && after != null && Timestamp.valueOf(before+DEFAULT_TIME_VALUE).after(Timestamp.valueOf(after+DEFAULT_TIME_VALUE))) {
			throw new InvalidDateOrderException(before, after);
		}
	}
	
	public void validateCompanyDto(CompanyDto companyDto) {
		this.validateId(companyDto.getId());
		this.required("name", companyDto.getName());
	}
	
	public void validateComputerDto(ComputerDto computerDto) {
		this.validateId(computerDto.getId());
		this.required("name", computerDto.getName());
		this.validateDate(computerDto.getIntroduction());
		this.validateDate(computerDto.getDiscontinued());
		this.validateDateOrder(computerDto.getIntroduction(), computerDto.getDiscontinued());
		this.validateId(computerDto.getCompanyId());
	}
	
	public void validateUserDto(UserDto userDto) {
		this.validateId(userDto.getId());
		this.required(userDto.getUsername(), "username");
		this.required(userDto.getPassword(), "password");
		this.validateId(userDto.getRoleId());
	}
	
	public void validatePagination(String paginationValue) {
		try {
			int localId = Integer.parseInt(paginationValue);
			if (localId <= 0)
				throw new InvalidIntegerException(paginationValue);
		} catch (Exception e) {
			throw new InvalidIntegerException(paginationValue);
		}
	}
}
