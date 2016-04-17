<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Edit Building Schedule</title>
		<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Timepicker/jquery.timepicker.js"></script>
	   
	    <!-- CSS -->
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
	    <link rel="stylesheet" type="text/css" href="table.css">
		<link rel="stylesheet" type="text/css" href="jquery/DataTables/jquery.dataTables.css">
	    <link rel="stylesheet" type="text/css" href="jquery/DataTables/dataTables.uikit.js">
	    <link rel="stylesheet" type="text/css" href="jquery/Timepicker/jquery.timepicker.css">
	
		<script>
			// used to load header and footer html
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
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
			// function for Time Picker plugin
			$(function(){
				$('#startTime').timepicker( {useSelect: true} );
				$('#endTime').timepicker( {useSelect: true} );
			});	
		</script>
	</head>
	<body>
	<div id="header1"></div>
	<br>
	<br>
	<br>
		<div align="center">
			<h2>Edit Building Schedule</h2><br>
			<h3>Please update the information below. <br> If you want to cancel, please select "View Building Hours."</h3>
			<h2>${msg}</h2>
			<form name="scheduleEdit" id="scheduleEdit" method="post" action="schedule-edit">
				<h3>Edit schedule entry #${scheduleID} for ${buildingName}</h3>
				Date: <input type="text" id="datepicker" name="startDateEdit" value="${startDate}"><br>
				Start Time: <input type="text" id="startTime" name="startTimeEdit" value="${tc.convertTimeTo12(startTime)}"><br>
				End Time: <input type="text" id="endTime" name="endTimeEdit" value="${tc.convertTimeTo12(endTime)}"><br>
				Summary: <input type="text" name="summaryEdit" value="${summary}"><br><br>
				<a href="Schedule"><button class="btn btn-lg btn-red" type="button" value="View Building Hours">View Building Hours</button></a>   <input class="btn btn-lg btn-red" type="submit" value="Submit">
			</form>
		</div>
		<div id="footer"></div>
	</body>
</html>