<!-- @Author Victoria Chambers-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MLC Reservations – Student View</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script> 
	<script> 
			// Importing Header and Footer
			$(function() {
				$("#header1").load("user/userheader.html"); 
				$("#footer1").load("footer.html"); 
			});
	</script>
</head>
<body>
		<div id="header1"></div>
<!-- === BEGIN CONTENT === -->
                    <div id="content">
                        <div class="container no-padding">
                            <div class="row">
                                <!-- Carousel Slideshow -->
                                <div id="carousel-example" class="carousel slide" data-ride="carousel">
                                    <!-- Carousel Indicators -->
                                    <ol class="carousel-indicators">
                                        <li data-target="#carousel-example" data-slide-to="0" class=""></li>
                                        <li data-target="#carousel-example" data-slide-to="1" class="active"></li>
                                        <li data-target="#carousel-example" data-slide-to="2" class=""></li>
                                        <li data-target="#carousel-example" data-slide-to="3" class=""></li>
                                    </ol>
                                    <div class="clearfix"></div>
                                    <!-- End Carousel Indicators -->
                                    <!-- Carousel Images -->
                                    <div class="carousel-inner">
                                        <div class="item">
                                            <img src="assets/img/fillers/mlc1.jpg">
                                        </div>
                                        <div class="item active">
                                            <img src="assets/img/fillers/mlc2.jpg">
                                        </div>
                                        <div class="item">
                                            <img src="assets/img/fillers/mlc3.jpg">
                                        </div>
                                        <div class="item">
                                            <img src="assets/img/fillers/mlc4.jpg">
                                        </div>
                                    </div>
                                    <!-- End Carousel Images -->
                                    <!--  
                                    <!-- Carousel Controls -->
                                    <a class="left carousel-control" href="UserHome#carousel-example" data-slide="prev">
                                        <span class="glyphicon glyphicon-chevron-left"></span>
                                    </a>
                                    <a class="right carousel-control" href="UserHome#carousel-example" data-slide="next">
                                        <span class="glyphicon glyphicon-chevron-right"></span>
                                    </a>
                                    <!-- End Carousel Controls -->
                    
                                </div>
                                <!-- End Carousel Slideshow -->
                            </div>
                        </div>
                        <div class="container background-gray-lighter">
                            <div class="row">
                                <h2 class="animate fadeIn text-center margin-top-50 animated">Welcome to the UGA Reservation System</h2>
                                <h4 class="animate fadeIn text-center margin-top-10 animated">Student View</h4>
                                <hr class="margin-top-15">
                                <p class="animate fadeIn text-center animated">This website will enable you to reserve a room in the Zell B. Miller Learning Center for study purposes or group meetings.</p>
                                <p class="text-center animate fadeInUp margin-bottom-50 animated">
                                    <a href="Browse"><button type="button" class="btn btn-lg btn-red">Reserve a Room</button></a>
                                </p>
                            </div>
                        </div>
                        <div class="container">
                            <div class="row margin-vert-30">
                                <!-- Main Column -->
                                <div class="col-md-9">
                                    <img class="visible-lg animate fadeInUp animated" style="bottom: -50px; position: relative; left: 85px; margin-top: -20px;" src="assets/img/mlc_head_terrace_unoccupied.jpg" alt="">
                                </div>
                                <!-- End Main Column -->
                                <!-- Side Column -->
                                <div class="col-md-3">
                                    <h3 class="title">Hours</h3>
                                    <h6 style="margin: 0;">Sunday – Thursday</h6>
                                    <h4 style="margin: 0;">7:00 am - 11:00 pm</h4>
                                    <p>
                                        <small>*Subject to change given holidays or special events</small>
                                    </p>
                                    <hr>
                                    <h6 style="margin: 0;">Saturday</h6>
                                    <h4 style="margin: 0;">7:00 am - 6:00 pm</h4>
                                    <p>
                                        <small>*Subject to change given holidays or special events</small>
                                    </p>
                                    <hr>
                                    <h6 style="margin: 0;">Finals Schedule</h6>
                                    <h4 style="margin: 0;">24 Hours</h4>
                                    
                                    <hr>
                                    <h6 style="margin: 0;">Spring Break</h6>
                                    <h4 style="margin: 0;">7:00 am – 5:00 pm</h4>
                                    
                                    <hr>
                                </div>
                                <!-- End Side Column -->
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
<!-- === END CONTENT === -->

<div id="footer1"></div>
</body>
</html>