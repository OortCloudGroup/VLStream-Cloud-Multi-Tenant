package org.springblade.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springblade.core.log.exception.ServiceException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 演示过滤器
 *
 * @author Chill
 */
public class PreviewFilter implements Filter {

	private static final List<String> KEYS = new ArrayList<>();

	static {
		KEYS.add("notice");
		KEYS.add("process");
		KEYS.add("work");
		KEYS.add("token");
	}


	@Override
	public void init(FilterConfig filterConfig) {
	}


	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String path = httpServletRequest.getServletPath();
		String method = httpServletRequest.getMethod();

		String get = "GET";
		if (method.equals(get) || KEYS.stream().anyMatch(path::contains)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			throw new ServiceException("演示环境暂时无法操作！");
		}

	}

	@Override
	public void destroy() {
	}
}
