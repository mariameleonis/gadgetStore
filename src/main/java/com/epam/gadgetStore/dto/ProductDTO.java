package com.epam.gadgetStore.dto;

import javax.servlet.http.Part;

public class ProductDTO {
	private String id;
	private String name;
	private String description;
	private Part imagePart;
	private String price;
	private String brandId;
	private String categoryId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Part getImagePart() {
		return imagePart;
	}

	public void setImagePart(Part imagePart) {
		this.imagePart = imagePart;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
