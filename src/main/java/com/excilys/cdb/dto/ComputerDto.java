package com.excilys.cdb.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class ComputerDto extends Dto {
	@NotEmpty
	private String name;
	@Pattern(regexp=DATE_PATTERN)
	private String introduction;
	@Pattern(regexp=DATE_PATTERN)
	private String discontinued;
	@PositiveOrZero
	private Long companyId;
	@NotEmpty
	private String companyName;
	
	private static final String DATE_PATTERN = "^(((19|2[0-9])[0-9]{2})/(0[469]|11)/(0[1-9]|[12][0-9]|30))$";
	
	public ComputerDto(Long id) {
		this(id,"",null,null,0L,"None");
	}
	
	public ComputerDto(Long id, String name, String intro, String disc, Long cId, String cName) {
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
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
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
		result = 31*result + this.getCompanyId().intValue();
		
		return result;
	}
}