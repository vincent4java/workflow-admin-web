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

import com.alibaba.fastjson.JSON;
import com.v4java.utils.DateUtil;
import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.constant.AdminConst;
import com.v4java.workflow.constant.FlowConst;
import com.v4java.workflow.pojo.Compare;
import com.v4java.workflow.pojo.CompareArray;
import com.v4java.workflow.pojo.FlowNode;
import com.v4java.workflow.query.admin.FlowNodeQuery;
import com.v4java.workflow.query.admin.JobsQuery;
import com.v4java.workflow.service.admin.IFlowNodeService;
import com.v4java.workflow.service.admin.IJobsService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.UpdateStatus;
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
	
	
	
	@RequestMapping(value = "/findFlowNodeById/{id}",method = RequestMethod.GET)
	public String findFlowNodeById(@PathVariable Integer id){
		JobsQuery jobsQuery = new JobsQuery();
		jobsQuery.setSystemId(getSystemId());
		try {
			List<JobsVO> jobsVOs = jobsService.findJobsBySystemId(jobsQuery);
			FlowNode flowNode = flowNodeService.findFlowNodeById(id);
			StringBuffer html = new StringBuffer();
			html.append("<div class=\"form-group\" id=\"jobsIdDiv\" hidden=\"hidden\">");
			html.append("<label for=\"\">结点</label>");
			html.append("<select name=\"jobsId\" class=\"form-control\" ");
			html.append("org-val=\"");
			html.append(flowNode.getJobsId());
			html.append("\" ");
			html.append(" >");
 			for (JobsVO jobsVO : jobsVOs) {
 				if (jobsVO.getStatus()==AdminConst.STATUS_TRUE) {
 					html.append("<option value=\"");
 					html.append(jobsVO.getId());
 					html.append("\">");
 					html.append(jobsVO.getName());
 					html.append("</option>");
				}
			}
 			html.append("<option value=\"0\">无</option>");
			html.append("</select>");
			html.append("</div>");
			StringBuffer testHtml = new StringBuffer();
			if (flowNode.getNodeType()==FlowConst.NODE_TYPE_IF) {
				String[] sign = {"<=","<","=",">",">="}; 
				List<Compare> compares = JSON.parseArray(flowNode.getFlowTest(),Compare.class);
				for (Compare compare : compares) {
					CompareArray[] arrays=compare.getCompareArrays();
					testHtml.append("<div class=\"row\"> ");
					testHtml.append("<small class=\"label label-danger\" name=\"add\">+</small> ");
					testHtml.append("<input type=\"text\" class=\"col-xs-2 compare\" name=\"sort\" placeholder=\"下一个节点序号\" value=\"");
					testHtml.append(compare.getSort());
					testHtml.append("\"> ");
					for (CompareArray compareArray : arrays) {
						testHtml.append("<vv > ");
						testHtml.append("<input type=\"text\" class=\"col-xs-2 compareArray\" name=\"name\"  value=\"");
						testHtml.append(compareArray.getName());
						testHtml.append("\" placeholder=\"josnname\"> ");
						testHtml.append("<select name=\"type\"  class=\"col-xs-1 compareArray\" > ");
						testHtml.append("<option value=\"-2\"><=</option> ");
						testHtml.append("<option value=\"-1\"><</option> ");
						testHtml.append("<option value=\"0\">=</option> ");
						testHtml.append("<option value=\"1\">></option> ");
						testHtml.append("<option value=\"2\">>=</option> ");
						for (int i = -2; i <=2; i++) {
							testHtml.append("<option value=\"");
							testHtml.append(i);
							testHtml.append("\"");
							if (i==compareArray.getType()) {
								testHtml.append("selected = \"selected\"");
							}
							testHtml.append( ">");
							testHtml.append(sign[i+2]);
							testHtml.append("</option> ");
						}
						testHtml.append("</select> ");
						testHtml.append("<input type=\"text\" class=\"col-xs-3 compareArray\" name=\"value\" placeholder=\"值\" value=\"");
						testHtml.append(compareArray.getValue());
						testHtml.append("\"> ");
						testHtml.append("<small class=\"label label-danger\" name=\"subtract\">-</small> ");
						testHtml.append("</vv> ");
					}
					testHtml.append("</div>");
				}
			}
			request.setAttribute("testHtml", testHtml.toString());
			request.setAttribute("jobsVOsHTML", html.toString());
			request.setAttribute("flowNode",flowNode);
		} catch (Exception e) {
			LOGGER.error( e);
		}
		return "page/admin/flowNode/update";
		
	}
	
	@RequestMapping(value = "/findFlowNode/{modelId}",method = RequestMethod.GET)
	public String findFlowNode(@PathVariable Integer modelId){
		request.setAttribute("modelId", modelId);
		JobsQuery jobsQuery = new JobsQuery();
		jobsQuery.setSystemId(getSystemId());
		try {
			List<JobsVO> jobsVOs = jobsService.findJobsBySystemId(jobsQuery);
			StringBuffer html = new StringBuffer();
			html.append("<div class=\"form-group\" id=\"jobsIdDiv\">");
			html.append("<label for=\"\">结点</label>");
			html.append("<select name=\"jobsId\" class=\"form-control\" >");
 			for (JobsVO jobsVO : jobsVOs) {
 				if (jobsVO.getStatus()==AdminConst.STATUS_TRUE) {
 					html.append("<option value=\"");
 					html.append(jobsVO.getId());
 					html.append("\">");
 					html.append(jobsVO.getName());
 					html.append("</option>");
				}
			}
 			html.append("<option value=\"0\">无</option>");
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
				op.append("type=\"button\" op-url=\"/flowNode/updateFlowNodeStatus.do\" class=\"btn btn-warning btn-flat\">");
				op.append(AdminConst.OP_STATUS_NAME[flowNodeVO.getStatus()]);
				op.append("</button>");
				op.append("<a href=\"/flowNode/findFlowNodeById/");
				op.append(flowNodeVO.getId());
				op.append(".do\" class=\"btn btn-warning btn-flat\">修改");
				op.append("</a>");
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
		if (flowNode.getNodeType()==FlowConst.NODE_TYPE_START) {
			flowNode.setJobsId(0);
			flowNode.setSort(0);
			flowNode.setNextSort(1);
		}else {
			if (flowNode.getSort()==0) {
				flowNode.setSort(null);
			}
		}
		if (flowNode.getNodeType()!=FlowConst.NODE_TYPE_TASK) {
			flowNode.setJobsId(0);
		}
		int n = -1;
		try {
			n = flowNodeService.insertFlowNode(flowNode);
		} catch (Exception e) {
			LOGGER.error("插入节点错误", e);
		}
		return n;
	}
	
	
	
	/**
	 * 更改结点状态
	 * @return
	 */
	@RequestMapping(value = "/updateFlowNodeStatus",method = RequestMethod.POST)
	public @ResponseBody UpdateStatus updateFlowNodeStatus(@RequestBody FlowNode flowNode){
		UpdateStatus updateStatus = new UpdateStatus();
		try {
			int n  = flowNodeService.updateFlowNodeStatus(flowNode);
			updateStatus.setIsSuccess(n);
			updateStatus.setMsg("更新结点失败：当前结点正在使用");
			if (n==1) {
				int x =flowNode.getStatus();
				updateStatus.setTarget("status");
				updateStatus.setStatus(x);
				updateStatus.setStatusName(AdminConst.STATUS_NAME[x]);
				updateStatus.setOpStatus(AdminConst.OP_STATUS[x]);
				updateStatus.setOpStatusName(AdminConst.OP_STATUS_NAME[x]);
				updateStatus.setMsg("更新结点成功");
			}
		} catch (Exception e) {
			LOGGER.error("更改结点状态错误", e);
		}
		return updateStatus;
	}

	/**
	 * 更改结点状态
	 * @return
	 */
	@RequestMapping(value = "/updateFlowNode",method = RequestMethod.POST)
	public @ResponseBody UpdateStatus updateFlowNode(@RequestBody FlowNode flowNode){
		UpdateStatus updateStatus = new UpdateStatus();
		try {
			int n  = flowNodeService.updateFlowNode(flowNode);
			updateStatus.setIsSuccess(n);
			updateStatus.setMsg("更新结点失败");
			if (n==1) {
				updateStatus.setMsg("更新结点成功");
			}
		} catch (Exception e) {
			LOGGER.error("更改结点错误", e);
		}
		return updateStatus;
	}
	
}
