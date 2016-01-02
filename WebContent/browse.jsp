<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Browse</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="browse.css">
		<script src="browse.js"></script>
		<script type="text/javascript" src="jquery/jquery-1.10.2.js"></script>
  		<script type="text/javascript" src="jquery/jquery-ui.js"></script>
  		
  		 <script>
	  		$(document).ready(function () {
	  		    $('table').accordion({header: '.room' });
	  		});
	  
  		</script>
	</head>
	<body>
		<form name="browseForm" action="Browse2" method="post">
			<h2>Please Select Building</h2>
			<p>${buildings}</p> 
			<input name="buildingSubmit" type="submit" value="Ok">
		</form>
		<form name="browseForm2" action="Browse3" method="post">
			<h2>Please Select Floor</h2>
			<p>${floor}</p>
				<input name="floorSubmit" type="submit" value="Ok">
		</form>
		<p>${table}</p>
		
	</body>
</html>