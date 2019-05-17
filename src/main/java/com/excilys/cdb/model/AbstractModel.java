package com.excilys.cdb.model;

public abstract class AbstractModel {
	protected int id;
	
	public AbstractModel(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
