package com.excilys.cdb.mapper;

import static org.junit.Assert.*;
import org.junit.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.exception.*;

public class CompanyMapperTest {

	@Test
	public void TestDtoToModel() {
		assertTrue(
			CompanyMapper.getInstance().dtoToModel(new CompanyDto("5","Entreprise")).equals(new Company(5,"Entreprise"))
		);
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void TestDtoToModelInvalidId() {
		CompanyMapper.getInstance().dtoToModel(new CompanyDto("Id","Entreprise"));
	}
	
	@Test
	public void TestModelToDto() {
		assertTrue(
			CompanyMapper.getInstance().modelToDto(new Company(5,"Entreprise")).equals(new CompanyDto("5","Entreprise"))
		);
	}
}
