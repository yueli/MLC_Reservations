<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Student Check-In</title>
		<link rel="stylesheet" type="text/css" href="student/styles.css">
		<script type="text/javascript" src="jquery/jquery-1.10.2.js"></script>
	  	<script type="text/javascript" src="jquery/jquery-ui.js"></script>
	  	<script type="text/javascript" src="jquery/jquery-ui.min.js"></script>
		<script type="text/javascript">
		(document).ready(function() {
		    ('.toggle-nav').click(function(e) {
		        (this).toggleClass('active');
		        ('.menu ul').toggleClass('active');
		 
		        e.preventDefault();
		    });
		});
		</script>
	</head>
	<body>
	<h2>Welcome, ${user.userName}!</h2>
		<nav class="menu">
		    <ul class="active">
		        <li class="Home"><a href="#">Home</a></li>
		        <li><a href="Browse">Browse</a></li>
		        <li><a href="#">Search</a></li>
		        <li><a href="#">View and Cancel Reservations</a></li>
		        <li><a href="current-item">Check In</a></li>
		        <li><a href="#">Logout</a></li>
		        <li><a href="BanReadServlet">Admin Login</a></li>
		        
		        
		    </ul>
		    <a class="toggle-nav" href="#">&#9776;</a>
		</nav>
	</body>
	<form name="Check_In" action="Check In" method="post">
		<h3>Room Check-In Page</h3><br>
		<h4>You may check into your room beginning at the top of the hour until 10 minutes after.</h4>
		<br>
		<h4>Current Reservations</h4>
		<h5>${currentDate} from ${reservationTime} ${roomNumber} in ${building} – ${secondary} </h5>
		<input type="submit" value="Check In"> <a href="home.jsp" id="button">Home</a>
		</form>
 		
</html>