<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="user/styles.css">
		<title>MLC Study Room Reservations - Administration</title>
	</head>
	<body>
		<h1>MLC Study Room Reservations - Administration</h1>
		
			${message}
			
		<form id="loginForm" action="AdminLoginController" method="POST">
			<h2>Log In</h2>
			<input type="text" name="username" placeholder="Username" required size=35><br>
			<input type="password" name="password" placeholder ="Password" required size=35><br>
			<input type="submit" value="LoginController"><br>
		</form>
	</body>
</html>