package com.epam.gadgetStore.command.impl.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.dao.UserDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.entity.User;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_DETAILS_JSP;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

public class ViewCustomerDetailsCommand implements Command {
	private final UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.getDAO();
	private final OrderDAO orderDAO = (OrderDAO) DAOFactory.ORDER_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Long customerId = Long.parseLong(request.getParameter(ID_PARAMETER));
		User customer = userDAO.getById(customerId);
		request.setAttribute(CUSTOMER_ATTRIBUTE, customer);
		
		List<Order> listOrders = orderDAO.getAllByCustomer(customerId);
		request.setAttribute(LIST_ORDERS_ATTRIBUTE, listOrders);
		
		forwardToPage(CUSTOMER_DETAILS_JSP, request, response);		
	}
}
