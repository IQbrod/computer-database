package com.excilys.cdb.service;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.DatabaseProblemException;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Computer;

public class ComputerService extends Service<ComputerDto, Computer>{
	private static ComputerService instance = null;
	
	private ComputerService() throws DatabaseProblemException {
		super(ComputerMapper.getInstance(), ComputerDao.getInstance());
	}
	
	public static ComputerService getInstance() throws DatabaseProblemException {
		if (instance == null)
			instance = new ComputerService();
		return instance;
	}
}
