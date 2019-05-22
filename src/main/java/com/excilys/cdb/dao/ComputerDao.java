package com.excilys.cdb.dao;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.mapper.ComputerRowMapper;
import com.excilys.cdb.dbconnector.JdbcTemplateProvider;
import com.excilys.cdb.enums.ComputerFields;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

@Repository
@Transactional
public class ComputerDao extends Dao<Computer> {
	private static final String SQL_COUNT_BY_NAME = "SELECT count(C.id) as count FROM computer C LEFT JOIN company D ON C.company_id = D.id WHERE UPPER(C.name) LIKE UPPER(:like) or UPPER(D.name) LIKE UPPER(:like)";
	
	public ComputerDao(JdbcTemplateProvider jdbcTemplateProvider, ComputerRowMapper rowMapper) {
		super(
			"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name,:introduced,:discontinued,:companyId);",
			"UPDATE computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:companyId WHERE id=:id;",
			"DELETE FROM computer WHERE id=:id;",
			"SELECT id, name, introduced, discontinued, companyId FROM computer WHERE id=:id;",
			"SELECT C.id as id, C.name as name, introduced, discontinued, company_id  FROM computer C LEFT OUTER JOIN company D ON C.company_id = D.id WHERE UPPER(C.name) LIKE UPPER(:like) or UPPER(D.name) LIKE UPPER(:like) ORDER BY ",
			" LIMIT :offset, :size",
			"SELECT count(id) AS count FROM computer",
			jdbcTemplateProvider,
			rowMapper
		);
		this.logger = LogManager.getLogger(this.getClass());
	}
	
	@Override
	protected Logger getLogger() {
		return this.logger;
	}
	
	@Override
	public Computer create(Computer aComputer) {
		if(aComputer.getId() < 0) {
			throw this.log(new InvalidIdException(aComputer.getId()));
		}
		try {
			SqlParameterSource params = new BeanPropertySqlParameterSource(aComputer);
			if (this.namedTemplate.update(this.sqlCreate, params) == 1)
				return aComputer;
			else {
				throw this.log(new FailedSQLQueryException(this.sqlCreate));
			}
		} catch (DuplicateKeyException exception) {
			throw this.log(new KeyViolationException(aComputer.getId()));			
		} catch (DataAccessException exception) {
			throw this.log(new FailedSQLQueryException(this.sqlCreate));
		}
	}

	@Override
	public Computer update(Computer aComputer) {
		if(aComputer.getId() <= 0) {
			throw this.log(new InvalidIdException(aComputer.getId()));
		}
		
		try {
			SqlParameterSource params = new BeanPropertySqlParameterSource(aComputer);

			if (this.namedTemplate.update(this.sqlUpdate, params) == 1)
				return aComputer;
			else {
				throw this.log(new FailedSQLQueryException(this.sqlUpdate));
			}	
		} catch (DuplicateKeyException exception) {
			throw this.log(new KeyViolationException(aComputer.getId()));			
		} catch (DataAccessException exception) {
			throw this.log(new FailedSQLQueryException(this.sqlUpdate));
		}
	}

	@Override
	public Computer delete(Computer aComputer) {
		return this.deleteById(aComputer.getId());
	}
	
	@Override
	public Computer deleteById(int id) {
		Computer returnComputer = this.read(id);
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);
			this.namedTemplate.update(this.sqlDelete, params);
			return returnComputer;
		} catch (DataAccessException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.sqlDelete),e);
		}
	}

	@Override
	public Computer read(int id) {
		if(id <= 0) {
			throw this.log(new InvalidIdException(id));
		}
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);
			return this.namedTemplate.query(this.sqlSelect, params, this.rowMapper).get(0);
		} catch (DataAccessException e) {
			throw this.log(new FailedSQLQueryBySQLException(this.sqlSelect),e);
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
		if (size <= 0)
			throw this.log(new InvalidPageSizeException(size));
		if (page <= 0)
			throw this.log(new InvalidPageValueException(page));
		int offset = (page-1)*size;
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("like", "%"+name+"%");
			params.addValue("offset", offset);
			params.addValue("size", size);
			
			return this.namedTemplate.query(this.sqlList+ ComputerFields.getOrderByField(orderBy).getField() + sqlLimit, params, this.rowMapper);		
		} catch (DataAccessException e) {
			throw this.log(new FailedSQLQueryException(this.sqlList),e);
		}
	}
	
	public int countByName(String name) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("like", "%"+name+"%");
			
			return this.namedTemplate.queryForObject(ComputerDao.SQL_COUNT_BY_NAME, params, Integer.class);
		} catch (DataAccessException e) {
			throw this.log(new FailedSQLQueryException(ComputerDao.SQL_COUNT_BY_NAME),e);
		}
	}

}
