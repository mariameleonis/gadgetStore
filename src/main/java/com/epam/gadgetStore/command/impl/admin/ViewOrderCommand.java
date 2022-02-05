package com.epam.gadgetStore.command.impl.admin;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;

import static com.epam.gadgetStore.constants.PageNameConstants.ORDER_JSP;
import static com.epam.gadgetStore.util.CommandUtils.setAttributesForOrderPage;

public class ViewOrderCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		setAttributesForOrderPage(request);
		request.getRequestDispatcher(ORDER_JSP).forward(request, response);
	}
}
