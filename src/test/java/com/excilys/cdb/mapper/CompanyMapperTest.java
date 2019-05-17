package com.excilys.cdb.mapper;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.spring.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= WebMvcConfig.class)
public class CompanyMapperTest {
	@Autowired
	private CompanyMapper mapperInstance;
		
	@Test
	public void TestDtoToModel() {
		assertTrue(
				mapperInstance.dtoToModel(new CompanyDto("5","Entreprise")).equals(new Company(5,"Entreprise"))
		);
	}
	
	@Test
	public void TestModelToDto() {
		assertTrue(
				mapperInstance.modelToDto(new Company(5,"Entreprise")).equals(new CompanyDto("5","Entreprise"))
		);
	}
}
