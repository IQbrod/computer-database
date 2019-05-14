package com.excilys.cdb.enums;

public enum CreateOptionEnum {
	NAME('n'),
	INTRODUCTION('i'),
	DISCONTINUED('d'),
	COMPANY('c'),
	UNKNOWN(' ');
	
	private CreateOptionEnum(char shor) {
		this.shortcut = shor;
	}
	
	private char shortcut;
	
	public static CreateOptionEnum getCommandEnum(char compareValue) {
		for (CreateOptionEnum create : CreateOptionEnum.values()) {
			if (create.shortcut == compareValue) {
				return create;
			}
		}
		return UNKNOWN;
	}
}
