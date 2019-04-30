package com.excilys.cdb.controller;

import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.util.*;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.enums.CommandEnum;
import com.excilys.cdb.enums.CreateOptionEnum;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.service.*;

// TODO: Sortir les display (passer les DTO)

public class CdbController {
	private String[] splitStr;
	private final String dateFormat = "yyyy-MM-dd/HH:mm:ss";
	
	private static CdbController instance = new CdbController();
	private Logger logger = (Logger) LogManager.getLogger(this.getClass());	
	
	private CdbController() {}
	
	public static CdbController getInstance() {
		return instance;
	}
	
	private Exception log (Exception exception) {
		this.logger.error(exception.getMessage());
		return exception;
	}
	
	public String treatMessage(String msg) throws Exception {
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
	
	private String castDate(String s) throws Exception {
		if (s.length() == 19) {
			// Check Date Format
			if (s.charAt(4) == '-' && s.charAt(7) == '-' && s.charAt(10) == '/' && s.charAt(13) == ':' && s.charAt(16) == ':') {
				return s.replace("/", " ");
			} else {
				throw this.log(new InvalidDateFormatException(this.dateFormat,s));
			}
		} else if (s.contentEquals("_")) {
			return null;
		} else {
			throw this.log(new InvalidDateFormatException(this.dateFormat,s));
		}
	}
	
	private String create() throws Exception {
		int sizeComputerExpected = 7;
		int sizeCompanyExpected = 4;
		
		switch (splitStr.length) {
			case 1:
				throw this.log(new MissingArgumentException(2,splitStr.length));
			case 2:
			case 3:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw this.log(new MissingArgumentException(sizeCompanyExpected,splitStr.length));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 4:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					CompanyDto ret = CompanyService.getInstance().create(c);
					return "Create "+ret.toString();
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 5:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw this.log(new TooManyArgumentsException(splitStr[4]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 6:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw this.log(new MissingArgumentException(sizeComputerExpected,splitStr.length));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw this.log(new TooManyArgumentsException(splitStr[4]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			case 7:
				if (splitStr[1].toLowerCase().equals("computer")) {
					ComputerDto c = new ComputerDto(splitStr[2],splitStr[3],this.castDate(splitStr[4]),this.castDate(splitStr[5]),new CompanyDto((splitStr[6].contentEquals("_")) ? "0" : splitStr[6],"None"));
					ComputerDto ret = ComputerService.getInstance().create(c);
					return "Create "+ret.toString();
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw this.log(new TooManyArgumentsException(splitStr[5]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
			default:
			case 8:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw this.log(new TooManyArgumentsException(splitStr[7]));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw this.log(new TooManyArgumentsException(splitStr[5]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				
		}
	}
	
	private String read() throws Exception {
		Dto c;
		
		int sizeExpected = 3;
		switch (splitStr.length) {
			case 1:
			case 2:
				throw this.log(new MissingArgumentException(sizeExpected,splitStr.length));
			case 3:
				// Load dto by id
				if (splitStr[1].toLowerCase().equals("computer")) {
					c = ComputerService.getInstance().read(splitStr[2]);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					c = CompanyService.getInstance().read(splitStr[2]);
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				// Display dto
				return c.toString();
			default:
				throw this.log(new TooManyArgumentsException(splitStr[3]));
		}
	}
	
	private String delete() throws Exception {		
		int sizeExpected = 3;
		
		switch (splitStr.length) {
			case 1:
			case 2:
				throw this.log(new MissingArgumentException(sizeExpected,splitStr.length));
			case 3:
				Dto ret;
				if (splitStr[1].toLowerCase().equals("computer")) {
					ret = ComputerService.getInstance().delete(new ComputerDto(splitStr[2]));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					ret = CompanyService.getInstance().delete(new CompanyDto(splitStr[2]));
				} else {
					throw this.log(new InvalidTableException(splitStr[1]));
				}
				return "Delete "+ret.toString();
			default:
				throw this.log(new TooManyArgumentsException(splitStr[3]));
		}
	}
	
	private void updateTreatOption(ComputerDto c, String s) throws Exception {
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
					c.setCompany(new CompanyDto(s.substring(3).contentEquals("_") ? "-1" : s.substring(3),"None"));
					break;
				case Unknown:
				default:
					throw this.log(new InvalidComputerOptionException(s));
			}
		}
	}
	
	private String update() throws Exception {
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
				ret = ComputerService.getInstance().update(c);;
			} else if (splitStr[1].toLowerCase().equals("company")) {
				if(splitStr.length == 4) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					ret = CompanyService.getInstance().update(c);
				} else {
					throw this.log(new TooManyArgumentsException(splitStr[4]));
				}
			} else {
				throw this.log(new InvalidTableException(splitStr[1]));
			}
			return "Update "+ret.toString();
		}
	}
	
	private String listAll() throws Exception {
		switch (splitStr.length) {
			case 1:
				throw this.log(new MissingArgumentException(2, splitStr.length));
			case 2:
				List<? extends Dto> dtoList;
				if (splitStr[1].toLowerCase().equals("computer")) {
					dtoList = ComputerService.getInstance().listAllElements();
				} else if (splitStr[1].toLowerCase().equals("company")) {
					dtoList = CompanyService.getInstance().listAllElements();
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
	
	private String list() throws Exception {
		int sizeExpected = 4;
		
		switch (splitStr.length) {
			case 1:
			case 2:
			case 3:
				throw this.log(new MissingArgumentException(sizeExpected, splitStr.length));
			case 4:
				List<? extends Dto> dtoList;
				if (splitStr[1].toLowerCase().equals("computer")) {
					dtoList = ComputerService.getInstance().list(splitStr[2], splitStr[3]);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					dtoList = CompanyService.getInstance().list(splitStr[2], splitStr[3]);
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
