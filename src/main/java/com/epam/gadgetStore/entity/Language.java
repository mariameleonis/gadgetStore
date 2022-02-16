package com.epam.gadgetStore.entity;

public class Language extends Entity {
	private String name;

	public Language(String name) {
		super();
		this.name = name;
	}

	public Language() {

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

		Language otherLanguage = (Language) obj;
		if (getId() == null) {
			if (otherLanguage.getId() != null)
				return false;
		} else if (!getId().equals(otherLanguage.getId()))
			return false;
		return true;
	}
}
