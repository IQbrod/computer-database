package com.excilys.cdb.service;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Computer;

public class ComputerService extends Service<ComputerDto, Computer>{
	private static ComputerService instance = new ComputerService();
	
	private ComputerService() {
		super(ComputerMapper.getInstance(), ComputerDao.getInstance());
	}
	
	public static ComputerService getInstance() {
		return instance;
	}
}
