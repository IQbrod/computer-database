package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.model.AbstractModel;

public abstract class Mapper<T extends Dto, U extends AbstractModel> {
	public abstract U dtoToModel (T dtoObject);
	public abstract T modelToDto (U modelObject);
	
	public int idToInt(String id) {
		return Integer.valueOf(id);
	}
}
