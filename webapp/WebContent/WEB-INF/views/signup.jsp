<!DOCTYPE html>
<%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        <sform:form action="${pageContext.request.contextPath}/signup" method="post" modelAttribute="user">
			<sform:label path="username">Name</sform:label>
			<sform:input type="text" path="username" id="username" required="required"/>
			
			<sform:label path="password">Password</sform:label>
			<sform:input type="password" path="password" id="password" required="required"/>	
				
			<sform:label path="roleId">Role</sform:label>
				<sform:select path="roleId" class="form-control" id="roleId" >
				<sform:option value="1" label="USER"/>
            </sform:select>		
            <div><input type="submit" value="Sign Up"/></div>
        </sform:form>
    </body>
</html>