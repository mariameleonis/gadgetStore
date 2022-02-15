package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.PRODUCT_ATTRIBUTE;
import static com.epam.gadgetStore.constants.PageNameConstants.*;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.extractLanguageIdFromSession;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;
import static com.epam.gadgetStore.util.caller.DAOCaller.tryCallGetByID;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.ProductDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Product;

public class ViewProductCommand implements Command {
	ProductDAO productDAO = (ProductDAO) DAOFactory.PRODUCT_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Long languageId = extractLanguageIdFromSession(request);
		String productIdParameter = request.getParameter(ID_PARAMETER);

		try {
			Product product = tryCallGetByID(productIdParameter, id -> {
				return productDAO.getById(id, languageId);
			});
 
			request.setAttribute(PRODUCT_ATTRIBUTE, product);
			forwardToPage(PRODUCT_JSP, request, response);
			return;

		} catch (NumberFormatException e) {
			LOGGER.debug("Invalid product ID parameter passed: " + e.getMessage());			
		} catch (IllegalArgumentException e) {
			LOGGER.debug("Failed to view product details: " + e.getMessage());		
		}	
		
		forwardToPage(ERROR_404_JSP, request, response);
	}
}
