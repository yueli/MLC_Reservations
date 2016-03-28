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
import helpers.RoomsSelectQuery;
import model.Email;
import model.Reservation;
import model.TimeConverter;

/**
 * Servlet implementation class AdminReservationsServlet3
 */
@WebServlet({ "/AdminReservationsServlet3", "/admin-reserve-confirm" })
public class AdminReservationsServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReservationsServlet3() {
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
		
		// check to see if there is a valid session
		//if (session != null){ // there is an active session
			
			// get the role for the currently logged in admin user.
			//Admin adminUser = (Admin) session.getAttribute("adminUser");
			//String role = adminUser.getRole();
			//int status = adminUser.getAdminStatus();
			
			// push content based off role
			//if((role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("super admin")) && status == 1){
				//------------------------------------------------//
				/*               VIEW FOR ADMIN                   */
				//------------------------------------------------//
				String buildingID = (String) session.getAttribute("buildingID");
				String startDate = (String) session.getAttribute("startDate");
				String endDate = (String) session.getAttribute("endDate");
				String startTime = (String) session.getAttribute("startTime");
				String endTime = (String) session.getAttribute("endTime");
				String reserveName = (String) session.getAttribute("reserveName");
				String roomNumber = (String) session.getAttribute("roomNumber");
				TimeConverter tc = (TimeConverter) session.getAttribute("tc");
				String msg = (String) session.getAttribute("msg");
				String startTime24 = tc.convertTimeTo24(startTime);
				String endTime24 = tc.convertTimeTo24(endTime);
				String free = "N";
				int roomID;
				
				//------------------------------------------------//
				/*            MAKE RESERVATION CONT.              */
				//------------------------------------------------//
				// get room ID
				RoomsSelectQuery roomsq = new RoomsSelectQuery();
				 roomID = (int) roomsq.getRoomID(Integer.parseInt(buildingID), roomNumber);
				
				// check if reservation is available
				ReservationSelectQuery rsq = new ReservationSelectQuery();
				rsq.doReservationRead(startDate, startTime24, roomNumber); //TODO add end date to method
				String reservationCheck = rsq.doReservationResults();
				
				// a returned value = the room was reserved at the time
				// an empty result set/string =  the room is free at the time
				if(!reservationCheck.isEmpty()){ // the room the user selected is reserved
					msg = "Another user just reserved this room at this time.  Please select another time. "
							+ "";
					url = "admin/confirmation.jsp";
					
				} else { // the room selected is not reserved = make a reservation
					// create reservation object to insert in query
					// subtract one sec from end time so that no end time overlap with start time for room/date/reservation in database
					int adminID = 0; // placeholder for admin ID
					int hourIncrement = 0; // placeholder for hourIncrement
					int buildingIDInt = Integer.parseInt(buildingID);
					Reservation reservation = new Reservation(adminID, roomID,
							startDate, endDate, startTime,  endTime, hourIncrement,
							reserveName, buildingIDInt, free);
					ReservationInsertQuery riq = new ReservationInsertQuery();
					riq.doReservationInsert(reservation);
					
					
					// set success message and forwarding URL
					msg = "You have successfully made a reservation.  "
							+ "You should receive a confirmation email shortly";
				}
			//} else { 
				//------------------------------------------------//
				/*                VIEW FOR CLERK                  */
				//------------------------------------------------//
				
				// forwarding URL
				//url = "AdminViewReservations";
				
				// set session attributes
			//}
			
		//} else { // there isn't an active session.
			//------------------------------------------------//
			/*           VIEW FOR INVALID SESSION             */
			//------------------------------------------------//
			//url = "http://ebus.terry.uga.edu:8080/MLC_Reservations";
		//}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

}
