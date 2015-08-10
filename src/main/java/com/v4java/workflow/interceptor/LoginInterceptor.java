package com.v4java.workflow.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.v4java.workflow.constant.SessionConst;
import com.v4java.workflow.pojo.Xf9System;

/**
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

	static final String[] noFilters = new String[] { "/goLogin.do", "/login.do"};
	
	public LoginInterceptor() { }  

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String url = request.getServletPath();

		for (String string : noFilters) {
			if (string.equals(url)) {
				return true;
			}
		}
		Xf9System xf9System = (Xf9System) request.getSession().getAttribute(SessionConst.ADMIN_USER);
		if (xf9System==null) {
			response.sendRedirect("/goLogin.do");
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
