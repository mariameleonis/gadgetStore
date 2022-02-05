package com.epam.gadgetStore.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dto.CategoryDTO;
import com.epam.gadgetStore.entity.Category;

public class CategoryDAOImpl extends AbstractBaseDAO<Category> implements CategoryDAO {
	private static final String GET_ALL_CATEGORIES_LOCALIZED_BY_ID = "SELECT * FROM category_view WHERE category_id = ?";
	private static final String GET_ALL_CATEGORIES_BY_LANGUAGE_ID = "SELECT * FROM category_view WHERE language_id = ?";
	private static final String GET_CATEGORY_LOCALIZED_BY_ID = "SELECT * FROM category_view WHERE category_id = ? AND language_id = ?";
	private static final String GET_LOCALIZED_CATEGORY_BY_NAME = "SELECT * FROM category_view WHERE category_name = ? AND language_id = ?";
	private static final String UPDATE_CATEGORY = "UPDATE category SET parent_id = ? WHERE id = ?";
	private static final String UPDATE_CATEGORY_DETAIL = "UPDATE category_localization SET name = ?, item_title = ? WHERE id = ? AND language_id = ?";
	private static final String DELETE_CATEGORY = "DELETE FROM CATEGORY WHERE id = ?";
	private static final String ADD_CATEGORY = "INSERT INTO category (parent_id) VALUES (?)";
	private static final String ADD_CATEGORY_DETAIL = "INSERT INTO category_localization (id, language_id, name, item_title) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_POTENTIAL_PARENT_CATEGORIES = "SELECT * FROM category_view WHERE (parent_id = 0 OR parent_id IN " + 
			"(SELECT category_id FROM category_view WHERE parent_id = 0)) AND category_id NOT IN (SELECT category_id FROM product) AND language_id = ?";
	private static final String GET_LEAF_CATEGORIES = "SELECT * FROM category_view WHERE category_id NOT IN (SELECT DISTINCT parent_id FROM category) "
			+ "AND language_id = ?";

	public Category getById(Long categoryId, Long languageId) {
		return getByParameters(GET_CATEGORY_LOCALIZED_BY_ID, categoryId, languageId);
	}

	public Category findByLocalizedName(String categoryName, Long languageId) {
		return getByParameters(GET_LOCALIZED_CATEGORY_BY_NAME, categoryName, languageId);
	}

	public List<Category> getAllById(Long categoryId) {
		return getAll(GET_ALL_CATEGORIES_LOCALIZED_BY_ID, categoryId);
	}

	public List<Category> getAllByLanguageId(Long languageId) {
		return getAll(GET_ALL_CATEGORIES_BY_LANGUAGE_ID, languageId);
	}

	public List<Category> getAllPotentialParent(Long languageId) {
		return getAll(GET_ALL_POTENTIAL_PARENT_CATEGORIES, languageId);
	}
	
	public List<Category> getAllLeafCategories(Long languageId) {
		return getAll(GET_LEAF_CATEGORIES, languageId);
	}

	public Long add(CategoryDTO categoryDTO) {
		Long categoryId = executeUpdateReturnKey(ADD_CATEGORY, categoryDTO.getParentId());
		categoryDTO.getLocalizations().forEach(el -> executeUpdate(ADD_CATEGORY_DETAIL, categoryId, 
				el.getLanguageId(), el.getName(), el.getItemTitle()));

		return categoryId;
	}

	public void update(CategoryDTO categoryDTO) {
		executeUpdate(UPDATE_CATEGORY, categoryDTO.getParentId(), categoryDTO.getId());
		categoryDTO.getLocalizations().forEach(el -> executeUpdate(UPDATE_CATEGORY_DETAIL, el.getName(), el.getItemTitle(), 
				categoryDTO.getId(), el.getLanguageId()));
	}

	public void delete(Long id) {
		executeUpdate(DELETE_CATEGORY, id);
	}

	@Override
	Category parseResultSet(ResultSet resultSet) throws SQLException {
		Category category = new Category();
		category.setId(resultSet.getLong("category_id"));
		category.setName(resultSet.getString("category_name"));
		category.setItemTitle(resultSet.getString("item_title"));
		category.setParentId(resultSet.getLong("parent_id"));
		category.setLanguageId(resultSet.getLong("language_id"));
		return category;
	}
}
