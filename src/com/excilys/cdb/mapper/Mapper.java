package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.model.Model;

public abstract class Mapper<T extends Dto, U extends Model> {

	public Mapper() {}
	
	public abstract U dtoToModel (T dtoObject) throws Exception;
	public abstract T modelToDto (U modelObject) throws Exception;
	
	public int idToInt(String id) {
		return (Integer.parseInt(id) <= 0) ? 0 : Integer.parseInt(id);
	}
}
