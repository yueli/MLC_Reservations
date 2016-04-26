<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
		<link rel="stylesheet" type="text/css" href="student/styles.css">
		<script type="text/javascript" src="jquery/jquery-1.10.2.js"></script>
	  	<script type="text/javascript" src="jquery/jquery-ui.js"></script>
	  	<script type="text/javascript" src="jquery/jquery-ui.min.js"></script>
		<script type="text/javascript">
		(document).ready(function() {
		    ('.toggle-nav').click(function(e) {
		        (this).toggleClass('active');
		        ('.menu ul').toggleClass('active');
		 
		        e.preventDefault();
		    });
		});
		</script>
	</head>
	<body>
	<h2>Welcome, ${user.userName}!</h2>
		<nav class="menu">
		    <ul class="active">
		        <li class="current-item"><a href="#">Home</a></li>
		        <li><a href="Browse">Browse</a></li>
		        <li><a href="#">Search</a></li>
		        <li><a href="#">View and Cancel Reservations</a></li>
		        <li><a href="#">Check In</a></li>
		        <li><a href="#">Logout</a></li>
		        <li><a href="BanReadServlet">Admin Login</a></li>
		        
		        
		    </ul>
		    <a class="toggle-nav" href="#">&#9776;</a>
		</nav>
	</body>
	
		<form name="Logout" action="LoginController" method="get">
 		<input type="submit" name = "logout" value="Logout">
 		</form>
 		
</html>