package com.epam.gadgetStore.command.factory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;

import java.io.IOException;

import static com.epam.gadgetStore.constants.MessagesConstants.ERROR_MESSAGE_UNRESOLVED_MESSAGE;
import static com.epam.gadgetStore.constants.MessagesConstants.ERROR_TITLE_UNRESOLVED_MESSAGE;
import static com.epam.gadgetStore.util.CommandUtils.forwardToErrorPage;

public class UndefinedCommand implements Command {
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("Undefined command has been executed.");
		forwardToErrorPage(ERROR_TITLE_UNRESOLVED_MESSAGE, ERROR_MESSAGE_UNRESOLVED_MESSAGE, request, response);
	}
}
