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
			PreparedStatement p = this.conn.prepareStatement("INSERT INTO company VALUES (?,?);");
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Company obj) {
		try {
			PreparedStatement p = this.conn.prepareStatement("UPDATE company SET name=? WHERE id=?;");
			p.setString(1, obj.getName());
			p.setInt(2, obj.getId());
			
			int nbRow = p.executeUpdate();
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
			PreparedStatement p = this.conn.prepareStatement("DELETE FROM company WHERE id=?;");
			p.setInt(1, id);
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Company read(int id) {
		try {
			PreparedStatement p = this.conn.prepareStatement("SELECT * FROM company WHERE id=?;");
			p.setInt(1, id);
			
			ResultSet r = p.executeQuery();
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
