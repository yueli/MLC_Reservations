<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QR Error Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
			$(function() {
				$("#header").load("./userheader.html"); 
				$("#footer").load("../footer.html"); 
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
<h1>QR Error Page</h1>

${message}
<div id="footer"></div>
</body>
</html>