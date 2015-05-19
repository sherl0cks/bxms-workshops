<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<div class="container">
		<h1>Dynamically Deploy Rules</h1>
		<p>With Red Hat BRMS, it is simple to package, version and hot deploy new business rules to your application without downtime. BRMS also provides the auditing and governance to
			ensure compliance in highly regulated environments. This section will explain the mechanics used by Red Hat BRMS to deploy business rules. Then we'll step though a reference implementation of the
			Administrator Push design pattern so you can see the theory in practice.</p>
	</div>
</div>

<div class="container">

	<!-- How Does it Work? -->
	<div class="row">
		<div class="text-left">
			<h2 class="section-heading">How Does it Work?</h2>
			<hr class="light">
			<p class="text-faded">
				The Red Hat BRMS deployment model is built on top of <a href="https://maven.apache.org/">Apache Maven</a>, the Java community's most widely used software project management tool. BRMS leverages
				Maven to provide the following features:
			</p>
			<ol>
				<li>The <a href=https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#The_kmodule>KieModule</a> and the <a
					href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#Creating_a_KIE_Project">KieProject</a> which are used to provide a simple,
					convention driven approach to business rules projects.
				</li>
				<li>The <a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html/Development_Guide/sect-Building_with_Maven.html#KIE_Plugin">Maven Kie Plugin</a> which is used
					to catch bugs at compile time, making it safer to hot deploy rules as KJARs's at runtime.
				</li>
				<li>The <a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#KieRepository">KieRepository</a> which loads KJAR's at
					runtime into an isolated classpath container known as a <a
					href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html-single/Development_Guide/index.html#Creating_a_KIE_Container">KieContainer</a></li>
			</ol>
			<p>Understanding these foundational elements of the product's architecture will help you make better application design decisions. In the next section, we'll take an example to show how easy it is
				easy to black box these mechanics and provide simple abstractions for developers and administrators to use alike.</p>
		</div>
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			
			<!-- panel 3 -->
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading-one-three">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapase-one-three" aria-expanded="true" aria-controls="collapse-one-three"> The KieRepository and the KieContainer in action </a>
					</h4>
				</div>
				<div id="collapase-one-three" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading-one-three">
					<div class="panel-body"><pre><code class="java">import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

KieServices kieServices = KieServices.Factory.get();
ReleaseId oldReleaseId = kieServices.newReleaseId( "org.acme", "myartifact", "1.0" );
KieContainer kieContainer = kieServices.newKieContainer( releaseId );

// Hot deploy new KJAR at runtime
ReleaseId newReleaseId = kieServices.newReleaseId( "org.acme", "myartifact", "2.0" );
kieContainer.updateToVersion( newReleaseId );</code></pre>			
					</div>
				</div>
			</div>
			
			
			<!-- panel 1 -->
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading-one-one">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapase-one-one" aria-expanded="true" aria-controls="collapse-one-one"> An Example KieProject Structure </a>
					</h4>
				</div>
				<div id="collapase-one-one" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-one-one">
					<div class="panel-body">
