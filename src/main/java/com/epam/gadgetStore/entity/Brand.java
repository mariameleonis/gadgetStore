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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Brand otherBrand = (Brand) obj;
		if (getId() == null) {
			if (otherBrand.getId() != null)
				return false;
		} else if (!getId().equals(otherBrand.getId()))
			return false;
		return true;
	}
}
