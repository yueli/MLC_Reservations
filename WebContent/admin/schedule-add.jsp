<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Add Schedule Entry</title>
		<!-- JS -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script type="text/javascript" charset="utf8" src="jquery/Timepicker/jquery.timepicker.js"></script>
        
        <!-- CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
     	<link rel="stylesheet" type="text/css" href="jquery/Timepicker/jquery.timepicker.css">
		<script> 
			// used to load header and footer html
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
			});
			// function for Time Picker plugin
			$(function(){
				$('#startTime').timepicker( {useSelect: true} );
				$('#endTime').timepicker( {useSelect: true} );
			});	
			// jQuery for datepicker plugin
			$(function() {
			    $( "#startDate" ).datepicker({
			      defaultDate: "+1w",
			      changeMonth: true,
			      numberOfMonths: 3,
			      onClose: function( selectedDate ) {
			        $( "#endDate" ).datepicker( "option", "minDate", selectedDate );
			      }
			    });
			    $( "#endDate" ).datepicker({
			      defaultDate: "+1w",
			      changeMonth: true,
			      numberOfMonths: 3,
			      onClose: function( selectedDate ) {
			        $( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
			      }
		    	});
			});
			// function for back button
			function goBack() {
			    window.history.back();
			}
		</script> 
	</head>
	<body>
		<!-- Header -->
		<div id="header1"></div>
		<br><br><br>
		
		<!-- Content -->
		
			<h2 align="center">Add Building Hours</h2><br>
			<h3 align="center">Adding opening and closing hours for a building. <br>
			Please enter all information below to add hours to a building.</h3><br>
			<h3 align="center">${msg}</h3>
			<div class="col-md-3 col-md-offset-5 col-sm-offset-5">
			<form name="scheduleForm" action="new-schedule" method="post">
				${buildings}<br>
				Start Date: <input type="text" id="startDate" name="startDate" value=""><br>
				End Date: <input type="text" id="endDate" name="endDate" value=""><br>
				Start Time: <input type="text" id="startTime" name="startTime" value=""><br>
				End Time: <input type="text" id="endTime" name="endTime" value=""><br>
				Summary: <input type="text" name="summary" value=""><br><br>
			</div>
			<div class="col-md-12" align="center">
				<input class="btn btn-lg btn-red" name="scheduleAdd" type="submit" value="Enter"> <a href="Schedule"><button align="center" class="btn btn-lg btn-red" type="button" value="Schedule">View Building Hours</button></a> <br>
			</form>   
			</div>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>