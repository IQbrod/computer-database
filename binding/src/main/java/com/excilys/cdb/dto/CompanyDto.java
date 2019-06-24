package com.excilys.cdb.dto;

import javax.validation.constraints.NotEmpty;

public class CompanyDto extends Dto {
	@NotEmpty
	private String name;
	
	public CompanyDto() {
		this(0L);
	}
	
	public CompanyDto(Long id) {
		this(id,"");
	}
	
	public CompanyDto(Long id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Company ["+this.id+"] "+this.name;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof CompanyDto))
            return false;
        
        CompanyDto companyDto = (CompanyDto) object;
        return companyDto.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		result = 31*result + this.getName().hashCode();
		
		return result;
	}
}