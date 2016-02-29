<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Reservations</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
		<!-- Meta -->
        
        <meta name="description" content="">
        <meta name="author" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <!-- Favicon -->
        <link href="favicon.ico" rel="shortcut icon">
        <!-- Bootstrap Core CSS -->
        <link rel="stylesheet" href="assets/css/bootstrap.css">
        <!-- Template CSS -->
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="assets/css/font-awesome.css">
        <link rel="stylesheet" href="assets/css/nexus.css">
        <link rel="stylesheet" href="assets/css/responsive.css">
        <link rel="stylesheet" href="assets/css/custom.css">
        <link rel="stylesheet" type="text/css" href="user/browse.css">
        <!-- Google Fonts-->
        <link href="assets/css" rel="stylesheet" type="text/css">
        <link href="assets/css(1)" rel="stylesheet" type="text/css">
        
		<script> 
			$(function() {
				$("#header").load("header.html"); 
				$("#footer").load("footer.html"); 
			});
			$(function() {
			    $( "#datepicker" ).datepicker({ minDate: -1, maxDate: +14, dateFormat: "yy-mm-dd" });
			 });
		</script> 
	</head>
	<body>
		<div id="header"></div>
			<br><br><br><br><br>
			<div id="search-container">
				<form name="adminViewForm" action="view-reservations?building?date" method="post">
					<p>Building ${buildings}<input name="enterBuilding" type="submit" value="Enter"></p>
				</form>
				<form name="adminViewForm2" action="view-reservations?building?date" method="post">
					<p>Date <input type="text" name="datepicker" id="datepicker"><input name="dateSort" type="submit" value="Enter"></p>
				</form>
			</div>
			<h2>Admin Reservations for ${currentDateLong}</h2>
			<p>${adminReservations}</p>
			<h2>Student Reservations for ${currentDateLong}</h2>
			<p>${userReservations}</p>
			<br><br><br><br>
		<div id="footer"></div>
	</body>
</html>