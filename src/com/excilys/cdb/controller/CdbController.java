package com.excilys.cdb.controller;

import java.util.Arrays;
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
	
	private CdbController() {}
	
	public static CdbController getInstance() {
		return instance;
	}
	
	public String treatMessage(String msg) throws Exception {
		// Parse message based on whitespace : Any amount might be placed beside and inbetween
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
				throw new UnknownCommandException(splitStr[0]);
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
	
	private String castDate(String s) throws InvalidDateFormatException {
		if (s.length() == 19) {
			// Check Date Format
			if (s.charAt(4) == '-' && s.charAt(7) == '-' && s.charAt(10) == '/' && s.charAt(13) == ':' && s.charAt(16) == ':') {
				return s.replace("/", " ");
			} else {
				throw new InvalidDateFormatException(this.dateFormat,s);
			}
		} else if (s.contentEquals("_")) {
			return null;
		} else {
			throw new InvalidDateFormatException(this.dateFormat,s);
		}
	}
	
	private String create() throws Exception {
		int sizeComputerExpected = 7;
		int sizeCompanyExpected = 4;
		
		switch (splitStr.length) {
			case 1:
				// Requires at least <table>
				throw new MissingArgumentException(2,splitStr.length);
			case 2:
			case 3:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new MissingArgumentException(sizeCompanyExpected,splitStr.length);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 4:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					CompanyDto ret = CompanyService.getInstance().create(c);
					return (ret == null) ? "No company has been created" : "Create "+ret.toString();
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 5:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[4]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 6:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[4]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 7:
				if (splitStr[1].toLowerCase().equals("computer")) {
					ComputerDto c = new ComputerDto(splitStr[2],splitStr[3],this.castDate(splitStr[4]),this.castDate(splitStr[5]),(splitStr[6].contentEquals("_")) ? "0" : splitStr[6]);
					ComputerDto ret = ComputerService.getInstance().create(c);
					return (ret == null) ? "No computer has been created" : "Create "+ret.toString();
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[5]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			default:
			case 8:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new TooManyArgumentsException(splitStr[7]);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[5]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				
		}
	}
	
	private String read() throws Exception {
		Dto c;
		
		int sizeExpected = 3;
		switch (splitStr.length) {
			case 1:
			case 2:
				throw new MissingArgumentException(sizeExpected,splitStr.length);
			case 3:
				// Load dto by id
				if (splitStr[1].toLowerCase().equals("computer")) {
					c = ComputerService.getInstance().read(splitStr[2]);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					c = CompanyService.getInstance().read(splitStr[2]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				// Display dto
				return (c == null) ? "["+splitStr[2]+"] Not Found" : c.toString();
			default:
				throw new TooManyArgumentsException(splitStr[3]);
		}
	}
	
	private String delete() throws Exception {		
		int sizeExpected = 3;
		
		switch (splitStr.length) {
			case 1:
			case 2:
				throw new MissingArgumentException(sizeExpected,splitStr.length);
			case 3:
				Dto ret;
				if (splitStr[1].toLowerCase().equals("computer")) {
					ret = ComputerService.getInstance().delete(new ComputerDto(splitStr[2]));
				} else if (splitStr[1].toLowerCase().equals("company")) {
					ret = CompanyService.getInstance().delete(new CompanyDto(splitStr[2]));
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				return (ret == null) ? "[" +splitStr[2]+"] Not Found" : "Delete "+ret.toString();
			default:
				throw new TooManyArgumentsException(splitStr[3]);
		}
	}
	
	private void updateTreatOption(ComputerDto c, String s) throws Exception {
		if (s.charAt(0) != '-' || s.charAt(2) != ':' || s.length() == 3) {
			throw new InvalidComputerOptionException(s);
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
					c.setCompany(s.substring(3).contentEquals("_") ? "-1" : s.substring(3));
					break;
				case Unknown:
				default:
					throw new InvalidComputerOptionException(s);
			}
		}
	}
	
	private String update() throws Exception {
		int sizeExpected = 4;
		
		switch (splitStr.length) {
		case 1:
		case 2:
		case 3:
			throw new MissingArgumentException(sizeExpected,splitStr.length);
		default:
			Dto ret;
			if (splitStr[1].toLowerCase().equals("computer")) {
				ComputerDto c = new ComputerDto(splitStr[2]);
				// Pour chaque option passée
				for (String s : Arrays.copyOfRange(splitStr, 3, splitStr.length)) {
					this.updateTreatOption(c,s);
				}
				ret = ComputerService.getInstance().update(c);;
			} else if (splitStr[1].toLowerCase().equals("company")) {
				if(splitStr.length == 4) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					ret = CompanyService.getInstance().update(c);
				} else {
					throw new TooManyArgumentsException(splitStr[4]);
				}
			} else {
				throw new InvalidTableException(splitStr[1]);
			}
			return (ret == null) ? "Aucune modification" : "Update "+ret.toString();
		}
	}
	
	private String listAll() throws Exception {
		switch (splitStr.length) {
			case 1:
				throw new MissingArgumentException(2, splitStr.length);
			case 2:
				List<Dto> dtoList;
				if (splitStr[1].toLowerCase().equals("computer")) {
					dtoList = ComputerService.getInstance().listAllElements();
				} else if (splitStr[1].toLowerCase().equals("company")) {
					dtoList = CompanyService.getInstance().listAllElements();
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				
				String ret = "";
				for (Dto d : dtoList) {
					ret += d.toString() + "\n";
				}
				return ret;
			default:
				throw new TooManyArgumentsException(splitStr[2]);
		}
	}
	
	private String list() throws Exception {
		int sizeExpected = 4;
		
		switch (splitStr.length) {
			case 1:
			case 2:
			case 3:
				throw new MissingArgumentException(sizeExpected, splitStr.length);
			case 4:
				List<Dto> dtoList;
				if (splitStr[1].toLowerCase().equals("computer")) {
					dtoList = ComputerService.getInstance().list(splitStr[2], splitStr[3]);
				} else if (splitStr[1].toLowerCase().equals("company")) {
					dtoList = CompanyService.getInstance().list(splitStr[2], splitStr[3]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				
				String ret = "";
				for (Dto d : dtoList) {
					ret += d.toString() + "\n";
				}
				return ret;
			default:
				throw new TooManyArgumentsException(splitStr[4]);
		}
	}
	
}
