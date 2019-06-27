package com.excilys.cdb.enums;

import com.excilys.cdb.model.QComputer;
import com.excilys.cdb.model.QCompany;
import com.querydsl.core.types.OrderSpecifier;

public enum ComputerFields {
	ID("id", QComputer.computer.id.asc()),
	NAME("name", QComputer.computer.name.asc()),
	INTRODUCTION("introduction", QComputer.computer.introduced.asc().nullsLast()),
	DISCONTINUED("discontinued", QComputer.computer.discontinued.asc().nullsLast()),
	COMPANY_ID("companyId", QCompany.company.id.asc().nullsLast()),
	COMPANY_NAME("companyName", QCompany.company.name.asc().nullsLast()),
	ID_REVERSE("id_rev", QComputer.computer.id.desc()),
	NAME_REVERSE("name_rev", QComputer.computer.name.desc()),
	INTRODUCED_REVERSE("introduction_rev", QComputer.computer.introduced.desc()),
	DISCONTINUED_REVERSE("discontinued_rev", QComputer.computer.discontinued.desc()),
	COMPANY_ID_REVERSE("companyId_rev", QCompany.company.id.desc()),
	COMPANY_NAME_REVERSE("companyName_rev", QCompany.company.name.desc());

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
