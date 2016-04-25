<%--
	@author: Ginger Nix
	This page is called when someone logs in and is banned from making reservations.
	They only have the option to log out
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Banned User</title>
				<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/dataTables.material.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>
		
		<!-- CSS -->
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.material.css">
		<link rel="stylesheet" type="text/css" href="jquery/Responsive/css/responsive.dataTables.css">
	
		<script> 
			$(function() {
				$("#header").load("user/userHeaderLoginError.html"); 
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
		<div id="margin-bottom-60" style="z-index:1;">
			<div id="header"></div>
			<br><br><br><br>
		</div>
		<br>
		<br>
		
		<!-- Content -->
		<div align="center">
			<div class="container margin-vert-60" style="z-index:1;">
				<div class="row">
			    	<h2 class="text-center margin-top-50">${user.userFirstName} 
			        ${user.userLastName} has been banned from making reservations.</h2>
			        
					<p class="text-center margin-top-30">You have been banned due to not checking into a reserved room for two or more times this period.</p>
			        <p class="text-center">For more information, call (706) 542-7000 or email <a href="mailto:learnctr@uga.edu?Subject=Banned%20from%20Making%20A%20Reservation" target="_top">learnctr@uga.edu</a></p>
			        
				</div>
			</div>
		</div>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>