<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Browse</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="user/browse.css">
		<script src="user/browse.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	 	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
	 	<script>
	  	  	function submitform(i, room) {
	  	  		var fwdInt = i;
	  	  		var roomNumber = room;
	  	  		document.getElementById("fwdReserve" + fwdInt + roomNumber).submit();	
	  		}
	  	    $(function() {
	  	        $( "#tabs" ).tabs();
	  	    });
			$(function() {
				$("#header1").load("user/userheader.html"); 
				$("#footer").load("footer.html"); 
			});
		</script> 
	</head>
	<body>
		<!-- Header -->
		<div id="header1"></div>
		<br><br><br><br>
		
		<!-- Content -->
		<div align="center">
			${currentDay}
		</div>
		
		<div align="center">	
			<form name="browseForm" action="BrowseFloors" method="post">
				<h3>${buildingHeader}</h3>
				<p>${buildings}
					${buildingSubmit}</p> 
			</form>
			
			<form name="browseForm2" action="BrowseRooms" method="post">
				<h3>${floorHeader}</h3>
				<p>${floor}</p>
			</form>
			
		</div>
		
		<div align="center">
			<h3>${msg}</h3><br> 
			<p>${table}</p>
			<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		</div>
		<!-- End Content -->
		
		<!-- Footer -->
		<div id="footer"></div>
	</body>
</html>
