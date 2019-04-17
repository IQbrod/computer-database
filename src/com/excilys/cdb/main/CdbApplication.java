package com.excilys.cdb.main;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.service.*;

public class CdbApplication {

	public static void main(String args[]){  
		try{  			
			ComputerService s = new ComputerService();
			CompanyService cs = new CompanyService();
			
			
			ComputerDto c = new ComputerDto("7500","OrdiNateur","2018-03-05 10:12:06",null,"750");
			CompanyDto d = new CompanyDto("750","EntrePrise");
			
			if(cs.create(d)) {
				System.out.println(cs.read("750").getName());
			}
			if(s.create(c)) {
				System.out.println(s.read("7500").getName());
			}
			
			d.setName("CompAny");
			if(cs.update(d)) {
				System.out.println(cs.read("750").getName());
			}
			c.setName("CompUter");c.setDiscon("2018-09-12 14:27:00");
			if(s.update(c)) {
				System.out.println(s.read("7500").getName());
			}
			
			if(s.delete(c) && cs.delete(d)) {
				System.out.println("Suppression OK");
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}