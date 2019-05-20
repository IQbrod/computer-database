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

	@Autowired
	private ComputerMapper mapperInstance;
	
	/*-- dtoToModel --*/
	// id
	@Test
	public void TestDtoToModel() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18,"Computer",null,null,0,"None")).equals(new Computer(18,"Computer",null,null,0))
		);
	}
	
	// intro
	@Test
	public void TestDtoToModelDateIntroduction() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18,"Computer","2017-05-27",null,0,"None")).equals(new Computer(18,"Computer",Timestamp.valueOf("2017-05-27 12:00:00"),null,0))
		);
	}
	// disc
	@Test
	public void TestDtoToModelDateDiscontinued() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18,"Computer",null,"2017-05-27",0,"None")).equals(new Computer(18,"Computer",null,Timestamp.valueOf("2017-05-27 12:00:00"),0))
		);
	}

	
	// company_id
	@Test
	public void TestDtoToModelCompanyId() {
		assertTrue(
			mapperInstance.dtoToModel(new ComputerDto(18,"Computer",null,null,12,"None")).equals(new Computer(18,"Computer",null,null,12))
		);
	}
	
	// modelToDto
	@Test
	public void TestModelToDto() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",null,null,0)).equals(new ComputerDto(5,"Entreprise","","",0,"None"))
		);
	}
	
	@Test
	public void TestModelToDtoIntroduction() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",Timestamp.valueOf("2017-05-27 12:00:00"),null,0)).equals(new ComputerDto(5,"Entreprise","2017-05-27","",0,"None"))
		);
	}
	
	@Test
	public void TestModelToDtoDiscontinued() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",null,Timestamp.valueOf("2017-05-27 12:00:00"),0)).equals(new ComputerDto(5,"Entreprise","","2017-05-27",0,"None"))
		);
	}
	
	@Test
	public void TestModelToDtoCompany() {
		assertTrue(
			mapperInstance.modelToDto(new Computer(5,"Entreprise",null,null,5)).equals(new ComputerDto(5,"Entreprise","","",5,"None"))
		);
	}

	
}
