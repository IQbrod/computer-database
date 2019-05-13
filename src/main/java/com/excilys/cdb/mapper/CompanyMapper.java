package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.exception.InvalidIntegerException;
import com.excilys.cdb.model.Company;

@Component
public class CompanyMapper extends Mapper<CompanyDto, Company>{	

	@Override
	public Company dtoToModel(CompanyDto dtoObject) throws InvalidIntegerException {
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
