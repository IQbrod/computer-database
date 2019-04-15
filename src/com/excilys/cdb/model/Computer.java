package com.excilys.cdb.model;

import java.sql.*;

public class Computer {
	private int id;
	private String name;
	private Date dateIntro;
	private Date dateDisc;
	// TODO: SWAP TO COMPANY
	private String manufacturer;
	
	public Computer(int id, String name, Date dateIntro, Date dateDisc, String manufacturer) {
		this.setId(id);
		this.setName(name);
		this.setDateIntro(dateIntro);
		this.setDateDisc(dateDisc);
		this.setManufacturer(manufacturer);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
