package com.excilys.cdb.enums;

public enum CommandEnum {
	Create("create","c"),
	Read("read","r"),
	Update("update","u"),
	Delete("delete","d"),
	List("list","l"),
	ListAll("list","la"),
	Help("help","h"),
	Empty("",""),
	Unknown("others","other");
	
	private CommandEnum(String cmd, String shor) {
		this.command = cmd;
		this.shortcut = shor;
	}
	
	private String command;
	private String shortcut;
	
	public static CommandEnum getCommandEnum(String s) {
		for (CommandEnum comd : CommandEnum.values()) {
			if (comd.command.contentEquals(s) || comd.shortcut.contentEquals(s)) {
				return comd;
			}
		}
		return Unknown;
	}
}
