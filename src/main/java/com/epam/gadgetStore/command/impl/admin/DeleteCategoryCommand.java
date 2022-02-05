package com.epam.gadgetStore.command.impl.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.exception.DAOException;

import static com.epam.gadgetStore.constants.MessagesConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.ADMIN_MESSAGE_JSP;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.*;;

public class DeleteCategoryCommand implements Command {
	private final CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
				
		Long categoryId = Long.parseLong(request.getParameter(ID_PARAMETER));
		
		try {
			categoryDAO.delete(categoryId);
		} catch (DAOException e) {
			LOGGER.debug("Failed to delete category ID " + categoryId.toString() +"/n", e);
			forwardToPage(ADMIN_MESSAGE_JSP, CATEGORY_NOT_EMPTY_MESSAGE, categoryId.toString(), request, response);
			return;
		}
		
		LOGGER.debug("Category ID " + categoryId.toString() + " has been deleted successfully");
		forwardToPage(ADMIN_MESSAGE_JSP, CATEGORY_DELETED_MESSAGE, categoryId.toString(), request, response);
	}

}
