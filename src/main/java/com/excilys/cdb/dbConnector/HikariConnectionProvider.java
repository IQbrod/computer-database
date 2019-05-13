package com.excilys.cdb.dbConnector;

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
		
		try {
			Class.forName(bundle.getString("driver"));
		} catch (ClassNotFoundException cause) {
			DriverNotFoundException except = new DriverNotFoundException(bundle.getString("driver"));
			LogManager.getLogger(HikariConnectionProvider.class).error(except.getMessage()+" caused by "+cause.getMessage(),cause);
			throw except;
		}
		
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName(bundle.getString("driver"));
		config.setJdbcUrl(bundle.getString("url"));
		config.setUsername(bundle.getString("username"));
		config.setPassword(bundle.getString("password"));
		
		dataSource = new HikariDataSource(config);
	}
}
