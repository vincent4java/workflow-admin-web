package com.v4java.workflow.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.constant.SessionConst;

@Controller
@Scope("prototype")
public class LogoutAction extends BaseAction{

	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String goLogin(){
		session.removeAttribute(SessionConst.ADMIN_USER);
		return "login";
	}
}
