package com.excilys.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<ComputerDto> list(String page, String size, String orderBy) {
		return (List<ComputerDto>) ((ComputerDao)this.dao).list(this.mapper.idToInt(page),this.mapper.idToInt(size), orderBy).stream().map(s -> mapper.modelToDto(s)).collect(Collectors.toList());
	}
	
	public List<ComputerDto> listByName(String name, String page, String size, String orderBy) throws RuntimeException {
		return (List<ComputerDto>) ((ComputerDao)this.dao).listByName(name,this.mapper.idToInt(page),this.mapper.idToInt(size), orderBy).stream().map(s -> mapper.modelToDto(s)).collect(Collectors.toList());
	};
	
	public int countByName(String name) throws Exception {
		return ((ComputerDao)this.dao).countByName(name);
	}
}
