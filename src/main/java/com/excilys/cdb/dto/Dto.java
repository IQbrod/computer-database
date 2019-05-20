package com.excilys.cdb.dto;

import javax.validation.constraints.PositiveOrZero;

public abstract class Dto {
	@PositiveOrZero
	protected Integer id;
	
	public Dto(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public abstract String toString();
}
