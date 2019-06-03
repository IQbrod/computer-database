<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="app.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}"><spring:message code="app.title"/></a>
		</div>
	</header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="add.computer"/></h1>
                    <spring:message code="computer.name" var="computerName"/>
                    <sform:form action="${pageContext.request.contextPath}/computers" method="POST" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <sform:label path="name">${computerName}</sform:label>
                                <sform:input type="text" path="name" class="form-control" placeholder="${computerName}" required="required"/>
                            </div>
                            <div class="form-group">
                                <sform:label path="introduction"><spring:message code="computer.introduction"/></sform:label>
                                <sform:input type="date" path="introduction" class="form-control" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
                            </div>
                            <div class="form-group">
                                <sform:label path="discontinued"><spring:message code="computer.discontinued"/></sform:label>
                                <sform:input type="date" path="discontinued" class="form-control" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
                            </div>
                            <div class="form-group">
                                <sform:label path="companyId"><spring:message code="computer.company"/></sform:label>
                                <sform:select path="companyId" class="form-control" id="companyId" >
                                	<sform:option value="0" label="-"/>
                                	<sform:options items="${companyList}" itemValue="id" itemLabel="name" />
                                </sform:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="add.computer"/>" class="btn btn-primary">
                            <spring:message code="or"/>
                            <a href="${pageContext.request.contextPath}/computers" class="btn btn-default"><spring:message code="cancel"/></a>
                        </div>
                    </sform:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>