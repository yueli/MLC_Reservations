<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String table = (String) request.getAttribute("table");
%> 


<!DOCTYPE html>
<html>
<<<<<<< HEAD
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MLC Reservations</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
<script> 

	$(function() {
		$("#header").load("./userheader.html"); 
		$("#footer").load("../footer.html"); 
	});
</script> 
</head>
<body>
<div id="header" style="z-index:1;"></div>
<br>
<br>
<div class="container margin-vert-60" style="z-index:1;">
	<div class="row text-center">
	<h1>Cancel Confirmation for
		${user.userFirstName} 
		${user.userLastName}
	</h1>
	
	<p>Are you sure you want to cancel this reservation?</p>
	
	<br />
	<%= table %>
</div>
</div>
<div id="footer"></div>

</body>
=======
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MLC Reservations</title>
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
		<h1>Cancel Confirmation for
			${user.userFirstName} 
			${user.userLastName}
		</h1>
		
		<p>Are you sure you want to cancel this reservation?</p>
		
		<br />
		<%= table %>
		<div id="footer"></div>
	</body>
>>>>>>> origin/master
</html>