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

import com.v4java.workflow.pojo.WorkFlowModel;
import com.v4java.workflow.query.admin.WorkFlowModelQuery;
import com.v4java.workflow.service.admin.IWorkFlowModelService;
import com.v4java.workflow.vo.BTables;

@Controller
@Scope("prototype")
@RequestMapping("/WorkFlowModel")
public class WorkFlowModelAction {

	
	private static final Logger LOGGER = Logger.getLogger(WorkFlowModelAction.class);
	
	@Autowired
	private IWorkFlowModelService workFlowModelService;
	
	
	@RequestMapping(value = "/findWorkFlowModelJson/{systemId}",method = RequestMethod.GET)
	public @ResponseBody BTables<WorkFlowModel> findWorkFlowModelJson(@PathVariable Integer systemId){
		BTables<WorkFlowModel> bTables = new BTables<WorkFlowModel>();
		WorkFlowModelQuery workFlowModelQuery = new WorkFlowModelQuery();
		workFlowModelQuery.setSystemId(systemId);
		try {
			List<WorkFlowModel> workFlowModels = workFlowModelService.findWorkFlowModel(workFlowModelQuery);
			int count = workFlowModelService.findWorkFlowModelCount(workFlowModelQuery);
			bTables.setTotal(count);
			bTables.setRows(workFlowModels);
		} catch (Exception e) {
			LOGGER.error("查询系统"+systemId+"工作流模板错误", e);
		}
		
		return bTables;
	}

	@RequestMapping(value = "/insertWorkFlowModel/{systemId}/{busyTypeId}/{name}",method = RequestMethod.GET)
	public @ResponseBody int insertWorkFlowModel(@PathVariable Integer systemId,@PathVariable Integer busyTypeId,@PathVariable String name){
		int n = -1;
		WorkFlowModel flowModel =new WorkFlowModel();
		flowModel.setSystemId(systemId);
		flowModel.setBusyTypeId(busyTypeId);
		flowModel.setDescription("");
		flowModel.setStatus(0);
		flowModel.setName(name);
		flowModel.setModelText("");
		try {
			n = workFlowModelService.insertWorkFlowModel(flowModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	
}
