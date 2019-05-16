package com.excilys.cdb.dao;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.excilys.cdb.dbconnector.JdbcTemplateProvider;
import com.excilys.cdb.model.Model;

public abstract class Dao<T extends Model> {
	protected NamedParameterJdbcTemplate namedTemplate;
	protected JdbcTemplate template;
	
	protected final String sqlCreate;
	protected final String sqlUpdate;
	protected final String sqlDelete;
	protected final String sqlSelect;
	protected final String sqlList;
	protected final String sqlLimit;
	protected final String sqlCount;
	
	protected RowMapper<T> rowMapper;
	
	protected Logger logger;
	
	protected Dao(String sqlCreate, String sqlUpdate, String sqlDelete, String sqlSelect, String sqlList, String sqlLimit, String sqlCount, JdbcTemplateProvider templateProvider, RowMapper<T> rowMapper) {
		this.sqlCreate = sqlCreate;
		this.sqlUpdate = sqlUpdate;
		this.sqlDelete = sqlDelete;
		this.sqlSelect = sqlSelect;
		this.sqlList = sqlList;
		this.sqlLimit = sqlLimit;
		this.sqlCount = sqlCount;
		this.namedTemplate = templateProvider.getJdbcTemplateNamedParameter();
		this.template = templateProvider.getJdbcTemplate();
		this.rowMapper = rowMapper;
		this.logger = LogManager.getLogger(this.getClass());
	}
	
	public abstract T create(T obj);
	public abstract T update(T obj);
	public abstract T delete(T obj);
	public abstract T deleteById(int i);
	public abstract T read(int id);
	public abstract List<T> listAll();
	public abstract List<T> list(int page, int size);
	public abstract int count();
	
	protected Logger getLogger() {
		return this.logger;
	}
	
	protected RuntimeException log (RuntimeException exception, Exception cause) {
		getLogger().error(exception.getMessage()+ " caused by "+cause.getMessage(),exception);
		return exception;
	}
	
	protected RuntimeException log (RuntimeException exception) {
		getLogger().error(exception.getMessage(),exception);
		return exception;
	}
	
}