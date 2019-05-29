package com.excilys.cdb.service;

import java.util.*;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.model.AbstractModel;


public abstract class AbstractService<U extends AbstractModel> {
	protected Dao<U> dao;
	
	protected AbstractService(Dao<U> dao) {
		this.dao = dao;
	}
	
	public void create(U modelObject) {
		this.dao.create(modelObject);
	}
	
	public void update(U modelObject) {
		this.dao.update(modelObject);
	}
	
	public void delete(U modelObject) {
		this.dao.delete(modelObject);
	}
	
	public U read(long id) {
		return this.dao.read(id);
	}
	
	public List<U> listAllElements() {
		return this.dao.listAll();
	}
	
	public List<U> list(int page, int size) {		
		return this.dao.list(page,size);
	}
	
	public long count() {
		return this.dao.count();
	}
}
