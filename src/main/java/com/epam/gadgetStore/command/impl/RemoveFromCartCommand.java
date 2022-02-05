package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.SHOPPING_CART_ATTRIBUTE;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ID_PARAMETER;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.entity.Product;
import com.epam.gadgetStore.shoppingcart.ShoppingCart;

public class RemoveFromCartCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String productIdParameter = request.getParameter(ID_PARAMETER);
		Long productId = Long.parseLong(productIdParameter);
		
		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
		
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			request.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, shoppingCart);
		}
		
		shoppingCart.removeItem(new Product(productId));
		
		response.sendRedirect(request.getRequestURL() + "?command=view_shopping_cart");
	}

}
