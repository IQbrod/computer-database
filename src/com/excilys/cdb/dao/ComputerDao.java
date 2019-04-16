package com.excilys.cdb.dao;

import java.sql.*;
import com.excilys.cdb.model.*;

public class ComputerDao extends Dao<Computer>{

	public ComputerDao(Connection c) {
		super(c);
	}

	@Override
	public boolean create(Computer obj) {
		try {
			this.conn.createStatement().executeUpdate("INSERT INTO computer VALUES ("+ obj.getId() +", '"+ obj.getName() + "', "+ obj.getDateIntro() + ", "+ obj.getDateDisc() + ", "+ obj.getManufacturer() + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			ResultSet r = this.conn.createStatement().executeQuery("SELECT * FROM computer WHERE id = "+id);
			if(r.first()) {
				// TODO: Change Company id
				Computer c = new Computer(id,r.getString("name"),r.getDate("introduced"),r.getDate("discontinued"), -1);
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
