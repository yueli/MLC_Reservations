<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MLC Reservations</title>
</head>
<body>
 <h1>MLC Reservations</h1>

 <form name="loginForm" action="LoginServlet" method="post">
 	<label>
 	UGA MyID (this is your id and NOT the number from the back of your UGA ID Card):
 	</label>
     <input type="text" name="cardNumber" /><br />
    <label>
 	Password:
 	</label>
     <input type="text" name="password" /><br />
     <input type="submit" name="submit" value="Login">
  </form>



</body>
</html>