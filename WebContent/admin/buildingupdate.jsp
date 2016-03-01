<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@ page import="model.Building" %>    
    
<!DOCTYPE html>
<html>
<head>

<%
   Building building = (Building) session.getAttribute("building");
    %>

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
<form name="updateForm" action=updatingbuilding method=get>

Building Name:<input type=text name=buildingName value="<%= building.getBuildingName()  %>" /><br>
Building Status:<input type=text name=buildingStatus value="<%= building.getBuildingStatus()  %>" /><br>
Building Cal Name:<input type=text name=buildingCalName value="<%= building.getBuildingCalName()  %>" /><br>
Building Cal URL:<input type=text name=buildingCalUrl value="<%= building.getBuildingCalUrl()  %>" /><br>
<p>
<input type=submit name=submit value='Update Building'>
</p>

</form>
<div id="footer"></div>
</body>
</html>


