package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.model.Model;

public abstract class Mapper<T extends Dto, U extends Model> {

	protected Mapper() {}
	
	public abstract U dtoToModel (T dtoObject) throws RuntimeException;
	public abstract T modelToDto (U modelObject) throws RuntimeException;
	
	public int idToInt(String id) {
		return Integer.parseInt(id);
	}
}
