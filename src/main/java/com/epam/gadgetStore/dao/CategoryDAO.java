package com.epam.gadgetStore.dao;

import java.util.List;

import com.epam.gadgetStore.dto.CategoryDTO;
import com.epam.gadgetStore.entity.Category;

public interface CategoryDAO extends BaseDAO {
	Category getById(Long categoryId, Long languageId);
	Category findByLocalizedName(String categoryName, Long languageId);
	List<Category> getAllById(Long categoryId);
	List<Category> getAllByLanguageId(Long languageId);
	List<Category> getAllPotentialParent(Long languageId);
	List<Category> getAllLeafCategories(Long languageId);
	Long add(CategoryDTO categoryDTO);
	void update(CategoryDTO categoryDTO);
	void delete(Long id);
}
