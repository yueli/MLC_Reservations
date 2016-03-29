<%-- ARE WE USING THIS JSP?? --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
    <%
    	String table = (String) request.getAttribute("table");
    %>
    
    
<!DOCTYPE html>
<html>
	<head>
	
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
			$(function() {
				$("#header1").load("./adminheader.html"); 
				$("#footer").load("../footer.html"); 
			});
		</script> 
	</head>
	<body>
		<div id="header1"></div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<%=table %>
	
	<div id="footer"></div>
	</body>
</html>