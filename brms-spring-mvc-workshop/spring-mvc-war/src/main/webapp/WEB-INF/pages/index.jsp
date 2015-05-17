    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
       	  <ul class="nav navbar-nav">
	        <li class="active"><a href="${pageContext.request.contextPath}/index">Home<span class="sr-only">(current)</span></a></li>
	        <li><a href="${pageContext.request.contextPath}/author">Author</a></li>
	        <li><a href="${pageContext.request.contextPath}/deploy">Deploy</a></li>
	        <li><a href="${pageContext.request.contextPath}/execute">Execute</a></li>
     	  </ul>
	      <ul class="nav navbar-nav navbar-right">
	      	<li><a href="https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BRMS/">Documentation</a></li>
	        <li><a href="${pageContext.request.contextPath}/contact">Contact Us</a></li>
	      </ul>
        </div><!--/.navbar-collapse -->
      </div>
    </nav>
   <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h1>Hello, Citi!</h1>
        <p>This is a simple Spring MVC web application to introduce you to Red Hat JBoss BRMS. We hope you walk away from this experience feeling the following three things about the product:
  		<ol> 
  			<li>Authoring rules is simple in Business Central and Eclipse</li>
  			<li>Deploying rules can be done with a push of a button</li>
  			<li>Executing rules with Spring is simple and easy</li>
  		</ol>
        </p>
        
      	<div class="accordion" id="accordion2">
		  <div class="accordion-group">
		    <div class="accordion-heading">
		      <a class="btn btn-primary btn-lg" data-toggle="collapse" data-parent="#accordion2" role="button" href="#collapseOne">
		        Learn more &raquo;
		      </a>
		    </div>
		    <div id="collapseOne" class="accordion-body collapse">
		    	<h1>Enterprise</h1>
		    	<h2>Deploys</h2>
		    	<h3>do things</h3>
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
          <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
          <h2>Deploy Rules Any Time</h2>
          <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
       </div>
        <div class="col-md-4">
          <h2>Execute Rules With Spring</h2>
          <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
          <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
      </div>