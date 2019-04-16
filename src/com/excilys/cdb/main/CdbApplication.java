package com.excilys.cdb.main;

import java.sql.*;
import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.service.*;

public class CdbApplication {

	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC","admincdb","qwerty1234");
			
			ComputerDao dao = new ComputerDao(con);
			System.out.println(dao.read(3).getManufacturer());
			
			
		} catch(Exception e) { System.err.println(e);}
	}
}