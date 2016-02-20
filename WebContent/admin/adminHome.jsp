<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Admin Home</title>
		<link rel="stylesheet" type="text/css" href="user/styles.css">
	</head>
	<body>
	<h2>MLC Reservations Administration</h2>
	
		${message}
		adminID = ${adminUser.adminID }
		my id = ${adminUser.adminMyID}
		first name = ${adminUser.fname}
		last name = ${adminUser.lname}
		role = ${adminUser.role}
		status = ${adminUser.adminStatus}
		can't be deleted = ${adminUser.cantBeDeleted}
		
		
		

		
		<p>Here are your options:
			blah blah blah </p>
			
		<form name="Logout" action="LoginController" method="get">
 		<input type="submit" name = "logout" value="Logout">
 		</form>
 		
		
	</body>

</html>