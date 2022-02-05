package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.AttributeConstants.CATEGORY_DTO_ATTRIBUTE;
import static com.epam.gadgetStore.constants.AttributeConstants.PARENT_CATEGORIES_ATTRIBUTE;
import static com.epam.gadgetStore.constants.PageNameConstants.CATEGORY_FORM_JSP;
import static com.epam.gadgetStore.util.CommandUtils.extractLanguageIdFromSession;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dto.CategoryDTO;
import com.epam.gadgetStore.dto.CategoryLocalizationDTO;
import com.epam.gadgetStore.entity.Category;

public class GetEditCategoryPageCommand implements Command {
	private final CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Long categoryId = Long.parseLong(request.getParameter("id"));

		List<Category> category = categoryDAO.getAllById(categoryId);
		CategoryDTO categoryDTO = createCategoryDTOFromCategory(category);

		request.setAttribute(CATEGORY_DTO_ATTRIBUTE, categoryDTO);

		Long languageId = extractLanguageIdFromSession(request);
		List<Category> listPotentialParentCategories = categoryDAO.getAllPotentialParent(languageId);
		Map<Long, List<Category>> allPotentialParentCategories = listPotentialParentCategories.stream()
				.collect(Collectors.groupingBy(Category::getParentId));

		request.setAttribute(PARENT_CATEGORIES_ATTRIBUTE, allPotentialParentCategories);

		forwardToPage(CATEGORY_FORM_JSP, request, response);
	}

	private CategoryDTO createCategoryDTOFromCategory(List<Category> category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setLocalizations(new ArrayList<>());

		category.forEach(cat -> {
			categoryDTO.setId(cat.getId());
			categoryDTO.setParentId(cat.getParentId());
			categoryDTO.getLocalizations()
					.add(new CategoryLocalizationDTO(cat.getLanguageId(), cat.getName(), cat.getItemTitle()));
		});

		return categoryDTO;
	}
}
