<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Reservations</title>
	</head>
	<body>
		<form name="adminReserveForm" action="BrowseFloors" method="post">
			<h2>Please Select a Building</h2>
			<p>${buildings}
			<input name="enterBuilding" type="submit" value="Enter"></p> 
		</form>
		<form name="adminReserveForm2" action="BrowseRooms" method="post">
			<h2>Floor</h2>
			<p>${floor}</p>
		</form>
	</body>
</html>