package com.excilys.cdb.controller;

import java.util.Arrays;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.service.*;

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
		
		switch(splitStr[0].toLowerCase()) {
			case "c":
			case "create":
				return this.create();
			case "r":
			case "read":
				return this.read();
			case "update":
			case "u":
				return this.update();
			case "delete":
			case "d":
				return this.delete();
			case "help":
				return this.help();
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
		String msgErr = "";
		for (String s : splitStr) {
			msgErr += s+" ";
		}
		switch (splitStr.length) {
			case 1:
				throw new MissingArgumentException(msgErr,"table");
			case 2:
				throw new MissingArgumentException(msgErr,"id");
			case 3:
				throw new MissingArgumentException(msgErr,"name");
			case 4:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(msgErr,"intro");
				} else if (splitStr[1].toLowerCase().equals("company")) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					if(CompanyService.getInstance().create(c)) {
						return "Create "+ c.toString();
					} else {
						return ""; //TODO
					}
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 5:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(msgErr,"disc");
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[4]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 6:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(msgErr,"company_id");
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[4]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			default:
			case 7:
				if (splitStr[1].toLowerCase().equals("computer")) {
					ComputerDto c = new ComputerDto(splitStr[2],splitStr[3],this.castDate(splitStr[4]),this.castDate(splitStr[5]),(splitStr[6].contentEquals("_")) ? "0" : splitStr[6]);
					if (ComputerService.getInstance().create(c)) {
						return "Create "+c.toString();
					} else {
						return ""; //TODO
					}
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[5]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
		}
	}
	
	private String read() throws Exception {
		Dto c;
		
		String msgErr = "";
		for (String s : splitStr) {
			msgErr += s+" ";
		}
		switch (splitStr.length) {
			case 1:
				throw new MissingArgumentException(msgErr,"table");
			case 2:
				throw new MissingArgumentException(msgErr,"id");
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
				return "Read " + c.toString();
			default:
				throw new TooManyArgumentsException(splitStr[3]);
		}
	}
	
	private String delete() throws Exception {		
		String msgErr = "";
		for (String s : splitStr) {
			msgErr += s+" ";
		}
		switch (splitStr.length) {
			case 1:
				throw new MissingArgumentException(msgErr,"table");
			case 2:
				throw new MissingArgumentException(msgErr,"id");
			case 3:
				if (splitStr[1].toLowerCase().equals("computer")) {
					if(ComputerService.getInstance().delete(new ComputerDto(splitStr[2]))) {
						return "Delete computer ["+splitStr[2]+"]";
					} else {
						return ""; //TODO
					}
				} else if (splitStr[1].toLowerCase().equals("company")) {
					if(CompanyService.getInstance().delete(new CompanyDto(splitStr[2]))) {
						return "Delete company ["+splitStr[2]+"]";
					} else {
						return ""; //TODO
					}
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			default:
				throw new TooManyArgumentsException(splitStr[3]);
		}
	}
	
	private void updateTreatOption(ComputerDto c, String s) throws Exception {
		if (s.charAt(0) != '-' || s.charAt(2) != ':' || s.length() == 3) {
			throw new InvalidComputerOptionException(s);
		} else {
			// TODO: Passer par ENUM
			// Switch sur l'initiale de l'option
			switch(s.charAt(1)) {
				case 'n':
					c.setName(s.substring(3));
					break;
				case 'i':
					c.setIntro(this.castDate(s.substring(3))); //TODO: CAST
					break;
				case 'd':
					c.setDiscon(this.castDate(s.substring(3))); //TODO: CAST
					break;
				case 'u':
					c.setComp(s.substring(3));
					break;
				default:
					throw new InvalidComputerOptionException(s);
			}
		}
	}
	
	private String update() throws Exception {
		String msgErr = "";
		for (String s : splitStr) {
			msgErr += s+" ";
		}
		switch (splitStr.length) {
		case 1:
			throw new MissingArgumentException(msgErr,"table");
		case 2:
			throw new MissingArgumentException(msgErr,"id");
		case 3:
			if (splitStr[1].toLowerCase().equals("computer")) {
				throw new MissingArgumentException(msgErr, "option(s)");
			} else if (splitStr[1].toLowerCase().equals("company")) {
				throw new MissingArgumentException(msgErr, "new_name");
			} else {
				throw new InvalidTableException(splitStr[1]);
			}
		default:
			if (splitStr[1].toLowerCase().equals("computer")) {
				ComputerDto c = new ComputerDto(splitStr[1]);
				// Pour chaque option passée
				for (String s : Arrays.copyOfRange(splitStr, 3, splitStr.length)) {
					this.updateTreatOption(c,s);
				}
				if (ComputerService.getInstance().update(c)) {
					return "Update " + c.toString();
				} else {
					return "";// TODO
				}
			} else if (splitStr[1].toLowerCase().equals("company")) {
				if(splitStr.length == 4) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					if(CompanyService.getInstance().update(c)) {
						return "Update "+c.toString();
					} else {
						return ""; //TODO
					}
				} else {
					throw new TooManyArgumentsException(splitStr[4]);
				}
			} else {
				throw new InvalidTableException(splitStr[1]);
			}
		}
	}
	
}
