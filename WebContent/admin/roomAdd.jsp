<%-- 
	
	@author: Ginger Nix
	This page displays a form for the admin to add a room to a building.
	
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
<style>

form {
   width: 300px;
    margin: 0 auto;
} 
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rooms Add</title>
		<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
		
		<!-- CSS -->
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.uikit.js">
		<link rel="stylesheet" type="text/css" href="jquery/Responsive/css/responsive.dataTables.css">
		<link rel="stylesheet" type="text/css" href="table.css">
		<script> 
			// used to load header and footer html
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
			
			window.onload = function() {
				  var input = document.getElementById("roomNumber").focus();
			}
			
			
		</script> 
	</head>
	

<body>
	<!-- Header -->
	<div id="header1"></div>
	<br><br>	
	
	<!-- Content -->	
	<div align='center'>	
		${message}
		<br /><br />
		${table}
	</div>
	<!-- End Content -->
	
	<!-- Footer -->
	<div id="footer"></div>
</body>
</html>