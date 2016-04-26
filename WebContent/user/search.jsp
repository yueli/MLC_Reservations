<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun    Used for search function to make a reservation -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Student Search</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Timepicker/jquery.timepicker.js"></script>
		
        <!-- Template CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="user/search.css">
 		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
        <link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.uikit.js">
        <link rel="stylesheet" type="text/css" href="jquery/Responsive/css/responsive.dataTables.css">
        <link rel="stylesheet" type="text/css" href="jquery/Timepicker/jquery.timepicker.css">
        <script>
        	// used to load header and footer html
			$(function() {
				$("#header1").load("user/userheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// function for Time Picker plugin
			$(function(){
				$('#startTime').timepicker( { useSelect: true, 'timeFormat': 'h:ia', 'step': 60 });
				$('#endTime').timepicker( {useSelect: true, 'timeFormat': 'h:ia', 'step': 60 } );
			});	
			// jQuery for datepicker plugin
			$(function() {
			    $( "#startDate" ).datepicker({ minDate: 0, maxDate: +14 });
			    $( "#endDate" ).datepicker({ minDate: 0, maxDate: +14 });
			 });
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
		<br><br>
		
		<!-- Content -->
		<div align="center">
			<form name="userReserveForm" action="SearchReservations" method="post">
				<h2>Search Reservations</h2><br>
				<h3>Search for reservations up to 2 weeks from today.</h3><br>
				<h3>${msg}</h3><br>
		</div>
		<div class="centerdiv">
				Building ${buildings}<br>
				Start Date: <input type="text" id="startDate" name="startDate" value="${startDate}"><br>
				Start Time: <input id="startTime" name="startTime" value="${tc.convertTimeTo12(startTime).trim()}"><br>
				End Date: <input type="text" id="endDate" name="endDate" value="${endDate}"><br>
				End Time: <input id="endTime" name="endTime" value="${tc.convertTimeTo12(endTime).trim()}"><br>
				Reservation Length: ${hourIncrementSelect}<br><br>
				<input align="center" class="btn btn-lg btn-red" name="makeReservation" type="submit" value="Search"> 
			</div>
			</form>
		</div>
		
		<div align="center">
			<p>${table}</p>
		</div>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>
