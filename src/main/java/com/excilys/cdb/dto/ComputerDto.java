package com.excilys.cdb.dto;

public class ComputerDto extends Dto {
	private String name;
	private String introduction;
	private String discontinued;
	private String companyId;
	private String companyName;
	
	public ComputerDto(String id) {
		this(id,"",null,null,"0","None");
	}
	
	public ComputerDto(String id, String name, String intro, String disc, String cId, String cName) {
		super(id);
		this.name = name;
		this.introduction = intro;
		this.discontinued = disc;
		this.companyId = cId;
		this.companyName = cName;
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
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Computer ["+this.getId()+"] " + this.getName() + " (" + this.getIntroduction() + ") (" + this.getDiscontinued() + ") " + this.getCompanyName();
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
		result = 31*result + Integer.valueOf(this.getCompanyId());
		
		return result;
	}
}