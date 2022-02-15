package com.epam.gadgetStore.command.impl.admin;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Order;

import static com.epam.gadgetStore.constants.MessagesConstants.ORDER_UPDATED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.ORDER_JSP;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.*;
import static com.epam.gadgetStore.util.CommandUtils.*;

public class UpdateOrderCommand implements Command {
	private final OrderDAO orderDAO = (OrderDAO) DAOFactory.ORDER_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Order order = fillOrderFromRequest(request);
		orderDAO.update(order);
		LOGGER.info("Order ID " + order.getId() + " has been updated");
		setAttributesForOrderPage(request);
		forwardToPage(ORDER_JSP, ORDER_UPDATED_MESSAGE, request, response);			
	}

	private Order fillOrderFromRequest(HttpServletRequest request) {
		Order order = orderDAO.getById(Long.parseLong(request.getParameter(ID_PARAMETER)));
		order.setShippingAddress(request.getParameter(ADDRESS_PARAMETER));
		order.setShippingPhone(request.getParameter(PHONE_NUMBER_PARAMETER));
		order.setStatus(request.getParameter(ORDER_STATUS_PARAMETER));	
		return order;
	}
}
