package com.excilys.cdb.service;

import java.util.*;
import java.util.stream.Collectors;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Model;


public abstract class Service<T extends Dto, U extends Model> {
	protected Mapper<T, U> mapper;
	protected Dao<U> dao;
	
	protected Service(Mapper<T, U> map, Dao<U> dao) {
		this.mapper = map;
		this.dao = dao;
	}
	
	public T create(T dtoObject) throws Exception {
		return this.mapper.modelToDto(this.dao.create(this.mapper.dtoToModel(dtoObject)));
	};
	
	public T update(T dtoObject) throws Exception {
		return this.mapper.modelToDto(this.dao.update(this.mapper.dtoToModel(dtoObject)));
	};
	
	public T delete(T dtoObject) throws Exception {
		return this.mapper.modelToDto(this.dao.delete(this.mapper.dtoToModel(dtoObject)));
	};
	
	public T read(String id) throws RuntimeException {
		return this.mapper.modelToDto(this.dao.read(this.mapper.idToInt(id)));
	};
	
	public List<T> listAllElements() throws Exception {
		return (List<T>) this.dao.listAll().stream().map(s -> mapper.modelToDto(s)).collect(Collectors.toList());
	};
	
	public List<T> list(String pageStr, String sizeStr) throws Exception {
		int page,size;
		
		page = this.mapper.idToInt(pageStr);
		size = this.mapper.idToInt(sizeStr);
		
		return (List<T>) this.dao.list(page,size).stream().map(s -> mapper.modelToDto(s)).collect(Collectors.toList());
	}
}
