package com.excilys.cdb.spring;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.excilys.cdb.dbconnector.HikariConnectionProvider;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
		"com.excilys.cdb.dao",
		"com.excilys.cdb.dbconnector",
		"com.excilys.cdb.mapper",
		"com.excilys.cdb.service",
		"com.excilys.cdb.validator"
	})
public class RootConfig {	
	
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(HikariConnectionProvider hikaricp) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(hikaricp.getDataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.excilys.cdb.model" });
		sessionFactory.setHibernateProperties(this.hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}
	
	private Properties hibernateProperties() {
		return new Properties() {
			private static final long serialVersionUID = 27052019L;
			{
				setProperty("hibernate.hbm2ddl.auto", "validate");
				setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
				setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
	}
}