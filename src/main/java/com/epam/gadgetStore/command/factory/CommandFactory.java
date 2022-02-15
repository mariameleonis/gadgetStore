package com.epam.gadgetStore.command.factory;

import static com.epam.gadgetStore.constants.ParameterNamesConstants.COMMAND_PARAMETER;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;

public class CommandFactory {
	private final static Logger LOGGER = LogManager.getLogger(CommandFactory.class);
	
	public static Command defineCommand(HttpServletRequest request) {
		String commandName = request.getParameter(COMMAND_PARAMETER);
		Command resultCommand = new UndefinedCommand();
		
		if  ((commandName != null) && (!commandName.trim().isEmpty())) {
			try {
				resultCommand = CommandEnum.valueOf(commandName.toUpperCase()).getCommand();
			} catch (IllegalArgumentException e) {
				LOGGER.warn("Command with specified name not found. (name: " + commandName + ")");
			}
		} else {
            if (commandName == null) {
                resultCommand = CommandEnum.HOME.getCommand();
            } else {
                LOGGER.warn("Command name is empty.");
            }
        }
		
		return resultCommand;
	}

}
