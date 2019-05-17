package com.excilys.cdb.model;

import java.sql.*;

public class Computer extends AbstractModel {
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Integer company_id;
	
	public Computer(int id, String name, Timestamp dateIntro, Timestamp dateDisc, Integer manufacturer) {
		super(id);
		this.setName(name);
		this.setIntroduced(dateIntro);
		this.setDiscontinued(dateDisc);
		this.setCompany_id(manufacturer);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp dateIntro) {
		this.introduced = dateIntro;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp dateDisc) {
		this.discontinued = dateDisc;
	}
	
	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof Computer))
            return false;
        
        Computer model = (Computer) object;
        return model.hashCode() == this.hashCode();
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId();
		result = 31*result + this.getName().hashCode();
		result = 31*result + ((this.getIntroduced() == null) ? 0 : this.getIntroduced().hashCode());
		result = 31*result + ((this.getDiscontinued() == null) ? 0 : this.getDiscontinued().hashCode());
		result = 31*result + this.getCompany_id();
		
		
		return result;
	}
	
	
	
	
}
