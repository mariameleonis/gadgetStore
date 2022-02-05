package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.PageNameConstants.ADMIN_PROFILE_JSP;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;

public class ShowAdminProfileCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		request.getRequestDispatcher(ADMIN_PROFILE_JSP).forward(request, response);
	}

}
