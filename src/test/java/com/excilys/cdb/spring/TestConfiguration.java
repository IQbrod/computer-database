package com.excilys.cdb.spring;

import org.springframework.context.annotation.*;

@Configuration
@Import(RootConfig.class)
@ComponentScan(basePackages = { "com.excilys.cdb.cli" })
public class TestConfiguration {

}