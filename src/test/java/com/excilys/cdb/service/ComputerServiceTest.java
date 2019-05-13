package com.excilys.cdb.service;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.spring.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AppConfig.class)
public class ComputerServiceTest {
	@Autowired
	private ComputerService serviceInstance;

	@Test
	public void createTest() throws Exception {
		serviceInstance.create(new Computer(888,"Ordinateur",null,null,0));
	}
	
	@Test
	public void readTest() throws Exception {
		serviceInstance.read(8);
	}
	
	@Test
	public void updateTest() throws Exception {
		serviceInstance.update(new Computer(27,"Updated",null,null,0));
	}
	
	@Test
	public void deleteTest() throws Exception {
		serviceInstance.delete(new Computer(888,"DeleteMe",null,null,0));
	}
	
	@Test
	public void listAllTest() throws Exception {
		serviceInstance.listAllElements();
	}
	
	@Test
	public void listTest() throws Exception {
		serviceInstance.list(1, 20);
	}
	
	@Test
	public void countTest() throws Exception {
		assertEquals(serviceInstance.listAllElements().size(), serviceInstance.count());
	}
}
