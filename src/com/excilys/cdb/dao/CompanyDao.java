package com.excilys.cdb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

public class CompanyDao extends Dao<Company>{
	private static CompanyDao instance = new CompanyDao();
	
	private CompanyDao() {
		super(
			"INSERT INTO company VALUES (?,?);",
			"UPDATE company SET name=? WHERE id=?;",
			"DELETE FROM company WHERE id=?;",
			"SELECT * FROM company WHERE id=?;",
			"SELECT * FROM company;",
			"SELECT * FROM company LIMIT ?,?;"
		);
	}
	
	public static CompanyDao getInstance() {
		return instance;
	}

	@Override
	public Company create(Company obj) throws Exception{
		if(obj.getId() <= 0) {
			throw new InvalidIdException(obj.getId());
		}
		
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_CREATE);
		) {
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			
			int nbRow = p.executeUpdate();
			if (nbRow == 1)
				return obj;
			else
				throw new FailedSQLQueryException(this.SQL_CREATE);
		} catch (SQLException e) {
			throw new PrimaryKeyViolationException(obj.getId());
		}
	}

	@Override
	public Company update(Company obj) throws Exception {
		// Read
		Company c = this.read(obj.getId());
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_UPDATE);
		) {
			c.setName(obj.getName());

			p.setString(1, c.getName());
			p.setInt(2, c.getId());
			
			if (p.executeUpdate() == 1) 
				return c;
			else
				throw new FailedSQLQueryException(this.SQL_UPDATE);
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
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_DELETE);
		) {
			p.setInt(1, id);
			
			if (p.executeUpdate() == 1) 
				return c;
			else
				throw new FailedSQLQueryException(this.SQL_DELETE);
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public Company read(int id) throws Exception {
		if(id <= 0) {
			throw new InvalidIdException(id);
		}
		
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_SELECT);
		) {
			p.setInt(1, id);
			
			ResultSet r = p.executeQuery();
			if(r.first()) {
				Company c = new Company(id,r.getString("name"));
				return c;
			} else {
				throw new FailedSQLQueryException(this.SQL_SELECT);
			}
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public List<Company> listAll() throws Exception {
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_LISTALL);
		) {
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
		
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_LIST);
		) {
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
