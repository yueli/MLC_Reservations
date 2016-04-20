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
import helpers.RoomsSelectQuery;
import model.DateTimeConverter;
import model.DbConnect;
import model.User;

/**
 * Servlet implementation class UserSearchServlet2
 * @author Brian Olaogun
 */
@WebServlet("/SearchReservation-MakeReservation")
public class UserSearchServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet2() {
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
		// get current session
		session = request.getSession(false);
		
		// If session is active/valid
		if(session != null){
			User user = (User) session.getAttribute("user");
			 
			if(user != null) { // run code if user object is not null
				int userID = user.getUserRecordID();
				String buildingID = request.getParameter("buildingID");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String startTime = request.getParameter("startTime");
				String endTime = request.getParameter("endTime");
				String roomNumber = request.getParameter("roomNumber");
				int hourIncrement = Integer.parseInt(request.getParameter("hourIncrement"));
				String reservationLength = hourIncrement + " hour(s)"; // string interpretation of hour increment
				
				String msg = "";
				int roomID;
				
				// time and date conversion methods
				DateTimeConverter dtc = new DateTimeConverter();
				
				// get the building name
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				String buildingName = bsq.getBuildingNameFromID(Integer.parseInt(buildingID));
				
				// get the roomID
				RoomsSelectQuery rsq = new RoomsSelectQuery();
				roomID = rsq.getRoomID(Integer.parseInt(buildingID), roomNumber);
				
				// check hour increment
				HourCountSelectQuery hcsq = new HourCountSelectQuery();
				hcsq.doIncrementRead(userID, startDate);
				int incrementSum = hcsq.incrementResult();
				
				// compare hour increment to see the user hasn't maxed out of reservations for selected dat
				if(incrementSum == 2){
					// user has maxed out of reservations for the day
					msg = "You have exceeded the maximum hours (2) for reservations for today.  <br>"
							+ "To make a reservation for another time for today, please cancel one of your current reservations first.";
					
					url = "user/search.jsp";
					
				} else if (incrementSum == 1){
					if(incrementSum == hourIncrement){
						// user can make another 1 hour reservation
						url = "user/searchConfirm.jsp";
						
					} else {
						msg = "You already have a 1-hour reservation set for " + dtc.convertDateLong(startDate) + "<br>"
								+ "You can only make another 1-hour reservation to not exceed the maximum of 2 hours per day.";
						
						url = "user/search.jsp";
					}
				} else if (incrementSum == 0){
					// user can make two 1-hour reservations or one 2-hour reservation
					if(hourIncrement <= 2){
						
						url = "user/searchConfirm.jsp";
						
					} else {
						
						msg = "Please enter a valid hour increment.";
						url = "user/search.jsp";
					}
				}
				// clear cache to clear back button functionality
				CASLogoutServlet.clearCache(request, response);
				
				session.setAttribute("msg", msg);
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildingName", buildingName);
				session.setAttribute("reservationLength", reservationLength);
				session.setAttribute("dtc", dtc);
				
				session.setAttribute("hourIncrement", hourIncrement);
				session.setAttribute("roomNumber", roomNumber);
				session.setAttribute("startDate", startDate);
				session.setAttribute("endDate", endDate);
				session.setAttribute("startTime", startTime);
				session.setAttribute("endTime", endTime);
				session.setAttribute("roomID", roomID);
			} else {
				//------------------------------------------------//
				/*               USER INFO EXPIRED                */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				
				session.invalidate();
				CASLogoutServlet.clearCache(request, response);
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
			
		} else {
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
		
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
	
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
