<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="global.css">
		<title>MLC Study Room Reservations - Administration</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 

			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
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
	<div align="center">
		<h1>MLC Study Room Reservations - Administration</h1>
		
			${message}
			
		<form id="loginForm" action="AdminLoginController" method="POST">
			<h3>Log In</h3><br>
			<input type="text" name="username" placeholder="Username" required size=35><br>
			<input type="password" name="password" placeholder ="Password" required size=35><br><br>
			<input class="btn btn-lg btn-red" type="submit" value="LoginController"><br>
		</form>
		</div>
		<div id="footer"></div>
	</body>
</html>