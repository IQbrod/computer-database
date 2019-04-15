package com.excilys.cdb.main;

import java.sql.*;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Computer;

public class CdbApplication {

	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database","cdbadmin","qwerty1234");
			
			ComputerDao dao = new ComputerDao(con);
			Computer c = dao.read(8);
			if (c != null) {
				System.out.println(c.getName());
			}
		}catch(Exception e){ System.out.println(e);}  
		}  

}
