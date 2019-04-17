package com.excilys.cdb.model;

import java.sql.*;

public class Computer extends Model {
	private String name;
	private Timestamp dateIntro;
	private Timestamp dateDisc;
	private int manufacturer;
	
	public Computer(int id, String name, Timestamp dateIntro, Timestamp dateDisc, int manufacturer) {
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

	public void setDateIntro(Timestamp dateIntro) {
		this.dateIntro = dateIntro;
	}

	public Timestamp getDateDisc() {
		return dateDisc;
	}

	public void setDateDisc(Timestamp dateDisc) {
		this.dateDisc = dateDisc;
	}

	public int getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	
	
}
