package com.epam.gadgetStore.command.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.command.factory.CommandEnum;
import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.dao.OrderItemDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dto.CheckoutDTO;
import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.entity.OrderItem;
import com.epam.gadgetStore.entity.User;
import com.epam.gadgetStore.shoppingcart.ShoppingCart;
import com.epam.gadgetStore.validation.CheckoutFormValidator;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.MessagesConstants.THANK_YOU_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.*;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ADDRESS_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.CARD_HOLDER_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.CARD_NUMBER_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.EXPIRATION_DATE_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.PHONE_NUMBER_PARAMETER;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.SECURITY_CODE_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

public class PlaceOrderCommand implements Command {
	private final OrderDAO orderDAO = (OrderDAO) DAOFactory.ORDER_DAO.getDAO();
	private final OrderItemDAO orderItemDAO = (OrderItemDAO) DAOFactory.ORDER_ITEM_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		CheckoutDTO checkoutDTO = createCheckoutDTOFromRequest(request);
		Set<String> validationErrorAttributes = CheckoutFormValidator.getInstance().getErrorAttributes(checkoutDTO);
		
		if (validationErrorAttributes.size() > 0) {
			validationErrorAttributes.forEach(attr -> request.setAttribute(attr, true));
            request.getSession().setAttribute(CHECKOUT_DTO_ATTRIBUTE, checkoutDTO);
            LOGGER.debug("Values in one or more fields are invalid.");
            forwardToPage(CHECKOUT_JSP, request, response);
            return;
		}
		
		Long orderId = saveOrderToDatabase(checkoutDTO, request);
		LOGGER.info("Order ID " + orderId + " has been placed successfully");
		
		clearShoppingCart(request);
		request.getSession().removeAttribute(CHECKOUT_DTO_ATTRIBUTE);
		request.setAttribute(ID_ATTRIBUTE, orderId);
		request.setAttribute(MESSAGE_ATTRIBUTE, THANK_YOU_MESSAGE);
		CommandEnum.VIEW_ORDER_DETAILS.getCommand().execute(request, response);
	}

	private void clearShoppingCart(HttpServletRequest request) {
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
		cart.clear();
	}
	
	private CheckoutDTO createCheckoutDTOFromRequest(HttpServletRequest request) {
		CheckoutDTO checkoutDTO = new CheckoutDTO();
		checkoutDTO.setPhone(request.getParameter(PHONE_NUMBER_PARAMETER));
		checkoutDTO.setAddress(request.getParameter(ADDRESS_PARAMETER));
		checkoutDTO.setCardHolder(request.getParameter(CARD_HOLDER_PARAMETER));
		checkoutDTO.setCardNumber(request.getParameter(CARD_NUMBER_PARAMETER));
		checkoutDTO.setExpirationDate(request.getParameter(EXPIRATION_DATE_PARAMETER));
		checkoutDTO.setSecurityCode(request.getParameter(SECURITY_CODE_PARAMETER));		
		return checkoutDTO;
	}

	private Long saveOrderToDatabase(CheckoutDTO checkoutDTO, HttpServletRequest request) {
		Order order = createOrderFromCheckoutDTO(checkoutDTO, request);
		Long orderId = orderDAO.add(order);
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
		cart.getItems().forEach((product, quantity) -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(orderId);
			orderItem.setProduct(product);
			orderItem.setSellingPrice(product.getPrice());
			orderItem.setQuantity(quantity);
			orderItemDAO.add(orderItem);		
		});
		return orderId;
	}

	private Order createOrderFromCheckoutDTO(CheckoutDTO checkoutDTO, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(LOGGED_USER_ATTRIBUTE);
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
		Order order = new Order();
		order.setUser(user);
		order.setShippingAddress(checkoutDTO.getAddress());
		order.setShippingPhone(checkoutDTO.getPhone());
		order.setTotal((float) cart.getTotalCost());
		return order;
	}
}
