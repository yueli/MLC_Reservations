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
<form name="addForm" action="addbuilding" method=get>

Building Name:<input type=text name=buildingName value="" /><br>
Building Status:<input type=text name=buildingStatus value="" /><br>
Building Cal Name:<input type=text name=buildingCalName value="" /><br>
Building Cal URL:<input type=text name=buildingCalUrl value="" /><br>
<p>
<input type=submit name=submit value='Add Building'>
</p>

</form>
<div id="footer"></div>
</body>
</html>


