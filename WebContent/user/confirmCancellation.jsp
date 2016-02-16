<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String table = (String) request.getAttribute("table");
%> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MLC Reservations</title>
</head>
<body>
	<h1>Cancel Confirmation for
		${user.userFirstName} 
		${user.userLastName}
	</h1>
	
	<br />
	<%= table %>


</body>
</html>