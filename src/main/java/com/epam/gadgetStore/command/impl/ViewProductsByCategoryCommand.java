package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.CATEGORY_JSP;
import static com.epam.gadgetStore.util.CommandUtils.extractLanguageIdFromSession;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dao.ProductDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Category;
import com.epam.gadgetStore.entity.Product;

public class ViewProductsByCategoryCommand implements Command {
	ProductDAO productDAO = (ProductDAO) DAOFactory.PRODUCT_DAO.getDAO();
	CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Long languageId = extractLanguageIdFromSession(request);
		String categoryIdParameter = request.getParameter("id");
		List<Product> listProducts = new ArrayList<>();
		Long categoryId = Long.parseLong(categoryIdParameter);

		Category currentCategory = categoryDAO.getById(categoryId, languageId);

		Map<Long, List<Category>> parentIdToCategories = (Map<Long, List<Category>>) request
				.getAttribute("categoriesTopMenu");

		if (parentIdToCategories.get(categoryId) == null) {
			listProducts = productDAO.findByCategory(categoryId, languageId);
		} else {
			listProducts = productDAO.findByLeafCategories(getLeafCategories(categoryId, parentIdToCategories),
					languageId);
		}

		request.setAttribute(LIST_PRODUCTS_ATTRIBUTE, listProducts);
		request.setAttribute(CURRENT_CATEGORY_ATTRUBUTE, currentCategory);

		forwardToPage(CATEGORY_JSP, request, response);
	}

	private List<Long> getLeafCategories(Long categoryId, Map<Long, List<Category>> parentIdToCategories) {
		List<Long> leafCategories = new ArrayList<>();

		for (Category category : parentIdToCategories.get(categoryId)) {
			if (parentIdToCategories.get(category.getId()) == null) {
				leafCategories.add(category.getId());
			} else {
				leafCategories.addAll(getLeafCategories(category.getId(), parentIdToCategories));
			}
		}

		return leafCategories;
	}
}
