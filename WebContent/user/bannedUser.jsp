<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Banned User</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
<script> 
	$(function() {
		$("#header").load("./userheader.html"); 
		$("#footer").load("../footer.html"); 
	});
</script> 
</head>
<body>
<div id="margin-bottom-60" style="z-index:1;">
<div id="header"></div>
</div>
<br>
<br>

<div class="container margin-vert-60" style="z-index:1;">
	<div class="row">
    	<h2 class="text-center margin-top-50">${user.userFirstName} 
        ${user.userLastName} has been banned from making reservations</h2>
        
		<p class="text-center margin-top-30">You have been banned due to not checking into a reserved room for two or more times this period</p>
        <p class="text-center">For more information, contact XXXXXXX or XXXXXX</p>
        
        <div align="center"	>
        	<form name="Logout" action="LoginController" method="get">
			<input class="btn btn-lg btn-red" type="submit" name = "logout" value="Logout">
			</form>
		</div>
     </div>
</div>
<div id="footer"></div>
</body>
</html>