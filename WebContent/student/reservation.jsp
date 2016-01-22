<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Student Reservation</title>
	</head>
	<body>
		
		<form name="Browse_Reservation" action="Reservation" method="post">
		<p>Reservation for ${currentDate}
		<p>Selected Start Time: ${startTime}</p>
		<p>Selected Room Number: ${roomNumber}</p>
		<p>Please Select Hour Increment:</p>
		<p>Please enter email of secondary person:
		<input type="text" name="secondary" required>
		
		</form>
	</body>
</html>