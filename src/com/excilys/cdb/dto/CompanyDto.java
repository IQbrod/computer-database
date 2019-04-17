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
}