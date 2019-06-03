package com.excilys.cdb.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;

@Component
public class ComputerRowMapper implements RowMapper<Computer>{

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Computer(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("introduced"), rs.getTimestamp("discontinued"), rs.getLong("company_id"));
	}
}
