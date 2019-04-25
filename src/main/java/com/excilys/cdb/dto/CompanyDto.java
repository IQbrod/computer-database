package com.excilys.cdb.dto;

public class CompanyDto extends Dto {
	private String name;
	
	public CompanyDto(String id) {
		this(id,"");
	}
	
	public CompanyDto(String id, String name) {
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
        return companyDto.getId().contentEquals(this.getId());
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		
		return result;
	}
}