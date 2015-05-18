
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<div class="container text-center">
		<h1>The Stateless Decision Service</h1>
		<p>Red Hat JBoss BRMS provides an easy, maintainable way to execute business decisions in Java applications. By enforcing stateless interaction with the BRMS runtime, developers can build easier
			to maintain applications that scale horizontally. Use the following design patterns to integrate the BRMS execution engine into your Spring application.</p>
	</div>
</div>


<div class="container">
	<div class="row">
		<div class="text-left">
			<h2 class="section-heading">A Common API</h2>
			<hr class="light">
			<p class="text-faded">Provide one clean and simple interface to every application that needs to fire rules. With this pattern, developers invoking a decision services to execute rules only need
				to know three things.</p>
			<ol>
				<li>The name of the process that will orchestrate the rules</li>
				<li>The facts required for the rules to make a decision</li>
				<li>The type of response required from the rules</li>
			</ol>
			<p>Everything else, including how and where the BRMS APIs will create sessions, insert facts and fire rules is an implementation detail that can be injected via the Spring context.</p>
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne"> The Stateless Decision Service Interface </a>
						</h4>
					</div>
					<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
						<div class="panel-body">
							<pre><code class="java">package com.redhat.brms.service.api;

public interface StatelessDecisionService {

 public &lt;T&gt; T execute(Collection&lt;Object&gt; facts, String processId, Class&lt;T&gt; responseClazz);

}</code></pre>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingTwo">
						<h4 class="panel-title">
							<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo"> Application Code Invoking the Interface </a>
						</h4>
					</div>
					<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
						<div class="panel-body">
							<pre><code class="java">package com.redhat.brms;

@ActiveProfiles(profiles = { "test-local" })
@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class LocalPremiumDecisionServiceTest extends AbstractJUnit4SpringContextTests {								
	
 @Autowired
 private StatelessDecisionService decisionService;		
	
 @Test
 public void shouldLoadWorkshopRulesAndExecuteLocally() {					
  Collection&lt;Object&gt; facts = new ArrayList&lt;Object&gt;();

  Driver driver = new Driver();
  driver.setAge(30);
  facts.add(driver);
		
  Premium premium = new Premium();
  facts.add(premium);
		
  PremiumResponse r;
  r = decisionService.execute(facts, "InsurancePremiumRuleFlow", PremiumResponse.class);
  
  Assert.assertEquals(1, r.getPremiums().size());
 }
}</code></pre>
						</div>
					</div>
				</div>



				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingThree">
						<h4 class="panel-title">
							<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree"> A Sample Response from the Decision Service </a>
						</h4>
					</div>
					<div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
						<div class="panel-body">
							<pre><code class="java">package com.redhat.brms;

public class PremiumResponse {

 @KieQuery(binding = "$premium", queryName = "Get All Premiums")
 private Collection&lt;Premium&gt; premiums;

 public Collection&lt;Premium&gt; getPremiums() {
  return premiums;
 }

 public void setPremiums(Collection&lt;Premium&gt; premiums) {
  this.premiums = premiums;
 }
	
}</code></pre>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="text-left">
			<h2 class="section-heading">Local Execution</h2>
			<hr class="light">
			<p class="text-faded">The simplest way to configure the BRMS runtime engine is to embed the execution libraries inside your application and deploy it to the same JVM. We've provided a reference
				implementation of this pattern that enables the following recommended practices:</p>
			<ol>
				<li><a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#StatelessKieSession">StatelessKieSessions</a> by default make
					it easy to support highly concurrent interaction with the engine</li>
				<li>Sensible defaults make it easy to build KieContainers from the <a
					href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#Creating_a_KIE_Container">classpath</a> or dynamically from <a
					href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#KieRepository">remote repositories</a>
				</li>
				<li><a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#sect-Debugging_Views_in_JBoss_Developer_Studio">Audit logs</a>
					and <a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#Event_Packages">console debugging</a> can be easily enable with
					configuration switches</li>
			</ol>
			<p class="text-faded">Use this implementation as the basis of your local BRMS decision service to accelerate your next project.</p>
			<div class="panel-group" id="accordion2" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingOne1">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne1" aria-expanded="true" aria-controls="collapseOne1"> Try it for yourself </a>
						</h4>
					</div>
					<div id="collapseOne1" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne1">
						<div class="panel-body">
							<div class="col-md-6">
								<div id="local-decision-form" class="">
									<form>
										<fieldset>

											<legend>Car Insurance Request</legend>

											<div class="form-group">
												<label class="control-label" for="name">Driver's Name</label>
												<div class="">
													<input id="name-input" name="name" value="Jane Doe" class="form-control input-md" type="text">
												</div>
											</div>

											<div class="form-group">
												<label class="control-label" for="age">Driver's Age</label>
												<div class="">
													<input id="age-input" name="age" value="25" class="form-control input-md" type="number">
												</div>
											</div>

											<div class="form-group">
												<label class="control-label" for="make">Vehicle Make</label>
												<div class="">
													<select id="make" name="make" class="form-control">
														<option value="BMW">BMW</option>
														<option value="Honda">Honda</option>
													</select>
												</div>
											</div>

											<button type="submit" class="btn btn-primary btn-large" formmethod="post">Post Request</button>

										</fieldset>
									</form>
								</div>

							</div>
							<div class="col-md-6">${driver.name}${driver.age}${premium.amount}</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingTwo1">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo1" aria-expanded="true" aria-controls="collapseTwo1"> Sample applicationContext.xml </a>
						</h4>
					</div>
					<div id="collapseTwo1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo1">
						<div class="panel-body">
							<pre>
							<code class="xml">&lt;bean id="decisionService" class="com.redhat.brms.service.local.LocalStatelessDecisionService"&gt;
 &lt;property name="auditLogName" value="brmsAudit" /&gt;
 &lt;property name="debugConsoleLogging" value="true" /&gt;
&lt;/bean&gt;</code>
						</pre>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="text-left">
			<h2 class="section-heading">Remote Execution</h2>
			<hr class="light">
			<p class="text-faded">
				The most flexible way to configure the BRMS runtime engine is to stand up the out of the box <a
					href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BRMS/6.1/html-single/User_Guide/index.html#chap-The_Realtime_Decision_Server">Realtime Decision Server</a> and then communicate
				with it over REST and JMS. We've provided a reference implementation of the Java client that simplifies interaction with the following APIs:
			</p>
			<ol>
				<li><a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#CommandExecutor_Interface">Batch Execution Interface</a></li>
				<li><a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BRMS/6.1/html-single/User_Guide/index.html#The_REST_API_for_Managing_the_Realtime_Decision_Server">Remote REST Interface</a></li>
				<li><a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#Available_API">XStream Marshaling</a></li>
			</ol>
			<p class="text-faded">Use this implementation as the basis of your remote BRMS decision service to accelerate your next project.</p>
		</div>
	</div>

</div>