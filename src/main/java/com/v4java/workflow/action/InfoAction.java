package com.v4java.workflow.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.v4java.enumerate.MD5Utils;
import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.param.admin.Xf9SystemParam;
import com.v4java.workflow.service.admin.IXf9SystemService;

@Controller
@Scope("prototype")
public class InfoAction extends BaseAction{

	@Autowired
	private IXf9SystemService xf9SystemService;

	private static final Logger LOGGER = Logger.getLogger(InfoAction.class);
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info() {
		return "page/info/index";
	}

	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public @ResponseBody String changeInfo(@RequestBody Xf9SystemParam xf9SystemParam) {
		String msg = null;
		try {
			xf9SystemParam.setId(getSystemId());
			xf9SystemParam.setNowPwd(MD5Utils.md5SaltMd5(xf9SystemParam.getNowPwd(), getXf9System().getUserCode()));
			xf9SystemParam.setOldPwd(MD5Utils.md5SaltMd5(xf9SystemParam.getOldPwd(), getXf9System().getUserCode()));
			if (xf9SystemParam.getNowPwd().equals(xf9SystemParam.getOldPwd())) {
				return "新旧密码不能一样";
			}
			int n = xf9SystemService.updateXf9System(xf9SystemParam);
			if (n==1) {
				msg ="修改成功";
			}else {
				msg ="修改失败";
			}
		} catch (Exception e) {
			LOGGER.error(xf9SystemParam.getId()+"更改信息失败", e);
		}
		return msg;
	}
}
