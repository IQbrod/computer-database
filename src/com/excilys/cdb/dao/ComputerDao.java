package com.excilys.cdb.dao;

import java.sql.*;
import com.excilys.cdb.model.*;

// TODO: Throw les exceptions jusqu'au controller
// TODO: Check les id à 0

public class ComputerDao extends Dao<Computer>{

	public ComputerDao() {
		super();
	}

	@Override
	public boolean create(Computer obj) {
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("INSERT INTO computer VALUES (?,?,?,?,?);");
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			p.setTimestamp(3, obj.getDateIntro());
			p.setTimestamp(4, obj.getDateDisc());
			// Help: https://stackoverflow.com/questions/14514589/inserting-null-to-an-integer-column-using-jdbc
			if (obj.getManufacturer() == 0) {
				p.setNull(5, java.sql.Types.INTEGER);
			} else {
				p.setInt(5, obj.getManufacturer());
			}
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Computer obj) {
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;");
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
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("DELETE FROM computer WHERE id=?;");
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
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("SELECT * FROM computer WHERE id=?;");
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
