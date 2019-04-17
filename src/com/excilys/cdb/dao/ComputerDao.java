package com.excilys.cdb.dao;

import java.sql.*;
import com.excilys.cdb.model.*;

// TODO: Vérifier le passage des dates (insert/update)
// TODO: Throw les exceptions jusqu'au controller

public class ComputerDao extends Dao<Computer>{

	public ComputerDao(Connection c) {
		super(c);
	}

	@Override
	public boolean create(Computer obj) {
		try {
			PreparedStatement p = this.conn.prepareStatement("INSERT INTO computer VALUES (?,?,?,?,?);");
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			p.setTimestamp(3, obj.getDateIntro());
			p.setTimestamp(4, obj.getDateDisc());
			p.setInt(5, obj.getManufacturer());
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Computer obj) {
		try {
			PreparedStatement p = this.conn.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;");
			p.setString(1, obj.getName());
			p.setTimestamp(2, obj.getDateIntro());
			p.setTimestamp(3, obj.getDateDisc());
			p.setInt(4, obj.getManufacturer());
			p.setInt(5,obj.getId());
			
			int nbRow = p.executeUpdate();
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
			PreparedStatement p = this.conn.prepareStatement("DELETE FROM computer WHERE id=?;");
			p.setInt(1, id);
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Computer read(int id) {
		try {
			PreparedStatement p = this.conn.prepareStatement("SELECT * FROM computer WHERE id=?;");
			p.setInt(1, id);
			
			ResultSet r = p.executeQuery();
			if(r.first()) {
				Computer c = new Computer(id,r.getString("name"),r.getTimestamp("introduced"),r.getTimestamp("discontinued"), r.getInt("company_id"));
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
