package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.exception.InvalidIntegerException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.validator.CompanyValidator;

public class CompanyMapper extends Mapper<CompanyDto, Company>{
	private static CompanyMapper instance;
	
	private CompanyMapper() {
		super(CompanyValidator.getInstance());
	}
	
	public static CompanyMapper getInstance() {
		if (instance == null)
			instance = new CompanyMapper();
		return instance;
	}
	
	@Override
	public Company dtoToModel(CompanyDto dtoObject) throws InvalidIntegerException {
		this.validator.validate(dtoObject);
		return new Company(
			this.idToInt(dtoObject.getId()),
			dtoObject.getName()
		);
	}

	@Override
	public CompanyDto modelToDto(Company modelObject) {
		return new CompanyDto(
			Integer.toString(modelObject.getId()),
			modelObject.getName()
		);
	}
	
}
