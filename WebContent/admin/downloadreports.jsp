<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <!--@Author Victoria Chambers -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Download Reports</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
			$(function() {
				$("#header1").load("./adminheader.html"); 
				$("#footer").load("./footer.html"); 
			});
		</script> 
	</head>
	<body>
		<div id="header1"></div>
	<br>
	<br>
	<br>
	
	<h2 align="center">Download Reports</h2>
	
	<br>
	<div align="center">
	    <form id="downloadBanned" action="../DownloadReports" method="POST">
            <input class="btn btn-lg btn-red" type="submit" value="Download Banned Student List"><br>
    </form>
    
        <form id="loginForm" action="../DownloadReports" method="POST">
            <h2>Log In</h2>
            <input type="text" name="username" placeholder="Username" required size=35><br>
            <input type="password" name="password" placeholder ="Password" required size=35><br>
            <input type="submit" value="Check into Room"><br>
    </form>
    
    
	</div>
	<div id="footer"></div>
	</body>
</html>