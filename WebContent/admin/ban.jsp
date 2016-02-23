 <%@ page import="model.Banned" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Ban</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
			$(function() {
				$("#header").load("header.html"); 
				$("#footer").load("footer.html"); 
			});
		</script> 
	</head>
	<body>
		<div id="header"></div>
		<form name="banForm" action=ban method=get>
		<!-- Ban ID -->
		Student ID:<input type=text name=studentID value=""/>
		
		<!-- Admin ID -->
		<!-- Ban Start, Current Time -->
		<!-- Ban Start-->
		<!-- Ban End-->
		<!-- Ban Status-->
		
		<p>
		<input type=submit name=submit value='Ban Now!'>
		</p>
		
		</form>
		<div id="footer"></div>
	</body>
</html>