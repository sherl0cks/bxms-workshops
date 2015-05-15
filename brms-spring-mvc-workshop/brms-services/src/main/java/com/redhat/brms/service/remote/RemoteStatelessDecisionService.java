package com.redhat.brms.service.remote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.core.command.runtime.process.StartProcessCommand;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.brms.service.api.StatelessDecisionService;
import com.redhat.brms.service.common.QueryUtils;
import com.redhat.brms.service.common.ReflectiveExecutionResultsTransformer;
import com.thoughtworks.xstream.XStream;

public class RemoteStatelessDecisionService implements StatelessDecisionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteStatelessDecisionService.class);

	private String httpUrl;
	private String containerId;
	private String userName;
	private String password;
	private int timeout = 0;
	private boolean isInitialized = false;

	private KieCommands commandFactory;
	private KieServicesClient client;
	private XStream xstream;

	public RemoteStatelessDecisionService(String httpUrl, String userName, String password, int timeout, String containerId) {
		this.httpUrl = httpUrl;
		this.userName = userName;
		this.password = password;
		this.timeout = timeout;
		this.containerId = containerId;
		init();
	}

	public RemoteStatelessDecisionService(String httpUrl, String userName, String password, String containerId) {
		this.httpUrl = httpUrl;
		this.userName = userName;
		this.password = password;
		this.containerId = containerId;
		init();
	}

	public RemoteStatelessDecisionService() {
	}

	public void init() {
		KieServicesConfiguration config;
		if (timeout == 0) {
			config = KieServicesFactory.newRestConfiguration(httpUrl, userName, password);
		} else {
			config = KieServicesFactory.newRestConfiguration(httpUrl, userName, password, 0);
		}
		this.client = KieServicesFactory.newKieServicesClient(config);
		commandFactory = KieServices.Factory.get().getCommands();
		xstream = BatchExecutionHelper.newXStreamMarshaller();
		isInitialized = true;
	}

	@Override
	public <Response> Response execute(Collection<Object> facts, String processId, Class<Response> responseClazz) {
		if (!isInitialized) {
			init();
		}

		String payload = getPayload(facts, processId, responseClazz);

		LOGGER.info(String.format("Remote BRMS request to %s/%s with below payload: \n %s", httpUrl, containerId, payload));

		ServiceResponse<String> reply = client.executeCommands(containerId, payload);
		if (reply.getType().equals(ResponseType.FAILURE)) {
			LOGGER.error(reply.toString());
			throw new RuntimeException(reply.getMsg());
		}

		LOGGER.info(reply.getResult());

		ExecutionResults results = (ExecutionResults) xstream.fromXML(reply.getResult());

		Response response = ReflectiveExecutionResultsTransformer.transform(results, responseClazz);

		return response;
	}

	public BatchExecutionCommand createBatchExecutionCommand(Collection<Object> facts, Class<?> responseClazz) {
		List<Command<?>> commands = new ArrayList<Command<?>>();

		if (facts != null) {
			commands.add(commandFactory.newInsertElements(facts));
		}

		commands.add(commandFactory.newFireAllRules());

		// creates commands to run the queries at the end of process
		commands.addAll(QueryUtils.buildQueryCommands(responseClazz));

		return commandFactory.newBatchExecution(commands, "defaultStatelessKieSession");
	}
	
	private String getPayload(Collection<Object> facts, String processId, Class<?> responseClazz){
		String payload = null;
		if ( processId == null){
			BatchExecutionCommand command = createBatchExecutionCommand(facts, responseClazz);
			payload = xstream.toXML(command);
		} else {
			String payloadTemplate = "<batch-execution lookup=\"defaultStatelessKieSession\">\n" +
	                "  <start-process processId=\"%s\"/>\n" +
	                "%s\n" +
	                "  <fire-all-rules/>\n" +
	                "</batch-execution>\n";
			String insertElements = "";
			if ( facts != null && facts.size() > 0){
				Command command = commandFactory.newInsertElements(facts);
				insertElements = xstream.toXML(command);
			}
			payload = String.format(payloadTemplate, processId, insertElements );
		}
		
		return payload;
	}

	@Override
	public <Response> Response execute(Collection<Object> facts, String processId) {
		return execute(facts, processId, null);
	}

	@Override
	public <Response> Response execute(Collection<Object> facts, Class<Response> responseClazz) {
		return execute(facts, null, responseClazz);
	}

	@Override
	public <Response> Response execute(Collection<Object> facts) {
		return execute(facts, null, null);
	}

	@Override
	public boolean upgradeRulesToVersion(String group, String artifact, String version) {
		return false;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
