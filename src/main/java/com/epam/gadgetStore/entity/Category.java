package com.epam.gadgetStore.entity;

import java.util.List;

public class Category extends Entity {
	
	private Long parentId;
	private String name;
	private String itemTitle;	
	private Long languageId;
	private List<Category> subcategories;
	
	public Category() {
		
	}
	
	public Category(String name, String itemTitle) {
		super();
		this.name = name;
		this.itemTitle = itemTitle;
	}
	
	public Category(Long id, Long parentId) {
		super(id);
		this.parentId = parentId;
	}

	public Category(long id) {
		super(id);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public List<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}

	
		

}
