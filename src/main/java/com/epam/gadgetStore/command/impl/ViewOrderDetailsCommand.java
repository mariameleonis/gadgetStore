package com.epam.gadgetStore.command.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.dao.OrderItemDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.entity.OrderItem;
import com.epam.gadgetStore.entity.User;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.ORDER_DETAILS_JSP;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.*;

public class ViewOrderDetailsCommand implements Command {
	private final OrderDAO orderDAO = (OrderDAO) DAOFactory.ORDER_DAO.getDAO();
	private final OrderItemDAO orderItemDAO = (OrderItemDAO) DAOFactory.ORDER_ITEM_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		Order order = getOrderFromRequest(request);	
		request.setAttribute(ORDER_ATTRIBUTE, order);

		if (order != null) {
			Long languageId = extractLanguageIdFromSession(request);
			List<OrderItem> orderItems = orderItemDAO.getAllByOrderId(order.getId(), languageId);
			request.setAttribute(ORDER_ITEMS_ATTRIBUTE, orderItems);
		}

		forwardToPage(ORDER_DETAILS_JSP, request, response);
	}

	private Order getOrderFromRequest(HttpServletRequest request) {
		Long orderId = getOrderIdFromRequest(request);
		User customer = (User) request.getSession().getAttribute(LOGGED_USER_ATTRIBUTE);
		Order order = orderDAO.getByIdAndCustomerId(orderId, customer.getId());
		return order;
	}

	private Long getOrderIdFromRequest(HttpServletRequest request) {
		Long orderId;
		if (request.getAttribute(ID_ATTRIBUTE) != null) {
			orderId = (Long) request.getAttribute(ID_ATTRIBUTE);
		} else {
			orderId = Long.parseLong(request.getParameter(ID_PARAMETER));
		}
		return orderId;
	}
}