<pre>.
|__ pom.xml
|__ src
    |__ main
        |__ resources
            |__ com
            |   |__ redhat
            |       |__ workshops
            |           |__ Eligibility.drl
            |           |__ InsurancePremiumRuleFlow.bpmn
            |           |__ PremiumCalculationRules.xls
            |           |__ Queries.drl
            |           |__ VehiclePremiumDSLRule.dsl
            |           |__ VehiclePremiumDSLRuleDSLR.dslr
            |__ META-INF
                |__ kmodule.xml</pre>
					</div>
				</div>
			</div>
			
			
			<!-- panel 2 -->
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading-one-two">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapase-one-two" aria-expanded="true" aria-controls="collapse-one-two"> An Example pom.xml with the Kie Maven Plugin and KJAR configs</a>
					</h4>
				</div>
				<div id="collapase-one-two" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-one-two">
					<div class="panel-body">
						<pre><code class="xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"&gt;
 &lt;modelVersion&gt;4.0.0&lt;/modelVersion&gt;

 &lt;groupId&gt;com.redhat.workshops&lt;/groupId&gt;
 &lt;artifactId&gt;business-rules&lt;/artifactId&gt;
 &lt;version&gt;2.1&lt;/version&gt;
 &lt;packaging&gt;kjar&lt;/packaging&gt;
 &lt;name&gt;externalized business rules&lt;/name&gt;

 &lt;dependencies&gt;
  &lt;dependency&gt;
   &lt;groupId&gt;com.redhat.workshops&lt;/groupId&gt;
   &lt;artifactId&gt;domain-model&lt;/artifactId&gt;
   &lt;version&gt;2.0&lt;/version&gt;
  &lt;/dependency&gt;
 &lt;/dependencies&gt;
 
 &lt;build&gt;
  &lt;plugins&gt;
   &lt;plugin&gt;			
    &lt;groupId&gt;org.kie&lt;/groupId&gt;
    &lt;artifactId&gt;kie-maven-plugin&lt;/artifactId&gt;
    &lt;version&gt;6.2.0.Final-redhat-4&lt;/version&gt;
    &lt;goals&gt;
     &lt;goal&gt;build&lt;/goal&gt;
    &lt;/goals&gt;
   &lt;/plugin&gt;
  &lt;/plugins&gt;
 &lt;/build&gt;	
&lt;/project&gt;</code></pre>
					</div>
				</div>
			</div>
			
		</div>
	</div>

	<!-- Business Rules Administrator -->
	<div class="row">
		<div class="text-left">
			<h2 class="section-heading">The Administrator Push Pattern</h2>
			<hr class="light">
			<p class="text-faded">Harnessing the power of the BRMS deployment architecture is surprisingly easy. The reference implementation of the Administrator Push pattern in this workshop is less than
				50 lines of code. Let's get hands on with the implementation to see how easy it really is. </p>
			<ol>
				<li>The name of the process that will orchestrate the rules</li>
				<li>The facts required for the rules to make a decision</li>
				<li>The type of response required from the rules</li>
			</ol>
			<p>Everything else, including how and where the BRMS APIs will create sessions, insert facts and fire rules is an implementation detail that can be injected via the Spring context.</p>
		</div>
	</div>
	<div class="panel-group" id="accordion3" role="tablist" aria-multiselectable="true">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingOne1">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne2" aria-expanded="true" aria-controls="collapseOne2"> Dynamically Deploy Rules Locally </a>
				</h4>
			</div>
			<div id="collapseOne2" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne2">
				<div class="panel-body">
	
					<div class="col-md-3">	
						<form id="local2-deploy-form">
							<fieldset>
								<legend>Rule Deploy Request</legend>

								<div class="form-group">
									<label class="control-label" for="group">Group</label>
									<div class="">
										<input id="group-input" name="group" value="com.redhat.workshops" class="form-control input-md" type="text">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label" for="artifact">Artifact</label>
									<div class="">
										<input id="artifact-input" name="artifact" value="business-rules" class="form-control input-md" type="text">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label" for="version">Version</label>
									<div class="">
										<input id="version-input" name="version" value="2.1" class="form-control input-md" type="text">
									</div>
								</div>

								<button type="submit" class="btn btn-primary btn-large">Post Request</button>

							</fieldset>
						</form>
					</div>
									
					<div class="col-md-3">
						<div id="local-deploy-response">
							<p>Current Rule Version: ${releaseid}</p>
						</div>
					</div>
					
					<div class="col-md-3">
						<div class="">
							<form id="local2-form">
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

									<button type="submit" class="btn btn-primary btn-large">Post Request</button>

								</fieldset>
							</form>
						</div>
					</div>
					
					<div id="local2-response" class="col-md-3">
						<h3>Note: Rules are being loaded dynamically, so the first request will take a few seconds.</h3>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>