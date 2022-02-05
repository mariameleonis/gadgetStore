package com.epam.gadgetStore.dto;

public class CategoryLocalizationDTO {
	
	private Long id;
	private Long languageId;
	private String name;
	private String itemTitle;
	
	public CategoryLocalizationDTO(Long languageId, String name, String itemTitle) {
		super();
		this.languageId = languageId;
		this.name = name;
		this.itemTitle = itemTitle;
	}
	
	public CategoryLocalizationDTO(Long languageId) {
		this.languageId = languageId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
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
		
}
