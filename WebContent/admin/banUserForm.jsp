<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ban User</title>
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
<br>
<br>
<br>
<p>

<div align="center">
</p><form name="addBan" action=addBan method=get>

<!-- Get banID -->



User ID:<input type=text name=userID value=""/><br><br>

<!-- Add Script to show student name -->

<!-- Get banID -->
<input type=text name=userID value=""/><br><br>

<!-- Get currentDate / banStart -->
<input type=text name=userID value=""/><br><br>

<!-- Get banEnd Date -->
<input type=text name=userID value=""/><br><br>

<!-- Get Ban Count -->


Reason for ban:<textarea rows="4" cols="50" type=text name=banReason value=""/>
</textarea><br><br>


<!-- Set Status to 1 -->



</form>
<input class="btn btn-lg btn-red col-med-3" type="submit" name="submit" value="Ban">
</div>
<div id="footer"></div>

</body>
</html>