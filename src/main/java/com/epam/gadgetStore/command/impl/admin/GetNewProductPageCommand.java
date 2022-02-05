package com.epam.gadgetStore.command.impl.admin;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;

import static com.epam.gadgetStore.constants.PageNameConstants.*;
import static com.epam.gadgetStore.util.CommandUtils.*;

public class GetNewProductPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
        getDataForProductForm(request);
        forwardToPage(PRODUCT_FORM_JSP, request, response);
	}

}
