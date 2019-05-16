package com.excilys.cdb.dao;

import java.sql.*;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dao.mapper.CompanyRowMapper;
import com.excilys.cdb.dbconnector.JdbcTemplateProvider;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

@Repository
public class CompanyDao extends Dao<Company>{
	private static final String SQL_DELETE_LINKED_COMPUTERS = "DELETE FROM computer WHERE company_id=?;";
	
	public CompanyDao(JdbcTemplateProvider templateProvider, CompanyRowMapper rowMapper) {		
		super(
			"INSERT INTO company VALUES (:id,:name);",
			"UPDATE company SET name=:name WHERE id=:id;",
			
			"DELETE FROM company WHERE id=?;",
			
			"SELECT id, name FROM company WHERE id=:id;",
			"SELECT id, name FROM company",
			" LIMIT :offset,:size;",
			"SELECT count(id) AS count FROM company",
			templateProvider,
			rowMapper
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
		
		try {
			SqlParameterSource params = new BeanPropertySqlParameterSource(aCompany);
			
			if (this.namedTemplate.update(this.sqlCreate, params) == 1)
				return aCompany;
			else {
				throw this.log(new FailedSQLQueryException(this.sqlCreate));
			}
		} catch (DuplicateKeyException exception) {
			throw this.log(new KeyViolationException(aCompany.getId()));			
		} catch (DataAccessException exception) {
			throw this.log(new FailedSQLQueryException(this.sqlCreate));
		}
	}

	@Override
	public Company update(Company aCompany) {
		this.read(aCompany.getId());
		try {
			SqlParameterSource params = new BeanPropertySqlParameterSource(aCompany);
			
			if (this.namedTemplate.update(this.sqlUpdate, params) == 1) 
				return aCompany;
			else {
				throw this.log(new FailedSQLQueryException(this.sqlUpdate));
			}
		} catch (DuplicateKeyException exception) {
			throw this.log(new KeyViolationException(aCompany.getId()));			
		} catch (DataAccessException exception) {
			throw this.log(new FailedSQLQueryException(this.sqlCreate));
		}
	}

	
	@Override
	public Company delete(Company aCompany) {
		return this.deleteById(aCompany.getId());
	}
	
	@Override
	public Company deleteById(int id) {
		Company company = this.read(id);
		try (Connection connection = DataSourceUtils.getConnection(this.namedTemplate.getJdbcTemplate().getDataSource());) {
			connection.setAutoCommit(false);
			
			try (
				PreparedStatement deleteComputer = connection.prepareStatement(CompanyDao.SQL_DELETE_LINKED_COMPUTERS);
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
				connection.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw this.log(new FailedSQLQueryBySQLException(CompanyDao.SQL_DELETE_LINKED_COMPUTERS+" or "+this.sqlDelete),e);
		}
	}

	@Override
	public Company read(int id) {
		if(id <= 0)
			throw this.log(new InvalidIdException(id));
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);
			return this.namedTemplate.query(this.sqlSelect, params, this.rowMapper).get(0);
		} catch (IndexOutOfBoundsException | DataAccessException e) {
			throw this.log(new FailedSQLQueryException(this.sqlSelect),e);
		}
	}
	
	@Override
	public List<Company> listAll() {
		return this.list(1, this.count());
	}
	
	@Override
	public List<Company> list(int page, int size) {
		if (size <= 0)
			throw this.log(new InvalidPageSizeException(size));
		if (page <= 0)
			throw this.log(new InvalidPageValueException(page));
		int offset = (page-1)*size;
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("offset", offset);
			params.addValue("size", size);
			
			return this.namedTemplate.query(this.sqlList+sqlLimit, params, this.rowMapper);
		} catch (DataAccessException e) {
			throw this.log(new FailedSQLQueryException(this.sqlList),e);
		}
	}
	
	@Override
	public int count() {
		try {
			return this.template.queryForObject(this.sqlCount, Integer.class);
		} catch (DataAccessException e) {
			throw this.log(new FailedSQLQueryException(this.sqlCount),e);
		}
	}
}
