package com.excilys.cdb.spring;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {
		"com.excilys.cdb.dao",
		"com.excilys.cdb.dbconnector",
		"com.excilys.cdb.mapper",
		"com.excilys.cdb.service",
		"com.excilys.cdb.validator"
	})
public class RootConfig {
	
}