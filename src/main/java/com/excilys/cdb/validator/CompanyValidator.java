package com.excilys.cdb.validator;

import com.excilys.cdb.dto.CompanyDto;

public class CompanyValidator extends Validator<CompanyDto> {
	private static CompanyValidator instance;
	
	private CompanyValidator() {}
	
	public static CompanyValidator getInstance() {
		if (instance == null)
			instance = new CompanyValidator();
		return instance;
	}
	
	@Override
	public void validate(CompanyDto dtoObject) {
		super.validate(dtoObject);
		this.required("name", dtoObject.getName());
	}
}
