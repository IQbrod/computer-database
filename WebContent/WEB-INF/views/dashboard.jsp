<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${nbComputer} Computers founds
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value='${pagination.search}' />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name <a href="dashboard?orderBy=name">&#x2193</a> <a href="dashboard?orderBy=name_rev">&#x2191</a>
                        </th>
                        <th>
                            Introduced date <a href="dashboard?orderBy=introduced">&#x2193</a> <a href="dashboard?orderBy=introduced_rev">&#x2191</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date <a href="dashboard?orderBy=discontinued">&#x2193</a> <a href="dashboard?orderBy=discontinued_rev">&#x2191</a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company <a href="dashboard?orderBy=company">&#x2193</a> <a href="dashboard?orderBy=company_rev">&#x2191</a>
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
                            	<a href="editComputer?id=${computer.getId()}" onclick="">${computer.getName()}</a>
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
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>