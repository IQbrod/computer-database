package com.excilys.cdb.dao;

import java.sql.*;
import com.excilys.cdb.model.*;

public class ComputerDao extends Dao<Computer>{

	public ComputerDao(Connection c) {
		super(c);
	}

	@Override
	public boolean create(Computer obj) {
		return false;
	}

	@Override
	public boolean update(Computer obj) {
		return false;
	}

	@Override
	public boolean delete(Computer obj) {
		return false;
	}

	@Override
	public Computer read(int id) {
		try {
			ResultSet r = this.conn.createStatement().executeQuery("SELECT * FROM Computer WHERE id = "+id);
			if(r.first()) {
				Computer c = new Computer(id,r.getString("name"),null,null,null);
				return c;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
