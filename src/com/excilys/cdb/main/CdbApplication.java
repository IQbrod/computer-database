package com.excilys.cdb.main;

import com.excilys.cdb.controller.CdbController;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.UnknownCommandException;
import com.excilys.cdb.service.*;

public class CdbApplication {

	public static void main(String args[]){  
		CdbController c = new CdbController();
		try {
			c.treatMessage("C computer 7500 OrdiNateur 20A7-10-27/14:26:19 _ 5");
			c.treatMessage("D computer 7500");
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}