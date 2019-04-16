package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;

public class CompanyMapper extends Mapper<CompanyDto, Company>{

	@Override
	public Company dtoToModel(CompanyDto dtoObject) {
		if (dtoObject == null) {
			return null;
		} else {
			return new Company(
				this.idToInt(dtoObject.getId()),
				dtoObject.getName()
			);
		}
	}

	@Override
	public CompanyDto modelToDto(Company modelObject) {
		if (modelObject == null) {
			return null;
		} else {
			return new CompanyDto(
				Integer.toString(modelObject.getId()),
				modelObject.getName()
			);
		}
	}
	
}
