package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.MessagesConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.*;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.*;
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
import com.epam.gadgetStore.exception.DAOException;

public class DeleteProductCommand implements Command {
	ProductDAO productDAO = (ProductDAO) DAOFactory.PRODUCT_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Long languageId = extractLanguageIdFromSession(request);
		String productIdParameter = request.getParameter(ID_PARAMETER);
		String commandResultMessage = PRODUCT_DELETED_MESSAGE;
		
		try {
			Product product = tryCallGetByID(productIdParameter, id -> {
				return productDAO.getById(id, languageId);
			});
			
			productDAO.delete(product.getId());
			LOGGER.debug("Product with id " + productIdParameter + " has been deleted");

		} catch (NumberFormatException e) {
			LOGGER.warn("Invalid product ID parameter passed (" + productIdParameter + ")");
			forwardToPage(ERROR_404_JSP, request, response);
			return;
		} catch (IllegalArgumentException e) {
			LOGGER.warn("The product with id " + productIdParameter + " is not found");
			commandResultMessage = PRODUCT_NOT_FOUND_MESSAGE;
		} catch (DAOException e) {
			LOGGER.warn("Failed to delete product from the database. /n" + e);
			commandResultMessage = PRODUCT_FAILED_TO_DELETE_MESSAGE;
		}
		
		forwardToPage(ADMIN_MESSAGE_JSP, commandResultMessage, productIdParameter, request, response);
	}
}
