<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

    
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add A Building</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
			});
		</script>
</head>
<body>
	<div id="header1"></div>
	<br>
	<br>
	<br>
<div align="center">
<form name="addForm" action="addbuilding" method=get>
<h2>Add a Building</h2><br>
<h3>Please enter the information below to add a building.</h3><br>
Building Name:<input type=text name=buildingName value="" /><br>
Building Status:<input type=text name=buildingStatus value="" /><br>
Building Cal Name:<input type=text name=buildingCalName value="" /><br>
Building Cal URL:<input type=text name=buildingCalUrl value="" /><br><br>
<p>
<input class="btn btn-lg btn-red" type=submit name=submit value='Add Building'>
</p>

</form>
</div>
<div id="footer"></div>
</body>
</html>


