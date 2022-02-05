package com.epam.gadgetStore.validation;

import java.util.HashSet;
import java.util.Set;

import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dto.CategoryDTO;
import com.epam.gadgetStore.entity.Category;

public class CategoryFormValidator implements FormValidator<CategoryDTO, CategoryErrorStorage> {
	private static final int MAX_LENGTH_CATEGORY_NAME = 30;
	private static final int MAX_LENGTH_CATEGORY_ITEM_TITLE = 45;
	
	private static CategoryFormValidator instance = new CategoryFormValidator();
	
	private CategoryFormValidator() {
		
	}

	@Override
	public Set<CategoryErrorStorage> getErrorAttributes(CategoryDTO categoryDTO) {
		Set<CategoryErrorStorage> categoryErrorStorages = new HashSet<>();
		CategoryDAO CATEGORY_DAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();
		
		categoryDTO.getLocalizations().forEach(el -> {
			CategoryErrorStorage categoryErrorStorage = new CategoryErrorStorage(el.getLanguageId());	
			
			categoryErrorStorage.setWrongName(el.getName().isEmpty() || el.getName().length() > MAX_LENGTH_CATEGORY_NAME);
			categoryErrorStorage.setWrongItemTitle(el.getItemTitle().isEmpty() || el.getItemTitle().length() > MAX_LENGTH_CATEGORY_ITEM_TITLE);			
			
			Category category = CATEGORY_DAO.findByLocalizedName(el.getName(), el.getLanguageId());
				
			if (categoryDTO.getId() == null) {
					categoryErrorStorage.setDuplicateName(category != null);
				} else {
					categoryErrorStorage.setDuplicateName(category != null && category.getId() != categoryDTO.getId());
				}
				
			categoryErrorStorages.add(categoryErrorStorage);
		});
		
		return categoryErrorStorages;
	}
	
	public static CategoryFormValidator getInstance() {
		 if (instance == null) {
	            instance = new CategoryFormValidator();
	        }
	        return instance;
	}

}
