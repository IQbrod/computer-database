package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.enums.ComputerFields;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

public class ComputerDao extends Dao<Computer> {
	private final String sqlSelectUpdate = "UPDATE computer SET company_id=? WHERE id=?;";
	private final String sqlInsertNoId = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
	private final String sqlCountByName = "SELECT count(*) FROM computer C LEFT JOIN company D ON C.company_id = D.id WHERE UPPER(C.name) LIKE UPPER(?) or UPPER(D.name) LIKE UPPER(?) LIMIT ?,?";
	
	private static ComputerDao instance = null;
	
	private ComputerDao() {
		super(
			"INSERT INTO computer VALUES (?,?,?,?,?);",
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;",
			"DELETE FROM computer WHERE id=?;",
			"SELECT * FROM computer WHERE id=?;",
			"SELECT C.id as id, C.name as name, introduced, discontinued, company_id  FROM computer C LEFT OUTER JOIN company D ON C.company_id = D.id WHERE UPPER(C.name) LIKE UPPER(?) or UPPER(D.name) LIKE UPPER(?) ORDER BY ",
			" LIMIT ?,?",
			"SELECT count(*) AS count FROM computer"
		);
		this.logger = LogManager.getLogger(this.getClass());
	}
	
	public static ComputerDao getInstance() {
		if (instance == null)
			instance = new ComputerDao();
		return instance;
	}
	
	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@Override
	public Computer create(Computer obj) {
		int nbRow = 0;
		
		if(obj.getId() < 0) {
			throw this.log(new InvalidIdException(obj.getId()));
		} else if (obj.getId() == 0) {
			try (
				Connection connection = this.dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.sqlInsertNoId,Statement.RETURN_GENERATED_KEYS)
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
						throw this.log(new FailedSQLQueryException(sqlInsertNoId));
				}
			} catch (SQLException e) {
				throw this.log(new PrimaryKeyViolationException(obj.getId()),e);
			}
		} else {
			try (
				Connection connection = this.dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.sqlCreate)
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
				throw this.log(new FailedSQLQueryException(this.sqlCreate));
			}
		} else {
			try (
				Connection connection = this.dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.sqlSelectUpdate);
			) {
				preparedStatement.setInt(1, obj.getManufacturer());
				preparedStatement.setInt(2, obj.getId());
				
				nbRow += preparedStatement.executeUpdate();
				if (nbRow == 2) {
					return obj;
				} else {
					this.delete(obj);
					throw this.log(new FailedSQLQueryException(this.sqlSelectUpdate));
				}
			} catch (SQLException e) {
				this.delete(obj);
				throw this.log(new ForeignKeyViolationException(obj.getManufacturer(), "company"),e);
			}
		}
	}

	@Override
	public Computer update(Computer obj) {
		if(obj.getId() <= 0) {
			throw this.log(new InvalidIdException(obj.getId()));
		}
		
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlUpdate);
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
				throw this.log(new FailedSQLQueryException(this.sqlUpdate));
			}		
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.sqlUpdate),e);
		}
	}

	@Override
	public Computer delete(Computer obj) {
		return this.deleteById(obj.getId());
	}
	
	@Override
	public Computer deleteById(int id) {
		Computer returnComputer = this.read(id);
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlDelete);
		) {
			preparedStatement.setInt(1, id);
			
			if (preparedStatement.executeUpdate() == 1) {
				return returnComputer;
			} else {
				throw this.log(new FailedSQLQueryException(this.sqlDelete));
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.sqlDelete),e);
		}
	}

	@Override
	public Computer read(int id) {
		if(id <= 0) {
			throw this.log(new InvalidIdException(id));
		}
		
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlSelect);
		) {
			preparedStatement.setInt(1, id);
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.first()) {
					return new Computer(id,resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id"));
				} else {
					throw this.log(new FailedSQLQueryException(this.sqlSelect));
				}
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.sqlSelect),e);
		}
	}
	
	@Override
	public int count() {
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlCount);
		) {
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				return (resultSet.next()) ? resultSet.getInt("count") : 0;
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.sqlCount),e);
		}
	}	

	@Override
	public List<Computer> listAll() {
		return this.listAll("id");
	}	
	public List<Computer> listAll(String orderBy) {
		return this.list(1, this.count(), orderBy);
	}
	
	@Override
	public List<Computer> list(int page, int size) {
		return this.list(page, size, "id");
	}	
	public List<Computer> list(int page, int size, String orderBy) {
		return this.listByName("", page, size, orderBy);
	}
	
	public List<Computer> listByName(String name, int page, int size, String orderBy) {
		if (size <= 0) {
			throw this.log(new InvalidPageSizeException(size));
		}
		if (page <= 0) {
			throw this.log(new InvalidPageValueException(page));
			
		}
		int offset = (page-1)*size;
		
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlList+ ComputerFields.getOrderByField(orderBy).getField() + sqlLimit);
		) {
			preparedStatement.setString(1, "%"+name+"%");
			preparedStatement.setString(2, "%"+name+"%");
			preparedStatement.setInt(3, offset);
			preparedStatement.setInt(4, size);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				List<Computer> computerList = new ArrayList<Computer>();
				while(resultSet.next()) {
					computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
				}
				return computerList;
			}			
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.sqlList),e);
		}
	}
	
	public int countByName(String name) {
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlCountByName);
		) {
			preparedStatement.setString(1, "%"+name+"%");
			preparedStatement.setString(2, "%"+name+"%");
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return (resultSet.next()) ? resultSet.getInt("count") : 0;
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.sqlCountByName),e);
		}
	}

}
