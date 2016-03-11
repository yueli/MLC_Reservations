<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QR Room Login</title>
</head>
<body>
<h2>QR Room Login</h2>

Login in to check into:<br />

Building: <b><%= request.getParameter("building") %> </b><br />
Room: <b><%= request.getParameter("room") %></b><br />

<%
   String building = request.getParameter( "building" );
   session.setAttribute( "building", building );
   String room = request.getParameter( "room" );
   session.setAttribute( "room", room );
%>

    <form id="loginForm" action="../QRLoginController" method="POST">
            <h2>Log In</h2>
            <input type="text" name="username" placeholder="Username" required size=35><br>
            <input type="password" name="password" placeholder ="Password" required size=35><br>
            <input type="submit" value="Check into room"><br>
    </form>
    
    
</body>
</html>