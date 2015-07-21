package com.v4java.workflow.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.v4java.enumerate.MD5Utils;
import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.constant.LoginMsgConst;
import com.v4java.workflow.constant.SessionConst;
import com.v4java.workflow.pojo.Xf9System;
import com.v4java.workflow.service.IXf9SystemService;
import com.v4java.workflow.vo.LoginMsg;

@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(LoginAction.class);
	@Autowired
	private IXf9SystemService xf9SystemService;
	
	@RequestMapping(value="/goLogin",method = RequestMethod.GET)
	public String goLogin(){
		Xf9System xf9System = (Xf9System) session.getAttribute(SessionConst.ADMIN_USER);
		if (null!=xf9System) {
			return "index";
		}
		return "login";
	}
	
	@ResponseBody 
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public LoginMsg login(@RequestParam("account") String account,@RequestParam("userPwd") String userPwd,@RequestParam("code") String code){
		LoginMsg loginMsg =  (LoginMsg) request.getSession().getAttribute(SessionConst.LOGIN_MSG);
		if (loginMsg == null) {
			loginMsg = new LoginMsg();
			loginMsg.setFailCount(0);
		}
		int count  = loginMsg.getFailCount();
		loginMsg.setFlag(false);
		if (count <=2) {
			//String eCode = (String) request.getSession().getAttribute(SessionConst.CODE);
			
			try {
				Xf9System xf9System = xf9SystemService.selectXf9SystemByUserCode(account);
				
				if (xf9System!=null) {
					if (xf9System.getUserPwd().equals(MD5Utils.md5SaltMd5(userPwd, xf9System.getUserCode()))) {
						loginMsg.setFlag(true);
						loginMsg.setFailCount(0);
						loginMsg.setMsg(LoginMsgConst.ACCOUNT_SUCCESS);
						xf9System.setUserPwd(null);
						session.setAttribute(SessionConst.ADMIN_USER, xf9System);
					}else {
						loginMsg.setMsg(LoginMsgConst.PWD_ERROR);
					}
				}else{
					loginMsg.setMsg(LoginMsgConst.ACCOUNT_NO);
				}
			} catch (Exception e) {
				logger.error("用户登录查询错误", e);
			}
		}else{
			loginMsg.setMsg(LoginMsgConst.ACCOUNT_SUCCESS);
		}
		session.setAttribute(SessionConst.LOGIN_MSG, loginMsg);
		return loginMsg;
	}
	
	
}
