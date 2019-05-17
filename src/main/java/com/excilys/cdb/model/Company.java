package com.excilys.cdb.model;

public class Company extends AbstractModel{
	private String name;
	
	public Company(int id, String name) {
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
        if (!(object instanceof Company))
            return false;
        
        Company model = (Company) object;
        return model.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId();
		result = 31*result + this.getName().hashCode();
		
		return result;
	}
}