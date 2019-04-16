package com.excilys.cdb.dao;

import java.sql.*;
import com.excilys.cdb.model.*;

public class CompanyDao extends Dao<Company>{

	public CompanyDao(Connection c) {
		super(c);
	}

	@Override
	public boolean create(Company obj) {
		try {
			int nbRow = this.conn.createStatement().executeUpdate("INSERT INTO company VALUES ("+ obj.getId() +", '"+ obj.getName() +"');");
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Company obj) {
		try {
			int nbRow = this.conn.createStatement().executeUpdate("UPDATE company SET name='"+ obj.getName()+"' WHERE id="+ obj.getId() +";");
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Company obj) {
		return this.deleteById(obj.getId());
	}
	
	public boolean deleteById(int id) {
		try {
			int nbRow = this.conn.createStatement().executeUpdate("DELETE FROM company WHERE id="+ id +";");
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Company read(int id) {
		try {
			ResultSet r = this.conn.createStatement().executeQuery("SELECT * FROM company WHERE id="+id);
			if(r.first()) {
				Company c = new Company(id,r.getString("name"));
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
