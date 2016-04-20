/** @author: Ginger Nix
 * 
 * The OnlineCheckInServlet is called when a user views a list of their reservations
 * and it is time for them to check in. They will only see this option when it is
 * within ten minutes after the hour of their reservation.
 * The user is checked in and if there was a problem, they are sent to an error jsp page
 * with the appropriate message.
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

import helpers.ReservationQuery;
import model.DbConnect;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class OnlineCheckInServlet
 */
@WebServlet("/OnlineCheckInServlet")
public class OnlineCheckInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   private HttpSession session; 
	    private String url;
	    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnlineCheckInServlet() {
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

		String message = "";
		
		//get our current session
		this.session = request.getSession(false); 
		
		// if this session is not null (active/valid)
		if (this.session != null){	
 		
	        User user = (User) session.getAttribute("user");		
	        int userRecdId = user.getUserRecordID();
	        
	    	Reservation reservation = new Reservation();
			reservation.setReserveID(Integer.parseInt(request.getParameter("resv_id")));
			int reservationRecdId = reservation.getReserveID();
			
			ReservationQuery resvQuery = new ReservationQuery();
			
			if (resvQuery.checkInUser(reservationRecdId, userRecdId)) { //if successfully checked in user
				
				message += "<div align='center'><h3> Successfully checked in! </h3></div>";
	
				url = "ViewServlet";
			
			}else{
				url="user/qrError.jsp"; 
				message += "Error: The reservation can not be checked in. Please contact adminstrators for help.";
				
			}
	        System.out.println("+_+_+_+ OnlineCheckInServ: message = " + message);
			session.setAttribute("user", user);
			session.setAttribute("message", message);
			
		} else { // there isn't an active session.
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.

			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
		
		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
      
	}

}
