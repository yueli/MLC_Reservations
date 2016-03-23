<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Make Reservations</title>
		<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script type="text/javascript" charset="utf8" src="jquery/Timepicker/jquery.timepicker.js"></script>
        
        <!-- CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
     	<link rel="stylesheet" type="text/css" href="jquery/Timepicker/jquery.timepicker.css">
		<script> 
			// used to load header and footer html
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// function for Time Picker plugin
			$(function(){
				$('#startTime').timepicker();
				$('#endTime').timepicker();
			});	
			// jQuery for datepicker plugin
			$(function() {
			    $( "#startDate" ).datepicker({
			      defaultDate: "+1w",
			      changeMonth: true,
			      numberOfMonths: 3,
			      onClose: function( selectedDate ) {
			        $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
			      }
			    });
			    $( "#endDate" ).datepicker({
			      defaultDate: "+1w",
			      changeMonth: true,
			      numberOfMonths: 3,
			      onClose: function( selectedDate ) {
			        $( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
			      }
		    	});
			});
			// function for back button
			function goBack() {
			    window.history.back();
			}
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
			<form name="adminReserveForm" action="admin-reservations" method="post">
				<h2>Please select a building, date, start and end time for your reservation.</h2>
				Building ${buildings}
				Start Date: <input type="text" id="startDate" name="startDate" value="${startDate}"><br>
				End Date: <input type="text" id="endDate" name="endDate" value="${endDate}"><br>
				Start Time: <input type="text" id="startTime" name="startTime" value="${tc.convertTimeTo12(startTime)}"><br>
				End Time: <input type="text" id="endTime" name="endTime" value="${tc.convertTimeTo12(endTime)}"><br>
				<input name="makeReservation" type="submit" value="Enter"> 

			</form>
			<p>${table}</p>
		</div>
		<div id="footer"></div>
	</body>
</html>