
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<div class="container text-center">
		<h1>The Decision Service</h1>
		<p>Red Hat JBoss BRMS provides an easy, maintainable way to execute business decisions in Java applications. Use the following design patterns to integrate the BRMS execution engine into your
			Spring application.</p>
	</div>
</div>


<div class="container">
	<div class="row">
		<div>
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
			<div class="text-center">
				<h2 class="section-heading">Remote Execution</h2>
				<hr class="light">
				<p class="text-faded">Start Bootstrap has everything you need to get your new website up and running in no time! All of the templates and themes on Start Bootstrap are open source, free to
					download, and easy to use. No strings attached!</p>
				<a href="#" class="btn btn-default btn-xl">Get Started!</a>
			</div>
		</div>
		<div class="row">
			<div class="text-center">
				<h2 class="section-heading">Local Execution</h2>
				<hr class="light">
				<p class="text-faded">Start Bootstrap has everything you need to get your new website up and running in no time! All of the templates and themes on Start Bootstrap are open source, free to
					download, and easy to use. No strings attached!</p>
				<a href="#" class="btn btn-default btn-xl">Get Started!</a>
			</div>
		</div>
	</div>