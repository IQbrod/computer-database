package com.excilys.cdb.spring;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.excilys.cdb.database.HikariConnectionProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
		"com.excilys.cdb.service",
		"com.excilys.cdb.database",
		"com.excilys.cdb.repository",
		"com.excilys.cdb.row_mapper",
		"com.excilys.cdb.mapper",
		"com.excilys.cdb.validator",
	})
public class RootConfig {
	@PersistenceContext EntityManager em;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(HikariConnectionProvider hikaricp) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(hikaricp.getDataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.excilys.cdb.model" });
		sessionFactory.setHibernateProperties(this.hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		txManager.setNestedTransactionAllowed(true);
		return txManager;
	}
	
	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(em);
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