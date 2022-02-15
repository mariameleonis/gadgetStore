package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.MessagesConstants.USER_LOGIN_BLOCKED_MESSAGE;
import static com.epam.gadgetStore.constants.MessagesConstants.USER_LOGIN_FAILED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.ADMIN_INDEX_JSP;
import static com.epam.gadgetStore.constants.PageNameConstants.CUSTOMER_LOGIN_JSP;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.EMAIL_PARAMETER;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.PASSWORD_PARAMETER;
import static com.epam.gadgetStore.enums.UserRole.*;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;

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
import com.epam.gadgetStore.dao.UserDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dto.LoginDTO;
import com.epam.gadgetStore.entity.User;
import com.epam.gadgetStore.util.HashGenerator;
import com.epam.gadgetStore.validation.LoginFormValidator;

public class LoginCommand implements Command {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
	private final UserDAO userDAO = (UserDAO) DAOFactory.USER_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		LoginDTO loginDTO = createLoginDTOFromRequest(request);
		
		if (!isValidLoginForm(request, response, loginDTO)) {
			LOGGER.debug("Values in one or more fields are invalid.");
			forwardToPage(CUSTOMER_LOGIN_JSP, request, response);
			return;
		}
		
		User loggedUser = getUserFromLoginDTOByCredentials(loginDTO);
		
		if (loggedUser == null) {
			LOGGER.debug("Login failed for \"" + loginDTO.getEmail() + "\"");
			forwardToPage(CUSTOMER_LOGIN_JSP, USER_LOGIN_FAILED_MESSAGE, request, response);
			return;
		}
		
		if (loggedUser.getIsBlocked()) {
			LOGGER.debug("Login blocked for \"" + loginDTO.getEmail() + "\"");
			forwardToPage(CUSTOMER_LOGIN_JSP, USER_LOGIN_BLOCKED_MESSAGE, request, response);
			return;
		}
		
		forwardAfterLogin(request, response, loggedUser);
	}
	
	private LoginDTO createLoginDTOFromRequest(HttpServletRequest request) {
		String email = request.getParameter(EMAIL_PARAMETER);
		String password = request.getParameter(PASSWORD_PARAMETER);
		
		return new LoginDTO(email, password);
	}	
	
	private void forwardAfterLogin(HttpServletRequest request, HttpServletResponse response, User loggedUser) 
			throws SQLException, ServletException, IOException {
		request.getSession().setAttribute(LOGGED_USER_ATTRIBUTE, loggedUser);
		
		if (loggedUser.getIsAdmin()) {
			request.getSession().setAttribute(USER_ROLE_ATTRIBUTE, ADMIN);
			LOGGER.debug("Admin \"" + loggedUser.getEmail() + "\" logged in");
			forwardToPage(ADMIN_INDEX_JSP, request, response);
			return;
		} 
		
		request.getSession().setAttribute(USER_ROLE_ATTRIBUTE, CUSTOMER);
		LOGGER.debug("Customer " + loggedUser.getEmail() + " logged in");
		
		if (request.getSession().getAttribute(TARGET_COMMAND_ATTRIBUTE) != null) {
			String targetCommand = (String) request.getSession().getAttribute(TARGET_COMMAND_ATTRIBUTE);
			request.getSession().removeAttribute(TARGET_COMMAND_ATTRIBUTE);
			response.sendRedirect(request.getRequestURL() + "?command=" + targetCommand);
			LOGGER.debug("Customer redirected to " + request.getRequestURL() + "?command=" + targetCommand);
			return;
		}

		CommandEnum.HOME.getCommand().execute(request, response);	
	}
	
	private boolean isValidLoginForm(HttpServletRequest request, HttpServletResponse response, LoginDTO loginDTO)
			throws ServletException, IOException {
		Set<String> validationErrorAttributes = LoginFormValidator.getInstance().getErrorAttributes(loginDTO);
		
		if (validationErrorAttributes.size() != 0) {
			validationErrorAttributes.forEach(attr -> request.setAttribute(attr, true));
			request.setAttribute(FORM_USER_ATTRIBUTE, loginDTO);
			return false;
		}
		return true;
	}
	
	private User getUserFromLoginDTOByCredentials(LoginDTO loginDTO) {
		String email = loginDTO.getEmail();
		String password = loginDTO.getPassword();
		
		String encryptedPassword = HashGenerator.generateMD5(password);
		User loggedUser = userDAO.checkLogin(email, encryptedPassword);
		
		return loggedUser;		
	}
}
