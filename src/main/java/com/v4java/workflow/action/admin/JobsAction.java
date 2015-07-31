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

import com.v4java.utils.DateUtil;
import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.constant.AdminConst;
import com.v4java.workflow.pojo.Jobs;
import com.v4java.workflow.query.admin.JobsQuery;
import com.v4java.workflow.service.admin.IJobsService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.UpdateStatus;
import com.v4java.workflow.vo.admin.JobsVO;

@Controller
@Scope("prototype")
@RequestMapping("/jobs")
public class JobsAction extends BaseAction{

	@Autowired
	private IJobsService jobsService;
	
	private static final Logger LOGGER = Logger.getLogger(JobsAction.class);
	
	
	@RequestMapping(value = "/findJobs",method = RequestMethod.GET)
	public String findAdminUser(){
		return "page/admin/jobs/index";
		
	}

	@RequestMapping(value = "/insertJobs",method = RequestMethod.POST)
		public @ResponseBody int insertJobs(@RequestBody Jobs jobs){
			jobs.setSystemId(getSystemId());
			jobs.setStatus(AdminConst.STATUS_TRUE);
			int n = -1;
			try {
				n = jobsService.insertJobs(jobs);
			} catch (Exception e) {
				LOGGER.error("添加岗位错误", e);
			}
			return n;
		
	}	
	
	
	@RequestMapping(value = "/findJobsJson",method = RequestMethod.POST)
	public @ResponseBody BTables<JobsVO> findAdminUserJson(@RequestBody JobsQuery jobsQuery){
		BTables<JobsVO> bTables = new BTables<JobsVO>();
		List<JobsVO> jobsVOs = null;
		jobsQuery.setSystemId(getSystemId());
		try {
			jobsVOs  = jobsService.findJobsBySystemId(jobsQuery);
			int total = jobsService.findJobsCountBySystemId(jobsQuery);
			StringBuffer op = null;
			for (JobsVO jobsVO : jobsVOs) {
				jobsVO.setStatusName(AdminConst.STATUS_NAME[jobsVO.getStatus()]);
				jobsVO.setCreateTimeName(DateUtil.datetimeToStr(jobsVO.getCreateTime()));
				jobsVO.setUpdateTimeName(DateUtil.datetimeToStr(jobsVO.getUpdateTime()));
				op = new StringBuffer();
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
				op.append("type=\"button\" op-url=\"/jobs/updateJobsStatus.do\" class=\"btn btn-warning btn-flat\">");
				op.append(AdminConst.OP_STATUS_NAME[jobsVO.getStatus()]);
				op.append("</button>");
				op.append("<a href=\"/jobsUser/findJobsUser/"+jobsVO.getId()+".do\""+" class=\"btn btn-warning btn-flat\">添加人员");
				op.append("</a>");
				jobsVO.setOperation(op.toString());
				op = null;
			}
			bTables.setRows(jobsVOs);
			bTables.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("查询岗位错误", e);
		}
		return bTables;
	}
	
	
	
	/**
	 * 更改岗位状态
	 * @return
	 */
	@RequestMapping(value = "/updateJobsStatus",method = RequestMethod.POST)
	public @ResponseBody UpdateStatus updateJobsStatus(@RequestBody Jobs jobs){
		UpdateStatus updateStatus = new UpdateStatus();
		try {
			int n  = jobsService.updateJobsStatus(jobs);
			updateStatus.setIsSuccess(n);
			if (n==1) {
				int x =jobs.getStatus();
				updateStatus.setTarget("status");
				updateStatus.setStatus(x);
				updateStatus.setStatusName(AdminConst.STATUS_NAME[x]);
				updateStatus.setOpStatus(AdminConst.OP_STATUS[x]);
				updateStatus.setOpStatusName(AdminConst.OP_STATUS_NAME[x]);
			}
			updateStatus.setIsSuccess(n);
		} catch (Exception e) {
			LOGGER.error("更改岗位状态错误", e);
		}
		
		return updateStatus;
	}
	
}
