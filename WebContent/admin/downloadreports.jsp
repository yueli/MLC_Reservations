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
		<div id="header1"></div>
	<br>
	<br>
	<br>
	
	<h2 align="center">Download Reports</h2>
	
	<br>
	<!-- Button to call the downloads servlet -->
	<div align="center">
	
	    <form id="downloadBanned" action="?action=bannedreport" method="Post">
            <input class="btn btn-lg btn-red" type="submit" value="Download Banned Student List"><br>
        
        </form>
<!--           
          <form id="downloadBannedGinger" action="?action=bannedreportGinger" method="GET">
            <input class="btn btn-lg btn-red" type="submit" value="Ginger Download Banned Student List"><br>
              
            
	    <form id="downloadBannedGinger" action="?action=bannedreport" method="GET">
            <input class="btn btn-lg btn-red" type="submit" value="Download Banned Student List"><br>
        </form>
 -->
	</div>
	<div id="footer"></div>
	</body>
</html>