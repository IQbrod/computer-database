package com.excilys.cdb.dto;

import javax.validation.constraints.PositiveOrZero;

public abstract class Dto {
	@PositiveOrZero
	protected Long id;
	
	public Dto(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public abstract String toString();
}
