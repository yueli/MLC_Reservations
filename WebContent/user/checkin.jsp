<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>

form {
   width: 300px;
    margin: 0 auto;
} 
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reservation Check In</title>
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
			// used to load header and footer html
			$(function() {
				$("#header1").load("user/userheader.html"); 
				$("#footer1").load("footer.html"); 
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
		<div id="header1"></div>

<div id="margin-bottom-60" style="z-index:1;">
<div id="header"></div>
<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
		<div align="center">
	    <form id="checkin" action="/CheckInServlet" method="POST">
            <input class="btn btn-lg btn-red" type="submit" value="Check into Room"><br>
    </form>
	</div>
</div>
<br>
<br>
<div id="footer1"></div>
</body>
</html>