package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Model;

public abstract class Dao<T extends Model> {
	protected final String DBACCESS;
	protected final String DBUSER;
	protected final String DBPASS;
	
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
		this.DBACCESS = bundle.getString("url");
		this.DBUSER = bundle.getString("username");
		this.DBPASS = bundle.getString("password");
		
		logger = (Logger) LogManager.getLogger(this.getClass());
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (
				Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			) {}
			catch (Exception e) {
				throw e;
			}
		} catch (Exception e) {
			DatabaseProblemException exception = new DatabaseProblemException(this.DBACCESS, this.DBUSER, this.DBPASS);
			logger.error(exception.getMessage() + " caused by " + e.getMessage(),exception);
			throw exception;
		}
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
		) {} catch (SQLException e) {
			DatabaseProblemException exception = new DatabaseProblemException(this.DBACCESS, this.DBUSER, this.DBPASS);
			logger.error(exception.getMessage() + " caused by " + e.getMessage(),exception);
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