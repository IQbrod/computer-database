package com.excilys.cdb.enums;

import com.excilys.cdb.model.QRole;
import com.querydsl.core.types.OrderSpecifier;

public enum RoleFields {
	ID("id", QRole.role.id.asc()),
	NAME("name", QRole.role.name.asc()),
	ID_REVERSE("id_rev", QRole.role.id.desc()),
	NAME_REVERSE("name_rev", QRole.role.name.desc());

	private RoleFields(String input, OrderSpecifier<?> field) {
		this.input = input;
		this.field = field;
	}
	
	private final String input;
	private final OrderSpecifier<?> field;
	
	public static RoleFields getOrderByField(String s) {
		for (RoleFields comd : RoleFields.values()) {
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
