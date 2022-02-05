package com.epam.gadgetStore.command.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dao.impl.ProductDAOImpl;
import com.epam.gadgetStore.entity.Product;

import static com.epam.gadgetStore.constants.PageNameConstants.SEARCH_RESULTS_JSP;
import static com.epam.gadgetStore.util.CommandUtils.*;

public class SearchCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		ProductDAOImpl productDAOImpl = (ProductDAOImpl) DAOFactory.PRODUCT_DAO.getDAO();
		String keyword = request.getParameter("keyword");
		Long languageId = extractLanguageIdFromSession(request);
		List<Product> searchResults = productDAOImpl.searchProducts(keyword, languageId);
		request.setAttribute("searchResults", searchResults);
		request.setAttribute("keyword", keyword);
		forwardToPage(SEARCH_RESULTS_JSP, request, response);
	}
}
