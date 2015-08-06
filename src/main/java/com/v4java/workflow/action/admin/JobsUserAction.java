package com.v4java.workflow.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.v4java.utils.DateUtil;
import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.constant.AdminConst;
import com.v4java.workflow.pojo.JobsUser;
import com.v4java.workflow.query.admin.JobsUserQuery;
import com.v4java.workflow.redis.util.JedisUtil;
import com.v4java.workflow.service.admin.IJobsUserService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.admin.JobsUserVO;
import com.v4java.workflow.vo.admin.UserVO;

@Controller
@Scope("prototype")
@RequestMapping("/jobsUser")
public class JobsUserAction extends BaseAction{
	
	private static final Logger LOGGER = Logger.getLogger(JobsUserAction.class);
	
	@Autowired
	private IJobsUserService jobsUserService; 
	
	
	@RequestMapping(value = "/findJobsUser/{jobsId}",method = RequestMethod.GET)
	public String findAdminUser(@PathVariable Integer jobsId){
		request.setAttribute("jobsId", jobsId);
		return "page/admin/jobsUser/index";
		
	}
	
	
	@RequestMapping(value = "/findJobsUserVOJson",method = RequestMethod.POST)
	public @ResponseBody BTables<JobsUserVO> JobsUserVOJson(@RequestBody JobsUserQuery jobsUserQuery){
		BTables<JobsUserVO> bTables = new BTables<JobsUserVO>();
		jobsUserQuery.setSystemId(getSystemId());
		try {
			List<JobsUserVO> jobsVOs = jobsUserService.findJobsUserVO(jobsUserQuery);
			int count = jobsUserService.findJobsUserVOCount(jobsUserQuery);
			StringBuffer op = null;
			for (JobsUserVO jobsUserVO : jobsVOs) {
				jobsUserVO.setStatusName(AdminConst.STATUS_NAME[jobsUserVO.getStatus()]);
				jobsUserVO.setCreateTimeName(DateUtil.datetimeToStr(jobsUserVO.getCreateTime()));
				jobsUserVO.setUpdateTimeName(DateUtil.datetimeToStr(jobsUserVO.getUpdateTime()));
				op = new StringBuffer();
				//冻结/解冻 按钮
				op.append("<button name=\"updateStatus\"");
				//data-id
				op.append("data-name=\"status\" data-id=\"");
				op.append(jobsUserVO.getId());
				op.append("\" ");
				//data-val
				op.append("data-status=\"");
				op.append(AdminConst.OP_STATUS[jobsUserVO.getStatus()]);
				op.append("\" ");
				op.append("type=\"button\" op-url=\"updateJobsnStatus.do\" class=\"btn btn-warning btn-flat\">");
				op.append(AdminConst.OP_STATUS_NAME[jobsUserVO.getStatus()]);
				op.append("</button>");
				jobsUserVO.setOperation(op.toString());
				op = null;
			}
			
			bTables.setRows(jobsVOs);
			bTables.setTotal(count);
		} catch (Exception e) {
			LOGGER.error("查询id为"+getSystemId()+"系统的岗位对应用户", e);
		}
		return bTables;
		
	}
	
	
	@RequestMapping(value = "/insertJobsUser",method = RequestMethod.POST)
	public  @ResponseBody int insertJobsUser(@RequestBody JobsUser jobsUser){
		jobsUser.setStatus(AdminConst.STATUS_TRUE);
		jobsUser.setSystemId(getSystemId());
		List<JobsUser> jobsUsers = new ArrayList<JobsUser>();
		jobsUsers.add(jobsUser);
		int n = -1;
		try {
			n = jobsUserService.batchInsertJobsUser(jobsUsers);
			JobsUserQuery jobsUserQuery = new JobsUserQuery();
			jobsUser.setSystemId(getSystemId());
			jobsUser.setUserCode(jobsUser.getUserCode());
			jobsUserQuery.setSystemId(getSystemId());
			jobsUserQuery.setUserCode(jobsUser.getUserCode());
			if (n==1) {
				List<JobsUser> jobs = jobsUserService.findJobsUser(jobsUserQuery);
				UserVO userVO = new UserVO();
				userVO.setUserCode(jobsUser.getUserCode());
				userVO.setUserName(jobsUser.getUserName());
				List<Integer> jobIds = new ArrayList<Integer>();
				for (JobsUser user : jobs) {
					if (user.getStatus()==0) {
						jobIds.add(user.getJobsId());
					}
				}
				userVO.setJobsIds(jobIds);
				userVO.setSystemId(getSystemId());
				JedisUtil.getInstance().set(getXf9System().getSystemCode()+":"+userVO.getUserCode(), JSON.toJSONString(userVO));
			}
		} catch (Exception e) {
			LOGGER.error("添加岗位失败", e);
		}
		return n;
		
	}
	
	
}
