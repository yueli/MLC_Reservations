<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Buildings</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		
		 <!-- Template CSS -->
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="user/browse.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
		        
		<script> 
			$(function() {
				$("#header").load("header.html"); 
				$("#footer").load("footer.html"); 
			});
			// jQuery for Datatable plugin for pagination
			$(document).ready( function () {
				$('table.display').DataTable();
		
			});
		
		</script> 
	</head>
	<body>
	<div id="header"></div>
	<a href='buildingform'>Add Building</a>
	
	<p>${table}</p>
	
	<div id="footer"></div>
	</body>
</html>