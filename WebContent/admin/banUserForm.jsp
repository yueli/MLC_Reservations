<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
<script> 
	$(function() {
		$("#header").load("header.html"); 
		$("#footer").load("footer.html"); 
	});
</script> 
</head>
<body>
<div id="header"></div>

<p>
<input type="submit" name="submit" value="Ban">
</p><form name="addBan" action=addBan method=get>

<!-- Get banID -->



User ID:<input type=text name=userID value=""/><br>

<!-- Add Script to show student name -->

<!-- Get banID -->
<input type=text name=userID value=""/><br>

<!-- Get currentDate / banStart -->
<input type=text name=userID value=""/><br>

<!-- Get banEnd Date -->
<input type=text name=userID value=""/><br>

<!-- Get Ban Count -->


Reason for ban:<input type=text name=banReason value=""/><br>

<!-- Set Status to 1 -->



</form>
<div id="footer"></div>

</body>
</html>