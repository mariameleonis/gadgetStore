 package com.epam.gadgetStore.entity;

import java.util.Base64;

public class Product extends Entity {

	private String name;
	private String description;
	private float price;
	private byte[] image;
	private String base64Image;
	private Brand brand;
	private Category category;
		
	public Product(Long id) {
		super(id);
	}

	public Product() {
		
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getBase64Image() {
		base64Image = Base64.getEncoder().encodeToString(this.image);
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
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
	    
	    Product otherProduct = (Product) obj;
	    if (getId() == null) {
			if (otherProduct.getId() != null)
				return false;
		} else if (!getId().equals(otherProduct.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id="+  super.getId() + ", name=" + name + ", price=" + price + "]";
	}



}
