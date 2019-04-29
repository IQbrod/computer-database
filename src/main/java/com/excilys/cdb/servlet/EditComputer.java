package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class EditComputer extends HttpServlet {

	private static final long serialVersionUID = 29042019L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward( request, response );
	}
}
