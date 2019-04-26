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
		CdbController.getInstance().treatMessage("D company 15");
	}
	
	@Test
	public void deleteComputerTest() throws Exception {
		CdbController.getInstance().treatMessage("D computer 15");
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
	
	
	
	
}
