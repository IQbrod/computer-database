package com.excilys.cdb.main;

import com.excilys.cdb.userinterface.CdbUi;

public class CdbApplication {

	public static void main(String args[]){  
		CdbUi c = new CdbUi(System.in,System.out, System.err);
		c.run();
	}
}