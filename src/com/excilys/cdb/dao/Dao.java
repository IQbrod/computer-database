package com.excilys.cdb.dao;

import java.util.List;

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
	
	protected Dao(String sqlCreate, String sqlUpdate, String sqlDelete, String sqlSelect, String sqlListall, String sqlList) {
		this.SQL_CREATE = sqlCreate;
		this.SQL_UPDATE = sqlUpdate;
		this.SQL_DELETE = sqlDelete;
		this.SQL_SELECT = sqlSelect;
		this.SQL_LISTALL = sqlListall;
		this.SQL_LIST = sqlList;
	}
	
	public abstract T create(T obj) throws Exception;
	public abstract T update(T obj) throws Exception;
	public abstract T delete(T obj) throws Exception;
	public abstract T deleteById(int i) throws Exception;
	public abstract T read(int id) throws Exception;
	public abstract List<T> listAll() throws Exception;
	public abstract List<T> list(int page, int size) throws Exception;
	
}
