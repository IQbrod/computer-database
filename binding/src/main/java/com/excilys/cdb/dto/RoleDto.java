package com.excilys.cdb.dto;

import javax.validation.constraints.NotEmpty;

public class RoleDto extends Dto {
	@NotEmpty
	private String name;
	
	public RoleDto() {
		this(0L);
	}
	
	public RoleDto(Long id) {
		this(id,"");
	}
	
	public RoleDto(Long id, String name) {
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
		return "Role ["+this.id+"] "+this.name;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof RoleDto))
            return false;
        
        RoleDto companyDto = (RoleDto) object;
        return companyDto.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		result = 31*result + this.getName().hashCode();
		
		return result;
	}
}