package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.MessagesConstants.REGISTRATION_SUCCESS_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_LOGIN_JSP;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_REGISTER_FORM_JSP;
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

public class RegisterCustomerCommand implements Command {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
	private final UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		User newUser = new User();
		fillUserFromRequest(newUser, request);
			
		if (!isValidUserForm(request, response, newUser)) {
			forwardToPage(CUSTOMER_REGISTER_FORM_JSP, request, response);
			return;
		}
		
		setEncryptedPassword(request, newUser);
	
		try {
			userDAO.add(newUser);
		} catch (DAOException e) {
			LOGGER.debug("Failed to register new user " + newUser.getEmail() +"/n", e);
			throw new UnsupportedOperationException(e);
		}
		
		LOGGER.debug("A new customer with email " + newUser.getEmail() + " has been registered");
		forwardToPage(CUSTOMER_LOGIN_JSP,  REGISTRATION_SUCCESS_MESSAGE, request, response);
	}

}
