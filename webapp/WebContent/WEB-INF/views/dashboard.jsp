<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="app.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
    
    <script src="./js/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/dashboard.js"></script>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${pagination.getNbComputer()} <spring:message code="computer.found"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="search.name"/>" value='${pagination.search}' />
                        <input type="submit" id="searchsubmit" value="<spring:message code="filter"/>"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="computers/new"><spring:message code="add.computer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="edit"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="" method="POST">
        	<input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <spring:message code="computer.name"/> <a href="?orderBy=name">&#x2193</a> <a href="?orderBy=name_rev">&#x2191</a>
                        </th>
                        <th>
                            <spring:message code="computer.introduction"/> <a href="?orderBy=introduced">&#x2193</a> <a href="?orderBy=introduced_rev">&#x2191</a>
                        </th>
                        <th>
                            <spring:message code="computer.discontinued"/> <a href="?orderBy=discontinued">&#x2193</a> <a href="?orderBy=discontinued_rev">&#x2191</a>
                        </th>
                        <th>
                            <spring:message code="computer.company"/> <a href="?orderBy=company">&#x2193</a> <a href="?orderBy=company_rev">&#x2191</a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach var="computer" items="${computerList}">
                		<tr>
                			<td class="editMode">
                				<input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
                			</td>
                			<td>
                            	<a href="computers/${computer.getId()}" onclick="">${computer.getName()}</a>
                        	</td>
                        	<td>
                        		${computer.getIntroduction()}
                        	</td>
                        	<td>
                        		${computer.getDiscontinued()}
                        	</td>
                        	<td>
                        		${computer.getCompanyName()}
                        	</td>
                		</tr>
                	</c:forEach>                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="?page=1" aria-label="Begin">
                      <span aria-hidden="true">&laquo;</span>
                  	</a>
              	</li>
	            <li><a href="?page=${pagination.medianPage-2}">${pagination.medianPage-2}</a></li>
	            <li><a href="?page=${pagination.medianPage-1}">${pagination.medianPage-1}</a></li>
	            <li><a href="?page=${pagination.medianPage}">${pagination.medianPage}</a></li>
	            <li><a href="?page=${pagination.medianPage+1}">${pagination.medianPage+1}</a></li>
	            <li><a href="?page=${pagination.medianPage+2}">${pagination.medianPage+2}</a></li>
	            <li>
	              	<a href="?page=${pagination.maxPage}" aria-label="End">
	                	<span aria-hidden="true">&raquo;</span>
	              	</a>
	            </li>
        	</ul>

	        <ul class="pagination pull-right" role="group" >
	            <li><a href="?page=1&size=10">10</a></li>
	            <li><a href="?page=1&size=50">50</a></li>
	            <li><a href="?page=1&size=100">100</a></li>
	        </ul>
        </div>
    </footer>
</body>
</html>