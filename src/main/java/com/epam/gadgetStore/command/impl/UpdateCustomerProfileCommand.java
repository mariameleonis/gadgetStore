package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.LOGGED_USER_ATTRIBUTE;
import static com.epam.gadgetStore.constants.MessagesConstants.USER_PROFILE_UPDATED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_PROFILE_EDIT_FORM_JSP;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_PROFILE_JSP;
import static com.epam.gadgetStore.util.CommandUtils.*;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.UserDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.User;
import com.epam.gadgetStore.exception.DAOException;

public class UpdateCustomerProfileCommand implements Command {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
	private final UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		User customer = (User) request.getSession().getAttribute(LOGGED_USER_ATTRIBUTE);
		
		fillUserFromRequest(customer, request);
						
		if (!isValidUserForm(request, response, customer)) {
			LOGGER.debug("Values in one or more fields are invalid.");
			forwardToPage(CUSTOMER_PROFILE_EDIT_FORM_JSP, request, response);
			return;
		}
		
		setEncryptedPassword(request, customer);
		
		try {
			userDAO.update(customer);
		} catch (DAOException e) {
			LOGGER.debug("Failed to update profile for user " + customer.getEmail() +"/n", e);
			throw new UnsupportedOperationException(e);
		}
		
		LOGGER.debug("Profile updated for user " + customer.getEmail());
		forwardToPage(CUSTOMER_PROFILE_JSP, USER_PROFILE_UPDATED_MESSAGE, request, response);	
	}

}
