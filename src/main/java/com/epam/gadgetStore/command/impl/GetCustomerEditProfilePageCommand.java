package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_PROFILE_EDIT_FORM_JSP;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;

public class GetCustomerEditProfilePageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		if (request.getAttribute(FORM_USER_ATTRIBUTE) == null)
		request.setAttribute(FORM_USER_ATTRIBUTE, request.getSession().getAttribute(LOGGED_USER_ATTRIBUTE));
		
		request.getRequestDispatcher(CUSTOMER_PROFILE_EDIT_FORM_JSP).forward(request, response);

	}

}
