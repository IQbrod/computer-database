package com.excilys.cdb.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.apache.logging.log4j.LogManager;


import java.util.*;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.enums.CommandEnum;
import com.excilys.cdb.enums.CreateOptionEnum;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.*;
import com.excilys.cdb.spring.AppConfig;
import com.excilys.cdb.validator.Validator;

public class CliController {
	private String[] splitStr;
	private final String dateFormat = "yyyy-MM-dd";
	private final String computerTable = "computer";
	private final String companyTable = "company";
	
	private Logger logger = LogManager.getLogger(this.getClass());
	private static CliController instance = null; 
	
	private final ComputerMapper computerMapper;
	private final CompanyMapper companyMapper;
	private final ComputerService computerService;
	private final CompanyService companyService;
	private final Validator validator;
	
	public CliController() {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
			this.computerMapper = context.getBean(ComputerMapper.class);
			this.companyMapper = context.getBean(CompanyMapper.class);
			this.computerService = context.getBean(ComputerService.class);
			this.companyService = context.getBean(CompanyService.class);
			this.validator = context.getBean(Validator.class);
		}
	}
	
	public static CliController getInstance() {
		if (instance == null)
			instance = new CliController();
		return instance;
	}
	
	private RuntimeException log (RuntimeException exception) {
		this.logger.error(exception.getMessage());
		return exception;
	}
	
	public String treatMessage(String msg) {
		this.logger.debug(msg);
		this.splitStr = msg.trim().split("\\s+");
		
		CommandEnum cmd = CommandEnum.getCommandEnum(splitStr[0].toLowerCase());
		switch(cmd) {
			case Create:
				return this.create();
			case Read:
				return this.read();
			case Update:
				return this.update();
			case Delete:
				return this.delete();
			case Help:
				return this.help();
			case ListAll:
				return this.listAll();
			case List:
				return this.list();
			case Empty:
				return "";
			case Unknown:
			default:
				throw this.log(new UnknownCommandException(splitStr[0]));
		}
	}
	
	private String help() {
		return "Please use custom format for dates: "+this.dateFormat+"\n"
			+ "create|update company <id> <new_name>\n"
			+ "create computer <id> <name> <intro | _> <disc | _> <company_id | _>\n"
			+ "update computer <id> <[-n:new_name] [-i:new_intro] [-d:new_disc] [-c:new_cid]>\n"
			+ "read|delete <table> <id>\n"
			+ "listall <table>\n"
			+ "list <table> <page> <size>\n"
			+ "help";
	}
	
	private String castDate(String s) {
		if (s.length() == 10) {
			// Check Date Format
			if (s.charAt(4) == '-' && s.charAt(7) == '-') {
				return s;
			} else {
				throw this.log(new InvalidDateFormatException(this.dateFormat,s));
			}
		} else if (s.contentEquals("_")) {
			return null;
		} else {
			throw this.log(new InvalidDateFormatException(this.dateFormat,s));
		}
	}
	
	private String create() {
		int sizeComputerExpected = 7;
		int sizeCompanyExpected = 4;
		
		switch (splitStr.length) {
			case 1:
				throw this.log(new MissingArgumentException(2,splitStr.length));
			case 2:
			case 3:
				if (splitStr[1].equalsIgnoreCase(this.computerTable)) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].equalsIgnoreCase(this.companyTable)) {
					throw this.log(new MissingArgumentException(sizeCompanyExpected,splitStr.length));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 4:
				if (splitStr[1].equalsIgnoreCase(this.computerTable)) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].equalsIgnoreCase(this.companyTable)) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					this.validator.validateCompanyDto(c);					
					CompanyDto ret = this.companyMapper.modelToDto(this.companyService.create(this.companyMapper.dtoToModel(c)));
					return "Create "+ret.toString();
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 5:
			case 6:
				if (splitStr[1].equalsIgnoreCase(this.computerTable)) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].equalsIgnoreCase(this.companyTable)) {
					throw this.log(new TooManyArgumentsException(splitStr[4]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 7:
				if (splitStr[1].equalsIgnoreCase(this.computerTable)) {
					ComputerDto c = new ComputerDto(splitStr[2],splitStr[3],this.castDate(splitStr[4]),this.castDate(splitStr[5]),(splitStr[6].contentEquals("_")) ? "0" : splitStr[6],"None");
					this.validator.validateComputerDto(c);
					ComputerDto ret = this.computerMapper.modelToDto(this.computerService.create(this.computerMapper.dtoToModel(c)));
					return "Create "+ret.toString();
				} else if (splitStr[1].equalsIgnoreCase(this.companyTable)) {
					throw this.log(new TooManyArgumentsException(splitStr[5]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			default:
			case 8:
				if (splitStr[1].equalsIgnoreCase(this.computerTable)) {
					throw this.log(new TooManyArgumentsException(splitStr[7]));
				} else if (splitStr[1].equalsIgnoreCase(this.companyTable)) {
					throw this.log(new TooManyArgumentsException(splitStr[5]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				
		}
	}
	
	private String read() {
		Dto c;
		
		int sizeExpected = 3;
		switch (splitStr.length) {
			case 1:
			case 2:
				throw this.log(new MissingArgumentException(sizeExpected,splitStr.length));
			case 3:
				// Load dto by id
				if (splitStr[1].toLowerCase().equals("computer")) {
					c = this.computerMapper.modelToDto(this.computerService.read(this.computerMapper.idToInt(splitStr[2])));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					c = this.companyMapper.modelToDto(this.companyService.read(this.companyMapper.idToInt(splitStr[2])));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				// Display dto
				return c.toString();
			default:
				throw this.log(new TooManyArgumentsException(splitStr[3]));
		}
	}
	
	private String delete() {		
		int sizeExpected = 3;
		
		switch (splitStr.length) {
			case 1:
			case 2:
				throw this.log(new MissingArgumentException(sizeExpected,splitStr.length));
			case 3:
				Dto ret;
				if (splitStr[1].toLowerCase().equals("computer")) {
					ret = this.computerMapper.modelToDto(this.computerService.delete(this.computerMapper.dtoToModel(new ComputerDto(splitStr[2]))));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					ret = this.companyMapper.modelToDto(this.companyService.delete(this.companyMapper.dtoToModel(new CompanyDto(splitStr[2]))));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				return "Delete "+ret.toString();
			default:
				throw this.log(new TooManyArgumentsException(splitStr[3]));
		}
	}
	
	private void updateTreatOption(ComputerDto c, String s) {
		if (s.charAt(0) != '-' || s.charAt(2) != ':' || s.length() == 3) {
			throw this.log(new InvalidComputerOptionException(s));
		} else {
			CreateOptionEnum opt = CreateOptionEnum.getCommandEnum(Character.toLowerCase(s.charAt(1)));
			switch(opt) {
				case Name:
					c.setName(s.substring(3));
					break;
				case Introduction:
					c.setIntroduction(this.castDate(s.substring(3)));
					break;
				case Discontinued:
					c.setDiscontinued(this.castDate(s.substring(3)));
					break;
				case Company:
					c.setCompanyId(s.substring(3).contentEquals("_") ? "-1" : s.substring(3));
					break;
				case Unknown:
				default:
					throw this.log(new InvalidComputerOptionException(s));
			}
		}
	}
	
	private String update() {
		int sizeExpected = 4;
		
		switch (splitStr.length) {
		case 1:
		case 2:
		case 3:
			throw this.log(new MissingArgumentException(sizeExpected,splitStr.length));
		default:
			Dto ret;
			if (splitStr[1].toLowerCase().equals("computer")) {
				ComputerDto c = new ComputerDto(splitStr[2]);
				for (String s : Arrays.copyOfRange(splitStr, 3, splitStr.length)) {
					this.updateTreatOption(c,s);
				}
				this.validator.validateComputerDto(c);
				ret = this.computerMapper.modelToDto(this.computerService.update(this.computerMapper.dtoToModel(c)));
			} else if (splitStr[1].toLowerCase().equals("company")) {
				if(splitStr.length == 4) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					ret = this.companyMapper.modelToDto(this.companyService.update(this.companyMapper.dtoToModel(c)));
				} else {
					throw this.log(new TooManyArgumentsException(splitStr[4]));
				}
			} else {
				throw this.log(new InvalidTableException(splitStr[1]));
			}
			return "Update "+ret.toString();
		}
	}
	
	private String listAll() {
		switch (splitStr.length) {
			case 1:
				throw this.log(new MissingArgumentException(2, splitStr.length));
			case 2:
				List<? extends Dto> dtoList;
				if (splitStr[1].toLowerCase().equals("computer")) {
					dtoList = this.computerService.listAllElements().stream().map(model -> this.computerMapper.modelToDto(model)).collect(Collectors.toList());
				} else if (splitStr[1].toLowerCase().equals("company")) {
					dtoList = this.companyService.listAllElements().stream().map(model -> this.companyMapper.modelToDto(model)).collect(Collectors.toList());
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				
				String ret = "";
				for (Dto d : dtoList) {
					ret += d.toString() + "\n";
				}
				return ret;
			default:
				throw this.log(new TooManyArgumentsException(splitStr[2]));
		}
	}
	
	private String list() {
		int sizeExpected = 4;
		
		switch (splitStr.length) {
			case 1:
			case 2:
			case 3:
				throw this.log(new MissingArgumentException(sizeExpected, splitStr.length));
			case 4:
				List<? extends Dto> dtoList;
				this.validator.validatePagination(splitStr[2]);
				this.validator.validatePagination(splitStr[3]);
				if (splitStr[1].toLowerCase().equals("computer")) {
					dtoList = this.computerService.list(this.computerMapper.idToInt(splitStr[2]), this.computerMapper.idToInt(splitStr[3])).stream().map(model -> this.computerMapper.modelToDto(model)).collect(Collectors.toList());
				} else if (splitStr[1].toLowerCase().equals("company")) {
					dtoList = this.companyService.list(this.companyMapper.idToInt(splitStr[2]), this.companyMapper.idToInt(splitStr[3])).stream().map(model -> this.companyMapper.modelToDto(model)).collect(Collectors.toList());
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				
				String ret = "";
				for (Dto d : dtoList) {
					ret += d.toString() + "\n";
				}
				return ret;
			default:
				throw this.log(new TooManyArgumentsException(splitStr[4]));
		}
	}
	
}
