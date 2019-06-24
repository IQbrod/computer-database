package com.excilys.cdb.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class UserDto extends Dto {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@Positive
	private Long roleId;
	@NotEmpty
	private String roleName;
	
	public UserDto() {
		this(0L);
	}
	
	public UserDto(Long id) {
		this(id,"name","pass",1L,"USER");
	}
	
	public UserDto(Long id, String username, String password, Long roleId, String roleName) {
		super(id);
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "User ["+this.id+"] "+this.username+ " " +this.roleName;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof UserDto))
            return false;
        
        UserDto companyDto = (UserDto) object;
        return companyDto.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		result = 31*result + this.getUsername().hashCode();
		
		return result;
	}
}