package com.excilys.cdb.enums;

public enum CommandEnum {
	CREATE("create","c"),
	READ("read","r"),
	UPDATE("update","u"),
	DELETE("delete","d"),
	LIST("list","l"),
	LISTALL("list","la"),
	HELP("help","h"),
	EMPTY("",""),
	UNKNOWN("others","other");
	
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
		return UNKNOWN;
	}
}
