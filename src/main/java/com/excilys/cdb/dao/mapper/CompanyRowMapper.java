package com.excilys.cdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class CompanyRowMapper implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Company(rs.getInt("id"),rs.getString("name"));
	}
}
