<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Banned User</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
			$(function() {
				$("#header").load("user/userHeaderLoginError.html"); 
				$("#footer").load("footer.html"); 
			});
		</script> 
	</head>
	<body>
		<div id="margin-bottom-60" style="z-index:1;">
		<div id="header"></div>
		<br>
			<br>
			<br>
			<br>
		</div>
		<br>
		<br>
		<div align="center">
			<div class="container margin-vert-60" style="z-index:1;">
				<div class="row">
			    	<h2 class="text-center margin-top-50">${user.userFirstName} 
			        ${user.userLastName} has been banned from making reservations.</h2>
			        
					<p class="text-center margin-top-30">You have been banned due to not checking into a reserved room for two or more times this period.</p>
			        <p class="text-center">For more information, call (706) 542-7000 or email <a href="mailto:learnctr@uga.edu?Subject=Banned%20from%20Making%20A%20Reservation" target="_top">learnctr@uga.edu</a></p>
			        
					</div>
			     </div>
			</div>
		<div id="footer"></div>
	</body>
</html>