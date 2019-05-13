package com.excilys.cdb.service;

import java.util.*;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.model.Model;


public abstract class AbstractService<U extends Model> {
	protected Dao<U> dao;
	
	protected AbstractService(Dao<U> dao) {
		this.dao = dao;
	}
	
	public U create(U modelObject) {
		return this.dao.create(modelObject);
	};
	
	public U update(U modelObject) {
		return this.dao.update(modelObject);
	};
	
	public U delete(U modelObject) {
		return this.dao.delete(modelObject);
	};
	
	public U read(int id) {
		return this.dao.read(id);
	};
	
	public List<U> listAllElements() {
		return this.dao.listAll();
	};
	
	public List<U> list(int page, int size) {		
		return this.dao.list(page,size);
	}
	
	public int count() {
		return this.dao.count();
	}
}
