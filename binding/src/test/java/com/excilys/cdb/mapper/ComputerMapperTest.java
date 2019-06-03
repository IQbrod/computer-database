package com.excilys.cdb.mapper;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.spring.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfiguration.class)
public class ComputerMapperTest {

	private ComputerMapper mapperInstance;
	
	@Autowired
	void setInstance(ComputerMapper beanInjection) {
		this.mapperInstance = beanInjection;
	}
	/*-- dtoToModel --*/
	// id
	@Test
	public void TestDtoToModel() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18L,"Computer",null,null,0L,"None")).equals(new Computer(18,"Computer",null,null,0L))
		);
	}
	
	// intro
	@Test
	public void TestDtoToModelDateIntroduction() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18L,"Computer","2017-05-27",null,0L,"None")).equals(new Computer(18,"Computer",Timestamp.valueOf("2017-05-27 12:00:00"),null,0L))
		);
	}
	// disc
	@Test
	public void TestDtoToModelDateDiscontinued() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18L,"Computer",null,"2017-05-27",0L,"None")).equals(new Computer(18,"Computer",null,Timestamp.valueOf("2017-05-27 12:00:00"),0L))
		);
	}

	
	// company_id
	@Test
	public void TestDtoToModelCompanyId() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18L,"Computer",null,null,12L,"None")).equals(new Computer(18,"Computer",null,null,12L))
		);
	}
	
	// modelToDto
	@Test
	public void TestModelToDto() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",null,null,0L)).equals(new ComputerDto(5L,"Entreprise","","",0L,"None"))
		);
	}
	
	@Test
	public void TestModelToDtoIntroduction() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",Timestamp.valueOf("2017-05-27 12:00:00"),null,0L)).equals(new ComputerDto(5L,"Entreprise","2017-05-27","",0L,"None"))
		);
	}
	
	@Test
	public void TestModelToDtoDiscontinued() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",null,Timestamp.valueOf("2017-05-27 12:00:00"),0L)).equals(new ComputerDto(5L,"Entreprise","","2017-05-27",0L,"None"))
		);
	}
	
	@Test
	public void TestModelToDtoCompany() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",null,null,5L)).equals(new ComputerDto(5L,"Entreprise","","",5L,"None"))
		);
	}

	
}
