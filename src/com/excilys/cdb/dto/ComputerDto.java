package com.excilys.cdb.dto;

public class ComputerDto extends Dto {
	private String name;
	private String intro;
	private String discon;
	private String comp;
	
	public ComputerDto(String id, String name, String i, String d, String c) {
		super(id);
		this.setName(name);
		this.setIntro(i);
		this.setDiscon(d);
		this.setComp(c);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getDiscon() {
		return discon;
	}

	public void setDiscon(String discon) {
		this.discon = discon;
	}

	public String getComp() {
		return comp;
	}

	public void setComp(String comp_name) {
		this.comp = comp_name;
	}
}