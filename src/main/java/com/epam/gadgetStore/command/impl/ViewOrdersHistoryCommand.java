package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.ORDERS_HISTORY_JSP;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.entity.User;

public class ViewOrdersHistoryCommand implements Command {
	private final OrderDAO orderDAO = (OrderDAO) DAOFactory.ORDER_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		User customer = (User) request.getSession().getAttribute(LOGGED_USER_ATTRIBUTE);
		List<Order> listOrders = orderDAO.getAllByCustomer(customer.getId());
		
		request.setAttribute(LIST_ORDERS_ATTRIBUTE, listOrders);
		request.getRequestDispatcher(ORDERS_HISTORY_JSP).forward(request, response);
	}
}
