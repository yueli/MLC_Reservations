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
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/dataTables.material.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
       <script type="text/javascript" charset="utf8" src="jquery/Timepicker/jquery.timepicker.js"></script>
       
        <!-- CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="admin/table-view-reservation.css">
 		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
        <!--link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.uikit.js"-->
        <link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.material.css">
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
				Building ${buildings}<br>
				Start Date: <input type="text" id="startDate" name="startDate" value="${startDate}"><br>
				End Date: <input type="text" id="endDate" name="endDate" value="${endDate}"><br>
				Start Time: <input type="text" id="startTime" name="startTime" value="${tc.convertTimeTo12(startTime)}"><br>
				End Time: <input type="text" id="endTime" name="endTime" value="${tc.convertTimeTo12(endTime)}"><br>
				Reservation Name: <input type = "text" id="reserveName" name="reserveName" value="${reserveName}">
				<input name="makeReservation" type="submit" value="Enter"> 
			</form>
			<p>${table}</p>
		</div>
		<div id="footer"></div>
	</body>
</html>