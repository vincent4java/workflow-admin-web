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
import com.v4java.workflow.common.DateUtil;
import com.v4java.workflow.constant.AdminConst;
import com.v4java.workflow.pojo.WorkFlowModel;
import com.v4java.workflow.query.admin.WorkFlowModelQuery;
import com.v4java.workflow.service.admin.IWorkFlowModelService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.admin.WorkFlowModelVO;

@Controller
@Scope("prototype")
@RequestMapping("/workFlowModel")
public class WorkFlowModelAction extends BaseAction{

	
	private static final Logger LOGGER = Logger.getLogger(WorkFlowModelAction.class);
	
	@Autowired
	private IWorkFlowModelService workFlowModelService;
	
	
	@RequestMapping(value = "/findWorkFlowModel",method = RequestMethod.GET)
	public String findWorkFlowModel(){
		return "page/admin/workFlowModel/index";
		
	}
	
	@RequestMapping(value = "/findWorkFlowModelJson",method = RequestMethod.POST)
	public @ResponseBody BTables<WorkFlowModelVO> findWorkFlowModelJson(WorkFlowModelQuery workFlowModelQuery){
		BTables<WorkFlowModelVO> bTables = new BTables<WorkFlowModelVO>();
		workFlowModelQuery.setSystemId(getSystemId());
		try {
			List<WorkFlowModelVO> workFlowModels = workFlowModelService.findWorkFlowModel(workFlowModelQuery);
			int count = workFlowModelService.findWorkFlowModelCount(workFlowModelQuery);
			for (WorkFlowModelVO workFlowModelVO : workFlowModels) {
				workFlowModelVO.setStatusName(AdminConst.STATUS_NAME[workFlowModelVO.getStatus()]);
				workFlowModelVO.setCreateTimeName(DateUtil.datetimeToStr(workFlowModelVO.getCreateTime()));
			}
			bTables.setTotal(count);
			bTables.setRows(workFlowModels);
		} catch (Exception e) {
			LOGGER.error("查询系统"+getSystemId()+"工作流模板错误", e);
		}
		
		return bTables;
	}
	
/*	@RequestMapping(value = "/findWorkFlowModelJson/{systemId}",method = RequestMethod.GET)
	public @ResponseBody BTables<WorkFlowModelVO> findWorkFlowModelJson(@PathVariable Integer systemId){
		BTables<WorkFlowModelVO> bTables = new BTables<WorkFlowModelVO>();
		WorkFlowModelQuery workFlowModelQuery = new WorkFlowModelQuery();
		workFlowModelQuery.setSystemId(systemId);
		try {
			List<WorkFlowModelVO> workFlowModels = workFlowModelService.findWorkFlowModel(workFlowModelQuery);
			int count = workFlowModelService.findWorkFlowModelCount(workFlowModelQuery);
			for (WorkFlowModelVO workFlowModelVO : workFlowModels) {
				workFlowModelVO.setStatusName(AdminConst.STATUS_NAME[workFlowModelVO.getStatus()]);
				workFlowModelVO.setCreateTimeName(DateUtil.datetimeToStr(workFlowModelVO.getCreateTime()));
			}
			bTables.setTotal(count);
			bTables.setRows(workFlowModels);
		} catch (Exception e) {
			LOGGER.error("查询系统"+systemId+"工作流模板错误", e);
		}
		
		return bTables;
	}*/

	@RequestMapping(value = "/insertWorkFlowModel/{systemId}/{busyTypeId}/{name}",method = RequestMethod.GET)
	public @ResponseBody int insertWorkFlowModel(@PathVariable Integer systemId,@PathVariable Integer busyTypeId,@PathVariable String name){
		int n = -1;
		WorkFlowModel flowModel =new WorkFlowModel();
		flowModel.setSystemId(systemId);
		flowModel.setBusyTypeId(busyTypeId);
		//在模板第一次建立没有节点
		flowModel.setDescription("");
		flowModel.setStatus(1);
		flowModel.setName(name);
		flowModel.setModelText("");
		try {
			n = workFlowModelService.insertWorkFlowModel(flowModel);
		} catch (Exception e) {
			LOGGER.error("新增工作流模板错误", e);
		}
		return n;
	}
	
}
