package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.exception.DatabaseProblemException;
import com.excilys.cdb.model.Model;

public abstract class Dao<T extends Model> {
	protected final String DBACCESS = "jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC";
	protected final String DBUSER = "admincdb";
	protected final String DBPASS = "qwerty1234";
	
	protected final String SQL_CREATE;
	protected final String SQL_UPDATE;
	protected final String SQL_DELETE;
	protected final String SQL_SELECT;
	protected final String SQL_LISTALL;
	protected final String SQL_LIST;
	
	protected Logger logger;
	
	protected Dao(String sqlCreate, String sqlUpdate, String sqlDelete, String sqlSelect, String sqlListall, String sqlList) throws DatabaseProblemException {
		this.SQL_CREATE = sqlCreate;
		this.SQL_UPDATE = sqlUpdate;
		this.SQL_DELETE = sqlDelete;
		this.SQL_SELECT = sqlSelect;
		this.SQL_LISTALL = sqlListall;
		this.SQL_LIST = sqlList;
		
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
	public abstract T read(int id) throws Exception;
	public abstract List<T> listAll() throws Exception;
	public abstract List<T> list(int page, int size) throws Exception;
	
	protected Logger getLogger() {
		return this.logger;
	}
	
	protected Exception log (Exception exception, Exception cause) throws Exception {
		getLogger().error(exception.getMessage()+ " caused by "+cause.getMessage(),exception);
		return exception;
	}
	
	protected Exception log (Exception exception) throws Exception {
		getLogger().error(exception.getMessage(),exception);
		return exception;
	}
	
}