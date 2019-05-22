<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="app.title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"><spring:message code="app.title"/></a>
		</div>
	</header>

	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				<table id="errtable">
					<tr>
				        <td><spring:message code="error.status"/></td>
				        <td>${error.getErrorCode()}</td>
				    </tr>
				    <tr>
				        <td><spring:message code="error.error"/></td>
				        <td><spring:message code="error.${error.getErrorCode()}"/></td>
				    </tr>
				    <tr>
				        <td><spring:message code="error.exception"/></td>
				        <td>${error.getClassName()}</td>
				    </tr>
				    <tr>
				        <td><spring:message code="error.message"/></td>
				        <td>${error.getCustomMessage()}</td>
				    </tr>
				</table>
			</div>
		</div>
	</section>

	<script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/dashboard.js"></script>

</body>
</html>