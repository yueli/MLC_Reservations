<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<!-- @author Brian Olaogun    Used to confirm a reservation is made for browse -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Reservation Confirmation</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script> 
			$(function() {
				$("#header").load("user/userheader.html"); 
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
		<div align="center">
			<p>${msg}</p>
		</div>
		<div id="footer"></div>
	</body>
</html>