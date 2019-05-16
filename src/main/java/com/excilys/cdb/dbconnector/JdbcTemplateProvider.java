package com.excilys.cdb.dbconnector;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateProvider {
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate jdbcTemplateNamedParameter;
	
	public JdbcTemplateProvider(HikariConnectionProvider connection) {
		this.jdbcTemplateNamedParameter = new NamedParameterJdbcTemplate(connection.getDataSource());
		this.jdbcTemplate = new JdbcTemplate(connection.getDataSource());
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getJdbcTemplateNamedParameter() {
		return jdbcTemplateNamedParameter;
	}
}
