package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.PageNameConstants.CATEGORY_LIST_JSP;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;

public class GetCategoryListPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		forwardToPage(CATEGORY_LIST_JSP, request, response);
	}

}
