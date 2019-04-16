package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Model;


public abstract class Service<T extends Dto, U extends Model> {
	protected Mapper<T, U> mapper;
	protected Dao<U> dao;
	
	public Service(Mapper<T, U> m, Dao<U> d) {
		this.mapper = m;
		this.dao = d;
	}
	
	public boolean create(T dtoObject) {
		return this.dao.create(this.mapper.dtoToModel(dtoObject));
	};
	
	public boolean update(T dtoObject) {
		return this.dao.update(this.mapper.dtoToModel(dtoObject));
	};
	
	public boolean delete(T dtoObject) {
		return this.dao.delete(this.mapper.dtoToModel(dtoObject));
	};
	
	public T read(String id) {
		return this.mapper.modelToDto(this.dao.read(this.mapper.idToInt(id)));
	};
	
	public abstract List<T> listAllElements();
}
