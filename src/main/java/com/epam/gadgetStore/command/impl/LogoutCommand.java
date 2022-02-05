package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.command.factory.CommandEnum;
import com.epam.gadgetStore.entity.User;

public class LogoutCommand implements Command {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(LOGGED_USER_ATTRIBUTE);
		if (user != null) {
		session.removeAttribute(LOGGED_USER_ATTRIBUTE);
		session.removeAttribute(USER_ROLE_ATTRIBUTE);
		LOGGER.debug("User " + user.getEmail() + " logged out");
		}
		
		CommandEnum.HOME.getCommand().execute(request, response);
	}
}
