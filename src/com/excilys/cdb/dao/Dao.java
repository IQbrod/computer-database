package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Model;

public abstract class Dao<T extends Model> {
	protected final String DBACCESS = "jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC";
	protected final String DBUSER = "admincdb";
	protected final String DBPASS = "qwerty1234";
	
	public Dao() {}
	
	public abstract boolean create(T obj) throws Exception;
	public abstract boolean update(T obj) throws Exception;
	public abstract boolean delete(T obj) throws Exception;
	public abstract boolean deleteById(int i) throws Exception;
	public abstract T read(int id) throws Exception;
	public abstract List<T> listAll() throws Exception;
	public abstract List<T> list(int page, int size) throws Exception;
	
}
