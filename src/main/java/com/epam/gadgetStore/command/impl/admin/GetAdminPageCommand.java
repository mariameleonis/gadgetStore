package com.epam.gadgetStore.command.impl.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.gadgetStore.constants.PageNameConstants.ADMIN_INDEX_JSP;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import com.epam.gadgetStore.command.Command;

public class GetAdminPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		forwardToPage(ADMIN_INDEX_JSP, request, response);
	}

}
