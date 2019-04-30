package com.excilys.cdb.mapper;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.exception.*;

public class ComputerMapperTest {

	@Test
	public void idToIntTest() {
		assertEquals(6,ComputerMapper.getInstance().idToInt("6"));
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void idToIntTestInvalid() {
		ComputerMapper.getInstance().idToInt("B");
	}
	/*-- dtoToModel --*/
	// id
	@Test
	public void TestDtoToModel() {
		assertTrue(
			ComputerMapper.getInstance().dtoToModel(new ComputerDto("18","Computer",null,null,new CompanyDto("0","None"))).equals(new Computer(18,"Computer",null,null,0))
		);
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void TestDtoToModelInvalidId() {
		ComputerMapper.getInstance().dtoToModel(new ComputerDto("Z","Entreprise",null,null,new CompanyDto("0","None")));
	}
	
	// intro
	@Test
	public void TestDtoToModelDateIntroduction() {
		assertTrue(
			ComputerMapper.getInstance().dtoToModel(new ComputerDto("18","Computer","2017-05-27 12:13:14",null,new CompanyDto("0","None"))).equals(new Computer(18,"Computer",Timestamp.valueOf("2017-05-27 12:13:14"),null,0))
		);
	}
	
	@Test (expected = InvalidDateValueException.class)
	public void TestDtoToModelInvalidDateIntroduction() {
		ComputerMapper.getInstance().dtoToModel(new ComputerDto("18","Entreprise","Une date invalide",null,new CompanyDto("0","None")));
	}
	
	// disc
	@Test
	public void TestDtoToModelDateDiscontinued() {
		assertTrue(
			ComputerMapper.getInstance().dtoToModel(new ComputerDto("18","Computer",null,"2017-05-27 12:13:14",new CompanyDto("0","None"))).equals(new Computer(18,"Computer",null,Timestamp.valueOf("2017-05-27 12:13:14"),0))
		);
	}
	
	@Test (expected = InvalidDateValueException.class)
	public void TestDtoToModelInvalidDateDiscontinued() {
		ComputerMapper.getInstance().dtoToModel(new ComputerDto("18","Entreprise",null,"Une date invalide",new CompanyDto("0","None")));
	}
	
	// company_id
	@Test
	public void TestDtoToModelCompanyId() {
		assertTrue(
			ComputerMapper.getInstance().dtoToModel(new ComputerDto("18","Computer",null,null,new CompanyDto("12","None"))).equals(new Computer(18,"Computer",null,null,12))
		);
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void TestDtoToModelInvalidCompanyId() {
		ComputerMapper.getInstance().dtoToModel(new ComputerDto("18","Entreprise",null,null,new CompanyDto("Entreprise","Entreprise")));
	}
	
	// modelToDto
	@Test
	public void TestModelToDto() {
		assertTrue(
			ComputerMapper.getInstance().modelToDto(new Computer(5,"Entreprise",null,null,0)).equals(new ComputerDto("5","Entreprise","_","_",new CompanyDto("0","None")))
		);
	}
	
	@Test
	public void TestModelToDtoIntroduction() {
		assertTrue(
			ComputerMapper.getInstance().modelToDto(new Computer(5,"Entreprise",Timestamp.valueOf("2017-05-27 12:13:14"),null,0)).equals(new ComputerDto("5","Entreprise","2017-05-27 12:13:14","_",new CompanyDto("0","None")))
		);
	}
	
	@Test
	public void TestModelToDtoDiscontinued() {
		assertTrue(
			ComputerMapper.getInstance().modelToDto(new Computer(5,"Entreprise",null,Timestamp.valueOf("2017-05-27 12:13:14"),0)).equals(new ComputerDto("5","Entreprise","_","2017-05-27 12:13:14",new CompanyDto("0","None")))
		);
	}
	
	@Test
	public void TestModelToDtoCompany() {
		assertTrue(
			ComputerMapper.getInstance().modelToDto(new Computer(5,"Entreprise",null,null,5)).equals(new ComputerDto("5","Entreprise","_","_",new CompanyDto("5","None")))
		);
	}

	
}
