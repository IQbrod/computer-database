package com.excilys.cdb.servlet;

import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.excilys.cdb.servlet.model.ServletModel;

public abstract class Servlet extends HttpServlet {
	private static final long serialVersionUID = 3052019L;
	protected final HashMap<String,ServletModel> modelMap = new HashMap<String,ServletModel>();
	
	protected void flushSetup(HttpServletRequest request) {
		this.modelMap.values().stream().forEach(x -> x.flush(request));
	}
}
