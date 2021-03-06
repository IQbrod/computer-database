package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.spring.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfiguration.class)
public class ComputerServiceTest {

	private ComputerService serviceInstance;
	@Autowired
	void setInstance(ComputerService beanInjection) {
		this.serviceInstance = beanInjection;
	}

	@Test
	public void createTest() throws Exception {
		serviceInstance.create(new Computer(888,"Ordinateur",null,null,0L));
	}
	
	@Test
	public void readTest() throws Exception {
		serviceInstance.read(8).getId();
	}
	
	@Test
	public void updateTest() throws Exception {
		serviceInstance.update(new Computer(27,"Updated",null,null,0L));
	}
	
	@Test
	public void deleteTest() throws Exception {
		serviceInstance.delete(new Computer(888,"DeleteMe",null,null,0L));
	}
	
	@Test
	public void listAllTest() throws Exception {
		assertTrue(serviceInstance.listAllElements().size() > 0);
	}
	
	@Test
	public void listTest() throws Exception {
		assertEquals(20,serviceInstance.list(1, 20).size());
	}
	
	@Test
	public void countTest() throws Exception {
		assertEquals(serviceInstance.listAllElements().size(), serviceInstance.count());
	}
}
