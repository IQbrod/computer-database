package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.repository.*;
import com.excilys.cdb.model.Computer;
import org.springframework.stereotype.Service;

@Service
public class ComputerService extends AbstractService<Computer>{	
	public ComputerService(ComputerDao compDao) {
		super(compDao);
	}
	
	public List<Computer> list(int page, int size, String orderBy) {
		return ((ComputerDao)this.dao).list(page,size, orderBy);
	}
	
	public long countByName(String name) {
		return ((ComputerDao)this.dao).countByName(name);
	}
}
