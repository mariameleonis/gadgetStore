package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.MessagesConstants.CATEGORY_CREATED_MESSAGE;
import static com.epam.gadgetStore.constants.MessagesConstants.CATEGORY_UPDATED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.*;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.CATEGORY_ITEM_TITLE_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.CATEGORY_NAME_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.PARENT_CATEGORY_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dto.CategoryDTO;
import com.epam.gadgetStore.dto.CategoryLocalizationDTO;
import com.epam.gadgetStore.entity.Language;
import com.epam.gadgetStore.validation.CategoryErrorStorage;
import com.epam.gadgetStore.validation.CategoryFormValidator;

public class SaveCategoryCommand implements Command {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
	private final CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		CategoryDTO categoryDTO = createCategoryDTOFromRequest(request);
		Set<CategoryErrorStorage> categoryErrorStorage = CategoryFormValidator.getInstance()
				.getErrorAttributes(categoryDTO);

		if (categoryErrorStorage.stream().anyMatch(storage -> hasErrorAttributes(storage))) {
			request.setAttribute(CATEGORY_DTO_ATTRIBUTE, categoryDTO);
			request.setAttribute(CATEGORY_ERROR_STORAGE_ATTRIBUTE, categoryErrorStorage);
			forwardToPage(CATEGORY_FORM_JSP, request, response);
			LOGGER.debug("Values in one or more fields are invalid.");
			return;
		}

		if (categoryDTO.getId() == null) {
			String categoryId = categoryDAO.add(categoryDTO).toString();
			LOGGER.info("Category ID " + categoryId + " has been created successfully");
			forwardToPage(ADMIN_MESSAGE_JSP, CATEGORY_CREATED_MESSAGE, categoryId, request, response);
			return;
		}

		categoryDAO.update(categoryDTO);
		LOGGER.info("Category ID " + categoryDTO.getId().toString() + " has been updated successfully");
		forwardToPage(ADMIN_MESSAGE_JSP, CATEGORY_UPDATED_MESSAGE, request, response);
	}

	private CategoryDTO createCategoryDTOFromRequest(HttpServletRequest request) {
		CategoryDTO categoryDTO = new CategoryDTO();
		String categoryIdParameter = request.getParameter(ID_PARAMETER);
		if (categoryIdParameter != null && !categoryIdParameter.isBlank()) {
			Long categoryId = Long.parseLong(categoryIdParameter);
			categoryDTO.setId(categoryId);
		}

		List<Language> languages = (List<Language>) request.getSession().getAttribute(APP_LANGUAGES_ATTRIBUTE);
		categoryDTO.setParentId(Long.parseLong(request.getParameter(PARENT_CATEGORY_PARAMETER)));
		categoryDTO.setLocalizations(new ArrayList<CategoryLocalizationDTO>());
		languages.forEach(lang -> categoryDTO.getLocalizations()
				.add(new CategoryLocalizationDTO(lang.getId(),
						request.getParameter(CATEGORY_NAME_PARAMETER + lang.getId()),
						request.getParameter(CATEGORY_ITEM_TITLE_PARAMETER + lang.getId()))));

		return categoryDTO;
	}

	private boolean hasErrorAttributes(CategoryErrorStorage storage) {
		return storage.getIsWrongName() || storage.getIsWrongItemTitle() || storage.getIsDuplicateName();
	}
}
