<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Browse</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="student/browse.css">
		<link rel="stylesheet" type="text/css" href="student/styles.css">
		<script src="student/browse.js"></script>
		<script type="text/javascript" src="jquery/jquery-1.10.2.js"></script>
  		<script type="text/javascript" src="jquery/jquery-ui.js"></script>
  		 <script>
	  	    $(function() {
	  	   		$( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
	  	    	$( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
	  	  	});
  		</script>
	</head>
	<body>
		<nav class="menu">
		    <ul class="active">
		        <li class="current-item"><a href="#">Home</a></li>
		        <li><a href="Browse">Browse</a></li>
		        <li><a href="#">Search</a></li>
		        <li><a href="#">View & Cancel Reservations</a></li>
		        <li><a href="#">Check In</a></li>
		        <li><a href="#">Logout</a></li>
		    </ul>
		    <a class="toggle-nav" href="#">&#9776;</a>
		</nav>
		<form name="browseForm" action="Browse2" method="post">
			<h2>Please Select Building</h2>
			<p>${buildings}
			<input name="buildingSubmit" type="submit" value="Ok"></p> 
		</form>
		<form name="browseForm2" action="Browse3" method="post">
			<h2>Please Select Floor</h2>
			<p>${floor}</p>
		</form>
		<p>${table}</p>
	</body>
</html>