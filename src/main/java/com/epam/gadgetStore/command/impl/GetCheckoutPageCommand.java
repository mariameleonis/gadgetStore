package com.epam.gadgetStore.command.impl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;

import static com.epam.gadgetStore.constants.AttributeConstants.TARGET_COMMAND_ATTRIBUTE;
import static com.epam.gadgetStore.constants.AttributeConstants.USER_ROLE_ATTRIBUTE;
import static com.epam.gadgetStore.constants.MessagesConstants.USER_LOGIN_REQUIRED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.CHECKOUT_JSP;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_LOGIN_JSP;
import static com.epam.gadgetStore.enums.UserRole.GUEST;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

public class GetCheckoutPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		if (request.getSession().getAttribute(USER_ROLE_ATTRIBUTE) == GUEST) {
			request.getSession().setAttribute(TARGET_COMMAND_ATTRIBUTE, "checkout");
			forwardToPage(CUSTOMER_LOGIN_JSP, USER_LOGIN_REQUIRED_MESSAGE, request, response);
			return;
		} 
		
		forwardToPage(CHECKOUT_JSP, request, response);

	}

}
