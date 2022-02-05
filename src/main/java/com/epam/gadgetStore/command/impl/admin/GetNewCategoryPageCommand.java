package com.epam.gadgetStore.command.impl.admin;

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
import com.epam.gadgetStore.entity.Language;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.CATEGORY_FORM_JSP;
import static com.epam.gadgetStore.util.CommandUtils.*;;

public class GetNewCategoryPageCommand implements Command {
	private final CategoryDAO CATEGORY_DAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setLocalizations(new ArrayList<>());

		List<Language> languages = (List<Language>) request.getSession().getAttribute(APP_LANGUAGES_ATTRIBUTE);
		languages.forEach(lang -> categoryDTO.getLocalizations().add(new CategoryLocalizationDTO(lang.getId())));

		request.setAttribute(CATEGORY_DTO_ATTRIBUTE, categoryDTO);

		Long languageId = extractLanguageIdFromSession(request);
		List<Category> listPotentialParentCategories = CATEGORY_DAO.getAllPotentialParent(languageId);
		Map<Long, List<Category>> allPotentialParentCategories = listPotentialParentCategories.stream()
				.collect(Collectors.groupingBy(Category::getParentId));

		request.setAttribute(PARENT_CATEGORIES_ATTRIBUTE, allPotentialParentCategories);

		forwardToPage(CATEGORY_FORM_JSP, request, response);
	}
}
