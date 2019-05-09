package com.excilys.cdb.dbConnector;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.exception.DriverNotFoundException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionProvider {
	private Logger logger = LogManager.getLogger(this.getClass());
	private static HikariConfig config;
	private static HikariDataSource dataSource = null;
	
	private HikariConnectionProvider() {
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
			this.logger.error(except.getMessage()+" caused by "+cause.getMessage(),cause);
			throw except;
		}
		
		config = new HikariConfig();
		
		config.setDriverClassName(bundle.getString("driver"));
		config.setJdbcUrl(bundle.getString("url"));
		config.setUsername(bundle.getString("username"));
		config.setPassword(bundle.getString("password"));
	}
	
	public static HikariDataSource getDataSource() {
		if (dataSource == null)
			dataSource = new HikariDataSource(config);
		return dataSource;
	}
}
