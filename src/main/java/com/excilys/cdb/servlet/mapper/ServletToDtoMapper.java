package com.excilys.cdb.servlet.mapper;

import com.excilys.cdb.dto.Dto;

public abstract class ServletToDtoMapper<T extends Dto> {
	protected abstract T convertFields(T dtoObject);
}
