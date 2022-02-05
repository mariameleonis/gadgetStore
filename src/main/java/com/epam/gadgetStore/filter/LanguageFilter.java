package com.epam.gadgetStore.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.epam.gadgetStore.dao.LanguageDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Language;

import static com.epam.gadgetStore.constants.AttributeConstants.*;

import java.util.List;

public class LanguageFilter implements Filter {
	private final LanguageDAO LANGUAGE_DAO = (LanguageDAO) DAOFactory.LANGUAGE_DAO.getDAO();
	private static final String CONFIG_LANGUAGE_ID_NAME = "defaultLanguageID";
	private static final String CONFIG_LANGUAGE_NAME = "defaultLanguage";
	private Long defaultLanguageID;
	private String defaultLanguage;

	@Override
	public void init(FilterConfig config) throws ServletException {
		defaultLanguageID = Long.parseLong(config.getInitParameter(CONFIG_LANGUAGE_ID_NAME));
		defaultLanguage = config.getInitParameter(CONFIG_LANGUAGE_NAME);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();

		List<Language> appLanguages;

		appLanguages = LANGUAGE_DAO.getAll();
		session.setAttribute(APP_LANGUAGES_ATTRIBUTE, appLanguages);

		Long languageID = (Long) session.getAttribute(LANGUAGE_ID_ATTRIBUTE);
		String language = (String) session.getAttribute(LANGUAGE_ATTRIBUTE);

		if (language == null || languageID == null) {
			session.setAttribute(LANGUAGE_ID_ATTRIBUTE, defaultLanguageID);
			session.setAttribute(LANGUAGE_ATTRIBUTE, defaultLanguage);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
