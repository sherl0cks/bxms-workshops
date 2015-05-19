
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<div class="container">
		<h1>Hello, Citi!</h1>
		<h2>This is a simple Spring MVC web application to show you that:</h2>
		<ul>
			<li><h3>Executing rules with Spring is simple and easy</h3></li>
			<li><h3>Deploying rules can be done with a push of a button</h3></li>
			<li><h3>Authoring rules is simple in Business Central and Eclipse</h3></li>
		</ul>
		<h2></h2>
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="btn btn-primary btn-lg" data-toggle="collapse" data-parent="#accordion2" role="button" href="#collapseOne"> Learn more &raquo; </a>
				</div>
				<div id="collapseOne" class="accordion-body collapse">
					<h2>Design Patterns Make Life Easier</h2>
					<p>The Red Hat Consulting Business Automation Practice publishes the below set of design patterns for building enterprise grade applications with Red Hat BRMS. The goal is to provide simple
						conventions for building rules driven applications. The patterns highlighted in the diagram below are illustrated in this workshop.</p>
					<div class="accordion-inner">
						<img src="${pageContext.request.contextPath}/resources/images/patterns2.png" style="max-width: 100%;" />
					</div>
				</div>
			</div>
		</div>

	</div>
</div>

<div class="container text-center">
	<!-- Example row of columns -->
	<div class="row ">
		<div class="col-md-4">
			<h2>Execute in Spring</h2>
			<img src="${pageContext.request.contextPath}/resources/images/app_shiny1.svg" height="400px" width="400px"> <a class="btn btn-primary btn-lg center-block" href="${pageContext.request.contextPath}/execute" role="button"> Learn more
				&raquo;</a>

		</div>
		<div class="col-md-4">
			<h2>Deploy Anytime</h2>
			<img src="${pageContext.request.contextPath}/resources/images/box_shiny1.svg" height="400px" width="400px"> <a class="btn btn-primary btn-lg center-block" href=" ${pageContext.request.contextPath}/deploy" role="button"> Learn more
				&raquo;</a>

		</div>
		<div class="col-md-4">
			<h2>Author Your Way</h2>
			<img src="${pageContext.request.contextPath}/resources/images/list_shiny1.svg" height="400px" width="400px"> <a class="btn btn-primary btn-lg center-block" href="${pageContext.request.contextPath}/author" role="button"> Learn more
				&raquo;</a>

		</div>
	</div>
</div>
