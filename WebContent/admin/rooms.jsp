<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--  @author Ginger Nix -->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Add and Edit Rooms</title>
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
		<div id="header1"></div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<form name="buildingForRoomsForm" action="RoomsListServlet" method="post">
			<h2>Please Select Building</h2>
			<p>${buildings}
			<input name="buildingSelected" type="submit" value="Enter"></p> 
		</form>
		<div id="footer"></div>
	</body>
</html>