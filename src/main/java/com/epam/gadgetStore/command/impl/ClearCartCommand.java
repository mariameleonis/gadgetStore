package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.SHOPPING_CART_ATTRIBUTE;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.command.factory.CommandEnum;
import com.epam.gadgetStore.shoppingcart.ShoppingCart;


public class ClearCartCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
		
		shoppingCart.clear();
		
		CommandEnum.VIEW_SHOPPING_CART.getCommand().execute(request, response);
	}

}
