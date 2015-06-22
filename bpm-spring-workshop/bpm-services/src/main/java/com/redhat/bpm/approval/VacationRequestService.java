package com.redhat.bpm.approval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.query.QueryFilter;

import com.redhat.approval.entities.VacationRequest;
import com.redhat.approval.value.RequestStatus;

public class VacationRequestService {

	protected ProcessService processService;
	protected RuntimeDataService runtimeDataService;
	protected DeploymentService deploymentService;
	protected UserTaskService userTaskService;
	
	
	
	public void approveTheRequest( String managerId ){
		List<TaskSummary> tasks = runtimeDataService.getTasksAssignedAsPotentialOwner(managerId, new QueryFilter());

		long taskId = tasks.get(0).getId();
		userTaskService.start(taskId, managerId);

		Map<String, Object> taskVar = userTaskService.getTaskInputContentByTaskId(taskId);
		VacationRequest request = (VacationRequest) taskVar.get("in_vacationRequest");
		request.setStatus(RequestStatus.APPROVED);	
		
		Map<String, Object> outVars = new HashMap<>();
		outVars.put("out_vacationRequest", request );
		
		userTaskService.complete(taskId, managerId, outVars);
	}
	
	public ProcessService getProcessService() {
		return processService;
	}
	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}
	public RuntimeDataService getRuntimeDataService() {
		return runtimeDataService;
	}
	public void setRuntimeDataService(RuntimeDataService runtimeDataService) {
		this.runtimeDataService = runtimeDataService;
	}
	public DeploymentService getDeploymentService() {
		return deploymentService;
	}
	public void setDeploymentService(DeploymentService deploymentService) {
		this.deploymentService = deploymentService;
	}
	public UserTaskService getUserTaskService() {
		return userTaskService;
	}
	public void setUserTaskService(UserTaskService userTaskService) {
		this.userTaskService = userTaskService;
	}

}
