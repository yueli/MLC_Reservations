<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Edit Schedule</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
	<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
	<script type="text/javascript" charset="utf8" src="jquery/DataTables/dataTables.material.js"></script>
	<!-- Meta -->
       
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   
    <!-- Template CSS -->
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="user/browse.css">
	<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.material.css">

	<script>
		// used to load header and footer html
		$(function() {
			$("#header").load("header.html"); 
			$("#footer").load("footer.html"); 
		});
		// function for back button
		function goBack() {
		    window.history.back();
		}
		// function for jQuery datepicker plugin
		$(function() {
		    $( "#datepicker" ).datepicker({dateFormat: "yy-mm-dd"});
		});
	</script>
	</head>
	<body>
		<div id="header"></div>
		<h2>${msg}</h2>
		<form name="scheduleEdit" id="scheduleEdit" method="post" action="schedule-edit">
			Date <input type="text" id="datepicker" name="startDateEdit" value="${startDate}"><br>
			Start Time <input type="text" name="startTimeEdit" value="${startTime}"><br>
			End Time <input type="text" name="endTimeEdit" value="${endTime}"><br>
			Summary <input type="text" name="summaryEdit" value="${summary}"><br>
			<button onclick="goBack()">Go Back</button><input type="submit" value="Submit">
		</form>
		<div id="footer"></div>
	</body>
</html>