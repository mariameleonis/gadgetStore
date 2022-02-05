package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.AttributeConstants.LIST_PRODUCTS_ATTRIBUTE;
import static com.epam.gadgetStore.constants.PageNameConstants.PRODUCT_LIST_JSP;
import static com.epam.gadgetStore.util.CommandUtils.extractLanguageIdFromSession;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.ProductDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Product;

public class GetProductListPageCommand implements Command {
	ProductDAO productDAO = (ProductDAO) DAOFactory.PRODUCT_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Long languageId = extractLanguageIdFromSession(request);
		List<Product> listProduct = productDAO.getAllByLangId(languageId);
		request.setAttribute(LIST_PRODUCTS_ATTRIBUTE, listProduct);
		forwardToPage(PRODUCT_LIST_JSP, request, response);
	}
}
