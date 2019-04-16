package com.excilys.cdb.model;

import java.sql.*;

public class Computer extends Model {
	private String name;
	private Date dateIntro;
	private Date dateDisc;
	private String manufacturer;
	
	public Computer(int id, String name, Date dateIntro, Date dateDisc, String manufacturer) {
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

	public Date getDateIntro() {
		return dateIntro;
	}

	public void setDateIntro(Date dateIntro) {
		this.dateIntro = dateIntro;
	}

	public Date getDateDisc() {
		return dateDisc;
	}

	public void setDateDisc(Date dateDisc) {
		this.dateDisc = dateDisc;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	
	
}
