<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<div class="container">
		<h1>Dynamically Deploy Rules</h1>
		<p>With Red Hat BRMS, it is simple to package, version and hot deploy new business rules to your application without downtime. This section will explain the mechanics used by Red Hat BRMS to
			deploy business rules. Then we'll step though a reference implementation of the Admistrator Push design pattern so you can see the theory in practice. Finally, we'll consider the implications of
			Citi's infrastructure on the Red Hat BRMS deployment model.</p>
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
				Maven during the following phases of your project's lifecycle:
			</p>
			<ol>
				<li>Design and development of business rule projects</li>
				<li>Build and deploy business rule projects</li>
				<li>Installation of business rule projects in running applications</li>
			</ol>
			<p>Understanding these foundational elements of the product's architecture will help you make better application design decisions. As you will in the next two sections, using some common design
				patterns can help you abstract away the details of Maven and allow you to focus on writing rules.</p>
		</div>
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading-one-one">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapase-one-one" aria-expanded="true" aria-controls="collapse-one-one"> The KieProject and the KieModule </a>
					</h4>
				</div>
				<div id="collapase-one-one" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading-one-one">
					<div class="panel-body">
						<p>I'll show a project tree here with a kmodule</p>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading-one-two">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapase-one-two" aria-expanded="true" aria-controls="collapse-one-two"> The Maven Kie Plugin and the KJar </a>
					</h4>
				</div>
				<div id="collapase-one-two" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-one-two">
					<div class="panel-body">
						<p>We'll show a simple pom.xml and</p>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading-one-two">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapase-one-two" aria-expanded="true" aria-controls="collapse-one-two"> The KieRepository and the KieContainer </a>
					</h4>
				</div>
				<div id="collapase-one-two" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-one-two">
					<div class="panel-body">
						<p>I'll show code that loads a KJar via the Repository and then creates a new KieContainer</p>
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
			<p class="text-faded">Provide one clean and simple interface to every application that needs to fire rules. With this pattern, developers invoking a decision services to execute rules only need
				to know three things.</p>
			<ol>
				<li>The name of the process that will orchestrate the rules</li>
				<li>The facts required for the rules to make a decision</li>
				<li>The type of response required from the rules</li>
			</ol>
			<p>Everything else, including how and where the BRMS APIs will create sessions, insert facts and fire rules is an implementation detail that can be injected via the Spring context.</p>
		</div>
	</div>

	<!-- Business Rules Administrator -->
	<div class="row">
		<div class="text-left">
			<h2 class="section-heading">Considerations for Citi's Network Partitions</h2>
			<hr class="light">
			<p class="text-faded">Provide one clean and simple interface to every application that needs to fire rules. With this pattern, developers invoking a decision services to execute rules only need
				to know three things.</p>
			<ol>
				<li>The name of the process that will orchestrate the rules</li>
				<li>The facts required for the rules to make a decision</li>
				<li>The type of response required from the rules</li>
			</ol>
			<p>Everything else, including how and where the BRMS APIs will create sessions, insert facts and fire rules is an implementation detail that can be injected via the Spring context.</p>
		</div>
	</div>
</div>