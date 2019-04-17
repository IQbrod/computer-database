package com.excilys.cdb.service;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Computer;

public class ComputerService extends Service<ComputerDto, Computer>{
	
	public ComputerService() {
		super(new ComputerMapper(), new ComputerDao());
	}
}
