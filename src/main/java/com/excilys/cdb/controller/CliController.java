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
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String COMPUTER_TABLE = "computer";
	private static final String COMPANY_TABLE = "company";
	
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
			case CREATE:
				return this.create();
			case READ:
				return this.read();
			case UPDATE:
				return this.update();
			case DELETE:
				return this.delete();
			case HELP:
				return this.help();
			case LISTALL:
				return this.listAll();
			case LIST:
				return this.list();
			case EMPTY:
				return "";
			case UNKNOWN:
			default:
				throw this.log(new UnknownCommandException(splitStr[0]));
		}
	}
	
	private String help() {
		return "Please use custom format for dates: "+DATE_FORMAT+"\n"
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
				throw this.log(new InvalidDateFormatException(DATE_FORMAT,s));
			}
		} else if (s.contentEquals("_")) {
			return null;
		} else {
			throw this.log(new InvalidDateFormatException(DATE_FORMAT,s));
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
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
					throw this.log(new MissingArgumentException(sizeCompanyExpected,splitStr.length));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 4:
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					this.validator.validateCompanyDto(c);					
					CompanyDto ret = this.companyMapper.modelToDto(this.companyService.create(this.companyMapper.dtoToModel(c)));
					return "Create "+ret.toString();
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 5:
			case 6:
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
					throw this.log(new TooManyArgumentsException(splitStr[4]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 7:
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					ComputerDto c = new ComputerDto(splitStr[2],splitStr[3],this.castDate(splitStr[4]),this.castDate(splitStr[5]),(splitStr[6].contentEquals("_")) ? "0" : splitStr[6],"None");
					this.validator.validateComputerDto(c);
					ComputerDto ret = this.computerMapper.modelToDto(this.computerService.create(this.computerMapper.dtoToModel(c)));
					return "Create "+ret.toString();
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
					throw this.log(new TooManyArgumentsException(splitStr[5]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			default:
			case 8:
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					throw this.log(new TooManyArgumentsException(splitStr[7]));
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
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
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					c = this.computerMapper.modelToDto(this.computerService.read(this.computerMapper.idToInt(splitStr[2])));
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
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
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					ret = this.computerMapper.modelToDto(this.computerService.delete(this.computerMapper.dtoToModel(new ComputerDto(splitStr[2]))));
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
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
				case NAME:
					c.setName(s.substring(3));
					break;
				case INTRODUCTION:
					c.setIntroduction(this.castDate(s.substring(3)));
					break;
				case DISCONTINUED:
					c.setDiscontinued(this.castDate(s.substring(3)));
					break;
				case COMPANY:
					c.setCompanyId(s.substring(3).contentEquals("_") ? "-1" : s.substring(3));
					break;
				case UNKNOWN:
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
			if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
				ComputerDto c = new ComputerDto(splitStr[2]);
				for (String s : Arrays.copyOfRange(splitStr, 3, splitStr.length)) {
					this.updateTreatOption(c,s);
				}
				this.validator.validateComputerDto(c);
				ret = this.computerMapper.modelToDto(this.computerService.update(this.computerMapper.dtoToModel(c)));
			} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
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
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					dtoList = this.computerService.listAllElements().stream().map(computerMapper::modelToDto).collect(Collectors.toList());
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
					dtoList = this.companyService.listAllElements().stream().map(companyMapper::modelToDto).collect(Collectors.toList());
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				
				StringBuilder ret = new StringBuilder("");
				for (Dto d : dtoList) {
					ret.append(d.toString()).append("\n");
				}
				return ret.toString();
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
				if (splitStr[1].equalsIgnoreCase(COMPUTER_TABLE)) {
					dtoList = this.computerService.list(this.computerMapper.idToInt(splitStr[2]), this.computerMapper.idToInt(splitStr[3])).stream().map(this.computerMapper::modelToDto).collect(Collectors.toList());
				} else if (splitStr[1].equalsIgnoreCase(COMPANY_TABLE)) {
					dtoList = this.companyService.list(this.companyMapper.idToInt(splitStr[2]), this.companyMapper.idToInt(splitStr[3])).stream().map(this.companyMapper::modelToDto).collect(Collectors.toList());
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				
				StringBuilder ret = new StringBuilder("");
				for (Dto d : dtoList) {
					ret.append(d.toString()).append("\n");
				}
				return ret.toString();
			default:
				throw this.log(new TooManyArgumentsException(splitStr[4]));
		}
	}
	
}
