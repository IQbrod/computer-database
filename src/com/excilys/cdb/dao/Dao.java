package com.excilys.cdb.dao;

import java.sql.*;

public abstract class Dao<T> {
	protected Connection conn;
	
	public Dao(Connection c) {
		this.conn = c;
	}
	
	public abstract boolean create(T obj);
	public abstract boolean update(T obj);
	public abstract boolean delete(T obj);
	public abstract T read(int id);
	
}
