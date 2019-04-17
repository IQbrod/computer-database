package com.excilys.cdb.dao;

import com.excilys.cdb.model.Model;

public abstract class Dao<T extends Model> {
	protected final String DBACCESS = "jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC";
	protected final String DBUSER = "admincdb";
	protected final String DBPASS = "qwerty1234";
	
	public Dao() {}
	
	public abstract boolean create(T obj);
	public abstract boolean update(T obj);
	public abstract boolean delete(T obj);
	public abstract T read(int id);
	
}
