package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.model.Computer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComputerService extends AbstractService<Computer>{	
	public ComputerService(ComputerDao compDao) {
		super(compDao);
	}
	
	public List<Computer> list(int page, int size, String orderBy) {
		return ((ComputerDao)this.dao).list(page,size, orderBy);
	}
	
	public List<Computer> listByName(String name, int page, int size, String orderBy) {
		return ((ComputerDao)this.dao).listByName(name, page, size, orderBy);
	}
	
	public int countByName(String name) {
		return ((ComputerDao)this.dao).countByName(name);
	}
}
