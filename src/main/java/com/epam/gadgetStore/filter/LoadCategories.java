package com.epam.gadgetStore.filter;

import static com.epam.gadgetStore.constants.AttributeConstants.LANGUAGE_ID_ATTRIBUTE;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.dao.CategoryDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.Category;
import com.epam.gadgetStore.exception.DAOException;

public class LoadCategories implements Filter {
	private final CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.CATEGORY_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);

		Long languageId = (Long) session.getAttribute(LANGUAGE_ID_ATTRIBUTE);
		List<Category> listCategory;
		try {
			listCategory = categoryDAO.getAllByLanguageId(languageId);
			Map<Long, List<Category>> parentIdToCategories = listCategory.stream()
					.collect(Collectors.groupingBy(Category::getParentId));
			request.setAttribute("categoriesTopMenu", parentIdToCategories);
		} catch (DAOException e) {
			LOGGER.debug("Could not load categories list for language ID " + languageId);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
