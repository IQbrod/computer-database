package com.excilys.cdb.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role extends AbstractModel {
	private String name;
	
	public Role() {}
	
	public Role(long id, String name) {
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
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof Role))
            return false;
        
        Role model = (Role) object;
        return model.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = (int) (31*17 + this.getId());
		result = 31*result + this.getName().hashCode();
		
		return result;
	}
}
