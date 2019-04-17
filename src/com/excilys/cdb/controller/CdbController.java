package com.excilys.cdb.controller;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;
import com.excilys.cdb.service.*;

public class CdbController {
	private String[] splitStr;
	
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
		System.out.println("Please use custom format for dates: yyyy-MM-dd/HH:mm:ss\n");
		System.out.println("create|update company <id> <new_name>");
		System.out.println("create computer <id> <name> <intro | _> <disc | _> <company_id | _>");
		System.out.println("update computer <id> <[-n:new_name] [-i:new_intro] [-d:new_disc] [-u:new_cid]>");
		System.out.println("read|delete <table> <id>");
		System.out.println("help");
	}
	
	private String castDate(String s) {
		return (s.contentEquals("_")) ? null : s.replace("/", " ");
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
					throw new TooManyArgumentsException(splitStr[5]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 6:
				if (splitStr[1].toLowerCase().equals("computer")) {
					throw new MissingArgumentException(msgErr,"company_id");
				} else if (splitStr[1].toLowerCase().equals("company")) {
					throw new TooManyArgumentsException(splitStr[5]);
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
	
}
