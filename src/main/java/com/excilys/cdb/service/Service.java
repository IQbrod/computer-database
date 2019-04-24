package com.excilys.cdb.service;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.exception.InvalidIntegerException;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Model;


public abstract class Service<T extends Dto, U extends Model> {
	protected Mapper<T, U> mapper;
	protected Dao<U> dao;
	
	private Logger logger = (Logger) LogManager.getLogger(this.getClass());
	
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
	
	public T read(String id) throws Exception {
		return this.mapper.modelToDto(this.dao.read(this.mapper.idToInt(id)));
	};
	
	public List<T> listAllElements() throws Exception {
		return (List<T>) this.dao.listAll().stream().map(s -> mapper.modelToDto(s)).collect(Collectors.toList());
	};
	
	public List<T> list(String pageStr, String sizeStr) throws Exception {
		int page,size;
		
		try {
			page = Integer.parseInt(pageStr);
		} catch (IllegalArgumentException e) {
			Exception exception = new InvalidIntegerException(pageStr);
			this.logger.error(exception.getMessage()+" caused by "+e.getMessage(),exception);
			throw exception;			
		}
		try {
			size = Integer.parseInt(sizeStr);
		} catch (IllegalArgumentException e) {
			Exception exception = new InvalidIntegerException(sizeStr);
			this.logger.error(exception.getMessage()+" caused by "+e.getMessage(),exception);
			throw exception;
		}
		
		return (List<T>) this.dao.list(page,size).stream().map(s -> mapper.modelToDto(s)).collect(Collectors.toList());
	}
}
