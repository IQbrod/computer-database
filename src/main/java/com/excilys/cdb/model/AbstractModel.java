package com.excilys.cdb.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractModel {
	@Id
	@GeneratedValue
	protected long id;
	
	public AbstractModel() {}
	
	public AbstractModel(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
