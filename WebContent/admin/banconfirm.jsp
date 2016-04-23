<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@ page import="model.Building" %>    
    
<!DOCTYPE html>
<html>
<head>
<%
   Building building = (Building) session.getAttribute("building");
    %>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Buildings</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 

			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
			});
		</script>
</head>
<body>
	<!-- Header -->
	<div id="header1"></div>
	<br><br><br><br><br><br><br>

	<!-- Content -->
	<div align="center">
		<p>${table}</p>
	</div>
	<!-- End Content -->
	
	<!-- Footer -->
	<div id="footer"></div>
</body>
</html>


