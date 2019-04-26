package com.excilys.cdb.controller;

import static org.junit.Assert.*;
import org.junit.*;

import com.excilys.cdb.exception.*;

public class ControllerTest {

	// Empty
	@Test
	public void emptyTest() throws Exception {
		assertEquals(CdbController.getInstance().treatMessage(""), "");
	}
	
	// Invalid
	@Test (expected = UnknownCommandException.class)
	public void invalidTest() throws Exception {
		CdbController.getInstance().treatMessage("cmd invalid");
	}
	
	// Help
	@Test
	public void helpTest() throws Exception {
		CdbController.getInstance().treatMessage("help");
	}
	
	// Read
	@Test (expected = MissingArgumentException.class)
	public void lowerReadTest() throws Exception {
		CdbController.getInstance().treatMessage("R table");
	}
	
	@Test
	public void readCompanyTest() throws Exception {
		CdbController.getInstance().treatMessage("R company 5");
	}
	
	@Test
	public void readComputerTest() throws Exception {
		CdbController.getInstance().treatMessage("R computer 5");
	}
	
	@Test (expected = InvalidTableException.class)
	public void readUnknownTableTest() throws Exception {
		CdbController.getInstance().treatMessage("R table 5");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherReadTest() throws Exception {
		CdbController.getInstance().treatMessage("R table id invalid");
	}
	
	// Delete
	@Test (expected = MissingArgumentException.class)
	public void lowerDeleteTest() throws Exception {
		CdbController.getInstance().treatMessage("D table");
	}
	
	@Test
	public void deleteCompanyTest() throws Exception {
		CdbController.getInstance().treatMessage("C company 975 comp");
		CdbController.getInstance().treatMessage("D company 975");
	}
	
	@Test
	public void deleteComputerTest() throws Exception {
		CdbController.getInstance().treatMessage("C computer 995 ordi _ _ 0");
		CdbController.getInstance().treatMessage("D computer 995");
	}
	
	@Test (expected = InvalidTableException.class)
	public void deleteUnknownTableTest() throws Exception {
		CdbController.getInstance().treatMessage("D table 15");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherDeleteTest() throws Exception {
		CdbController.getInstance().treatMessage("D table id invalid");
	}

	// ListAll
	@Test (expected = MissingArgumentException.class)
	public void lowerListAllTest() throws Exception {
		CdbController.getInstance().treatMessage("LA");
	}
	
	@Test
	public void listAllCompanyTest() throws Exception {
		CdbController.getInstance().treatMessage("LA company");
	}
	
	@Test
	public void listAllComputerTest() throws Exception {
		CdbController.getInstance().treatMessage("LA computer");
	}
	
	@Test (expected = InvalidTableException.class)
	public void listAllUnknownTableTest() throws Exception {
		CdbController.getInstance().treatMessage("LA table");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherListAllTest() throws Exception {
		CdbController.getInstance().treatMessage("LA table invalid");
	}
	
	// List
	@Test (expected = MissingArgumentException.class)
	public void lowerListTest() throws Exception {
		CdbController.getInstance().treatMessage("L");
	}
	
	@Test
	public void listCompanyTest() throws Exception {
		CdbController.getInstance().treatMessage("L company 1 20");
	}
		
	@Test
	public void listComputerTest() throws Exception {
		CdbController.getInstance().treatMessage("L computer 1 20");
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void listInvalidPageTest() throws Exception {
		CdbController.getInstance().treatMessage("L computer A 20");
	}
	
	@Test (expected = InvalidPageValueException.class)
	public void listForbiddenPageTest() throws Exception {
		CdbController.getInstance().treatMessage("L computer 0 20");
	}
	
	@Test (expected = InvalidPageSizeException.class)
	public void listForbiddenSizeTest() throws Exception {
		CdbController.getInstance().treatMessage("L computer 1 0");
	}
	
	@Test (expected = InvalidIntegerException.class)
	public void listInvalidSizeTest() throws Exception {
		CdbController.getInstance().treatMessage("L computer 1 A");
	}
		
	@Test (expected = InvalidTableException.class)
	public void listUnknownTableTest() throws Exception {
		CdbController.getInstance().treatMessage("L table 1 20");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherListTest() throws Exception {
		CdbController.getInstance().treatMessage("L table 1 20 invalid");
	}
	
	// Create
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateTest() throws Exception {
		CdbController.getInstance().treatMessage("C");
	}
	
	// 2-3
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateCompanyTest() throws Exception {
		CdbController.getInstance().treatMessage("C company 4");
	}
	
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputerTest() throws Exception {
		CdbController.getInstance().treatMessage("C computer 5");
	}
	
	@Test (expected = InvalidTableException.class)
	public void lowerCreateInvalidTableTest() throws Exception {
		CdbController.getInstance().treatMessage("C table _");
	}
	
	// 4
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputer4Args() throws Exception {
		CdbController.getInstance().treatMessage("C computer 5 Ordinateur");
	}
	// Valid Company
	@Test
	public void createCompany() throws Exception {
		CdbController.getInstance().treatMessage("C company 984 Entreprise");
		CdbController.getInstance().treatMessage("D company 984");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable4Args() throws Exception {
		CdbController.getInstance().treatMessage("C table _ _");
	}
	
	// 5
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputer5Args() throws Exception {
		CdbController.getInstance().treatMessage("C computer 5 Ordinateur _");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany5Args() throws Exception {
		CdbController.getInstance().treatMessage("C company 1754 Entreprise invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable5Args() throws Exception {
		CdbController.getInstance().treatMessage("C table _ _ _");
	}
	
	// 6
	@Test (expected = MissingArgumentException.class)
	public void lowerCreateComputer6Args() throws Exception {
		CdbController.getInstance().treatMessage("C computer 5 Ordinateur _ _");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany6Args() throws Exception {
		CdbController.getInstance().treatMessage("C company 1754 Entreprise _ invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable6Args() throws Exception {
		CdbController.getInstance().treatMessage("C table _ _ _ invalid");
	}
	
	// 7
	@Test
	public void createComputer() throws Exception {
		CdbController.getInstance().treatMessage("C computer 1752 Ordinateur _ _ 0");
		CdbController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test (expected = InvalidDateFormatException.class)
	public void createComputerInvalidDateFormat() throws Exception {
		CdbController.getInstance().treatMessage("C computer 1752 Ordinateur unedate _ 0");
		CdbController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test (expected = InvalidDateFormatException.class)
	public void createComputerInvalidDateFormat2() throws Exception {
		CdbController.getInstance().treatMessage("C computer 1752 Ordinateur 2019:12:27/12:32-15 _ 0");
		CdbController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test 
	public void createComputerValidDate() throws Exception {
		CdbController.getInstance().treatMessage("C computer 1752 Ordinateur 2019:12:27/12:32:15 _ 0");
		CdbController.getInstance().treatMessage("D computer 1752");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany7Args() throws Exception {
		CdbController.getInstance().treatMessage("C company 1754 Entreprise _ _ invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable7Args() throws Exception {
		CdbController.getInstance().treatMessage("C table _ _ _ _ invalid");
	}
	
	// 8
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateComputer() throws Exception {
		CdbController.getInstance().treatMessage("C computer 1753 Ordinateur _ _ 0 invalid");
	}
	
	@Test (expected = TooManyArgumentsException.class)
	public void higherCreateCompany8Args() throws Exception {
		CdbController.getInstance().treatMessage("C company 1754 Entreprise _ _ _ invalid");
	}
	
	@Test (expected = InvalidTableException.class)
	public void higherCreateInvalidTable8Args() throws Exception {
		CdbController.getInstance().treatMessage("C table _ _ _ _ _ invalid");
	}
}