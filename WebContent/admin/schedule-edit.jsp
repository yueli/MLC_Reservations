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
				$('#endTime').timepicker( {useSelect: true, 'noneOption': [{'label': '11:59pm', 'className': '11:59pm', 'value': '11:59pm'}]});
				                                                               
				                                                               
				                                                               
				                                                           
			});	
		</script>
	</head>
	<body>
		<!-- Header -->
		<div id="header1"></div>
		<br><br><br>
		
		<!-- Content -->
		<div align="center">
			<h2>Edit Building Schedule</h2><br>
			<h3>Please update the information below. <br> If you want to cancel, please select "View Building Hours."</h3><br>
			<form name="scheduleEdit" id="scheduleEdit" method="post" action="schedule-confirm">
				<h3>Edit schedule entry for ${buildingName}</h3><br>
		</div>
		<div class="col-md-3 col-md-offset-5 col-sm-offset-5">
				<h3color="#cc0033">${msg}</h3><br>
				Date: <input type="text" id="datepicker" name="startDateEdit" value="${startDate}" disabled><br>
				Start Time: <input type="text" id="startTime" name="startTimeEdit" value="${tc.convertTimeTo12(startTime)}"><br>
				End Time: <input type="text" id="endTime" name="endTimeEdit" value="${tc.convertTimeTo12(endTime)}"><br>
				Summary: <input type="text" name="summaryEdit" value="${summary}"><br><br>
		</div>
		<div class="col-md-12" align="center">
				<input class="btn btn-lg btn-red" type="submit" value="Submit"> <a href="Schedule"><button align="center" class="btn btn-lg btn-red" type="button" value="View Building Hours">View Building Hours</button></a>   
		</div>
			</form>
		
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>