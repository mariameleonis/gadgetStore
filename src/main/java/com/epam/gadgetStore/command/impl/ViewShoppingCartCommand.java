package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.SHOPPING_CART_ATTRIBUTE;
import static com.epam.gadgetStore.constants.PageNameConstants.SHOPPING_CART_JSP;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.shoppingcart.ShoppingCart;

public class ViewShoppingCartCommand implements Command {


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);	
		
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			request.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, shoppingCart);
		}
			
		request.getRequestDispatcher(SHOPPING_CART_JSP).forward(request, response);
	}

}
