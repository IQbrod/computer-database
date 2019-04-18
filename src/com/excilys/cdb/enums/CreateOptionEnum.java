package com.excilys.cdb.enums;

public enum CreateOptionEnum {
	Name('n'),
	Introduction('i'),
	Discontinue('d'),
	Company('c'),
	Unknown(' ');
	
	private CreateOptionEnum(char shor) {
		this.shortcut = shor;
	}
	
	private char shortcut;
	
	public static CreateOptionEnum getCommandEnum(char ca) {
		for (CreateOptionEnum c : CreateOptionEnum.values()) {
			if (c.shortcut == ca) {
				return c;
			}
		}
		return Unknown;
	}
}
