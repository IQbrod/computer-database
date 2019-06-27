package com.excilys.cdb.enums;

import com.excilys.cdb.model.QCompany;
import com.querydsl.core.types.OrderSpecifier;

public enum CompanyFields {
	ID("id", QCompany.company.id.asc()),
	NAME("name", QCompany.company.name.asc()),
	ID_REVERSE("id_rev", QCompany.company.id.desc()),
	NAME_REVERSE("name_rev", QCompany.company.name.desc());

	private CompanyFields(String input, OrderSpecifier<?> field) {
		this.input = input;
		this.field = field;
	}
	
	private final String input;
	private final OrderSpecifier<?> field;
	
	public static CompanyFields getOrderByField(String s) {
		for (CompanyFields comd : CompanyFields.values()) {
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
