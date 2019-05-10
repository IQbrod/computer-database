package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.model.Computer;

public class ComputerService extends Service<Computer>{
	private static ComputerService instance = null;
	
	private ComputerService() {
		super(ComputerDao.getInstance());
	}
	
	public static ComputerService getInstance() {
		if (instance == null)
			instance = new ComputerService();
		return instance;
	}
	
	public List<Computer> list(int page, int size, String orderBy) {
		return ((ComputerDao)this.dao).list(page,size, orderBy);
	}
	
	public List<Computer> listByName(String name, int page, int size, String orderBy) {
		return ((ComputerDao)this.dao).listByName(name, page, size, orderBy);
	};
	
	public int countByName(String name) {
		return ((ComputerDao)this.dao).countByName(name);
	}
}
