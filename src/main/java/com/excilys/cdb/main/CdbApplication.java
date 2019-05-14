package com.excilys.cdb.main;

import com.excilys.cdb.userinterface.CdbUi;

public class CdbApplication {

	public static void main(String[] args){  
		if (args.length == 0) {
			CdbUi c = new CdbUi(System.in,System.out, System.err);
			c.run();
		} else {
			System.out.println("Please do not use any arguments");
		}
	}
}