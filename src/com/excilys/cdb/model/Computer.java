package com.excilys.cdb.model;

import java.sql.*;

import com.excilys.cdb.exception.InvalidDateOrderException;

public class Computer extends Model {
	private String name;
	private Timestamp dateIntro;
	private Timestamp dateDisc;
	private int manufacturer;
	
	public Computer(int id, String name, Timestamp dateIntro, Timestamp dateDisc, int manufacturer) throws InvalidDateOrderException {
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

	public void setDateIntro(Timestamp dateIntro) throws InvalidDateOrderException {
		// We consider dateDisc should be greater than dateIntro
		if (dateIntro != null && this.dateDisc != null && dateIntro.after(this.dateDisc)) {
			throw new InvalidDateOrderException(dateIntro, this.dateDisc);
		}
		this.dateIntro = dateIntro;
	}

	public Timestamp getDateDisc() {
		return dateDisc;
	}

	public void setDateDisc(Timestamp dateDisc) throws InvalidDateOrderException {
		// TODO: UPDATE --> Vérifier en BD (car on ne change que les éléments proposés et donc la vérif ne passe pas CMP avec null)
		if (dateDisc != null && this.dateIntro != null && dateDisc.before(this.dateIntro)) {
			throw new InvalidDateOrderException(this.dateIntro, dateDisc);
		}
		this.dateDisc = dateDisc;
	}

	public int getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	
	
}
