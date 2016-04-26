<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun    Used for browse function to make a reservation -->
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
		<!-- Header -->
		<div id="header1" style="z-index:1;"></div>
		<br><br><br><br>
		
		<!-- Content -->
		<div class="clearfix"></div>
		<div id="container margin-vert-60" style="z-index:1;" >
			<div class="row text-center">
				<div id="reservation_div">	
					<div align="center">
					<form name="browse_reservation" id ="browse_reservation" action="BrowseConfirmation" method="post">
						<h2>Make a Reservation</h2><br>
						<h3>Please fill out additional information below to make a reservation.</h3><br>
						<h3>${msg}</h3><br>
						<h3>Reservation for ${currentDateLong}</h3>
					</div>
					<div class="centerdiv">
						<p>Building:
							<input type="text" name="building" id="building" value="${buildingName}" disabled></p>				
						<p>Room Number:  
							<input type="text" name ="roomNumber" id="roomNumber" value="${roomNumber}" disabled></p>
						<p>Starting at: 
							<input type="text" name="startTime" id="startTime" value="${startTime}" disabled></p>
						<p>Reservation Length: ${incrementSelect}</p>
						<p>Please enter myID of secondary person:<br>
						<input type="text" name="secondary" id="secondary" placeholder="MyID" required></p>
					</div>
					<div align="center">
						<button class="btn btn-lg btn-red" onclick="goBack()">Cancel</button>    <input class="btn btn-lg btn-red" type="submit" value="Make Reservation">
					</div>
					</form>
				</div>	
			</div>
		</div>
		<div class="clearfix"></div>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>