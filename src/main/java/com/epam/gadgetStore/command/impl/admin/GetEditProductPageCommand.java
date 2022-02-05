package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.AttributeConstants.PRODUCT_ATTRIBUTE;
import static com.epam.gadgetStore.constants.PageNameConstants.PRODUCT_FORM_JSP;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.extractLanguageIdFromSession;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;
import static com.epam.gadgetStore.util.CommandUtils.getDataForProductForm;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.ProductDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Product;

public class GetEditProductPageCommand implements Command {
	ProductDAO productDAO = (ProductDAO) DAOFactory.PRODUCT_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Long languageId = extractLanguageIdFromSession(request);
		Long productId = Long.parseLong(request.getParameter(ID_PARAMETER));

		Product product = productDAO.getById(productId, languageId);
		request.setAttribute(PRODUCT_ATTRIBUTE, product);
		
		getDataForProductForm(request);
		forwardToPage(PRODUCT_FORM_JSP, request, response);
	}
}
