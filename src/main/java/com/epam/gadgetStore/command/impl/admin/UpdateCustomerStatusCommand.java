package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.MessagesConstants.CUSTOMER_STATUS_UPDATED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.ADMIN_MESSAGE_JSP;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

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

public class UpdateCustomerStatusCommand implements Command {
	private final UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		Long customerId = Long.parseLong(request.getParameter(ID_PARAMETER));
		User customer = userDAO.getById(customerId);
		
		try {
			userDAO.updateStatus(customer);
		} catch (DAOException e) {
			LOGGER.debug("Failed to update status for user " + customer.getEmail() +"/n", e);
			throw new UnsupportedOperationException(e);
		}
		
		LOGGER.debug("Property 'isBlocked' for user " + customer.getEmail() + " was set in '" + !customer.getIsBlocked() +"'");
		forwardToPage(ADMIN_MESSAGE_JSP, CUSTOMER_STATUS_UPDATED_MESSAGE, customer.getEmail(), request, response);	
	}
}
