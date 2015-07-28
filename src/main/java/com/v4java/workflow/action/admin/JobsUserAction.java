package com.v4java.workflow.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.v4java.workflow.pojo.JobsUser;
import com.v4java.workflow.query.admin.JobsUserQuery;
import com.v4java.workflow.service.admin.IJobsUserService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.admin.JobsUserVO;

@Controller
@Scope("prototype")
@RequestMapping("/jobsUser")
public class JobsUserAction {
	
	private static final Logger LOGGER = Logger.getLogger(JobsUserAction.class);
	
	@Autowired
	private IJobsUserService jobsUserService; 
	
	@RequestMapping(value = "/findJobsUserVOJson/{systemId}",method = RequestMethod.GET)
	public @ResponseBody BTables<JobsUserVO> JobsUserVOJson(@PathVariable Integer systemId){
		BTables<JobsUserVO> bTables = new BTables<JobsUserVO>();
		JobsUserQuery jobsUserQuery = new JobsUserQuery();
		jobsUserQuery.setSystemId(systemId);
		try {
			List<JobsUserVO> jobsVOs = jobsUserService.findJobsUserVO(jobsUserQuery);
			int count = jobsUserService.findJobsUserVOCount(jobsUserQuery);
			bTables.setRows(jobsVOs);
			bTables.setTotal(count);
		} catch (Exception e) {
			LOGGER.error("查询id为"+systemId+"系统的岗位对应用户", e);
		}
		return bTables;
		
	}
	
	
	@RequestMapping(value = "/insertJobsUser/{systemId}/{jobsId}/{userCode}/{userName}",method = RequestMethod.GET)
	public  @ResponseBody int insertJobsUser(@PathVariable Integer systemId,@PathVariable Integer jobsId,@PathVariable String userCode,@PathVariable String userName){
		JobsUser jobsUser = new JobsUser();
		jobsUser.setJobsId(jobsId);
		jobsUser.setStatus(0);
		jobsUser.setSystemId(systemId);
		jobsUser.setUserCode(userCode);
		jobsUser.setUserName(userName);
		List<JobsUser> jobsUsers = new ArrayList<JobsUser>();
		jobsUsers.add(jobsUser);
		int n = -1;
		try {
			n = jobsUserService.batchInsertJobsUser(jobsUsers);
		} catch (Exception e) {
			LOGGER.error("添加岗位失败", e);
		}
		return n;
		
	}
	
	
}
