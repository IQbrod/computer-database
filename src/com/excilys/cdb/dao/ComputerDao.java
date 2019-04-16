package com.excilys.cdb.dao;

import java.sql.*;
import com.excilys.cdb.model.*;

// TODO: Vérifier le passage des dates (insert/update)

public class ComputerDao extends Dao<Computer>{

	public ComputerDao(Connection c) {
		super(c);
	}

	@Override
	public boolean create(Computer obj) {
		try {
			int nbRow = this.conn.createStatement().executeUpdate("INSERT INTO computer VALUES ("+ obj.getId() +", '"+ obj.getName() + "', "+ obj.getDateIntro() + ", "+ obj.getDateDisc() + ", "+ obj.getManufacturer() + ");");
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Computer obj) {
		try {
			int nbRow = this.conn.createStatement().executeUpdate("UPDATE computer SET name='"+ obj.getName()+"', introduced="+ obj.getDateIntro() +", discontinued="+ obj.getDateDisc() +", company_id="+ obj.getManufacturer() +" WHERE id="+ obj.getId() +";");
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Computer obj) {
		return this.deleteById(obj.getId());
	}
	
	public boolean deleteById(int id) {
		try {
			int nbRow = this.conn.createStatement().executeUpdate("DELETE FROM computer WHERE id="+ id +";");
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Computer read(int id) {
		try {
			ResultSet r = this.conn.createStatement().executeQuery("SELECT * FROM computer WHERE id = "+id);
			if(r.first()) {
				// TODO: Change Company id
				Computer c = new Computer(id,r.getString("name"),r.getDate("introduced"),r.getDate("discontinued"), r.getInt("company_id"));
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
