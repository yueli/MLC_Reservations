<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Banned User</title>
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
		<h1>${user.userFirstName} 
			${user.userLastName} 
			has been banned from making reservations</h1>
		<br />
		<p>You have been banned due to not checking into a reserved room for two or more times this period.</p>
		<br />
		<p>For more information, contact XXXXXXX or XXXXXX<p>
		<br />
		
		<form name="Logout" action="LoginController" method="get">
			<input type="submit" name = "logout" value="Logout">
		</form>
		<div id="footer"></div>
	</body>
</html>