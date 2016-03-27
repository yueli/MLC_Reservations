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
import model.DateTimeConverter;
import model.Email;
import model.Reservation;
import model.TimeConverter;
import model.User;

/**
 * Servlet implementation class BrowseConfirmServlet
 */
@WebServlet({ "/BrowseConfirmation", "/BrowseConfirm" })
public class BrowseConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseConfirmServlet() {
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
		this.session = request.getSession();
		
		// get request and session parameters/attributes
		String startTime = (String) session.getAttribute("startTime");
		/* session has start time in 12-hr format, 
		 * startTime24 is for the 24-hour conversion.
		 */
		String startTime24; 
		int roomID = Integer.parseInt((String) session.getAttribute("roomID"));
		String roomNumber = (String) session.getAttribute("roomNumber");
		String currentDate = (String) session.getAttribute("currentDate");
		String buildingName = (String) session.getAttribute("buildingName");
		int buildingID = (Integer) session.getAttribute("buildingID");
		User primaryUser = (User) session.getAttribute("user");
		int hourIncrement = Integer.parseInt(request.getParameter("userIncrementSelected"));
		String secondaryMyID = (String) request.getParameter("secondary");
		
		// user helper used to get user information
		UserHelper uh = new UserHelper();
		
		// initialize message and forwarding URL
		String url = "";
		String msg = "";
		System.out.println("Print primary user - reserve: " + primaryUser.getMyID());
		System.out.println("Print secondary user - reserve: " + secondaryMyID);
		// secondary user ID check
		// make sure inputted ID is not their own
		if(primaryUser.getMyID() == secondaryMyID){
			msg = "You cannot enter your MyID as a secondary ID. "
					+ "Please Enter a MyID other than your own. ";
			url = "user/reservation.jsp";
		}
		// verify inputed secondary user ID
		// if user is not in our local database, have them login once to register
		else if(!uh.inUserTable(secondaryMyID)){
			msg = "Please have " + secondaryMyID + " login once into the application. "
					+ "Logging in once serves as a form of user registration. Once " + secondaryMyID 
					+ " has logged in once, you can add them to any future reservation. ";
			url = "user/reservation.jsp";
		} else {
			
			//-----------------------//
			  // MAKE A RESERVATION
			//-----------------------//
			String free = "N"; // room is not free
			
			// convert time to 24-hour format + get the end time
			TimeConverter tc = new TimeConverter();
			startTime24 = tc.convertTimeTo24(startTime);
			String endTime = TimeConverter.addTime(startTime24, hourIncrement); // adding time and hour increment together to get end time
			
			//--- user information ---//
			       // primary user
			int primaryUserID = primaryUser.getUserRecordID();
			
			      // secondary user
			User secondaryUser = uh.getUserInfo(secondaryMyID);
			int secondaryUserID = secondaryUser.getUserRecordID();
			System.out.println("PRINT of secondary ID: " + secondaryUserID);
			
			// check if reservation is available
			ReservationSelectQuery rsq = new ReservationSelectQuery();
			rsq.doReservationRead(currentDate, startTime24, roomNumber);
			String reservationCheck = rsq.doReservationResults();
			
			// a returned value = the room was reserved at the time
			// an empty result set/string =  the room is free at the time
			if(!reservationCheck.isEmpty()){ // the room the user selected is reserved
				msg = "Another user just reserved this room at this time.  Please select another time. "
						+ "";
				url = "user/reservation.jsp";
				
			} else { // the room selected is not reserved = make a reservation
				// create reservation object to insert in query
				// subtract one sec from end time so that no end time overlap with start time for room/date/reservation in database
				Reservation reservation = new Reservation(primaryUserID, secondaryUserID, roomID, currentDate, currentDate, startTime24, TimeConverter.subtractOneSecondToTime(endTime), hourIncrement, buildingID, free);
				ReservationInsertQuery riq = new ReservationInsertQuery();
				riq.doReservationInsert(reservation);
				
				// send confirmation email
				String primaryEmail = primaryUser.getUserEmail();
				String secondaryEmail;
				
				// make sure an email exists in our local database for secondary user. 
				// if not, use MyID@uga.edu as email.
				if(secondaryUser.getUserEmail() == null || secondaryUser.getUserEmail().isEmpty()){
					secondaryEmail = secondaryMyID + "@uga.edu";
				} else {
					secondaryEmail = secondaryUser.getUserEmail();
				}
				
				// class used to send email
				Email email = new Email();
				email.setWebsiteURL("http://ebus.terry.uga.edu:8080/MLC_Reservations");
				email.sendMail(primaryEmail, secondaryEmail, currentDate, startTime24, endTime, buildingName, roomNumber, email.getWebsiteURL());
				
				// set success message and forwarding URL
				msg = "You have successfully made a reservation.  "
						+ "You should receive a confirmation email shortly";
				url = "user/reservationConfirmation.jsp";
				
				session.setAttribute("secondaryEmail", secondaryEmail);
			}
			
		}
		
		// set session attributes
		session.setAttribute("startTime", startTime);
		session.setAttribute("roomNumber", roomNumber);
		session.setAttribute("building", buildingName);
		session.setAttribute("hourIncrement", hourIncrement);	
		session.setAttribute("msg", msg);
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
