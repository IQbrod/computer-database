package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

// TODO: Throw les exceptions jusqu'au controller

public class ComputerDao extends Dao<Computer>{
	private final String SQL_SELECT_UPDATE_COMPANY = "UPDATE computer SET company_id=? WHERE id=?;";
	
	private static ComputerDao instance = new ComputerDao();
	
	private ComputerDao() {
		super(
			"INSERT INTO computer VALUES (?,?,?,?,?);",
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;",
			"DELETE FROM computer WHERE id=?;",
			"SELECT * FROM computer WHERE id=?;",
			"SELECT * FROM computer;",
			"SELECT * FROM computer LIMIT ?,?;"
		);
	}
	
	public static ComputerDao getInstance() {
		return instance;
	}

	@Override
	public Computer create(Computer obj) throws Exception {
		int nbRow = 0;
		
		if(obj.getId() <= 0) {
			throw new InvalidIdException(obj.getId());
		}
		
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_CREATE)
		) {
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			p.setTimestamp(3, obj.getDateIntro());
			p.setTimestamp(4, obj.getDateDisc());
			p.setNull(5, java.sql.Types.INTEGER);
			
			nbRow = p.executeUpdate();
		} catch (SQLException e) {
			throw new PrimaryKeyViolationException(obj.getId());
		}
		
		if (obj.getManufacturer() == 0) {
			if (nbRow == 1) {
				return obj;
			} else {
				throw new FailedSQLQueryException(this.SQL_CREATE);
			}
		} else {
			try (
				Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
				PreparedStatement p = conn.prepareStatement(this.SQL_SELECT_UPDATE_COMPANY);
			) {
				p.setInt(1, obj.getManufacturer());
				p.setInt(2, obj.getId());
				
				nbRow += p.executeUpdate();
				if (nbRow == 2) {
					return obj;
				} else {
					throw new FailedSQLQueryException(this.SQL_SELECT_UPDATE_COMPANY);
				}
			} catch (SQLException e) {
				throw new ForeignKeyViolationException(obj.getManufacturer(), "company");
			}
		}
	}

	@Override
	public Computer update(Computer obj) throws Exception {
		Computer c = this.read(obj.getId());

		if (obj.getName().contentEquals("")) {
			c.setName(obj.getName());
		}
		if (obj.getDateIntro() != null) {
			c.setDateIntro(obj.getDateIntro());
		}
		if (obj.getDateDisc() != null) {
			c.setDateDisc(obj.getDateDisc());
		}
		if (obj.getManufacturer() != -1) {
			c.setManufacturer(obj.getManufacturer());
		}
		
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_UPDATE);
		) {
			p.setString(1, c.getName());
			p.setTimestamp(2, c.getDateIntro());
			p.setTimestamp(3, obj.getDateDisc());
			if (c.getManufacturer() == 0) {
				p.setNull(4, java.sql.Types.INTEGER);
			} else {
				p.setInt(4, c.getManufacturer());
			}
			p.setInt(5, obj.getId());

			if (p.executeUpdate() == 1) {
				return c;
			} else {
				throw new FailedSQLQueryException(this.SQL_UPDATE);
			}		
		} catch (SQLException e) {
			throw new ForeignKeyViolationException(c.getManufacturer(), "company");
		}
	}

	@Override
	public Computer delete(Computer obj) throws Exception {
		return this.deleteById(obj.getId());
	}
	
	public Computer deleteById(int id) throws Exception {
		Computer c = this.read(id);
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_DELETE);
		) {
			p.setInt(1, id);
			
			if (p.executeUpdate() == 1) {
				return c;
			} else {
				throw new FailedSQLQueryException(this.SQL_DELETE);
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public Computer read(int id) throws Exception {
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
				return new Computer(id,r.getString("name"),r.getTimestamp("introduced"),r.getTimestamp("discontinued"), r.getInt("company_id"));
			} else {
				throw new FailedSQLQueryException(this.SQL_SELECT);
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public List<Computer> listAll() throws Exception {
		try (
			Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement p = conn.prepareStatement(this.SQL_LISTALL);
		) {
			
			ResultSet r = p.executeQuery();
			List<Computer> lst = new ArrayList<Computer>();
			while(r.next()) {
				lst.add(new Computer(r.getInt("id"),r.getString("name"),r.getTimestamp("introduced"),r.getTimestamp("discontinued"), r.getInt("company_id")));
			}
			return lst;
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public List<Computer> list(int page, int size) throws Exception {
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
			List<Computer> lst = new ArrayList<Computer>();
			while(r.next()) {
				lst.add(new Computer(r.getInt("id"),r.getString("name"),r.getTimestamp("introduced"),r.getTimestamp("discontinued"), r.getInt("company_id")));
			}
			return lst;
			
		} catch (SQLException e) {
			throw e;
		}
	}

}
