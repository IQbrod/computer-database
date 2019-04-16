package com.excilys.cdb.main;

import java.sql.*;
import com.excilys.cdb.dao.*;
import com.excilys.cdb.model.*;

public class CdbApplication {

	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC","admincdb","qwerty1234");
			CompanyDao dao = new CompanyDao(con);
			Company c = new Company(375,"MonEntreprise");
			
			if (dao.create(c)) {
				System.out.println(dao.read(375).getName());
			}
			
			c.setName("EntrePrise");
			
			if (dao.update(c)) {
				System.out.println(dao.read(375).getName());
			}
			
			if (dao.delete(c)) {			
				if (dao.read(7500) == null) {
					System.out.println("Supprimé :D");
				}
			}
		} catch(Exception e) { System.err.println(e);}
	}
}