package com.epam.gadgetStore.entity;

public class Brand extends Entity {
	private String name;

	public Brand(String name) {
		this.name = name;
	}

	public Brand() {

	}

	public Brand(long id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
