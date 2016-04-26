<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <!--@Author Victoria Chambers -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Download Reports</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
		<script> 
		//Importing Header & Footer
			$(function() {
				$("#header1").load("admin/adminheader.html"); 
				$("#footer").load("footer.html"); 
			});
		</script> 
	</head>
	<body>
		<!-- Header -->
		<div id="header1"></div>
		<br><br><br>
		
		<!-- Content -->
		<h2 align="center">Download Reports</h2><br>
		<h3 align="center">Reports will download as an Excel (.xls) File</h3><br>
	
		<br>
		<!-- Buttons to call the downloads servlets -->
		<div align="center" class="padding-horiz-3">
		
	    <form name="banForm" id="banForm" action="DownloadReports" method="Post">
	    	<input type="hidden" name="bannedList" value="bannedList">
            <input class="btn btn-lg btn-red" type="submit" value="Banned Student List"><br><br>
    	</form> 
    	
    	<form name="adminForm" id="adminForm" action="DownloadReportsAdmin" method="Post">
	    	<input type="hidden" name="adminList" value="adminList">
            <input class="btn btn-lg btn-red" type="submit" value="Admin Stats"><br><br>
    	</form>
    	
    	<form name="scheduleForm" id="scheduleForm" action="DownloadReportsSchedule" method="Post">
	    	<input type="hidden" name="schedule" value="schedule">
            <input class="btn btn-lg btn-red" type="submit" value="Building Schedule List">
    	</form>
    	
		</div>
		<!-- End Content -->
		
		<!-- Footer -->
	<div id="footer"></div>
	</body>
</html>