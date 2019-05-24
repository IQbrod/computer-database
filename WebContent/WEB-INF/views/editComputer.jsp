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
                    <div class="label label-default pull-right">
                        id: ${computer.getId()}
                    </div>
                    <h1><spring:message code="edit.computer"/></h1>

                    <sform:form action="${pageContext.request.contextPath}/computers" method="POST" modelAttribute="computer">
                    	<input type="hidden" name="_method" value="PUT"/>
                        <sform:input type="hidden" value="${computer.getId()}" path="id"/>
                        <fieldset>
                            <div class="form-group">
                                <sform:label path="name"><spring:message code="computer.name"/></sform:label>
                                <sform:input type="text" class="form-control" path="name" id="computerName" value="${computer.getName()}" required="required"/>
                            </div>
                            <div class="form-group">
                                <sform:label path="introduction"><spring:message code="computer.introduction"/></sform:label>
                                <sform:input type="date" class="form-control" path="introduction" id="introduction" placeholder="YYYY-MM-DD" value="${computer.getIntroduction()}"/>
                            </div>
                            <div class="form-group">
                                <sform:label path="discontinued"><spring:message code="computer.discontinued"/></sform:label>
                                <sform:input type="date" class="form-control" path="discontinued" id="discontinued" placeholder="YYYY-MM-DD" value="${computer.getDiscontinued()}"/>
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
                            <input type="submit" value="<spring:message code="edit"/>" class="btn btn-primary">
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