package com.excilys.cdb.service;

import java.util.*;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.exception.InvalidIntegerException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Model;


public abstract class Service<T extends Dto, U extends Model> {
	protected Mapper<T, U> mapper;
	protected Dao<U> dao;
	
	protected Service(Mapper<T, U> m, Dao<U> d) {
		this.mapper = m;
		this.dao = d;
	}
	
	public boolean create(T dtoObject) throws Exception {
		return this.dao.create(this.mapper.dtoToModel(dtoObject));
	};
	
	public boolean update(T dtoObject) throws Exception {
		return this.dao.update(this.mapper.dtoToModel(dtoObject));
	};
	
	public boolean delete(T dtoObject) throws Exception {
		return this.dao.delete(this.mapper.dtoToModel(dtoObject));
	};
	
	public T read(String id) throws Exception {
		return this.mapper.modelToDto(this.dao.read(this.mapper.idToInt(id)));
	};
	
	public List<Dto> listAllElements() throws Exception {
		List<Dto> returnList = new ArrayList<Dto>();
		List<U> lst = this.dao.listAll();
		for (U u : lst) {
			returnList.add(this.mapper.modelToDto(u));
		}
		return returnList;
	};
	
	public List<Dto> list(String p, String s) throws Exception {
		int page,size;
		
		try {
			page = Integer.parseInt(p);
		} catch (IllegalArgumentException e) {
			throw new InvalidIntegerException(p);
		}
		try {
			size = Integer.parseInt(s);
		} catch (IllegalArgumentException e) {
			throw new InvalidIntegerException(s);
		}
		
		List<Dto> returnList = new ArrayList<Dto>();
		List<U> lst = this.dao.list(page,size);
		for (U u : lst) {
			returnList.add(this.mapper.modelToDto(u));
		}
		return returnList;
	}
}
