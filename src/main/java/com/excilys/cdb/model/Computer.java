package com.excilys.cdb.model;

import java.sql.*;

public class Computer extends AbstractModel {
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Integer companyId;
	
	public Computer(int id, String name, Timestamp dateIntro, Timestamp dateDisc, Integer companyId) {
		super(id);
		this.setName(name);
		this.setIntroduced(dateIntro);
		this.setDiscontinued(dateDisc);
		this.setCompanyId(companyId);
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
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
		result = 31*result + this.getCompanyId();
		
		
		return result;
	}
	
	
	
	
}
