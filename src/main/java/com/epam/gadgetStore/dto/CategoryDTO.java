package com.epam.gadgetStore.dto;

import java.util.List;

public class CategoryDTO {
	private Long id;
	private Long parentId;
	private List<CategoryLocalizationDTO> localizations;

	public CategoryDTO(Long parentId, List<CategoryLocalizationDTO> localizations) {
		this.parentId = parentId;
		this.localizations = localizations;
	}

	public CategoryDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<CategoryLocalizationDTO> getLocalizations() {
		return localizations;
	}

	public void setLocalizations(List<CategoryLocalizationDTO> localizations) {
		this.localizations = localizations;
	}
}
