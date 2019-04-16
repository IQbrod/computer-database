package com.excilys.cdb.dao;

import java.sql.*;
import com.excilys.cdb.model.Model;

public abstract class Dao<T extends Model> {
	protected Connection conn;
	
	public Dao(Connection c) {
		this.conn = c;
	}
	
	public abstract boolean create(T obj);
	public abstract boolean update(T obj);
	public abstract boolean delete(T obj);
	public abstract T read(int id);
	
}
