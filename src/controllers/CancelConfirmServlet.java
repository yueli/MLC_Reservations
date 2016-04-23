/** @author: Ginger Nix
 * 
 * The CancelConfirmServlet is called when a user clicks the cancel reservation button
 * next to the reservation they want to cancel. It gets the user's reservation data and sends 
 * it to a jsp where the reservation is displayed and the user confirms that they want to cancel this reservation.
 * 
 **/
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ListUserReservationsQuery;
import model.Admin;
import model.DbConnect;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class CancelConfirmServlet
 */
@WebServlet("/CancelConfirmServlet")
public class CancelConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelConfirmServlet() {
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
		String table = "";
		String message = " ";
				
		
		// get the current session
		session = request.getSession(false);
	
		// check to see if there is a valid session
		if (session != null){ // there is an active session
			User user = (User) session.getAttribute("user");
			
			if(user != null) { // run code if user object is not null				
					message = (String) request.getAttribute("message");	
					
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}

					int userRecdID = user.getUserRecordID();
					
					Reservation reservation = new Reservation();
					
					reservation.setReserveID(Integer.parseInt(request.getParameter("resv_id")));
					
					// have reserve id from the parameter passed from view.jsp
					// use reserve id to get the rest of the record's data and format into a table	
					ListUserReservationsQuery lurq = new ListUserReservationsQuery();
					table = lurq.GetUserReservation(reservation.getReserveID(),userRecdID);
					
					//forward our request along
					request.setAttribute("user", user);
					request.setAttribute("table",table);
					request.setAttribute("message",message); 
					
					url = "user/confirmCancellation.jsp";	
					
			} else {
				//------------------------------------------------//
				/*            ADMIN USER INFO EXPIRED             */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
	
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

}
