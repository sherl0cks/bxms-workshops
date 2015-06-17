package com.redhat.brms.service.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.brms.service.api.StatelessDecisionService;
import com.redhat.brms.service.common.QueryUtils;
import com.redhat.brms.service.common.ReflectiveExecutionResultsTransformer;

public class LocalStatelessDecisionService implements StatelessDecisionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalStatelessDecisionService.class);

	private KieCommands commandFactory;
	private KieContainer kieContainer;
	private boolean debugConsoleLogging;
	private String auditLogName;
	private KieRuntimeLogger auditLogger;

	public LocalStatelessDecisionService() {
		kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		try {
			StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession();
			LOGGER.debug(statelessKieSession.getKieBase().toString());
		} catch (Exception e) {
			LOGGER.warn("There is no KieModule on the classpath. Upgrade the KieContainer to a valid KieModule to fire rules");
		}

		/**
		 * Break point here to find what rules are in the KIE Base
		 */
		commandFactory = KieServices.Factory.get().getCommands();
	}

	@Override
	public <Response> Response execute(Collection<Object> facts, String processId, Class<Response> responseClazz) {
		StatelessKieSession session;
		try {
			session = kieContainer.newStatelessKieSession();
		} catch (Exception e) {
			LOGGER.error( e.getLocalizedMessage() );
			return null;
		}

		BatchExecutionCommand batchExecutionCommand = createBatchExecutionCommand(facts, processId, responseClazz);

		// only use in test situations
		if (auditLogName != null) {
			auditLogger = KieServices.Factory.get().getLoggers().newFileLogger(session, auditLogName);
		}
		// only use in test situations
		if (debugConsoleLogging) {
			session.addEventListener(new DebugRuleRuntimeEventListener());
			session.addEventListener(new DebugAgendaEventListener());
		}

		ExecutionResults results = session.execute(batchExecutionCommand);

		Response response = ReflectiveExecutionResultsTransformer.transform(results, responseClazz);

		if (auditLogger != null) {
			auditLogger.close();
		}

		return response;
	}

	public BatchExecutionCommand createBatchExecutionCommand(Collection<Object> facts, String processId, Class<?> responseClazz) {
		List<Command<?>> commands = new ArrayList<Command<?>>();

		if (facts != null) {
			commands.add(commandFactory.newInsertElements(facts));
		}
		if (processId != null && !processId.isEmpty()) {
			commands.add(commandFactory.newStartProcess(processId));
		}

		commands.add(commandFactory.newFireAllRules());

		// creates commands to run the queries at the end of process
		commands.addAll(QueryUtils.buildQueryCommands(responseClazz));

		return commandFactory.newBatchExecution(commands);
	}

	@Override
	public boolean createOrUpgradeRulesWithVersion(String group, String artifact, String version) {
		ReleaseId releaseId = KieServices.Factory.get().newReleaseId(group, artifact, version);
		Results results = null;
		try {
			results = kieContainer.updateToVersion(releaseId);
		} catch (UnsupportedOperationException e) {
			LOGGER.info("Upgrading to version " + releaseId.toString());
			try {
				kieContainer = KieServices.Factory.get().newKieContainer(releaseId);
				results = kieContainer.updateToVersion(releaseId);
			} catch (Exception e2) {
				return false;
			}
		}

		return results.getMessages().size() == 0;
	}
	
	@Override
	public String getCurrentVersion() {
		ReleaseId version = this.kieContainer.getReleaseId();
		if ( version == null){
			return "local container is empty";
		}
		return version.toString();
	}

	public KieContainer getKieContainer() {
		return kieContainer;
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

	public boolean isDebugConsoleLogging() {
		return debugConsoleLogging;
	}

	public void setDebugConsoleLogging(boolean debugConsoleLogging) {
		this.debugConsoleLogging = debugConsoleLogging;
	}

	public String getAuditLogName() {
		return auditLogName;
	}

	public void setAuditLogName(String auditLogName) {
		this.auditLogName = auditLogName;
	}



}
