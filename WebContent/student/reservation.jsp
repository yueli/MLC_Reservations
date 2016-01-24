<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Student Reservation</title>
	</head>
	<body>
		<nav class="menu">
		    <ul class="active">
		        <li class="current-item"><a href="#">Home</a></li>
		        <li><a href="Browse">Browse</a></li>
		        <li><a href="#">Search</a></li>
		        <li><a href="#">View & Cancel Reservations</a></li>
		        <li><a href="#">Check In</a></li>
		        <li><a href="#">Logout</a></li>
		    </ul>
		    <a class="toggle-nav" href="#">&#9776;</a>
		</nav>		
		<form name="Browse_Reservation" action="Reservation" method="post">
			<p>Reservation for room ${roomNumber} in ${building} on ${currentDate}
			<p>Starting At: ${startTime}</p>
			<p>Please Select Hour Increment:</p>
			<p>Please enter email of secondary person:
			<input type="text" name="secondary" required>
			<input type="submit" value="Make Reservation">
		</form>
	</body>
</html>