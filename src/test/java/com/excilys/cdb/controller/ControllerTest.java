package com.excilys.cdb.controller;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.spring.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AppConfig.class)
public class ControllerTest {

	// Empty
	@Test
	public void emptyTest() throws Exception {
		assertEquals("", CliController.getInstance().treatMessage(""));
	}
	
	// Invalid
	@Test (expected = UnknownCommandException.class)
	public void invalidTest() throws Exception {
		CliController.getInstance().treatMessage("cmd invalid");
	}
	
	// Help
	@Test
	public void helpTest() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("help").startsWith("Please use"));
	}
	
	// Read
	@Test (expected = MissingArgumentException.class)
	public void lowerReadTest() throws Exception {
		CliController.getInstance().treatMessage("R table");
	}
	
	@Test
	public void readCompanyTest() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("R company 5").startsWith("Company"));
	}
	
	@Test
	public void readComputerTest() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("R computer 5").startsWith("Computer"));
	}
	
	@Test (expected = InvalidTableException.class)
	public void readUnknownTableTest() throws Exception {
		CliController.getInstance().treatMessage("R table 5");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherReadTest() throws Exception {
		CliController.getInstance().treatMessage("R table id invalid");
	}
	
	// Delete
	@Test (expected = MissingArgumentException.class)
	public void lowerDeleteTest() throws Exception {
		CliController.getInstance().treatMessage("D table");
	}
	
	@Test
	public void deleteCompanyTest() throws Exception {
		CliController.getInstance().treatMessage("C company 975 comp");
		assertTrue(CliController.getInstance().treatMessage("D company 975").startsWith("Delete Company"));
	}
	
	@Test
	public void deleteComputerTest() throws Exception {
		CliController.getInstance().treatMessage("C computer 995 ordi _ _ 0");
		assertTrue(CliController.getInstance().treatMessage("D computer 995").startsWith("Delete Computer"));
	}
	
	@Test (expected = InvalidTableException.class)
	public void deleteUnknownTableTest() throws Exception {
		CliController.getInstance().treatMessage("D table 15");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherDeleteTest() throws Exception {
		CliController.getInstance().treatMessage("D table id invalid");
	}

	// ListAll
	@Test (expected = MissingArgumentException.class)
	public void lowerListAllTest() throws Exception {
		CliController.getInstance().treatMessage("LA");
	}
	
	@Test
	public void listAllCompanyTest() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("LA company").startsWith("Company"));
	}
	
	@Test
	public void listAllComputerTest() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("LA computer").startsWith("Computer"));
	}
	
	@Test (expected = InvalidTableException.class)
	public void listAllUnknownTableTest() throws Exception {
		CliController.getInstance().treatMessage("LA table");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherListAllTest() throws Exception {
		CliController.getInstance().treatMessage("LA table invalid");
	}
	
	// List
	@Test (expected = MissingArgumentException.class)
	public void lowerListTest() throws Exception {
		CliController.getInstance().treatMessage("L");
	}
	
	@Test
	public void listCompanyTest() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("L company 1 20").startsWith("Company"));
	}
		
	@Test
	public void listComputerTest() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("L computer 1 20").startsWith("Computer"));
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void listInvalidPageTest() throws Exception {
		CliController.getInstance().treatMessage("L computer A 20");
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void listForbiddenPageTest() throws Exception {
		CliController.getInstance().treatMessage("L computer 0 20");
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void listForbiddenSizeTest() throws Exception {
		CliController.getInstance().treatMessage("L computer 1 0");
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void listInvalidSizeTest() throws Exception {
		CliController.getInstance().treatMessage("L computer 1 A");
	}
		
	@Test (expected = InvalidTableException.class)
	public void listUnknownTableTest() throws Exception {
		CliController.getInstance().treatMessage("L table 1 20");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherListTest() throws Exception {
		CliController.getInstance().treatMessage("L table 1 20 invalid");
	}
	
	// Create
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateTest() throws Exception {
		CliController.getInstance().treatMessage("C");
	}
	
	// 2-3
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateCompanyTest() throws Exception {
		CliController.getInstance().treatMessage("C company 4");
	}
	
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputerTest() throws Exception {
		CliController.getInstance().treatMessage("C computer 5");
	}
	
	@Test (expected = InvalidTableException.class)
	public void lowerCreateInvalidTableTest() throws Exception {
		CliController.getInstance().treatMessage("C table _");
	}
	
	// 4
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputer4Args() throws Exception {
		CliController.getInstance().treatMessage("C computer 5 Ordinateur");
	}
	// Valid Company
	@Test
	public void createCompany() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("C company 984 Entreprise").startsWith("Create Company"));
		CliController.getInstance().treatMessage("D company 984");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable4Args() throws Exception {
		CliController.getInstance().treatMessage("C table _ _");
	}
	
	// 5
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputer5Args() throws Exception {
		CliController.getInstance().treatMessage("C computer 5 Ordinateur _");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany5Args() throws Exception {
		CliController.getInstance().treatMessage("C company 1754 Entreprise invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable5Args() throws Exception {
		CliController.getInstance().treatMessage("C table _ _ _");
	}
	
	// 6
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputer6Args() throws Exception {
		CliController.getInstance().treatMessage("C computer 5 Ordinateur _ _");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany6Args() throws Exception {
		CliController.getInstance().treatMessage("C company 1754 Entreprise _ invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable6Args() throws Exception {
		CliController.getInstance().treatMessage("C table _ _ _ invalid");
	}
	
	// 7
	@Test
	public void createComputer() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("C computer 1752 Ordinateur _ _ 0").startsWith("Create Computer"));
		CliController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test (expected = InvalidDateFormatException.class)
	public void createComputerInvalidDateFormat() throws Exception {
		CliController.getInstance().treatMessage("C computer 1752 Ordinateur unedate _ 0");
		CliController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test (expected = InvalidDateFormatException.class)
	public void createComputerInvalidDateFormat2() throws Exception {
		CliController.getInstance().treatMessage("C computer 1752 Ordinateur 2019:12:27 _ 0");
		CliController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test 
	public void createComputerValidDate() throws Exception {
		assertTrue(CliController.getInstance().treatMessage("C computer 1752 Ordinateur 2019-12-27 _ 0").startsWith("Create Computer"));
		CliController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany7Args() throws Exception {
		CliController.getInstance().treatMessage("C company 1754 Entreprise _ _ invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable7Args() throws Exception {
		CliController.getInstance().treatMessage("C table _ _ _ _ invalid");
	}
	
	// 8
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateComputer() throws Exception {
		CliController.getInstance().treatMessage("C computer 1753 Ordinateur _ _ 0 invalid");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany8Args() throws Exception {
		CliController.getInstance().treatMessage("C company 1754 Entreprise _ _ _ invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable8Args() throws Exception {
		CliController.getInstance().treatMessage("C table _ _ _ _ _ invalid");
	}
	
	// Update
	@Test (expected = MissingArgumentException.class)
	public void lowerUpdateTest() throws Exception {
		CliController.getInstance().treatMessage("U table");
	}
	
	@Test
	public void updateCompanyTest() throws Exception {
		CliController.getInstance().treatMessage("C company 943 UnNom");
		assertTrue(CliController.getInstance().treatMessage("U company 943 Name").startsWith("Update Company"));
		CliController.getInstance().treatMessage("D company 943");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherUpdateCompanyTest() throws Exception {
		CliController.getInstance().treatMessage("U company 943 Name _");
	}
	
	@Test
	public void updateComputerNameTest() throws Exception {
		CliController.getInstance().treatMessage("C computer 943 ordi _ _ 0");
		assertTrue(CliController.getInstance().treatMessage("U computer 943 -n:Ordinateur").startsWith("Update Computer"));
		CliController.getInstance().treatMessage("D computer 943");
	}
	
	@Test
	public void updateComputerIntroTest() throws Exception {
		CliController.getInstance().treatMessage("C computer 943 ordi _ _ 0");
		assertTrue(CliController.getInstance().treatMessage("U computer 943 -i:2017-08-27").startsWith("Update Computer"));
		CliController.getInstance().treatMessage("D computer 943");
	}
	
	@Test
	public void updateComputerDiscTest() throws Exception {
		CliController.getInstance().treatMessage("C computer 943 ordi _ _ 0");
		assertTrue(CliController.getInstance().treatMessage("U computer 943 -d:2017-08-27").startsWith("Update Computer"));
		CliController.getInstance().treatMessage("D computer 943");
	}
	
	@Test
	public void updateComputerCompanyidTest() throws Exception {
		CliController.getInstance().treatMessage("C computer 943 ordi _ _ 0");
		assertTrue(CliController.getInstance().treatMessage("U computer 943 -c:7").startsWith("Update Computer"));
		CliController.getInstance().treatMessage("D computer 943");
	}
	
	@Test (expected = InvalidComputerOptionException.class)
	public void updateComputerInvalidOptionTest() throws Exception {
		CliController.getInstance().treatMessage("U computer 3 -z:7");
	}
	
	@Test (expected = InvalidComputerOptionException.class)
	public void updateComputerInvalidOptionTest2() throws Exception {
		CliController.getInstance().treatMessage("U computer 3 uneoption");
	}
	
	@Test (expected = InvalidTableException.class)
	public void updateInvalidTable() throws Exception {
		CliController.getInstance().treatMessage("U table 3 _");
	}
	
}