package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingSelectQuery;
import helpers.HourCountSelectQuery;
import model.DateTimeConverter;
import model.DbConnect;
import model.TimeConverter;
import model.User;

/**
 * @author Brian Olaogun
 * Servlet implementation class BrowseReserve
 */
@WebServlet({ "/BrowseReserve", "/BrowseReservation" })
public class BrowseReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseReserveServlet() {
        super();
        
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
		session = request.getSession(false);
		
		if(session != null){
			// get user info from session
			User user = (User) session.getAttribute("user");
			
			if(user != null) { // run code if user object is not null
				
				// get parameters from request & session
				String roomID = (String) request.getParameter("roomID");
				String startTime = (String) request.getParameter("startTime");
				String roomNumber = (String) request.getParameter("roomNumber");
				String currentDate = (String) request.getParameter("currentDate");
				int buildingID = (Integer) session.getAttribute("building");
				
				
				int userID = user.getUserRecordID();
				System.out.println("USER INFO FROM BROWSE RESERVE SERVLET: " + user.getUserRecordID() + ", " + user.getMyID() + ", " + user.getLastLogin());
				// get the building name from ID
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				String buildingName = bsq.buildingName(buildingID);
				
				//------------------------------------------------------------------//
				// Check to see if user has reservations. Get sum of hour increment.
				//------------------------------------------------------------------//
				HourCountSelectQuery hcsq = new HourCountSelectQuery();
				hcsq.doIncrementRead(userID);
				int incrementSum = hcsq.incrementResult();
				System.out.println("PRINT OF THE HOUR INCREMENT SUM in BrowseReserveServlet: " + incrementSum);
				
				// build HTML select
				String incrementSelect = "<select id='userIncrementSelected' name='userIncrementSelected'>";
				
				// set message and the forwarding URL
				String msg = "";
				
				
				if (incrementSum == 0){ 
					// 0 = no reservations made
					incrementSelect += "<option value='1'>1</option>";
					incrementSelect += "<option value='2'>2</option></select>";
					url = "user/reservation.jsp";
					
				} else if (incrementSum == 1){ 
					// only 1 1-hour reservation was made
					incrementSelect += "<option value='1'>1</option></select>";
					url = "user/reservation.jsp";
					
				} else if (incrementSum >= 2){ 
					// either 2 1-hour reservations or 1 2-hour reservation was made
					// user has reached 2 hour max for the day
					msg = "You have exceeded the maximum hours (2) for reservations for today.  "
							+ "To make a reservation for another time for today, please cancel one of your current reservations first.";
					url = "user/browse.jsp"; 
					
				} 
				
				// change date into long format
				DateTimeConverter dtc = new DateTimeConverter();
				String currentDateLong = dtc.convertDateLong(currentDate); // convert date to long format: ex. February 12, 2016
				
				// change time to 12 hour format
				TimeConverter tc = new TimeConverter();
				startTime = tc.convertTimeTo12(startTime);
				
				// set session attributes
				session.setAttribute("msg", msg);
				session.setAttribute("roomID", roomID);
				session.setAttribute("startTime", startTime);
				session.setAttribute("roomNumber", roomNumber);
				session.setAttribute("currentDate", currentDate);
				session.setAttribute("currentDateLong", currentDateLong);
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildingName", buildingName);
				session.setAttribute("incrementSelect", incrementSelect);
			} else {
				//------------------------------------------------//
				/*               USER INFO EXPIRED                */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				//url = "LoginServlet"; // USED TO TEST LOCALLY
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
			
		} else {
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session isnt active, go to home page
			// the app should log them out.
			//url = "LoginServlet";
			response.sendRedirect(DbConnect.urlRedirect());
			return;
			
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
				
	}

}
