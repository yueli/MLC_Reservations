<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@ page import="model.Building" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
   Building building = (Building) session.getAttribute("building");
    %>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form name="updateForm" action=updatingbuilding method=get>

Building Name:<input type=text name=buildingName value="<%= building.getBuildingName()  %>" /><br>
Building Status:<input type=text name=buildingStatus value="<%= building.getBuildingStatus()  %>" /><br>
Building Cal Name:<input type=text name=buildingCalName value="<%= building.getBuildingCalName()  %>" /><br>
Building Cal URL:<input type=text name=buildingCalUrl value="<%= building.getBuildingCalUrl()  %>" /><br>
<p>
<input type=submit name=submit value='Update Building'>
</p>

</form>

</body>
</html>


