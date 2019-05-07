package com.excilys.cdb.validator;

import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.exception.*;

public abstract class Validator<T extends Dto> {
	protected Logger logger;
	
	protected Validator() {}
	
	public void validate(T dtoObject) throws RuntimeException {
		validateId(dtoObject.getId());
	};
	
	public void validateId(String id) {
		try {
			int localId = Integer.parseInt(id);
			if (localId < 0)
				throw new Exception();
		} catch (Exception e) {
			throw new InvalidIntegerException(id);
		}
	}
	
	protected void required(String name, String element) {
		if (element == null) {
			throw new RequiredElementException(name);
		}
	}
}
