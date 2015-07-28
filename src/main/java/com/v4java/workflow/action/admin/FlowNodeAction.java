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

import com.v4java.workflow.common.BaseAction;
import com.v4java.workflow.pojo.FlowNode;
import com.v4java.workflow.query.admin.FlowNodeQuery;
import com.v4java.workflow.service.admin.IFlowNodeService;
import com.v4java.workflow.vo.BTables;



@Controller
@Scope("prototype")
@RequestMapping("/flowNode")
public class FlowNodeAction extends BaseAction {

	private static final Logger LOGGER = Logger.getLogger(FlowNodeAction.class);

	@Autowired
	private IFlowNodeService flowNodeService;
	
	@RequestMapping(value = "/findFlowNodeJson/{modelId}",method = RequestMethod.GET)
	public @ResponseBody BTables<FlowNode> findFlowNodeJson(@PathVariable Integer modelId){
		BTables<FlowNode> bTables = new BTables<FlowNode>();
		try {
			FlowNodeQuery flowNodeQuery =  new FlowNodeQuery();
			flowNodeQuery.setModelId(modelId);
			List<FlowNode> flowNodes=flowNodeService.findFlowNode(flowNodeQuery);
			int count = flowNodeService.findFlowNodeCount(flowNodeQuery);
			bTables.setRows(flowNodes);
			bTables.setTotal(count);
		} catch (Exception e) {
			LOGGER.error("查询模板为"+modelId+"节点错误", e);
		}
		return bTables;
	}
	
	@RequestMapping(value = "/insertFlowNode/{modelId}/{name}/{jobsId}/{sort}/{nextSort}/{nodeType}/{flowTest}",method = RequestMethod.GET)
	public @ResponseBody int insertFlowNode(@PathVariable Integer modelId,@PathVariable String name,@PathVariable Integer jobsId,@PathVariable Integer sort,@PathVariable Integer nextSort,@PathVariable Integer nodeType, @PathVariable String flowTest){
		FlowNode flowNode = new FlowNode();
		flowNode.setFlowTest(flowTest);
		flowNode.setJobsId(jobsId);
		flowNode.setName(name);
		flowNode.setModelId(modelId);
		flowNode.setStatus(0);
		flowNode.setSort(sort);
		flowNode.setNextSort(nextSort);
		flowNode.setNodeType(nodeType);
		flowNode.setFlowTest(flowTest);
		flowNode.setDescription(name);
		int n = -1;
		try {
			n = flowNodeService.insertFlowNode(flowNode);
		} catch (Exception e) {
			LOGGER.error("插入节点错误", e);
		}
		return n;
	}
	
}