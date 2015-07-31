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
import com.v4java.workflow.pojo.WorkFlowModel;
import com.v4java.workflow.query.admin.WorkFlowModelQuery;
import com.v4java.workflow.service.admin.IWorkFlowModelService;
import com.v4java.workflow.vo.BTables;
import com.v4java.workflow.vo.UpdateStatus;
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
			StringBuffer op = null;
			for (WorkFlowModelVO workFlowModelVO : workFlowModels) {
				workFlowModelVO.setStatusName(AdminConst.STATUS_NAME[workFlowModelVO.getStatus()]);
				workFlowModelVO.setCreateTimeName(DateUtil.datetimeToStr(workFlowModelVO.getCreateTime()));
				workFlowModelVO.setUpdateTimeName(DateUtil.datetimeToStr(workFlowModelVO.getUpdateTime()));
				op = new StringBuffer();
				//冻结/解冻 按钮
				op.append("<button name=\"updateStatus\"");
				//data-id
				op.append("data-name=\"status\" data-id=\"");
				op.append(workFlowModelVO.getId());
				op.append("\" ");
				//data-val
				op.append("data-status=\"");
				op.append(AdminConst.OP_STATUS[workFlowModelVO.getStatus()]);
				op.append("\" ");
				op.append("type=\"button\" op-url=\"/workFlowModel/updateWorkFlowModelStatus.do\" class=\"btn btn-warning btn-flat\">");
				op.append(AdminConst.OP_STATUS_NAME[workFlowModelVO.getStatus()]);
				op.append("</button>");
				op.append("<a href=\"/flowNode/findFlowNode/"+workFlowModelVO.getId()+".do\""+" class=\"btn btn-warning btn-flat\">编辑节点");
				op.append("</a>");
				workFlowModelVO.setOperation(op.toString());
				op = null;
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

	@RequestMapping(value = "/insertWorkFlowModel",method = RequestMethod.POST)
	public @ResponseBody int insertWorkFlowModel(@RequestBody WorkFlowModel workFlowModel){
		int n = -1;
		//在模板第一次建立没有节点
		workFlowModel.setStatus(AdminConst.STATUS_FLASE);
		workFlowModel.setSystemId(getSystemId());
		try {
			n = workFlowModelService.insertWorkFlowModel(workFlowModel);
		} catch (Exception e) {
			LOGGER.error("新增工作流模板错误", e);
		}
		return n;
	}
	

	/**
	 * 更改工作流模板状态
	 * @return
	 */
	@RequestMapping(value = "/updateWorkFlowModelStatus",method = RequestMethod.POST)
	public @ResponseBody UpdateStatus updateWorkFlowModelStatus(@RequestBody WorkFlowModel workFlowModel){
		UpdateStatus updateStatus = new UpdateStatus();
		try {
			int n  = workFlowModelService.updateWorkFlowModelStatus(workFlowModel);
			updateStatus.setIsSuccess(n);
			if (n==1) {
				int x =workFlowModel.getStatus();
				updateStatus.setTarget("status");
				updateStatus.setStatus(x);
				updateStatus.setStatusName(AdminConst.STATUS_NAME[x]);
				updateStatus.setOpStatus(AdminConst.OP_STATUS[x]);
				updateStatus.setOpStatusName(AdminConst.OP_STATUS_NAME[x]);
			}
			updateStatus.setIsSuccess(n);
		} catch (Exception e) {
			LOGGER.error("更改工作流模板状态", e);
		}
		
		return updateStatus;
	}
	
}
