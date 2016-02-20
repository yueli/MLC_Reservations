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
		        <li><a href="ViewServlet">View & Cancel Reservations</a></li>
		        <li><a href="#">Check In</a></li>
		        <li><a href="#">Logout</a></li>
		    </ul>
		    <a class="toggle-nav" href="#">&#9776;</a>
		</nav>
		<div id="reservation_div">	
			<form name="browse_reservation" id ="browse_reservation" action="BrowseConfirm" method="post">
				<p>Please fill out additional information below to make a reservation</p>
				<p>${msg}</p>
				<p>Reservation for ${currentDateLong}</p>
				<p>Building:
				<input type="text" name="building" id="building" value="${buildingName}" disabled></p>				
				<p>Room Number:  
				<input type="text" name ="roomNumber" id="roomNumber" value="${roomNumber}" disabled></p>
				<p>Starting at: 
				<input type="text" name="startTime" id="startTime" value="${startTime}" disabled></p>
				<p>Reservation Length: ${incrementSelect}</p>
				<p>Please enter email of secondary person:<br>
				<input type="email" name="secondary" id="secondary" placeholder="example@email.com" required></p>
				<input type="submit" value="Make Reservation">
			</form>
		</div>	
	</body>
</html>