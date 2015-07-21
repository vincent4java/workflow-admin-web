package com.v4java.workflow.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.v4java.workflow.common.BaseAction;


@Controller
@Scope("prototype")
public class IndexAction  extends BaseAction{

	
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String index() throws Exception{
		return "index";
	}

}
