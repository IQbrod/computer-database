package com.excilys.cdb.dao;

import java.sql.*;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

public class CompanyDao extends Dao<Company>{

	public CompanyDao() {
		super();
	}

	@Override
	public boolean create(Company obj) throws Exception{
		if(obj.getId() <= 0) {
			throw new InvalidIdException(obj.getId());
		}
		
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("INSERT INTO company VALUES (?,?);");
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			throw new PrimaryKeyViolationException(obj.getId());
		}
	}

	@Override
	public boolean update(Company obj) throws Exception {
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("UPDATE company SET name=? WHERE id=?;");
			p.setString(1, obj.getName());
			p.setInt(2, obj.getId());
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			throw new PrimaryKeyViolationException(obj.getId());
		}
	}

	@Override
	public boolean delete(Company obj) throws SQLException{
		return this.deleteById(obj.getId());
	}
	
	@Override
	public boolean deleteById(int id) throws SQLException {
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("DELETE FROM company WHERE id=?;");
			p.setInt(1, id);
			
			int nbRow = p.executeUpdate();
			return nbRow == 1;
		} catch (SQLException e) {
			// Yet never seen
			throw e;
		}
	}

	@Override
	public Company read(int id) throws Exception {
		if(id <= 0) {
			throw new InvalidIdException(id);
		}
		
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("SELECT * FROM company WHERE id=?;");
			p.setInt(1, id);
			
			ResultSet r = p.executeQuery();
			if(r.first()) {
				Company c = new Company(id,r.getString("name"));
				return c;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw e;
		}
	}

}
