<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Student Reservation</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
		
			$(function() {
				$("#header1").load("user/userheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// function for back button
			function goBack() {
			    window.history.back();
			}
		</script> 
	</head>
	<body>
		<div id="header1" style="z-index:1;"></div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<div class="clearfix"></div>
		<div id="container margin-vert-60" style="z-index:1;" >
		<div class="row text-center">
		<div id="reservation_div">	
			<form name="browse_reservation" id ="browse_reservation" action="BrowseConfirmation" method="post">
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
				<p>Please enter myID of secondary person:<br>
				<input type="text" name="secondary" id="secondary" placeholder="MyID" required></p>
				<button class="btn btn-lg btn-red" onclick="goBack()">Go Back</button><input class="btn btn-lg btn-red" type="submit" value="Make Reservation">
			</form>
		</div>	
		</div>
		</div>
		<div class="clearfix"></div>
		<div id="footer"></div>
	</body>
</html>