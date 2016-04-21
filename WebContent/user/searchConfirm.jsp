<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun    Used for search function to make a reservation -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Search Reservation - Make Reservation</title>
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
		<div class="clearfix"></div>
		<div id="container margin-vert-60" style="z-index:1;" >
		<div class="row text-center">
		<div id="reservation_div">	
			<form name="searchConfirmForm" id ="searchConfirmForm" action="SearchReservation-Confirm" method="post">
				<h2>Make a Reservation</h2><br>
				<h3>Please fill out additional information below to make a reservation.</h3><br>
				<h3>${msg}</h3>
				<h3>Reservation for ${dtc.convertDateLong(startDate)}</h3><br>
				<p>Building: ${buildingName}</p>				
				<p>Room Number:  
				<input type="text" name ="roomNumber" id="roomNumber" value="${roomNumber}" disabled></p>
				<p>Starting at: 
				<input type="text" name="startTime" id="startTime" value="${tc.convertTimeTo12(startTime)}" disabled></p>
				<p>Ending at: 
				<input type="text" name="endTime" id="endTime" value="${tc.convertTimeTo12(endTime)}" disabled></p>
				<p>Reservation Length: ${reservationLength}</p>
				<p>Please enter myID of secondary person:<br>
				<input type="text" name="secondary" id="secondary" placeholder="MyID" required></p>
				<button class="btn btn-lg btn-red" onclick="goBack()">Cancel</button>    <input class="btn btn-lg btn-red" type="submit" value="Make Reservation">
			</form>
		</div>	
		</div>
		</div>
		<div class="clearfix"></div>
		<div id="footer"></div>
	</body>
</html>