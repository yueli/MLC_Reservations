<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Building Schedule</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/DataTables/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf8" src="jquery/Responsive/js/dataTables.responsive.js"></script>

        <!-- Template CSS -->
  		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="admin/table-schedule.css">
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
			    $( "#from" ).datepicker({
			      defaultDate: "+1w",
			      changeMonth: true,
			      numberOfMonths: 3,
			      onClose: function( selectedDate ) {
			        $( "#to" ).datepicker( "option", "minDate", selectedDate );
			      }
			    });
			    $( "#to" ).datepicker({
			      defaultDate: "+1w",
			      changeMonth: true,
			      numberOfMonths: 3,
			      onClose: function( selectedDate ) {
			        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
			      }
		    	});
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
			<h2>Viewing Building Hours</h2><br>
			<h3>${scheduleHeader}<br>
			You can also edit a building's hours or add hours to a building.</h3><br>
			<h3>${msg}</h3><br>
			<h2>Hours for ${buildingName}</h2>
		</div>
		<div class="col-md-3 col-md-offset-5 col-sm-offset-5">
			<form name="BuildingSelect" action="Schedule" method="post"> <br>
					<p>${buildings}<br>
					<label for="from">From </label> <input type="text" id="from" name="from" placeholder="mm/dd/yyyy"> <br>
					<label for="to">To </label> <input type="text" id="to" name="to" placeholder="mm/dd/yyyy"><br>
		</div>
		<div class="col-md-12" align="center">
					<input class="btn btn-lg btn-red" name="enterBuilding" type="submit" value="Enter"> <br><br>
		</div>
			</form>
		
		<div class="col-md-12" align="center">	
			<a href="buildings"><button class="btn btn-lg btn-red" type="submit" value="Back to Buildings">Back To Buildings</button></a>  <a href="add-schedule"><button class="btn btn-lg btn-red" type="submit" value="add-schedule">Add Building Hours</button></a>
		</div>
		<p>${schedule}</p>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>