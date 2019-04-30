package com.excilys.cdb.dto;

public class ComputerDto extends Dto {
	private String name;
	private String introduction;
	private String discontinued;
	private CompanyDto company;
	
	public ComputerDto(String id) {
		this(id,"",null,null,new CompanyDto("-1","None"));
	}
	
	public ComputerDto(String id, String name, String i, String d, CompanyDto c) {
		super(id);
		this.setName(name);
		this.setIntroduction(i);
		this.setDiscontinued(d);
		this.setCompany(c);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto companyName) {
		this.company = companyName;
	}
	
	@Override
	public String toString() {
		return "Computer ["+this.getId()+"] " + this.getName() + " (" + this.getIntroduction() + ") (" + this.getDiscontinued() + ") " + this.getCompany();
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof ComputerDto))
            return false;
        
        ComputerDto computerDto = (ComputerDto) object;
        return computerDto.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		result = 31*result + this.getName().hashCode();
		result = 31*result + ((this.getIntroduction() == null) ? 0 : this.getIntroduction().hashCode());
		result = 31*result + ((this.getDiscontinued() == null) ? 0 : this.getDiscontinued().hashCode());
		result = 31*result + this.getCompany().getId().hashCode();
		
		return result;
	}
}