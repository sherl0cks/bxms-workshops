
   <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h1>Hello, Citi!</h1>
        <p>This is a simple Spring MVC web application to introduce you to Red Hat JBoss BRMS. We hope you walk away from this experience feeling the following three things about the product:</p>
  		<ol> 
  			<li>Authoring rules is simple in Business Central and Eclipse</li>
  			<li>Deploying rules can be done with a push of a button</li>
  			<li>Executing rules with Spring is simple and easy</li>
  		</ol>
        
      	<div class="accordion" id="accordion2">
		  <div class="accordion-group">
		    <div class="accordion-heading">
		      <a class="btn btn-primary btn-lg" data-toggle="collapse" data-parent="#accordion2" role="button" href="#collapseOne">
		        Learn more &raquo;
		      </a>
		    </div>
		    <div id="collapseOne" class="accordion-body collapse">
		    	<h2>Design Patterns Make Life Easier</h2>
		    	<p>The Red Hat Consulting Business Automation Practice publishes the below set of design patterns for building enterprise grade applications with Red Hat BRMS. The goal is to provide simple conventions 
		    	for building rules driven applications. The patterns highlighted in the diagram below are illustrated in this workshop. TODO make new graphic with highlights.
		    	</p>
		      <div class="accordion-inner">
             	<img src="${pageContext.request.contextPath}/resources/images/patterns.png" style="max-width: 100%;" />
		      </div>
		    </div>
		  </div>
		</div>
        
      </div>
    </div>

    <div class="container">
      <!-- Example row of columns -->
      <div class="row">
        <div class="col-md-4">
          <h2>Author Rules Your Way</h2>
          <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          <p><a class="btn btn-default" href="${pageContext.request.contextPath}/author" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
          <h2>Deploy Rules Anytime</h2>
          <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          <p><a class="btn btn-default" href="${pageContext.request.contextPath}/deploy" role="button">View details &raquo;</a></p>
       </div>
        <div class="col-md-4">
          <h2>Execute Rules in Spring</h2>
          <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
          <p><a class="btn btn-default" href="${pageContext.request.contextPath}/execute" role="button">View details &raquo;</a></p>
        </div>
      </div>
    </div>  
      