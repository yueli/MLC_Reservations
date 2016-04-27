<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Make Reservations</title>
		<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
        <script type="text/javascript" charset="utf8" src="jquery/Timepicker/jquery.timepicker.js"></script>
       
        <!-- CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="admin/table-view-reservation.css">
 		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
        <link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.uikit.js">
        <link rel="stylesheet" type="text/css" href="jquery/Responsive/css/responsive.dataTables.css">
        <link rel="stylesheet" type="text/css" href="jquery/Timepicker/jquery.timepicker.css">
		<script> 
			// used to load header and footer html
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// function for Time Picker plugin
			$(function(){
				$('#startTime').timepicker( { useSelect: true, 'timeFormat': 'h:ia', 'step': 60 });
				$('#endTime').timepicker( {useSelect: true, 'timeFormat': 'h:ia', 'step': 60 } );
			});	
			// jQuery for datepicker plugin
			$(function() {
			    $( "#startDate" ).datepicker({
			      minDate: 0,
			      defaultDate: null,
			      changeMonth: true,
			      numberOfMonths: 3,
			    });
			});
			// function for back button
			function goBack() {
			    window.history.back();
			}
			// jQuery for Datatable plugin for pagination, sort, and search
			$(document).ready( function () {
				$('table.mdl-data-table').DataTable( { responsive: true,
					columnDefs: [
				             {
				                 targets: [ 0, 1, 2 ],
				                 className: 'mdl-data-table__cell--non-numeric'
				             }
				         ]
				});
			});
		</script> 
	</head>
	<body>
		<!-- Header -->
		<div id="header1"></div>
		<br><br><br>
		
		<!-- Content -->
		<div align="center">
			<form name="adminReserveForm" action="admin-reservations" method="post">
				<h2>Make a Reservation</h2><br>
				<h3>Please enter all information below to make a reservation.</h3><br>
				<h3>${msg}</h3><br>
		</div>
			<div class="centerdiv">
				Building ${buildings}<br>
				Date: <input type="text" id="startDate" name="startDate" value="${startDate}"><br>
				Start Time: <input id="startTime" name="startTime" value="${tc.convertTimeTo12(startTime)}"><br>
				End Time: <input id="endTime" name="endTime" value="${tc.convertTimeTo12(endTime)}"><br>
				Reservation Name: <input type = "text" id="reserveName" name="reserveName" value="${reserveName}"><br><br>
			</div>
				<input align="center" class="btn btn-lg btn-red" name="makeReservation" type="submit" value="Enter"><br> <a href="AdminViewReservations"><button align="center" class="btn btn-lg btn-red" type="button" value="AdminViewReservations">Cancel</button></a>
			</form>
		
			<p>${table}</p>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>