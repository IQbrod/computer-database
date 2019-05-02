package com.excilys.cdb.validator;

import java.sql.Timestamp;

import org.apache.logging.log4j.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateOrderException;
import com.excilys.cdb.exception.InvalidDateValueException;

public class ComputerValidator extends Validator<ComputerDto> {
	private static ComputerValidator instance;
	
	private ComputerValidator() {
		this.logger = (Logger) LogManager.getLogger(this.getClass());
	}
	
	public static ComputerValidator getInstance() {
		if (instance == null)
			instance = new ComputerValidator();
		return instance;
	}
	
	@Override
	public void validate(ComputerDto dtoObject) {
		super.validate(dtoObject);
		this.required("name", dtoObject.getName());
		this.validateDate(dtoObject.getIntroduction());
		this.validateDate(dtoObject.getDiscontinued());
		this.validateDateOrder(dtoObject.getIntroduction(), dtoObject.getDiscontinued());
		this.validateId(dtoObject.getCompanyId());
	}
	
	private void validateDate(String date) {
		if (date != null) {
			try {
				Timestamp.valueOf(date);
			} catch (Exception e) {
				RuntimeException exception = new InvalidDateValueException(date);
				this.logger.error(exception.getMessage());
				throw exception;
			}
		}
	}
	
	private void validateDateOrder(String before, String after) {
		if (Timestamp.valueOf(before).after(Timestamp.valueOf(after))) {
			RuntimeException exception = new InvalidDateOrderException(before, after);
			this.logger.error(exception.getMessage());
			throw exception;
		}
	}
}
