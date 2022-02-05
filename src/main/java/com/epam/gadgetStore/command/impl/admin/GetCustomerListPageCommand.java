package com.epam.gadgetStore.command.impl.admin;

import static com.epam.gadgetStore.constants.AttributeConstants.LIST_CUSTOMERS_ATTRIBUTE;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_LIST_JSP;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.UserDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.User;

public class GetCustomerListPageCommand implements Command {
	private final UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, ServletException, IOException {
		List<User> listCustomers = userDAO.getAllCustomers();
		request.setAttribute(LIST_CUSTOMERS_ATTRIBUTE, listCustomers);
		forwardToPage(CUSTOMER_LIST_JSP, request, response);		
	}
}
