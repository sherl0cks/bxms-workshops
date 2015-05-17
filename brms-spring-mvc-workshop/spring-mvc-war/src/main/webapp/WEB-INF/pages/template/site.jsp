<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<title>BRMS Spring MVC Application</title>
<!--  
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/screen.css" type="text/css" media="screen, projection"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/print.css" type="text/css" media="print"></link>
-->
<!-- Bootstrap --> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"><link>
<!-- Redhat -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat1.css" media="all" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat2.css" media="all" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat3.css" media="print" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/redhat4.css" media="all" />
<!--[if IE]>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ie.css"  type="text/css" media="screen, projection">
	<![endif]-->
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
		
    

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
		
	<script>
    $(document).ready(function() {
        $('a[href="' + this.location.pathname + '"]').parent().addClass('active');
    });
	</script>
</body>
</html>