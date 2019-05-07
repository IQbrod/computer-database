package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Model;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class Dao<T extends Model> {
	protected HikariDataSource dataSource;
	
	protected final String SQL_CREATE;
	protected final String SQL_UPDATE;
	protected final String SQL_DELETE;
	protected final String SQL_SELECT;
	protected final String SQL_LIST;
	protected final String SQL_LIMIT;
	protected final String SQL_COUNT;
	
	protected Logger logger;
	
	protected Dao(String sqlCreate, String sqlUpdate, String sqlDelete, String sqlSelect, String sqlList, String sqlLimit, String sqlCount) throws RuntimeException {
		this.SQL_CREATE = sqlCreate;
		this.SQL_UPDATE = sqlUpdate;
		this.SQL_DELETE = sqlDelete;
		this.SQL_SELECT = sqlSelect;
		this.SQL_LIST = sqlList;
		this.SQL_LIMIT = sqlLimit;
		this.SQL_COUNT = sqlCount;
		
		ResourceBundle bundle;
		try {
			bundle = ResourceBundle.getBundle("dbconfig");
		} catch (MissingResourceException ex) {
			bundle = ResourceBundle.getBundle("dbconfig_travis");
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			DriverNotFoundException exception = new DriverNotFoundException("com.mysql.cj.jdbc.Driver");
			logger.error(exception.getMessage() + " caused by " + e.getMessage(),exception);
			throw exception;
		}
		
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setJdbcUrl(bundle.getString("url"));
		config.setUsername(bundle.getString("username"));
		config.setPassword(bundle.getString("password"));
		
		dataSource = new HikariDataSource(config);
		
		logger = (Logger) LogManager.getLogger(this.getClass());
		
		try (
			Connection connection = this.dataSource.getConnection();
		) {} catch (SQLException e) {
			DatabaseProblemException exception = new DatabaseProblemException(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
			logger.error(exception.getMessage() + " caused by " + e.getMessage(),e);
			throw exception;
		}
	}
	
	public abstract T create(T obj) throws Exception;
	public abstract T update(T obj) throws Exception;
	public abstract T delete(T obj) throws Exception;
	public abstract T deleteById(int i) throws Exception;
	public abstract T read(int id) throws RuntimeException;
	public abstract List<T> listAll() throws RuntimeException;
	public abstract List<T> list(int page, int size) throws Exception;
	public abstract int count() throws RuntimeException;
	
	protected Logger getLogger() {
		return this.logger;
	}
	
	protected RuntimeException log (RuntimeException exception, Exception cause) throws RuntimeException {
		getLogger().error(exception.getMessage()+ " caused by "+cause.getMessage(),exception);
		return exception;
	}
	
	protected RuntimeException log (RuntimeException exception) throws RuntimeException {
		getLogger().error(exception.getMessage(),exception);
		return exception;
	}
	
}