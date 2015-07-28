package com.v4java.workflow.action.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.v4java.workflow.common.DateUtil;
import com.v4java.workflow.constant.AdminConst;
import com.v4java.workflow.pojo.Jobs;
import com.v4java.workflow.query.admin.JobsQuery;
import com.v4java.workflow.service.admin.IJobsService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.admin.JobsVO;

@Controller
@Scope("prototype")
@RequestMapping("/jobs")
public class JobsAction {

	@Autowired
	private IJobsService jobsService;
	
	private static final Logger logger = Logger.getLogger(JobsAction.class);
	
	
	@RequestMapping(value = "/findJobs",method = RequestMethod.GET)
	public String findAdminUser(){
		return "page/admin/jobs/index";
		
	}
	
	
	@RequestMapping(value = "/findJobsJson/{systemId}",method = RequestMethod.GET)
	public @ResponseBody BTables<JobsVO> findJobsJson(@PathVariable Integer systemId){
		BTables<JobsVO> bTables = new BTables<JobsVO>();
		List<JobsVO> jobsVOs = null;
		JobsQuery jobsQuery = new JobsQuery();
		jobsQuery.setSystemId(systemId);
		try {
			jobsVOs  = jobsService.findJobsBySystemId(jobsQuery);
			int total = jobsService.findJobsCountBySystemId(jobsQuery);
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
	

	@RequestMapping(value = "/insertJobs/{systemId}/{name}/{desc}",method = RequestMethod.GET)
		public @ResponseBody int insertJobs(@PathVariable Integer systemId,@PathVariable String name,@PathVariable String desc){
			Jobs jobs = new Jobs();
			jobs.setDescription(desc);
			jobs.setName(name);
			jobs.setSystemId(systemId);
			jobs.setStatus(0);
			int n = -1;
			try {
				n = jobsService.insertJobs(jobs);
			} catch (Exception e) {
				logger.error("添加岗位错误", e);
			}
			return n;
		
	}	
	
	
/*	@RequestMapping(value = "/findJobsJson",method = RequestMethod.POST)
	public @ResponseBody BTables<JobsVO> findAdminUserJson(@RequestBody JobsQuery jobsQuery){
		BTables<JobsVO> bTables = new BTables<JobsVO>();
		List<JobsVO> jobsVOs = null;
		try {
			jobsVOs  = jobsService.findJobs(jobsQuery);
			int total = jobsService.findJobsCount(jobsQuery);
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
	}*/
	
	
}
