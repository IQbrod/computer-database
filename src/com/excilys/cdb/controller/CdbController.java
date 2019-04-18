package com.excilys.cdb.controller;

// TODO: Verifier les dates avant le cast

import java.util.Arrays;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.service.*;

public class CdbController {
	private String[] splitStr;
	private final String dateFormat = "yyyy-MM-dd/HH:mm:ss";
	
	public CdbController() {}
	
	public void treatMessage(String msg) throws Exception {
		// Parse message based on whitespace : Any amount might be placed beside and inbetween
		this.splitStr = msg.trim().split("\\s+");
		
		switch(splitStr[0].toLowerCase()) {
			case "c":
			case "create":
				this.create();
				break;
			case "r":
			case "read":
				this.read();
				break;
			case "update":
			case "u":
				this.update();
				break;
			case "delete":
			case "d":
				this.delete();
				break;
			case "help":
				this.displayHelp();
				break;
			default:
				throw new UnknownCommandException(splitStr[0]);
		}
	}
	
	private void displayHelp() {
		System.out.println("Please use custom format for dates: "+this.dateFormat+"\n");
		System.out.println("create|update company <id> <new_name>");
		System.out.println("create computer <id> <name> <intro | _> <disc | _> <company_id | _>");
		System.out.println("update computer <id> <[-n:new_name] [-i:new_intro] [-d:new_disc] [-c:new_cid]>");
		System.out.println("read|delete <table> <id>");
		System.out.println("help");
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
	
	private void create() throws Exception {
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
						System.out.println("Create "+ c.toString());
					};
					break;
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
			case 7:
				if (splitStr[1].toLowerCase().equals("computer")) {
					ComputerDto c = new ComputerDto(splitStr[2],splitStr[3],this.castDate(splitStr[4]),this.castDate(splitStr[5]),(splitStr[6].contentEquals("_")) ? "0" : splitStr[6]);
					if (ComputerService.getInstance().create(c)) {
						System.out.println("Create "+c.toString());
					}
					break;
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[5]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
		}
	}
	
	private void read() throws Exception {
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
				System.out.println("Read " + c.toString());
				break;
			default:
				throw new TooManyArgumentsException(splitStr[3]);
		}
	}
	
	private void delete() throws Exception {		
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
						System.out.println("Delete computer ["+splitStr[2]+"]");
					}
				} else if (splitStr[1].toLowerCase().equals("company")) {
					if(CompanyService.getInstance().delete(new CompanyDto(splitStr[2]))) {
						System.out.println("Delete company ["+splitStr[2]+"]");
					}
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				break;
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
	
	private void update() throws Exception {
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
			} else if (splitStr[1].toLowerCase().equals("company")) {
				if(splitStr.length == 4) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					if(CompanyService.getInstance().update(c)) {
						System.out.println("Update "+c.toString());
					}
					return;
				} else {
					throw new TooManyArgumentsException(splitStr[4]);
				}
			} else {
				throw new InvalidTableException(splitStr[1]);
			}
		}
	}
	
}
