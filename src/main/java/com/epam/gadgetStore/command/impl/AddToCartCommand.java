package com.epam.gadgetStore.command.impl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dao.impl.ProductDAOImpl;
import com.epam.gadgetStore.entity.Product;
import com.epam.gadgetStore.shoppingcart.ShoppingCart;

import static com.epam.gadgetStore.constants.AttributeConstants.SHOPPING_CART_ATTRIBUTE;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.ID_PARAMETER;
import static com.epam.gadgetStore.util.CommandUtils.extractLanguageIdFromSession;


public class AddToCartCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		ProductDAOImpl productDAOImpl = (ProductDAOImpl) DAOFactory.PRODUCT_DAO.getDAO();
		Long languageId = extractLanguageIdFromSession(request);
		String productIdParameter = request.getParameter(ID_PARAMETER);
		Long productId = Long.parseLong(productIdParameter);
		
		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(SHOPPING_CART_ATTRIBUTE);
		
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			request.getSession().setAttribute(SHOPPING_CART_ATTRIBUTE, shoppingCart);
		}
		
		Product product = productDAOImpl.getById(productId, languageId);
		shoppingCart.addItem(product);
		
		response.sendRedirect(request.getRequestURL() + "?command=view_shopping_cart");
	}

}
