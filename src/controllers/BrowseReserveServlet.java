package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.HourCountSelectQuery;
import model.DateTimeConverter;
import model.TimeConverter;
import model.User;

/**
 * @author Brian Olaogun
 * Servlet implementation class BrowseReserve
 */
@WebServlet({ "/BrowseReserve", "/BrowseReservation", "/Reservation", "/Browse_Reservation" })
public class BrowseReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseReserveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get the current session
		HttpSession session = request.getSession();
		
		// get parameters from request & session
		String startTime = (String) request.getParameter("startTime");
		String roomNumber = (String) request.getParameter("roomNumber");
		String currentDate = (String) request.getParameter("currentDate");
		String building = (String) session.getAttribute("building");
		
		// get user info from session
		User user = (User) session.getAttribute("user");
		int userID = user.getUserRecordID();
		
		// Check to see if user has reservations. Get sum of hour increment.
		HourCountSelectQuery hcsq = new HourCountSelectQuery();
		hcsq.doIncrementRead(userID);
		int incrementSum = hcsq.incrementResult();
		
		// build select
		String incrementSelect = "<select id='incrementSel' name='incrementSel'>";
		
		// set the forwarding url
		String url = "";
		
		if (incrementSum == 0){ // 0 = no reservations made
			incrementSelect += "<option value='1'>1</option>";
			incrementSelect += "<option value='2'>2</option></select>";
			url = "user/reservation.jsp";
		} else if (incrementSum == 1){ // only 1 1-hour reservation was made
			incrementSelect += "<option value='1'>1</option></select>";
			url = "user/reservation.jsp";
		} else if (incrementSum >= 2){ // either 2 1-hour reservations or 1 2-hour reservation was made
			// user has at 2 hour max for the day
			url = "user/home.jsp"; // TODO change this because it shouldn't be this page
		} else {
			
		}
		
		// change date into long format
		DateTimeConverter dtc = new DateTimeConverter();
		currentDate = dtc.convertDateLong(currentDate); // convert date to long format: ex. February 12, 2016
		
		// change time to 12 hour format
		TimeConverter tc = new TimeConverter();
		startTime = tc.convertTimeTo12(startTime);
		
		// TODO query to see if user has any reservations for current day
		
		// set session attributes
		session.setAttribute("startTime", startTime);
		session.setAttribute("roomNumber", roomNumber);
		session.setAttribute("currentDate", currentDate);
		session.setAttribute("building", building);
		session.setAttribute("incrementSelect", incrementSelect);
		
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
				
	}

}
