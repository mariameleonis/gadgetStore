package com.epam.gadgetStore.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.command.factory.CommandFactory;

import static com.epam.gadgetStore.constants.MessagesConstants.*;
import static com.epam.gadgetStore.util.CommandUtils.forwardToErrorPage;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Command command = CommandFactory.defineCommand(request);
		try {
			command.execute(request, response);
		} catch (SQLException | NumberFormatException  | UnsupportedOperationException e) {
			LOGGER.error("Cannot execute command.\n", e);
			forwardToErrorPage(ERROR_TITLE_COMMAND_MESSAGE, ERROR_MESSAGE_COMMAND_MESSAGE, request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
    public void destroy() {
       
    }

}
