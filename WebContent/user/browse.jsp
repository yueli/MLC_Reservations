<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
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
				$("#header").load("userheader.html"); 
				$("#footer").load("footer.html"); 
			});
		</script> 
	</head>
	<body>
		<div id="header"></div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<form name="browseForm" action="BrowseFloors" method="post">
			<h2>Please Select Building</h2>
			<p>${buildings}
			<input name="enterBuilding" type="submit" value="Enter"></p> 
		</form>
		<form name="browseForm2" action="BrowseRooms" method="post">
			<h2>Please Select Floor</h2>
			<p>${floor}</p>
		</form>
		<h3>${msg}</h3> <!-- If the user has reserved a room for a total of 2 hours and makes another reservation, a message will display. -->
		<p>${table}</p>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<div id="footer"></div>
	</body>
</html>