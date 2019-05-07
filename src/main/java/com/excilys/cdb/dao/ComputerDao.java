package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.enums.ComputerFields;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

// TODO: Throw les exceptions jusqu'au controller

public class ComputerDao extends Dao<Computer> {
	private final String SQL_SELECT_UPDATE_COMPANY = "UPDATE computer SET company_id=? WHERE id=?;";
	private final String SQL_INSERT_WITHOUT_ID = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private final String SQL_COUNT_BY_NAME = "SELECT count(*) FROM computer C LEFT JOIN company D ON C.company_id = D.id WHERE UPPER(C.name) LIKE UPPER(?) or UPPER(D.name) LIKE UPPER(?) LIMIT ?,?";
	
	private static ComputerDao instance = null;
	
	private ComputerDao() throws DatabaseProblemException {
		super(
			"INSERT INTO computer VALUES (?,?,?,?,?);",
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;",
			"DELETE FROM computer WHERE id=?;",
			"SELECT * FROM computer WHERE id=?;",
			"SELECT C.id as id, C.name as name, introduced, discontinued, company_id  FROM computer C LEFT OUTER JOIN company D ON C.company_id = D.id WHERE UPPER(C.name) LIKE UPPER(?) or UPPER(D.name) LIKE UPPER(?) ORDER BY ",
			" LIMIT ?,?",
			"SELECT count(*) AS count FROM computer"
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
	public Computer create(Computer obj) throws RuntimeException {
		int nbRow = 0;
		
		if(obj.getId() < 0) {
			throw this.log(new InvalidIdException(obj.getId()));
		} else if (obj.getId() == 0) {
			try (
				Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_INSERT_WITHOUT_ID,Statement.RETURN_GENERATED_KEYS)
			) {
				preparedStatement.setString(1, obj.getName());
				preparedStatement.setTimestamp(2, obj.getDateIntro());
				preparedStatement.setTimestamp(3, obj.getDateDisc());
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
				
				nbRow = preparedStatement.executeUpdate();
				
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next())
						obj.setId((int)generatedKeys.getLong(1));
					else
						throw this.log(new FailedSQLQueryException(SQL_INSERT_WITHOUT_ID));
				}
			} catch (SQLException e) {
				throw this.log(new PrimaryKeyViolationException(obj.getId()),e);
			}
		} else {
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
					this.delete(obj);
					throw this.log(new FailedSQLQueryException(this.SQL_SELECT_UPDATE_COMPANY));
				}
			} catch (SQLException e) {
				this.delete(obj);
				throw this.log(new ForeignKeyViolationException(obj.getManufacturer(), "company"),e);
			}
		}
	}

	@Override
	public Computer update(Computer obj) throws Exception {
		if(obj.getId() <= 0) {
			throw this.log(new InvalidIdException(obj.getId()));
		}
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_UPDATE);
		) {
			preparedStatement.setString(1, obj.getName());
			preparedStatement.setTimestamp(2, obj.getDateIntro());
			preparedStatement.setTimestamp(3, obj.getDateDisc());
			if (obj.getManufacturer() == 0) {
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			} else {
				preparedStatement.setInt(4, obj.getManufacturer());
			}
			preparedStatement.setInt(5, obj.getId());

			if (preparedStatement.executeUpdate() == 1) {
				return this.read(obj.getId());
			} else {
				throw this.log(new FailedSQLQueryException(this.SQL_UPDATE));
			}		
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.SQL_UPDATE),e);
		}
	}

	@Override
	public Computer delete(Computer obj) throws RuntimeException {
		return this.deleteById(obj.getId());
	}
	
	@Override
	public Computer deleteById(int id) throws RuntimeException {
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
			throw this.log(new FailedSQLQueryBySQLException(this.SQL_DELETE),e);
		}
	}

	@Override
	public Computer read(int id) throws RuntimeException {
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
				throw this.log(new FailedSQLQueryException(this.SQL_SELECT));
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.SQL_SELECT),e);
		}
	}
	
	@Override
	public int count() throws RuntimeException {
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_COUNT);
		) {
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return (resultSet.next()) ? resultSet.getInt("count") : 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw this.log(new FailedSQLQueryException(this.SQL_COUNT),e);
		}
	}	

	@Override
	public List<Computer> listAll() throws RuntimeException {
		return this.listAll("id");
	}	
	public List<Computer> listAll(String orderBy) throws RuntimeException {
		return this.list(1, this.count(), orderBy);
	}
	
	@Override
	public List<Computer> list(int page, int size) throws RuntimeException {
		return this.list(page, size, "id");
	}	
	public List<Computer> list(int page, int size, String orderBy) throws RuntimeException {
		return this.listByName("", page, size, orderBy);
	}
	
	public List<Computer> listByName(String name, int page, int size, String orderBy) throws RuntimeException {
		if (size <= 0) {
			throw this.log(new InvalidPageSizeException(size));
		}
		if (page <= 0) {
			throw this.log(new InvalidPageValueException(page));
			
		}
		int offset = (page-1)*size;
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_LIST+ ComputerFields.getOrderByField(orderBy).field + SQL_LIMIT);
		) {
			preparedStatement.setString(1, "%"+name+"%");
			preparedStatement.setString(2, "%"+name+"%");
			preparedStatement.setInt(3, offset);
			preparedStatement.setInt(4, size);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			return computerList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw this.log(new FailedSQLQueryException(this.SQL_LIST),e);
		}
	}
	
	public int countByName(String name) throws RuntimeException {
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_COUNT_BY_NAME);
		) {
			preparedStatement.setString(1, "%"+name+"%");
			preparedStatement.setString(2, "%"+name+"%");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return (resultSet.next()) ? resultSet.getInt("count") : 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw this.log(new FailedSQLQueryException(this.SQL_COUNT_BY_NAME),e);
		}
	}

}
