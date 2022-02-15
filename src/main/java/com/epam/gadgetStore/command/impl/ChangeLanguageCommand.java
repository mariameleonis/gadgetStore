package com.epam.gadgetStore.command.impl;

import static com.epam.gadgetStore.constants.AttributeConstants.LANGUAGE_ATTRIBUTE;
import static com.epam.gadgetStore.constants.AttributeConstants.LANGUAGE_ID_ATTRIBUTE;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.LANGUAGE_TO_CHANGE_PARAMETER;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.command.factory.CommandEnum;
import com.epam.gadgetStore.dao.LanguageDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;

public class ChangeLanguageCommand implements Command {
	private final LanguageDAO languageDAO = (LanguageDAO) DAOFactory.LANGUAGE_DAO.getDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		String prevPage = request.getHeader("referer");

        String languageToChange = request.getParameter(LANGUAGE_TO_CHANGE_PARAMETER);
        Long languageId = languageDAO.findByName(languageToChange).getId();
      
        
        session.setAttribute(LANGUAGE_ATTRIBUTE, languageToChange);
        session.setAttribute(LANGUAGE_ID_ATTRIBUTE, languageId);
        
        if (prevPage == null) {
        	CommandEnum.HOME.getCommand().execute(request, response);
        	return;
        }
       
        response.sendRedirect(prevPage);
	}

}
