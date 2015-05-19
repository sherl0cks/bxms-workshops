<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="viewport" content="initial-scale=1, maximum-scale=1"></meta>
<title>BRMS Spring MVC Application</title>
<!--  
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/screen.css" type="text/css" media="screen, projection"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/print.css" type="text/css" media="print"></link>
-->
<!-- Bootstrap -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"></link>

<!-- Highlight -->
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/8.5/highlight.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/obsidian.css"></link>

<!-- Redhat -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat1.css" media="all"></link>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat2.css" media="all"></link>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat3.css" media="print"></link>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat4.css" media="all"></link>
<!--[if IE]>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ie.css"  type="text/css" media="screen, projection">
	<![endif]-->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

<script>
	$(document).ready(
			function() {
				$('a[href="' + this.location.pathname + '"]').parent()
						.addClass('active');
			});
</script>
<script src="${pageContext.request.contextPath}/highlight.min.js"></script>
<script>
	hljs.initHighlightingOnLoad();
</script>

<script>
	$(document).ready(function() {
		$('#local-form').submit(function() {

			var url = "${pageContext.request.contextPath}/execute/premium/local"; // the script where you handle the form input.
			$.ajax({
				type : "GET",
				url : url,
				data :  $(this).serialize(),
				success : function(data) {
					$("#local-response").html(data);
				},
				 error: function (data) {
					 alert("something broke in the ajax. go to the contact us section and send us a bug!"); // show response from the php script.
				 }
				
			});

			return false; // avoid to execute the actual submit of the form.
		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#remote-form').submit(function() {

			var url = "${pageContext.request.contextPath}/execute/premium/remote"; // the script where you handle the form input.
			$.ajax({
				type : "GET",
				url : url,
				data :  $(this).serialize(),
				success : function(data) {
					$("#remote-response").html(data);
				},
				 error: function (data) {
					 alert("something broke in the ajax. go to the contact us section and send us a bug!"); // show response from the php script.
				 }
				
			});

			return false; // avoid to execute the actual submit of the form.
		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#local-deploy-form').submit(function() {

			var url = "${pageContext.request.contextPath}/deploy/local"; // the script where you handle the form input.
			$.ajax({
				type : "GET",
				url : url,
				data :  $(this).serialize(),
				success : function(data) {
					$("#local-deploy-response").html(data);
				},
				 error: function (data) {
					 alert("something broke in the ajax. go to the contact us section and send us a bug!"); // show response from the php script.
				 }
				
			});

			return false; // avoid to execute the actual submit of the form.
		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#local2-form').submit(function() {

			var url = "${pageContext.request.contextPath}/execute/premium/local"; // the script where you handle the form input.
			$.ajax({
				type : "GET",
				url : url,
				data :  $(this).serialize(),
				success : function(data) {
					$("#local2-response").html(data);
				},
				 error: function (data) {
					 alert("something broke in the ajax. go to the contact us section and send us a bug!"); // show response from the php script.
				 }
				
			});

			return false; // avoid to execute the actual submit of the form.
		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#remote-deploy-form').submit(function() {

			var url = "${pageContext.request.contextPath}/deploy/remote"; // the script where you handle the form input.
			$.ajax({
				type : "GET",
				url : url,
				data :  $(this).serialize(),
				success : function(data) {
					$("#remote-deploy-response").html(data);
				},
				 error: function (data) {
					 alert("something broke in the ajax. go to the contact us section and send us a bug!"); // show response from the php script.
				 }
				
			});

			return false; // avoid to execute the actual submit of the form.
		});
	});
</script>

<script>
	$(document).ready(function() {
		$('#remote2-form').submit(function() {

			var url = "${pageContext.request.contextPath}/execute/premium/remote"; // the script where you handle the form input.
			$.ajax({
				type : "GET",
				url : url,
				data :  $(this).serialize(),
				success : function(data) {
					$("#remote2-response").html(data);
				},
				 error: function (data) {
					 alert("something broke in the ajax. go to the contact us section and send us a bug!"); // show response from the php script.
				 }
				
			});

			return false; // avoid to execute the actual submit of the form.
		});
	});
</script>

<style>
body {
	margin-top: 20px;
	margin-bottom: 20px;
	background-color: #DFDFDF;
}
</style>
</head>


<body data-spy="scroll" data-target="navbar">


	<tiles:insertAttribute name="header" />

	<tiles:insertAttribute name="body" />

	<hr></hr>

	<tiles:insertAttribute name="footer" />




</body>
</html>