<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
		<link rel="stylesheet" type="text/css" href="global.css">
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
	<h2>Welcome, 
	${user.userFirstName}
	${user.userLastName},
	${user.userRecordID}!</h2>
		${message}
		<nav class="menu">
		    <ul class="active">
		        <li class="current-item"><a href="#">Home</a></li>
		        <li><a href="Browse">Browse</a></li>
		        <li><a href="#">Search</a></li>
		        <li><a href="ViewServlet">View and Cancel Reservations</a></li>
		        <li><a href="#">Check In</a></li>
		        <li><a href="#">Logout</a></li>
		        <li><a href="BanReadServlet">Admin Login</a></li>
		        <li><a href="BuildingListServlet">Building Login</a></li>
		    </ul>
		    <a class="toggle-nav" href="#">&#9776;</a>
		</nav>
	
		<form name="Logout" action="LoginController" method="get">
 			<input type="submit" name = "logout" value="Logout">
 		</form>
 		<div id="footer"></div>
 	</body>
</html>