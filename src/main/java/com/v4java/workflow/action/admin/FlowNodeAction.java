package com.v4java.workflow.action.admin;

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

import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.common.DateUtil;
import com.v4java.workflow.constant.AdminConst;
import com.v4java.workflow.constant.FlowConst;
import com.v4java.workflow.pojo.FlowNode;
import com.v4java.workflow.query.admin.FlowNodeQuery;
import com.v4java.workflow.query.admin.JobsQuery;
import com.v4java.workflow.service.admin.IFlowNodeService;
import com.v4java.workflow.service.admin.IJobsService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.admin.FlowNodeVO;
import com.v4java.workflow.vo.admin.JobsVO;



@Controller
@Scope("prototype")
@RequestMapping("/flowNode")
public class FlowNodeAction extends BaseAction {

	private static final Logger LOGGER = Logger.getLogger(FlowNodeAction.class);

	@Autowired
	private IFlowNodeService flowNodeService;
	@Autowired
	private IJobsService jobsService;
	
	@RequestMapping(value = "/findFlowNode/{modelId}",method = RequestMethod.GET)
	public String findFlowNode(@PathVariable Integer modelId){
		request.setAttribute("modelId", modelId);
		JobsQuery jobsQuery = new JobsQuery();
		jobsQuery.setSystemId(getSystemId());
		try {
			List<JobsVO> jobsVOs = jobsService.findJobsBySystemId(jobsQuery);
			StringBuffer html = new StringBuffer();
			html.append("<div class=\"form-group\">");
			html.append("<label for=\"\">岗位</label>");
			html.append("<select name=\"jobsId\" class=\"form-control\" >");
 			for (JobsVO jobsVO : jobsVOs) {
				html.append("<option value=\"");
				html.append(jobsVO.getId());
				html.append("\">");
				html.append(jobsVO.getName());
				html.append("</option>");
			}
			html.append("</select>");
			html.append("</div>");
			
			request.setAttribute("jobsVOsHTML", html.toString());
		} catch (Exception e) {
			LOGGER.error( e);
		}
		return "page/admin/flowNode/index";
		
	}
	
	@RequestMapping(value = "/findFlowNodeJson",method = RequestMethod.POST)
	public @ResponseBody BTables<FlowNodeVO> findFlowNodeJson(@RequestBody FlowNodeQuery flowNodeQuery){
		BTables<FlowNodeVO> bTables = new BTables<FlowNodeVO>();
		try {
			List<FlowNodeVO> flowNodeVOs=flowNodeService.findFlowNodeVO(flowNodeQuery);
			int count = flowNodeService.findFlowNodeVOCount(flowNodeQuery);
			StringBuffer op = null;
			for (FlowNodeVO flowNodeVO : flowNodeVOs) {
				flowNodeVO.setStatusName(AdminConst.STATUS_NAME[flowNodeVO.getStatus()]);
				flowNodeVO.setCreateTimeName(DateUtil.datetimeToStr(flowNodeVO.getCreateTime()));
				flowNodeVO.setUpdateTimeName(DateUtil.datetimeToStr(flowNodeVO.getUpdateTime()));
				flowNodeVO.setNodeTypeName(FlowConst.NODE_TYPE_NAME[flowNodeVO.getNodeType()]);
				op = new StringBuffer();
				//冻结/解冻 按钮
				op.append("<button name=\"updateStatus\"");
				//data-id
				op.append("data-name=\"status\" data-id=\"");
				op.append(flowNodeVO.getId());
				op.append("\" ");
				//data-val
				op.append("data-status=\"");
				op.append(AdminConst.OP_STATUS[flowNodeVO.getStatus()]);
				op.append("\" ");
				op.append("type=\"button\" op-url=\"updateJobsnStatus.do\" class=\"btn btn-warning btn-flat\">");
				op.append(AdminConst.OP_STATUS_NAME[flowNodeVO.getStatus()]);
				op.append("</button>");
				flowNodeVO.setOperation(op.toString());
				op = null;
			}
			bTables.setRows(flowNodeVOs);
			bTables.setTotal(count);
		} catch (Exception e) {
			LOGGER.error("查询模板为"+flowNodeQuery.getModelId()+"节点错误", e);
		}
		return bTables;
	}
	
	@RequestMapping(value = "/insertFlowNode",method = RequestMethod.POST)
	public @ResponseBody int insertFlowNode(@RequestBody FlowNode flowNode ){
		flowNode.setStatus(AdminConst.STATUS_TRUE);
		flowNode.setNodeType(flowNode.getNodeTypeId());
		int n = -1;
		try {
			n = flowNodeService.insertFlowNode(flowNode);
		} catch (Exception e) {
			LOGGER.error("插入节点错误", e);
		}
		return n;
	}
	
}
