<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QR Confirmation</title>
</head>
<body>

<h3>QR Confirmation</h3>
<p>Are you sure you want
Building: <b><%= request.getParameter("building") %></b> <br />
Floor: <b><%= request.getParameter("floor") %></b><br />
Room: <b><%= request.getParameter("room") %></b><br />

Building: <% session.getAttribute("building"); %>

</body>
</html>