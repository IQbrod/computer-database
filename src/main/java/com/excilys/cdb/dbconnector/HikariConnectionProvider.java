package com.excilys.cdb.dbconnector;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.DriverNotFoundException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class HikariConnectionProvider {
	public final HikariDataSource dataSource;
	
	public HikariConnectionProvider() {
		ResourceBundle bundle;
		try {
			bundle = ResourceBundle.getBundle("dbconfig");
		} catch (MissingResourceException ex) {
			bundle = ResourceBundle.getBundle("dbconfig_travis");
		}
		
		String driver = bundle.getString("driver");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException cause) {
			DriverNotFoundException except = new DriverNotFoundException(driver);
			LogManager.getLogger(HikariConnectionProvider.class).error(except.getMessage()+" caused by "+cause.getMessage(),cause);
			throw except;
		}
		
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName(driver);
		config.setJdbcUrl(bundle.getString("url"));
		config.setUsername(bundle.getString("username"));
		config.setPassword(bundle.getString("password"));
		
		dataSource = new HikariDataSource(config);
	}
}
