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
		HttpSession session = request.getSession();
		
		String startTime = (String) session.getAttribute("startTime");
		int roomID = Integer.parseInt((String) session.getAttribute("roomID"));
		String roomNumber = (String) session.getAttribute("roomNumber");
		String currentDate = (String) session.getAttribute("currentDate");
		String buildingName = (String) session.getAttribute("buildingName");
		int buildingID = (Integer) session.getAttribute("buildingID");
		User primaryUser = (User) session.getAttribute("user");
		int hourIncrement = Integer.parseInt(request.getParameter("userIncrementSelected"));
		String secondaryMyID = (String) request.getParameter("secondary");
		
		UserHelper uh = new UserHelper();
		
		// initialize message and forwarding URL
		String url = "";
		String msg = "";
		
		// verify inputed secondary user ID
		if(!uh.inUserTable(secondaryMyID)){
			msg = "Please have " + secondaryMyID + " login once into the application. "
					+ "Logging in once serves as a form of user registration. Once " + secondaryMyID 
					+ " has logged in once, you can add them to any future reservation. ";
			url = "user/reservation.jsp";
		}
		
		//-----------------------//
		  // MAKE A RESERVATION
		//-----------------------//
		String free = "N"; // room is not free
		
		// convert time to 24-hour format + get the end time
		TimeConverter tc = new TimeConverter();
		startTime = tc.convertTimeTo24(startTime);
		String endTime = DateTimeConverter.addTime(startTime, hourIncrement);
		
		//--- user information ---//
		       // primary user
		int primaryUserID = primaryUser.getUserRecordID();
		
		      // secondary user
		User secondaryUser = uh.getUserInfo(secondaryMyID);
		int secondaryUserID = secondaryUser.getUserRecordID();
		
		
		
		// create reservation object to insert in query
		Reservation reservation = new Reservation(primaryUserID, secondaryUserID, roomID, currentDate, currentDate, startTime, endTime, hourIncrement, buildingID, free);
		ReservationInsertQuery riq = new ReservationInsertQuery();
		riq.doReservationInsert(reservation);
		
		// send confirmation email
		String primaryEmail = primaryUser.getUserEmail();
		String secondaryEmail;
		if(secondaryUser.getUserEmail() == null && secondaryUser.getUserEmail().isEmpty()){
			secondaryEmail = secondaryMyID + "@uga.edu";
		} else {
			secondaryEmail = secondaryUser.getUserEmail();
		}
		
		Email email = new Email();
		email.sendMail(primaryEmail, secondaryEmail, currentDate, startTime, endTime, buildingName, roomNumber);
		
		// set success message and forwarding URL
		msg = "You have successfully made a reservation.  "
				+ "You should receive a confirmation email shortly";
		url = "user/reservationConfirmation.jsp";
		
		// set session attributes
		session.setAttribute("startTime", startTime);
		session.setAttribute("roomNumber", roomNumber);
		session.setAttribute("building", buildingName);
		session.setAttribute("hourIncrement", hourIncrement);
		session.setAttribute("secondaryEmail", secondaryEmail);
		session.setAttribute("msg", msg);
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
