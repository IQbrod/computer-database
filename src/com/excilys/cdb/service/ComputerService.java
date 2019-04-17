package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Computer;

public class ComputerService extends Service<ComputerDto, Computer>{
	
	public ComputerService(Dao<Computer> d) {
		super(new ComputerMapper(), d);
	}

	@Override
	public List<ComputerDto> listAllElements() {
		// TODO Auto-generated method stub
		return null;
	}
}
