package com.epam.gadgetStore.command.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dao.impl.ProductDAOImpl;
import com.epam.gadgetStore.entity.Product;

import static com.epam.gadgetStore.constants.PageNameConstants.HOME_JSP;
import static com.epam.gadgetStore.util.CommandUtils.extractLanguageIdFromSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetHomePageCommand implements Command {
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		ProductDAOImpl productDAOImpl = (ProductDAOImpl) DAOFactory.PRODUCT_DAO.getDAO();
		Long languageId = extractLanguageIdFromSession(request);
		List<Product> listNewProducts = productDAOImpl.getNewProducts(languageId);
		List<Product> listBestSellingProducts = productDAOImpl.getBestsellingProducts(languageId);
		request.setAttribute("listNewProducts", listNewProducts);
		request.setAttribute("listBestSellingProducts", listBestSellingProducts);
		request.getRequestDispatcher(HOME_JSP).forward(request, response);
	}

}
