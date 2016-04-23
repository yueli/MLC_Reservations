<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Buildings</title>
		<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
		 <!-- Template CSS -->
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.uikit.js">
		<link rel="stylesheet" type="text/css" href="jquery/Responsive/css/responsive.dataTables.css">
		<link rel="stylesheet" type="text/css" href="table.css">       
		<script> 
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
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
		<br><br><br>
		
		<!-- Content -->
		<div align="center">
			<h2>Buildings</h2><br>
			<h3>Listing of all buildings currently added to the site.  <br>
 				Edit buildings, hours, or rooms.</h3>
 
 		<div class="centerdiv">
    		<div>
				<br><br>
				<a href='buildingform'><button class='btn btn-lg btn-red' type='submit' value='EditHours'>Add Building</button></a>
				<br /><br />
			</div>
		</div>

			<p>${table}</p>
		</div>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>
