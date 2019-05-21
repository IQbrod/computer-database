<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="app.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
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

                    <form action="editComputer" method="POST">
                        <input type="hidden" value="${computer.getId()}" name="id" id="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="computer.name"/></label>
                                <input type="text" class="form-control" name="computerName" id="computerName" value="${computer.getName()}">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="computer.introduction"/></label>
                                <input type="date" class="form-control" name="introduced" id="introduced" placeholder="YYYY-MM-DD" value="${computer.getIntroduction()}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="computer.discontinued"/></label>
                                <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="YYYY-MM-DD" value="${computer.getDiscontinued()}">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="computer.company"/></label>
                                <select class="form-control" name="companyId" id="companyId" >
                                	<option value="0">--</option>
                                    <c:forEach var="company" items="${companyList}">
                                		<option value="${company.getId()}" ${computer.getCompanyId() == company.getId() ? "selected" : ""}>${company.getName()}</option>
                                	</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="edit"/>" class="btn btn-primary">
                            <spring:message code="or"/>
                            <a href="dashboard" class="btn btn-default"><spring:message code="cancel"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>