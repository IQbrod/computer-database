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
	
	protected final String sqlCreate;
	protected final String sqlUpdate;
	protected final String sqlDelete;
	protected final String sqlSelect;
	protected final String sqlList;
	protected final String sqlLimit;
	protected final String sqlCount;
	
	protected Logger logger;
	
	protected Dao(String sqlCreate, String sqlUpdate, String sqlDelete, String sqlSelect, String sqlList, String sqlLimit, String sqlCount) {
		this.sqlCreate = sqlCreate;
		this.sqlUpdate = sqlUpdate;
		this.sqlDelete = sqlDelete;
		this.sqlSelect = sqlSelect;
		this.sqlList = sqlList;
		this.sqlLimit = sqlLimit;
		this.sqlCount = sqlCount;
		
		ResourceBundle bundle;
		try {
			bundle = ResourceBundle.getBundle("dbconfig");
		} catch (MissingResourceException ex) {
			bundle = ResourceBundle.getBundle("dbconfig_travis");
		}
		
		try {
			Class.forName(bundle.getString("driver"));
		} catch (ClassNotFoundException e) {
			throw this.log(new DriverNotFoundException(bundle.getString("driver")));
		}
		
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName(bundle.getString("driver"));
		config.setJdbcUrl(bundle.getString("url"));
		config.setUsername(bundle.getString("username"));
		config.setPassword(bundle.getString("password"));
		
		dataSource = new HikariDataSource(config);
		
		logger = LogManager.getLogger(this.getClass());
		
		try (
			Connection connection = this.dataSource.getConnection();
		) {
			;
		} catch (SQLException e) {
			throw this.log(new DatabaseProblemException(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword()), e);
		}
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