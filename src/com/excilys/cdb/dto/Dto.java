package com.excilys.cdb.dto;

public abstract class Dto {
	protected String id;
	
	public Dto(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public abstract String toString();
}
