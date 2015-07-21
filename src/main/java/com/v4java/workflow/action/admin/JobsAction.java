package com.v4java.workflow.action.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.v4java.workflow.common.AdminConst;
import com.v4java.workflow.common.BTables;
import com.v4java.workflow.common.DateUtil;
import com.v4java.workflow.query.admin.JobsQuery;
import com.v4java.workflow.service.IJobsService;
import com.v4java.workflow.view.admin.JobsVO;

@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class JobsAction {

	@Autowired
	private IJobsService jobsService;
	
	private static final Logger logger = Logger.getLogger(JobsAction.class);
	
	
	@RequestMapping(value = "/findJobs",method = RequestMethod.GET)
	public String findAdminUser(){
		return "page/admin/jobs/index";
		
	}

	@RequestMapping(value = "/findJobsJson",method = RequestMethod.POST)
	public @ResponseBody BTables<JobsVO> findAdminUserJson(@RequestBody JobsQuery jobsQuery){
		BTables<JobsVO> bTables = new BTables<JobsVO>();
		List<JobsVO> jobsVOs = null;
		try {
			jobsVOs  = jobsService.selectJobs(jobsQuery);
			int total = jobsService.selectJobsCount(jobsQuery);
			StringBuffer op = null;
			for (JobsVO jobsVO : jobsVOs) {
				op = new StringBuffer();
				jobsVO.setStatusName(AdminConst.STATUS_NAME[jobsVO.getStatus()]);
				jobsVO.setCreateTimeName(DateUtil.datetimeToStr(jobsVO.getCreateTime()));
				//冻结/解冻 按钮
				op.append("<button name=\"updateStatus\"");
				//data-id
				op.append("data-name=\"status\" data-id=\"");
				op.append(jobsVO.getId());
				op.append("\" ");
				//data-val
				op.append("data-status=\"");
				op.append(AdminConst.OP_STATUS[jobsVO.getStatus()]);
				op.append("\" ");
				op.append("type=\"button\" op-url=\"updateAdminStatus.do\" class=\"btn btn-warning btn-flat\">");
				op.append(AdminConst.OP_STATUS_NAME[jobsVO.getStatus()]);
				op.append("</button>");
				jobsVO.setOperation(op.toString());
				op = null;
			}
			bTables.setRows(jobsVOs);
			bTables.setTotal(total);
		} catch (Exception e) {
			logger.error("查询岗位错误", e);
		}
		return bTables;
	}
	
	
}
