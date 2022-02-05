package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_PROFILE_JSP;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;

public class ShowCustomerProfileCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		request.getRequestDispatcher(CUSTOMER_PROFILE_JSP).forward(request, response);
	}

}
