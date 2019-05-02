package com.excilys.cdb.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.exception.*;

public abstract class Validator<T extends Dto> {
	protected Logger logger = (Logger) LogManager.getLogger(this.getClass());
	
	protected Validator() {}
	
	public void validate(T dtoObject) throws RuntimeException {
		validateId(dtoObject.getId());
	};
	
	protected void validateId(String id) {
		try {
			int localId = Integer.parseInt(id);
			if (localId < 0)
				throw new Exception();
		} catch (Exception e) {
			RuntimeException exception = new InvalidIntegerException(id);
			this.logger.error(exception.getMessage()+" caused by "+e.getMessage(),e);
			throw exception;
		}
	}
	
	protected void required(String name, String element) {
		if (element == null || element.equals("")) {
			RuntimeException exception = new RequiredElementException(name);
			this.logger.error(exception.getMessage());
			throw exception;
		}
	}
}
