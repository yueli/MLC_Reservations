<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String table = (String) request.getAttribute("table");
%> 
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="WEB-INF/global.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>View Home Page</title>
	</head>
	<body>
		<h1>View Home Page</h1>
		<h2>Welcome, 
			${user.userFirstName} 
			${user.userLastName}
			${user.userRecordID}!</h2>
			
			<%= table %>
	</body>
</html>