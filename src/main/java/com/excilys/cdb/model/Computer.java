package com.excilys.cdb.model;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.exception.InvalidDateOrderException;

public class Computer extends Model {
	private String name;
	private Timestamp dateIntro;
	private Timestamp dateDisc;
	private int manufacturer;
	
	private Logger logger = (Logger) LogManager.getLogger(this.getClass());
	
	public Computer(int id, String name, Timestamp dateIntro, Timestamp dateDisc, int manufacturer) throws RuntimeException {
		super(id);
		this.setName(name);
		this.setDateIntro(dateIntro);
		this.setDateDisc(dateDisc);
		this.setManufacturer(manufacturer);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDateIntro() {
		return dateIntro;
	}

	public void setDateIntro(Timestamp dateIntro) throws RuntimeException {
		if (dateIntro != null && this.dateDisc != null && dateIntro.after(this.dateDisc)) {
			RuntimeException exception = new InvalidDateOrderException(dateIntro, this.dateDisc);
			this.logger.error(exception.getMessage());
			throw exception;
		}
		this.dateIntro = dateIntro;
	}

	public Timestamp getDateDisc() {
		return dateDisc;
	}

	public void setDateDisc(Timestamp dateDisc) throws RuntimeException {
		if (dateDisc != null && this.dateIntro != null && dateDisc.before(this.dateIntro)) {
			RuntimeException exception = new InvalidDateOrderException(dateIntro, this.dateDisc);
			this.logger.error(exception.getMessage());
			throw exception;
		}
		this.dateDisc = dateDisc;
	}

	public int getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof Computer))
            return false;
        
        Computer model = (Computer) object;
        return model.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int result = 31*17 + this.getId();
		
		return result;
	}
	
	
	
	
}
