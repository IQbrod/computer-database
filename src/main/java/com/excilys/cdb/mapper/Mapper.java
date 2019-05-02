package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.model.Model;
import com.excilys.cdb.validator.Validator;

public abstract class Mapper<T extends Dto, U extends Model> {
	protected Validator<T> validator;
	
	
	protected Mapper(Validator<T> validator) {
		this.validator = validator;
	}
	
	public abstract U dtoToModel (T dtoObject) throws RuntimeException;
	public abstract T modelToDto (U modelObject) throws RuntimeException;
	
	public int idToInt(String id) {
		return Integer.valueOf(id);
	}
}
