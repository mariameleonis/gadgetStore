package com.epam.gadgetStore.util;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.PageNameConstants.ERROR_JSP;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.epam.gadgetStore.dao.BrandDAO;
import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dao.OrderDAO;
import com.epam.gadgetStore.dao.OrderItemDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Brand;
import com.epam.gadgetStore.entity.Category;
import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.entity.OrderItem;
import com.epam.gadgetStore.entity.User;
import com.epam.gadgetStore.enums.OrderStatus;
import com.epam.gadgetStore.validation.UserFormValidator;

public class CommandUtils {

	public static void forwardToPage(String page, String message, String messageParameter, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (message != null)
			request.setAttribute(MESSAGE_ATTRIBUTE, message);

		if (messageParameter != null)
			request.setAttribute(MESSAGE_PARAMETER_ATTRIBUTE, messageParameter);
		forwardToPage(page, request, response);
	}

	public static void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(page).forward(request, response);
	}

	public static void forwardToErrorPage(String errorTitle, String errorMessage, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(ERROR_TITLE_ATTRIBUTE, errorTitle);
		request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
		forwardToPage(ERROR_JSP, request, response);
	}

	public static void forwardToPage(String page, String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		forwardToPage(page, message, null, request, response);
	}

	public static long extractLanguageIdFromSession(HttpServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();

		long langId = (Long) session.getAttribute(LANGUAGE_ID_ATTRIBUTE);
		return langId;
	}
	
	public static void getDataForProductForm(HttpServletRequest request) {
		CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();
		BrandDAO brandDAO = (BrandDAO) DAOFactory.BRAND_DAO.getDAO();
		
		List<Category> allLeafCategories = categoryDAO.getAllLeafCategories(extractLanguageIdFromSession(request));
		request.setAttribute(LEAF_CATEGORIES_ATTRIBUTE, allLeafCategories);
		
		List<Brand> allBrands = brandDAO.getAll();
		request.setAttribute(ALL_BRANDS_ATTRIBUTE, allBrands);
	}

	public static void fillUserFromRequest(User newUser, HttpServletRequest request) {
		String email = request.getParameter(EMAIL_PARAMETER);
		String firstName = request.getParameter(FIRSTNAME_PARAMETER);
		String lastName = request.getParameter(LASTNAME_PARAMETER);
		String phoneNumber = request.getParameter(PHONE_NUMBER_PARAMETER);
		String password = request.getParameter(PASSWORD_PARAMETER);
		boolean isAdmin = Boolean.parseBoolean(request.getParameter(IS_ADMIN_PARAMETER));

		if (email != null && !email.isBlank())
			newUser.setEmail(email);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		if (password != null && !password.isBlank()) {
			newUser.setPassword(password);
		}

		if (phoneNumber != null && !phoneNumber.isBlank())
			newUser.setPhoneNumber(phoneNumber);

		newUser.setIsAdmin(isAdmin);

	}
		
	public static boolean isValidUserForm(HttpServletRequest request, HttpServletResponse response, User user)
			throws ServletException, IOException {
		Set<String> validationErrorAttributes = UserFormValidator.getInstance().getErrorAttributes(user);
		if (validationErrorAttributes.size() != 0) {
			validationErrorAttributes.forEach(attr -> request.setAttribute(attr, true));
			request.setAttribute(FORM_USER_ATTRIBUTE, user);
			return false;
		}
		return true;
	}

	public static void setEncryptedPassword(HttpServletRequest request, User admin) {
		String password = request.getParameter(PASSWORD_PARAMETER);
		if (password != null && !password.isBlank()) {
			String encryptedPassword = HashGenerator.generateMD5(admin.getPassword());
			admin.setPassword(encryptedPassword);
		}
	}
	
	public static void setAttributesForOrderPage(HttpServletRequest request) {	
		OrderDAO orderDAO = (OrderDAO) DAOFactory.ORDER_DAO.getDAO();
		OrderItemDAO orderItemDAO = (OrderItemDAO) DAOFactory.ORDER_ITEM_DAO.getDAO();
		Long orderId = Long.parseLong(request.getParameter(ID_PARAMETER));
		Order order = orderDAO.getById(orderId);
		request.setAttribute(ORDER_ATTRIBUTE, order);

		Long languageId = extractLanguageIdFromSession(request);
		List<OrderItem> orderItems = orderItemDAO.getAllByOrderId(order.getId(), languageId);
		request.setAttribute(ORDER_ITEMS_ATTRIBUTE, orderItems);
		
		List<String> orderStatuses = new ArrayList<String>();
		Arrays.asList(OrderStatus.values()).forEach(el -> orderStatuses.add(el.name()));
		
		request.setAttribute(ORDER_STATUSES_ATTRIBUTE, orderStatuses);
	}
}
