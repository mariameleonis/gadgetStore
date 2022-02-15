package com.epam.gadgetStore.validation;

public class CategoryErrorStorage {
	private Long languageId;
	private boolean isWrongName;
	private boolean isWrongItemTitle;
	private boolean isDuplicateName;
	
	public CategoryErrorStorage(Long languageId) {
		this.languageId = languageId;
		this.isWrongName = false;
		this.isWrongItemTitle = false;
		this.isDuplicateName = false;
	}
	
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	public boolean getIsWrongName() {
		return isWrongName;
	}
	public void setWrongName(boolean isWrongName) {
		this.isWrongName = isWrongName;
	}
	public boolean getIsWrongItemTitle() {
		return isWrongItemTitle;
	}
	public void setWrongItemTitle(boolean isWrongItemTitle) {
		this.isWrongItemTitle = isWrongItemTitle;
	}
	public boolean getIsDuplicateName() {
		return isDuplicateName;
	}
	public void setDuplicateName(boolean isDuplicateName) {
		this.isDuplicateName = isDuplicateName;
	}

	@Override
	public String toString() {
		return "CategoryErrorStorage [languageId=" + languageId + ", isWrongName=" + isWrongName + ", isWrongItemTitle="
				+ isWrongItemTitle + ", isDuplicateName=" + isDuplicateName + "]";
	}
	
	

}
