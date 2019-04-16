package com.excilys.cdb.main;

import java.sql.*;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Computer;

public class CdbApplication {

	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC","admincdb","qwerty1234");
			ComputerDao dao = new ComputerDao(con);
			
			Computer c = new Computer(7500,"OrdiNateur",null,null,4);
			
			if (dao.update(c)) {
				System.out.println(dao.read(7500).getName());
			}
		}catch(Exception e){ System.out.println(e);}  
		}  

}
