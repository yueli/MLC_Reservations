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
	<div align="center">
		<h2>MLC Reservations Administration</h2>
	
		${message} <br />
		admin record ID = ${loggedInAdminUser.adminID } <br />
		my id = ${loggedInAdminUser.adminMyID} <br />
		first name = ${loggedInAdminUser.fname} <br />
		last name = ${loggedInAdminUser.lname} <br />
		role = ${loggedInAdminUser.role} <br />
		status = ${loggedInAdminUser.adminStatus} <br />
		

		
		<p>Here are your options:
			blah blah blah </p>
			
			<p><a href='AdminListServlet'>List Admin Users</a></p>
			
			
		<form name="Logout" action="AdminLoginController" method="get">
 			<input class="btn btn-lg btn-red" type="submit" name = "logout" value="Logout">
 		</form>
 		</div>
 		
		<div id="footer"></div>
	</body>

</html>