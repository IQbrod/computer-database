package com.excilys.cdb.enums;

import com.excilys.cdb.model.QRole;
import com.excilys.cdb.model.QUser;
import com.querydsl.core.types.OrderSpecifier;

public enum UserFields {
	ID("id", QUser.user.id.asc()),
	NAME("username", QUser.user.username.asc()),
	ROLE_ID("roleId", QRole.role.id.asc()),
	ROLE_NAME("roleName", QRole.role.name.asc().nullsLast()),
	ID_REVERSE("id_rev", QUser.user.id.desc()),
	NAME_REVERSE("username_rev", QUser.user.username.desc()),
	ROLE_ID_REVERSE("roleId_rev", QRole.role.id.desc()),
	ROLE_NAME_REVERSE("roleName_rev", QRole.role.name.desc());

	private UserFields(String input, OrderSpecifier<?> field) {
		this.input = input;
		this.field = field;
	}
	
	private final String input;
	private final OrderSpecifier<?> field;
	
	public static UserFields getOrderByField(String s) {
		for (UserFields comd : UserFields.values()) {
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
