package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

// TODO: Throw les exceptions jusqu'au controller

public class ComputerDao extends Dao<Computer> {
	private final String SQL_SELECT_UPDATE_COMPANY = "UPDATE computer SET company_id=? WHERE id=?;";
	
	private static ComputerDao instance = null;
	
	private ComputerDao() throws DatabaseProblemException {
		super(
			"INSERT INTO computer VALUES (?,?,?,?,?);",
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;",
			"DELETE FROM computer WHERE id=?;",
			"SELECT * FROM computer WHERE id=?;",
			"SELECT * FROM computer;",
			"SELECT * FROM computer LIMIT ?,?;"
		);
		this.logger = (Logger) LogManager.getLogger(this.getClass());
	}
	
	public static ComputerDao getInstance() throws DatabaseProblemException {
		if (instance == null)
			instance = new ComputerDao();
		return instance;
	}
	
	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@Override
	public Computer create(Computer obj) throws Exception {
		int nbRow = 0;
		
		if(obj.getId() <= 0) {
			throw this.log(new InvalidIdException(obj.getId()));
		}
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_CREATE)
		) {
			preparedStatement.setInt(1,obj.getId());
			preparedStatement.setString(2, obj.getName());
			preparedStatement.setTimestamp(3, obj.getDateIntro());
			preparedStatement.setTimestamp(4, obj.getDateDisc());
			preparedStatement.setNull(5, java.sql.Types.INTEGER);
			
			nbRow = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw this.log(new PrimaryKeyViolationException(obj.getId()),e);
		}
		
		if (obj.getManufacturer() == 0) {
			if (nbRow == 1) {
				return obj;
			} else {
				throw this.log(new FailedSQLQueryException(this.SQL_CREATE));
			}
		} else {
			try (
				Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_SELECT_UPDATE_COMPANY);
			) {
				preparedStatement.setInt(1, obj.getManufacturer());
				preparedStatement.setInt(2, obj.getId());
				
				nbRow += preparedStatement.executeUpdate();
				if (nbRow == 2) {
					return obj;
				} else {
					throw this.log(new FailedSQLQueryException(this.SQL_SELECT_UPDATE_COMPANY));
				}
			} catch (SQLException e) {
				throw this.log(new ForeignKeyViolationException(obj.getManufacturer(), "company"),e);
			}
		}
	}

	@Override
	public Computer update(Computer obj) throws Exception {
		Computer returnComputer = this.read(obj.getId());

		if (obj.getName().contentEquals("")) {
			returnComputer.setName(obj.getName());
		}
		if (obj.getDateIntro() != null) {
			returnComputer.setDateIntro(obj.getDateIntro());
		}
		if (obj.getDateDisc() != null) {
			returnComputer.setDateDisc(obj.getDateDisc());
		}
		if (obj.getManufacturer() != -1) {
			returnComputer.setManufacturer(obj.getManufacturer());
		}
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_UPDATE);
		) {
			preparedStatement.setString(1, returnComputer.getName());
			preparedStatement.setTimestamp(2, returnComputer.getDateIntro());
			preparedStatement.setTimestamp(3, obj.getDateDisc());
			if (returnComputer.getManufacturer() == 0) {
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			} else {
				preparedStatement.setInt(4, returnComputer.getManufacturer());
			}
			preparedStatement.setInt(5, obj.getId());

			if (preparedStatement.executeUpdate() == 1) {
				return returnComputer;
			} else {
				throw this.log(new FailedSQLQueryException(this.SQL_UPDATE));
			}		
		} catch (SQLException e) {
			throw this.log(new ForeignKeyViolationException(returnComputer.getManufacturer(), "company"),e);

		}
	}

	@Override
	public Computer delete(Computer obj) throws Exception {
		return this.deleteById(obj.getId());
	}
	
	public Computer deleteById(int id) throws Exception {
		Computer returnComputer = this.read(id);
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_DELETE);
		) {
			preparedStatement.setInt(1, id);
			
			if (preparedStatement.executeUpdate() == 1) {
				return returnComputer;
			} else {
				throw this.log(new FailedSQLQueryException(this.SQL_DELETE));
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.SQL_DELETE),e);
		}
	}

	@Override
	public Computer read(int id) throws Exception {
		if(id <= 0) {
			throw this.log(new InvalidIdException(id));
		}
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_SELECT);
		) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.first()) {
				return new Computer(id,resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id"));
			} else {
				throw this.log(new InvalidIdException(id));
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.SQL_SELECT));
		}
	}

	@Override
	public List<Computer> listAll() throws Exception {
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_LISTALL);
		) {
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			return computerList;
			
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.SQL_LISTALL),e);
		}
	}
	
	@Override
	public List<Computer> list(int page, int size) throws Exception {
		if (size <= 0) {
			throw this.log(new InvalidPageSizeException(size));
		}
		if (page <= 0) {
			throw this.log(new InvalidPageValueException(page));
			
		}
		int offset = (page-1)*size;
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_LIST);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, size);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			return computerList;
			
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.SQL_LIST),e);
		}
	}

}
