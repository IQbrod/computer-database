package com.excilys.cdb.enums;

public enum CreateOptionEnum {
	Name('n'),
	Introduction('i'),
	Discontinued('d'),
	Company('c'),
	Unknown(' ');
	
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
		return Unknown;
	}
}
