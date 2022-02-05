package com.epam.gadgetStore.entity;

public class Language extends Entity{
	
	private String name;
	
	public Language(String name) {
		super();
		this.name = name;
	}
	
	public Language() {
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
