package com.epam.gadgetStore.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
	private String code;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		code = fConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(code);
		response.setCharacterEncoding(code);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}