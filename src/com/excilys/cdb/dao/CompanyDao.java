package com.excilys.cdb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

public class CompanyDao extends Dao<Company>{

	public CompanyDao() {
		super(
			"INSERT INTO company VALUES (?,?);",
			"UPDATE company SET name=? WHERE id=?;",
			"DELETE FROM company WHERE id=?;",
			"SELECT * FROM company WHERE id=?;",
			"SELECT * FROM company;",
			"SELECT * FROM company LIMIT ?,?;"
		);
	}

	@Override
	public Company create(Company obj) throws Exception{
		if(obj.getId() <= 0) {
			throw new InvalidIdException(obj.getId());
		}
		
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement(this.SQL_CREATE);
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			
			int nbRow = p.executeUpdate();
			return (nbRow == 1) ? obj : null;
		} catch (SQLException e) {
			throw new PrimaryKeyViolationException(obj.getId());
		}
	}

	@Override
	public Company update(Company obj) throws Exception {
		// Read
		Company c = this.read(obj.getId());
		if (c == null) {
			return null;
		}
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			// Update
			c.setName(obj.getName());
			// Push update
			PreparedStatement p = conn.prepareStatement(this.SQL_UPDATE);
			p.setString(1, c.getName());
			p.setInt(2, c.getId());
			
			return (p.executeUpdate() == 1) ? c : null;
		} catch (SQLException e) {
			throw new PrimaryKeyViolationException(obj.getId());
		}
	}

	@Override
	public Company delete(Company obj) throws Exception{
		return this.deleteById(obj.getId());
	}
	
	@Override
	public Company deleteById(int id) throws Exception {
		Company c = this.read(id);
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement(this.SQL_DELETE);
			p.setInt(1, id);
			
			int nbRow = p.executeUpdate();
			return (nbRow == 1) ? c : null;
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
			PreparedStatement p = conn.prepareStatement(this.SQL_SELECT);
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
	
	@Override
	public List<Company> listAll() throws Exception {
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement(this.SQL_LISTALL);
			
			ResultSet r = p.executeQuery();
			List<Company> lst = new ArrayList<Company>();
			while(r.next()) {
				lst.add(new Company(r.getInt("id"),r.getString("name")));
			}
			return lst;
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public List<Company> list(int page, int size) throws Exception {
		if (size <= 0) {
			throw new InvalidPageSizeException(size);
		}
		if (page <= 0) {
			throw new InvalidPageValueException(page);
		}
		int offset = (page-1)*size;
		
		//System.out.println(offset); Merci max pour l'absence de Company [21] :D
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement(this.SQL_LIST);
			p.setInt(1, offset);
			p.setInt(2, size);
			
			ResultSet r = p.executeQuery();
			List<Company> lst = new ArrayList<Company>();
			while(r.next()) {
				lst.add(new Company(r.getInt("id"),r.getString("name")));
			}
			return lst;
			
		} catch (SQLException e) {
			throw e;
		}
	}

}
