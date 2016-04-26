<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Reservations</title>
		<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
       
        <!-- CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="admin/table-view-reservation.css">
 		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
        <link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.uikit.js">
        <link rel="stylesheet" type="text/css" href="jquery/Responsive/css/responsive.dataTables.css">
		<script> 
			// used to load header and footer html
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// jQuery for datepicker plugin
			$(function() {
			    $( "#datepicker" ).datepicker({ maxDate: +14, dateFormat: "yy-mm-dd" });
			 });
			// jQuery for Datatable plugin for pagination
			$(document).ready( function () {
				$('table.display').DataTable({responsive: true});
  
			});
		</script> 
	</head>
	<body>
		<!-- Header -->
		<div id="header1"></div>
		<br><br><br>
		
		<!-- Content -->
		<div align="center">
			<h2>View All Reservations</h2><br>
			<h3>Please select a building and or date to search for reservations. <br> 
			To search by a user's MyID, please enter it in the search box.</h3><br>
		</div>
			<div class="centerdiv" id="search-container">
				<h2>${msg}</h2>
				<form name="adminViewForm" action="view-reservations?update" method="post">
					<p>Building ${buildings} <br> Date <input type="text" name="datepicker" id="datepicker">
			</div>
			<div align="center">
					<input align="center" class="btn btn-lg btn-red" name="enterBuilding" type="submit" value="Enter"></p>
				</form>
			</div>
			<br>
			<div>
			<h2>Admin Reservations for ${currentDateLong}</h2>
			<p>${adminReservations}</p>
			</div>
			<h2>Student Reservations for ${currentDateLong}</h2>
			<p>${userReservations}</p>
		</div>
			<br><br><br><br>
			<!-- End Content -->
			
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>