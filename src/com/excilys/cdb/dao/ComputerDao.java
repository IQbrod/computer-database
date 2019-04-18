package com.excilys.cdb.dao;

import java.sql.*;

import com.excilys.cdb.exception.ForeignKeyViolationException;
import com.excilys.cdb.exception.InvalidIdException;
import com.excilys.cdb.exception.PrimaryKeyViolationException;
import com.excilys.cdb.model.*;

// TODO: Throw les exceptions jusqu'au controller

public class ComputerDao extends Dao<Computer>{

	public ComputerDao() {
		super();
	}

	@Override
	public boolean create(Computer obj) throws Exception {
		int nbRow = 0;
		
		if(obj.getId() <= 0) {
			throw new InvalidIdException(obj.getId());
		}
		
		try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
			PreparedStatement p = conn.prepareStatement("INSERT INTO computer VALUES (?,?,?,?,?);");
			p.setInt(1,obj.getId());
			p.setString(2, obj.getName());
			p.setTimestamp(3, obj.getDateIntro());
			p.setTimestamp(4, obj.getDateDisc());
			// Help: https://stackoverflow.com/questions/14514589/inserting-null-to-an-integer-column-using-jdbc
			p.setNull(5, java.sql.Types.INTEGER);
			
			nbRow = p.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new PrimaryKeyViolationException(obj.getId());
		}
		
		if (obj.getManufacturer() != 0) {
			try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
				PreparedStatement p = conn.prepareStatement("UPDATE computer SET company_id=? WHERE id=?;");
				p.setInt(1, obj.getManufacturer());
				p.setInt(2, obj.getId());
				
				nbRow += p.executeUpdate();
				return nbRow == 2;
			} catch (SQLIntegrityConstraintViolationException e) {
				throw new ForeignKeyViolationException(obj.getManufacturer(), "company");
			}
		} else {
			return nbRow == 1;
		}
	}

	@Override
	public boolean update(Computer obj) throws Exception {
		// Check id
		if (obj.getId() <= 0) {
			throw new InvalidIdException(obj.getId());
		}
		// Count differences
		int diff = 0;
		int nbUpdate = 0;
		// Update name
		if (! obj.getName().contentEquals("")) {
			diff += 1;
			try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
				PreparedStatement p = conn.prepareStatement("UPDATE computer SET name=? WHERE id=?;");
				p.setString(1, obj.getName());
				p.setInt(2,obj.getId());
				
				nbUpdate += p.executeUpdate();
			} catch (SQLException e) {
				// Never Seen Yet
				throw e;
			}
		}
		// Update date1
		if (obj.getDateIntro() != null) {
			diff += 1;
			try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
				PreparedStatement p = conn.prepareStatement("UPDATE computer SET introduced=? WHERE id=?;");
				p.setTimestamp(1, obj.getDateIntro());
				p.setInt(2,obj.getId());
				
				nbUpdate += p.executeUpdate();
			} catch (SQLException e) {
				// Never Seen Yet
				throw e;
			}
		}
		// Update date2
		if (obj.getDateDisc() != null) {
			diff += 1;
			try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
				PreparedStatement p = conn.prepareStatement("UPDATE computer SET discontinued=? WHERE id=?;");
				p.setTimestamp(1, obj.getDateDisc());
				p.setInt(2,obj.getId());
				
				nbUpdate += p.executeUpdate();
			} catch (SQLException e) {
				// Never Seen Yet
				throw e;
			}
		}
		// Update cid
		if (obj.getManufacturer() != 0) {
			diff += 1;
			try (Connection conn = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS)) {
				PreparedStatement p = conn.prepareStatement("UPDATE computer SET company_id=? WHERE id=?;");
				p.setTimestamp(1, obj.getDateDisc());
				p.setInt(2,obj.getId());
				
				nbUpdate += p.executeUpdate();
			} catch (SQLException e) {
				throw new ForeignKeyViolationException(obj.getManufacturer(), "company");
			}
		}
		return diff == nbUpdate;
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
	public Computer read(int id) throws Exception {
		if(id <= 0) {
			throw new InvalidIdException(id);
		}
		
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
			throw e;
		}
	}

}
