package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cdbuser")
public class User extends AbstractModel {
	private String username;
	private String password;
	@Column(name="role_id")
	private long roleId;

	public User() {}
	
	public User(long id, String username, String password, long roleId) {
		super(id);
		this.username = username;
		this.password = password;
		this.roleId = roleId;
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
	
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof User))
            return false;
        
        User model = (User) object;
        return model.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = (int) (31*17 + this.getId());
		result = 31*result + this.getUsername().hashCode();
		result = 31*result + this.getPassword().hashCode();
		result = 31*result + (int) this.getRoleId();
		
		return result;
	}
}
