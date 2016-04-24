package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ReservationInsertQuery;
import helpers.ReservationSelectQuery;
import helpers.UserHelper;
import model.DbConnect;
import model.Email;
import model.Reservation;
import model.TimeConverter;
import model.User;

/**
 * Servlet implementation class UserSearchServlet3
 * @author Brian Olaogun
 */
@WebServlet("/SearchReservation-Confirm")
public class UserSearchServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet3() {
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
			User primaryUser = (User) session.getAttribute("user");
			 
			if(primaryUser != null) { // run code if user object is not null
				
				String buildingName = (String) session.getAttribute("buildingName");
				String buildingID = (String) session.getAttribute("buildingID");
				int roomID = (Integer) session.getAttribute("roomID");
				int hourIncrement = (Integer) session.getAttribute("hourIncrement");
				String roomNumber = (String) session.getAttribute("roomNumber");
				String startDate = (String) session.getAttribute("startDate");
				String endDate = (String) session.getAttribute("endDate");
				String startTime = (String) session.getAttribute("startTime");
				String endTime = (String) session.getAttribute("endTime");
				String secondaryMyID = request.getParameter("secondary");
				String msg = "";
				
				// input validation for secondary myID
				if(secondaryMyID.isEmpty() || secondaryMyID.equals("")){
					
					msg = "Please enter a myID of a secondary person to reserve a room";
					url = "user/searchConfirm.jsp";
					
				} else if (secondaryMyID.equalsIgnoreCase(primaryUser.getMyID())){
					
					msg = "Please enter a myID other than your own. <br> The person also needs to have "
							+ "logged into the site at least once to be added to a reservation.";
					url = "user/searchConfirm.jsp";
							
				} else if (User.containsSpaces(secondaryMyID) == true){
					
					msg = "Please remove spaces from the ID entered.";
					url = "user/searchConfirm.jsp";
					
				} else {
	
					System.out.println();
					System.out.println("USER INFO FROM SEARCH CONFIRM SERVLET: " + primaryUser.getUserRecordID() + ", " + primaryUser.getMyID() + ", " + primaryUser.getLastLogin());
					System.out.println();
					
					// user helper used to get user information
					UserHelper uh = new UserHelper();
					
					// print to console to test IDs
					System.out.println("Print primary user - search reserve: " + primaryUser.getMyID());
					System.out.println("Print secondary user - search reserve: " + secondaryMyID);
					System.out.println();
					
					// secondary user ID check
					// make sure inputted ID is not their own
					if(primaryUser.getMyID() == secondaryMyID){
						
						msg = "You cannot enter your MyID as a secondary ID. "
								+ "Please Enter a MyID other than your own. ";
						url = "user/searchConfirm.jsp";
						
					}
					
					// verify inputed secondary user ID
					// if user is not in our local database, have them login once to register
					else if(!uh.inUserTable(secondaryMyID)){
						
						msg = "Please have " + secondaryMyID + " login once into the application. <br>"
								+ "Logging in once serves as a form of user registration.<br> Once " + secondaryMyID 
								+ " has logged in once, you can add them to any future reservation. ";
						url = "user/searchConfirm.jsp";
						
					} else {
						
						//-----------------------//
						  // MAKE A RESERVATION
						//-----------------------//
						String free = "N"; // room is not free
						
						 
						// TODO If statement if endTime is 23:59:59
						if(endTime.equals("23:59:59")){
							endTime = "00:00:00";
						}
						
						//--- user information ---//
						       // primary user
						int primaryUserID = primaryUser.getUserRecordID();
						
							  // secondary user 
						User secondaryUser = uh.getUserInfoFromMyID(secondaryMyID);
						int secondaryUserID = secondaryUser.getUserRecordID();
						
						System.out.println("SECONDARY USER INFO FROM SEARCH CONFIRM SERVLET: " + secondaryUser.getUserRecordID() + ", " + secondaryUser.getMyID() + ", " + secondaryUser.getLastLogin());
						System.out.println();
						System.out.println("PRINT of secondary ID: " + secondaryUserID);
						
						// check if reservation is available
						ReservationSelectQuery rsq = new ReservationSelectQuery();
						rsq.doReservationRead(startDate, startTime, roomNumber);
						String reservationCheck = rsq.doReservationResults();
						
						// a returned value = the room was reserved at the time
						// an empty result set/string =  the room is free at the time
						if(!reservationCheck.isEmpty()){ // the room the user selected is reserved
							
							msg = "Another user just reserved this room at this time.  Please select another time. "
									+ "";
							url = "user/searchConfirm.jsp";
							
						} else { // the room selected is not reserved = make a reservation
							
							// create reservation object to insert in query
							// subtract one sec from end time so that no end time overlap with start time for room/date/reservation in database
							Reservation reservation = new Reservation(primaryUserID, secondaryUserID, roomID, startDate, endDate, startTime, TimeConverter.subtractOneSecondToTime(endTime), hourIncrement, Integer.parseInt(buildingID), free);
							ReservationInsertQuery riq = new ReservationInsertQuery();
							riq.doReservationInsert(reservation);
							
							// send confirmation email
							String primaryEmail;
							String secondaryEmail;
							
							// make sure an email exists in our local database for primary user. 
							// if not, use MyID@uga.edu as email.
							if (primaryUser.getUserEmail() == null || primaryUser.getUserEmail().isEmpty()){
								
								primaryEmail = primaryUser.getMyID() + "@uga.edu";
								
							} else {
								
								primaryEmail = primaryUser.getUserEmail();
							}
							
							// make sure an email exists in our local database for secondary user. 
							// if not, use MyID@uga.edu as email.
							if(secondaryUser.getUserEmail() == null || secondaryUser.getUserEmail().isEmpty()){
								
								secondaryEmail = secondaryMyID + "@uga.edu";
								
							} else {
								
								secondaryEmail = secondaryUser.getUserEmail();
								
							}
							
							// class used to send email
							Email email = new Email();
							email.setWebsiteURL(DbConnect.urlRedirect());
							email.sendMail(primaryEmail, secondaryEmail, startDate, startTime, endTime, buildingName, roomNumber, email.getWebsiteURL());
							
							// set success message and forwarding URL
							String message = "<div align='center'><h3>You have successfully made a reservation. <br> "
									+ "You should receive a confirmation email shortly.</h3></div>";
							
							url = "View";
							
							session.setAttribute("secondaryEmail", secondaryEmail);				
							session.setAttribute("message", message);
							
							// clear cache to clear back button functionality
							CASLogoutServlet.clearCache(request, response);
							
							// remove session attributes
							session.removeAttribute("buildings");
							session.removeAttribute("buildingID");
							session.removeAttribute("buildingName");
							session.removeAttribute("table");
							session.removeAttribute("hourIncrement");
							session.removeAttribute("startDate");
							session.removeAttribute("endDate");
							session.removeAttribute("startTime");
							session.removeAttribute("endTime");
							session.removeAttribute("tc");
							session.removeAttribute("roomNumber");
							session.removeAttribute("roomID");
							session.removeAttribute("msg");
							session.removeAttribute("reservationLength");
							
						}
						
					}
					
					// set session attributes
					session.setAttribute("msg", msg);
					session.setAttribute("buildingID", buildingID);
					session.setAttribute("buildingName", buildingName);
					session.setAttribute("hourIncrement", hourIncrement);
					session.setAttribute("roomNumber", roomNumber);
					session.setAttribute("startDate", startDate);
					session.setAttribute("endDate", endDate);
					session.setAttribute("startTime", startTime);
					session.setAttribute("endTime", endTime);
					session.setAttribute("roomID", roomID);
				}
				
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
