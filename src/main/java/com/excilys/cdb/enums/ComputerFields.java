package com.excilys.cdb.enums;

public enum ComputerFields {
	ID("id","C.id"),
	NAME("name","C.name"),
	INTRODUCED("introduced","introduced IS NULL, introduced"),
	DISCONTINUED("discontinued","discontinued IS NULL, discontinued"),
	COMPANY("company","D.name IS NULL, D.name"),
	NAME_REVERSE("name_rev","C.name DESC"),
	INTRODUCED_REVERSE("introduced_rev","introduced DESC"),
	DISCONTINUED_REVERSE("discontinued_rev","discontinued DESC"),
	COMPANY_REVERSE("company_rev","D.name DESC");

	private ComputerFields(String input, String field) {
		this.input = input;
		this.field = field;
	}
	
	private final String input;
	private final String field;
	
	public static ComputerFields getOrderByField(String s) {
		for (ComputerFields comd : ComputerFields.values()) {
			if (comd.input.contentEquals(s)) {
				return comd;
			}
		}
		return ID;
	}

	public String getField() {
		return field;
	}	
}
