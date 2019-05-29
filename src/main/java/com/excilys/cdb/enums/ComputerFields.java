package com.excilys.cdb.enums;

import com.excilys.cdb.model.QComputer;
import com.excilys.cdb.model.QCompany;
import com.querydsl.core.types.OrderSpecifier;

public enum ComputerFields {
	ID("id", QComputer.computer.id.asc()),
	NAME("name", QComputer.computer.name.asc()),
	INTRODUCED("introduced", QComputer.computer.introduced.asc().nullsLast()),
	DISCONTINUED("discontinued", QComputer.computer.discontinued.asc().nullsLast()),
	COMPANY("company", QCompany.company.name.asc().nullsLast()),
	NAME_REVERSE("name_rev", QComputer.computer.name.desc()),
	INTRODUCED_REVERSE("introduced_rev", QComputer.computer.introduced.desc()),
	DISCONTINUED_REVERSE("discontinued_rev", QComputer.computer.discontinued.desc()),
	COMPANY_REVERSE("company_rev", QCompany.company.name.desc());

	private ComputerFields(String input, OrderSpecifier<?> field) {
		this.input = input;
		this.field = field;
	}
	
	private final String input;
	private final OrderSpecifier<?> field;
	
	public static ComputerFields getOrderByField(String s) {
		for (ComputerFields comd : ComputerFields.values()) {
			if (comd.input.contentEquals(s)) {
				return comd;
			}
		}
		return ID;
	}

	public OrderSpecifier<?> getField() {
		return field;
	}	
}
