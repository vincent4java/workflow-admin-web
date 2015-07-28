package com.v4java.workflow.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.v4java.workflow.constant.SessionConst;
import com.v4java.workflow.pojo.Xf9System;


public class BaseAction {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	
	

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		
	}
	
	public Integer getSystemId(){
		return (Integer) session.getAttribute(SessionConst.SYSTEM_ID);
	}

	
	public Xf9System getXf9System(){
		return (Xf9System) session.getAttribute(SessionConst.ADMIN_USER);
	}

	
}