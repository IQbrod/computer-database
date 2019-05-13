package com.excilys.cdb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

@Component
public class CompanyDao extends Dao<Company>{
	private final String sqlDeleteLinkedComputer = "DELETE FROM computer WHERE company_id=?;";
	
	public CompanyDao() {
		super(
			"INSERT INTO company VALUES (?,?);",
			"UPDATE company SET name=? WHERE id=?;",
			"DELETE FROM company WHERE id=?;",
			"SELECT id, name FROM company WHERE id=?;",
			"SELECT id, name FROM company",
			" LIMIT ?,?;",
			"SELECT count(id) AS count FROM company"
		);
		
		this.logger = LogManager.getLogger(this.getClass());
	}
	
	@Override
	protected Logger getLogger() {
		return this.logger;
	}

	@Override
	public Company create(Company aCompany) {
		if(aCompany.getId() <= 0) {
			throw this.log(new InvalidIdException(aCompany.getId()));
		}
		
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlCreate);
		) {
			preparedStatement.setInt(1,aCompany.getId());
			preparedStatement.setString(2, aCompany.getName());
			
			int nbRow = preparedStatement.executeUpdate();
			if (nbRow == 1)
				return aCompany;
			else {
				throw this.log(new FailedSQLQueryException(this.sqlCreate));
			}
		} catch (SQLException e) {
			throw this.log(new PrimaryKeyViolationException(aCompany.getId()),e);
		}
	}

	@Override
	public Company update(Company aCompany) {
		Company company = this.read(aCompany.getId());
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlUpdate);
		) {
			company.setName(aCompany.getName());

			preparedStatement.setString(1, company.getName());
			preparedStatement.setInt(2, aCompany.getId());
			
			if (preparedStatement.executeUpdate() == 1) 
				return company;
			else {
				throw this.log(new FailedSQLQueryException(this.sqlUpdate));
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.sqlUpdate),e);
		}
	}

	@Override
	public Company delete(Company aCompany) {
		return this.deleteById(aCompany.getId());
	}
	
	@Override
	public Company deleteById(int id) {
		Company company = this.read(id);
		try (Connection connection = this.dataSource.getConnection();) {
			connection.setAutoCommit(false);
			
			try (
				PreparedStatement deleteComputer = connection.prepareStatement(this.sqlDeleteLinkedComputer);
				PreparedStatement deleteCompany = connection.prepareStatement(this.sqlDelete);
			) {
				deleteComputer.setInt(1, id);
				deleteComputer.executeUpdate();
				
				deleteCompany.setInt(1, id);
				if (deleteCompany.executeUpdate() == 0)
					throw this.log(new FailedSQLQueryException(this.sqlDelete));
				
				connection.commit();
				return company;
			} catch (SQLException e) {
				if (connection != null) {
					connection.rollback();
				}
				throw e;
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.sqlDeleteLinkedComputer+" or "+this.sqlDelete),e);
		}
	}

	@Override
	public Company read(int id) {
		if(id <= 0) {
			throw this.log(new InvalidIdException(id));
		}
		
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlSelect);
		) {
			preparedStatement.setInt(1, id);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.first()) {
					Company company = new Company(id,resultSet.getString("name"));
					return company;
				} else {
					throw this.log(new FailedSQLQueryException(this.sqlSelect));
				}			
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.sqlSelect),e);
		}
	}
	
	@Override
	public List<Company> listAll() {
		return this.list(1, this.count());
	}
	
	@Override
	public List<Company> list(int page, int size) {
		if (size <= 0) {
			throw this.log(new InvalidPageSizeException(size));
		}
		if (page <= 0) {
			throw this.log(new InvalidPageValueException(page));
		}
		int offset = (page-1)*size;
		
		try (
			Connection connection = this.dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlList+this.sqlLimit);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, size);
			
			try(ResultSet r = preparedStatement.executeQuery()) {
				List<Company> lst = new ArrayList<Company>();
				while(r.next()) {
					lst.add(new Company(r.getInt("id"),r.getString("name")));
				}
				return lst;
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryException(this.sqlList),e);
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
}
