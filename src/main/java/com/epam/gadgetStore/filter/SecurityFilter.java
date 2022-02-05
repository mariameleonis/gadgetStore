package com.epam.gadgetStore.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.MessagesConstants.USER_LOGIN_REQUIRED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_LOGIN_JSP;
import static com.epam.gadgetStore.constants.RequestParameterNamesConstants.COMMAND_PARAMETER;
import static com.epam.gadgetStore.enums.UserRole.*;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

import com.epam.gadgetStore.command.factory.CommandEnum;
import com.epam.gadgetStore.dao.UserDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.User;
import com.epam.gadgetStore.enums.UserRole;

public class SecurityFilter implements Filter {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		User loggedUser = (User) session.getAttribute(LOGGED_USER_ATTRIBUTE);
		UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.getDAO();
		UserRole loggedUserRole = GUEST;

		if (loggedUser != null) {
			try {
				User currentUser = userDAO.getById(loggedUser.getId());
				if (currentUser == null)
					throw new IllegalArgumentException("Logged user " + loggedUser.getEmail() + " not found");
				if (currentUser.getIsBlocked())
					throw new IllegalArgumentException("Logged user " + currentUser.getEmail() + " was blocked");

				session.setAttribute(LOGGED_USER_ATTRIBUTE, loggedUser);
				loggedUserRole = defineUserRole(loggedUser);

			} catch (IllegalArgumentException e) {
				LOGGER.warn(e.getMessage());
				session.removeAttribute(LOGGED_USER_ATTRIBUTE);
			}
		}

		session.setAttribute(USER_ROLE_ATTRIBUTE, loggedUserRole);

		String commandName = request.getParameter(COMMAND_PARAMETER);
		UserRole requiredUserRole = defineUserRoleFromCommand(commandName);

		boolean hasPermission = requiredUserRole == GUEST || loggedUserRole.equals(requiredUserRole);

		if (!hasPermission) {
			LOGGER.warn("User is trying to execute command '" + commandName + "' without permission");
			forwardToPage(CUSTOMER_LOGIN_JSP, USER_LOGIN_REQUIRED_MESSAGE, httpRequest, (HttpServletResponse) response);
			return;
		}

		chain.doFilter(request, response);
	}

	private UserRole defineUserRole(User loggedUser) {
		UserRole loggedUserRole;
		if (loggedUser.getIsAdmin()) {
			loggedUserRole = ADMIN;
		} else {
			loggedUserRole = CUSTOMER;
		}
		return loggedUserRole;
	}

	private UserRole defineUserRoleFromCommand(String commandName) {
		UserRole requiredUserRole = GUEST;

		if ((commandName != null) && (!commandName.trim().isEmpty())) {
			try {
				requiredUserRole = CommandEnum.valueOf(commandName.toUpperCase()).getUserRole();
			} catch (Exception e) {
				LOGGER.warn("Failed to define required user role for command '" + commandName + "'");
			}
		}
		return requiredUserRole;
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
