package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;

import com.dto.JsonDto;
import com.util.Constants;
import com.util.URLUtil;


public class CheckSessionFilter implements Filter {

	private String loginPage;

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.loginPage = filterConfig.getInitParameter("loginPage");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session= httpRequest.getSession();
		String url = httpRequest.getServletPath();
		if (URLUtil.isNotCheckURL(url)||isLogin(session)) {
			chain.doFilter(request, response);
		}else{
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if(this.isAjaxRequest(httpRequest)) {//ajax请求  写json串到前台
				httpResponse.getWriter().write(JSONObject.fromObject(new JsonDto(Constants.Status.ERR_SESSION_TIMEOUT.getStatus())).toString());
			}else {//没session重定向
				String contextPath = httpRequest.getContextPath();
				httpResponse.sendRedirect(contextPath + "/" + this.loginPage);
			}

		}
		
	}
	
	
	
	@Override
	public void destroy() {
		
	}
	
	
	/**
	 * 判断是否是ajax请求
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
	}
	


	/**
	 * 判断是否登陆 session是否存在
	 * @param session
	 * @return
	 */
	private boolean isLogin(HttpSession session) {
		return session != null && session.getAttribute(Constants.LOGIN_USER_SESSION_KEY) != null;
	}
	
}
