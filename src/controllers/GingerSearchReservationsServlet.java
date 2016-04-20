package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

/**
 * Servlet implementation class GingerSearchReservations
 */
@WebServlet("/GingerSearchReservations")
public class GingerSearchReservationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GingerSearchReservationsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get current session
		session = request.getSession(false);

		// remove session variable
		session.removeAttribute("msg");
		session.removeAttribute("table");
		
		// get user parameter
		String buildingID = request.getParameter("buildingID"); // get the value from request
		String startDateSlashed = request.getParameter("startDate");
		String endDateSlashed = request.getParameter("endDate");
		String startTime = request.getParameter("startTime");
		String endTime = "";
		String hourIncrement = request.getParameter("hourIncrement");
		String hourIncrementSelect = "";
		String msg = "";
		String table = "";

		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			startDate = formatter.parse("2010-12-20");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endDate = null;
		try {
			endDate = formatter.parse("2010-12-26");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		
		for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
		    System.out.println("GingerSearchResv: date =  " + date);
		}
		
		
		/*
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
		*/
		
		
		
		
	}
}