package com.redhat.bpm.approval;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.runtime.manager.impl.deploy.DeploymentDescriptorImpl;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.query.QueryFilter;
import org.kie.internal.runtime.conf.DeploymentDescriptor;
import org.kie.internal.runtime.conf.NamedObjectModel;

import com.redhat.approval.entities.VacationRequest;
import com.redhat.approval.value.RequestStatus;

public class VacationRequestService {

	protected static final String GROUP_ID = "com.redhat.workshops";
	protected static final String ARTIFACT_ID = "approval-knowledge";
	protected static final String VERSION = "1.0.0-SNAPSHOT";
	protected static final DeploymentUnit DEPLOYMENT_UNIT = getDeploymentUnit();
	protected static final String PROCESS_ID = "com.redhat.workshops.VacationApproval";

	protected ProcessService processService;
	protected RuntimeDataService runtimeDataService;
	protected DeploymentService deploymentService;
	protected UserTaskService userTaskService;

	public Long startProcess(VacationRequest request) {
		ensureKJarDeployed();

		Map<String, Object> processData = request.toProcessDataMap();
		return processService.startProcess(DEPLOYMENT_UNIT.getIdentifier(), PROCESS_ID, processData);
	}

	public void provideMoreInformation(String employeeId, String comment) {
		List<TaskSummary> tasks = runtimeDataService.getTasksAssignedAsPotentialOwner(employeeId, new QueryFilter());

		long taskId = tasks.get(0).getId();
		userTaskService.start(taskId, employeeId);

		Map<String, Object> outVars = new HashMap<String, Object>();
		outVars.put("out_comment", comment);

		userTaskService.complete(taskId, employeeId, outVars);
	}

	public void approveTheRequest(String managerId) {
		handleManagerTask(managerId, RequestStatus.APPROVED, null);
	}

	public void needMoreInfoOnTheRequest(String managerId, String comment) {
		handleManagerTask(managerId, RequestStatus.NEED_MORE_INFO, comment);
	}

	public void handleManagerTask(String managerId, RequestStatus status, String comment) {
		List<TaskSummary> tasks = runtimeDataService.getTasksAssignedAsPotentialOwner(managerId, new QueryFilter());

		long taskId = tasks.get(0).getId();

		System.err.println(processService.getProcessInstanceVariables(tasks.get(0).getProcessInstanceId()));
		userTaskService.start(taskId, managerId);

		Map<String, Object> taskVar = userTaskService.getTaskInputContentByTaskId(taskId);
		VacationRequest request = (VacationRequest) taskVar.get("in_vacationRequest");
		request.setStatus(status);

		Map<String, Object> outVars = new HashMap<String, Object>();
		outVars.put("out_vacationRequest", request);
		if (comment != null && !comment.isEmpty()) {
			outVars.put("out_comment", comment);
		}

		userTaskService.complete(taskId, managerId, outVars);
	}

	public void ensureKJarDeployed() {
		Collection<DeployedUnit> deployedUnits = deploymentService.getDeployedUnits();
		if (deployedUnits.size() == 0) {
			deploymentService.deploy(DEPLOYMENT_UNIT);
		}
	}
	
	private static DeploymentUnit getDeploymentUnit(){
		KModuleDeploymentUnit deploymentUnit = new KModuleDeploymentUnit(GROUP_ID, ARTIFACT_ID, VERSION);
		DeploymentDescriptor baseDescriptor = new DeploymentDescriptorImpl("org.jbpm.persistence.jpa");
		DeploymentDescriptor deploymentDescriptor = baseDescriptor.getBuilder().addWorkItemHandler( new NamedObjectModel("REST", "org.jbpm.process.workitem.rest.RESTWorkItemHandler") ).get();
		deploymentUnit.setDeploymentDescriptor(deploymentDescriptor);
		
		return deploymentUnit;
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
