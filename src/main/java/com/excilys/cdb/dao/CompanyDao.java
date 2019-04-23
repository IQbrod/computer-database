package com.excilys.cdb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

public class CompanyDao extends Dao<Company>{
	private static CompanyDao instance = null;
	
	private CompanyDao() throws DatabaseProblemException {
		super(
			"INSERT INTO company VALUES (?,?);",
			"UPDATE company SET name=? WHERE id=?;",
			"DELETE FROM company WHERE id=?;",
			"SELECT * FROM company WHERE id=?;",
			"SELECT * FROM company;",
			"SELECT * FROM company LIMIT ?,?;"
		);
	}
	
	public static CompanyDao getInstance() throws DatabaseProblemException {
		if (instance == null)
			instance = new CompanyDao();
		return instance;
	}

	@Override
	public Company create(Company obj) throws Exception{
		if(obj.getId() <= 0) {
			throw new InvalidIdException(obj.getId());
		}
		
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_CREATE);
		) {
			preparedStatement.setInt(1,obj.getId());
			preparedStatement.setString(2, obj.getName());
			
			int nbRow = preparedStatement.executeUpdate();
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
		Company company = this.read(obj.getId());
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_UPDATE);
		) {
			company.setName(obj.getName());

			preparedStatement.setString(1, company.getName());
			preparedStatement.setInt(2, company.getId());
			
			if (preparedStatement.executeUpdate() == 1) 
				return company;
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
		Company company = this.read(id);
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_DELETE);
		) {
			preparedStatement.setInt(1, id);
			
			if (preparedStatement.executeUpdate() == 1) 
				return company;
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
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_SELECT);
		) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.first()) {
				Company company = new Company(id,resultSet.getString("name"));
				return company;
			} else {
				throw new InvalidIdException(id);
			}
		} catch (SQLException e) {
			throw new FailedSQLQueryException(this.SQL_SELECT);
		}
	}
	
	@Override
	public List<Company> listAll() throws Exception {
		try (
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_LISTALL);
		) {
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Company> companyList = new ArrayList<Company>();
			while(resultSet.next()) {
				companyList.add(new Company(resultSet.getInt("id"),resultSet.getString("name")));
			}
			return companyList;
			
		} catch (SQLException e) {
			throw new FailedSQLQueryException(this.SQL_LISTALL);
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
			Connection connection = DriverManager.getConnection(this.DBACCESS, this.DBUSER, this.DBPASS);
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_LIST);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, size);
			
			ResultSet r = preparedStatement.executeQuery();
			List<Company> lst = new ArrayList<Company>();
			while(r.next()) {
				lst.add(new Company(r.getInt("id"),r.getString("name")));
			}
			return lst;
			
		} catch (SQLException e) {
			throw new FailedSQLQueryException(this.SQL_LIST);
		}
	}

}
