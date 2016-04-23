/*package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingSelectQuery;
import helpers.HourCountSelectQuery;
import helpers.ReservationSelectQuery;
import helpers.RoomsSelectQuery;
import model.DateTimeConverter;
import model.DbConnect;
import model.TimeConverter;
import model.User;

*//**
 * Servlet implementation class GingerSearchReservations
 *//*
@WebServlet("/GingerSearchReservations")
public class GingerSearchReservationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
	
    *//**
     * @see HttpServlet#HttpServlet()
     *//*
    public GingerSearchReservationsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get current session
		session = request.getSession(false);

		// remove session variable
		session.removeAttribute("msg");
		session.removeAttribute("table");
		
		
		
		
		// get user parameters
		//int buildingID = Integer.parseInt(request.getParameter("buildingID"));  
		String startDateSlashed = request.getParameter("startDate");
		String endDateSlashed = request.getParameter("endDate");
		String startTime = request.getParameter("startTime");
		String endTime = "";
		String hourIncrement = request.getParameter("hourIncrement");
		String hourIncrementSelect = "";
		String msg = "";
		String table = "";

		// HARD CODED FOR TESTING
		int buildingID = 1;
		startDateSlashed = "04/19/2016";
		endDateSlashed = "04/25/2016";
		startTime = "10:00 AM";
		
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
	    SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
	    Date date = parseFormat.parse("10:30 PM");
	    System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
		
		//convert the time from user parameter to military time
		TimeConverter tc = new TimeConverter();
		String userStartTime24 = tc.convertTimeTo24(startTime);
		userStartTime24 = tc.convertTimeTo24(startTime);
	
		// convert slashed date to mysql format
		//Date TimeConverter dtc = new DateTimeConverter();
		//String startDateToFormat = dtc.slashDateConvert(startDateSlashed);
		//String endDateToFormat = dtc.slashDateConvert(endDateSlashed);
		 
		//System.out.println("GingerSearchResv:  start and end dates to format: " + startDateToFormat + " " + endDateToFormat);
			
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date searchStartDate = null;
		
		String startDateToFormat;
		try {
			searchStartDate = formatter.parse(startDateToFormat); //parses a string into a date
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date searchEndDate = null;
		
		String endDateToFormat;
		try {
			searchEndDate = formatter.parse(endDateToFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 System.out.println("GingerSearchResv:  searchStart and searchEnd date: " + searchStartDate.getTime());
			
		
		Calendar start = Calendar.getInstance();
		start.setTime(searchStartDate);
		
		Calendar end = Calendar.getInstance();
		end.setTime(searchEndDate);
		
		 System.out.println("GingerSearchResv:  start and end dates w parsing 1: " + start.getTime() + " " + end.getTime());
		 
		 end.add(Calendar.DAY_OF_YEAR, 1); // add one date to the end date to make sure it is processed in for loop
		 
		 System.out.println("GingerSearchResv:  start and end dates w parsing 2: " + start.getTime() + " " + end.getTime());
			
	 	Date dateToFormat = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String thisDate;
		String buildingStartTime = "";
		String buildingEndTime = "";
		BuildingSelectQuery bsq = new BuildingSelectQuery();
		
		//TEST
		Date date = start.getTime();
		dateToFormat = date; // get the date object of the day we are on
		thisDate = dateFormat.format(dateToFormat); //turn it into a string to compare	
		 
	    System.out.println("GingerSearchResv: thisDate =  " + thisDate);
	  
		// END TEST
		
		for (Date date = start.getTime();  start.before(end);  start.add(Calendar.DATE, 1), date = start.getTime()) {

		    System.out.println("GingerSearchResv: beg of for loop date =  " + date);
		    
		    dateToFormat = date; // get the date object of the day we are on
		    thisDate = dateFormat.format(dateToFormat); //turn it into a string to compare	
		    
		    System.out.println("GingerSearchResv: thisDate and building id =  " + thisDate + " " + buildingID);
		   
		    // get the building's hours for this date
			buildingStartTime = bsq.getBuildingStartTime (buildingID, thisDate); // get when this building's hours start
			buildingEndTime = bsq.getBuildingEndTime (buildingID, thisDate); // get when this building's hours start
			  
			System.out.println("GingerSearchResv: bldg start and end time = " + buildingStartTime + " " + buildingEndTime);
			
			// check to see if this building is open on this day
			if (Objects.equals(buildingStartTime, buildingEndTime)) { // the only time they should be the same is when they are closed
				//System.out.println("GingerSearchResv: bldg start = end time " + buildingStartTime + " " + buildingEndTime);
				continue; // the building is closed this day, so go to next day in the range
				
			}else{ // the building is open
			
				// if this is the first day in range or they are only searching on one day
				if (Objects.equals(startDateToFormat, endDateToFormat)) {
					System.out.println("GingerSearchResv: start date in range = this day in the loop");
				
					//the time to search = the user's input search time (startTime24) or the building 
					//open time (buildingStartTime), whichever is greater
					
					SimpleDateFormat timeParser = new SimpleDateFormat("HH:mm:ss");
					Date buildingStartTimeToCompare = timeParser.parse(buildingStartTime);
					Date buildingEndTimeToCompare = timeParser.parse(buildingEndTime);

					try {
					    Date userDate = parser.parse(someOtherDate);
					    if (userDate.after(ten) && userDate.before(eighteen)) {
					        ...
					    }    
					     
					    if (buildingStartTimeToCompare.before(when))
					        
					        
					    
					} catch (ParseException e) {
					    // Invalid date was entered
					}
					
					
					
				}

				
				
			}
			
		    // if the start date parameter = the day we are on in this loop, then it's the first day to search
		    // so figure out the time to start the search for this day which is the
		    // user's start time or the building's open time, whichever is latest
		    
		    // if the start date = the end date, the end time is the user's end time
		    // or the building's closing hour, which ever is first
		    // else the end time is the last hour of the building - the increment
		    
		    
		    
		}
		
		
		 * 
		 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
Date startDate = formatter.parse("2010-12-20");
Date endDate = formatter.parse("2010-12-26");

		 * Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);

		for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
		    // Do your job here with `date`.
		    System.out.println(date);
		}
		
		
		Date startDate = new Date();
		
		
		  Date today = new Date();                   
		  Date myDate = new Date(today.getYear(),today.getMonth()-1,today.getDay());
		  System.out.println("My Date is"+myDate);    
		  System.out.println("Today Date is"+today);
		  if (today.compareTo(myDate)<0)
		      System.out.println("Today Date is Lesser than my Date");
		  else if (today.compareTo(myDate)>0)
		      System.out.println("Today Date is Greater than my date"); 
		  else
		      System.out.println("Both Dates are equal"); 
		
		
		
		
		
	}
}*/