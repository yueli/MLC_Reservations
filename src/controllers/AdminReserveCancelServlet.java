package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminUpdateQuery;
import model.Admin;
import model.DbConnect;

/**
 * Servlet implementation class AdminReserveCancelServlet.  This servlet is used to cancel admin reservations.
 * Only admins and super admins can see the cancellation button on AdminViewReservations.
 * @author Brian Olaogun
 */
@WebServlet({"/AdminReserveCancelServlet", "/AdminCancel"})
public class AdminReserveCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private String url;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReserveCancelServlet() {
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
		this.session = request.getSession(false);
		
		// check to see if there is a valid session
		if (session != null){ // there is an active session
					
			// check to see if logged in user session is still active.
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			
			if(loggedInAdminUser != null){ // there is an admin session object not expired
				//------------------------------------------------//
				/*           CANCEL ADMIN RESERVATION             */
				//------------------------------------------------//
				// get fields from form
				int reserveID = Integer.parseInt(request.getParameter("reserveID"));
				String free = request.getParameter("free");
				
				// update reservation to show cancellation
				AdminUpdateQuery auq = new AdminUpdateQuery();
				auq.cancelAdminReservation(reserveID, free);
				
				url = "view-reservations";
				
			} else {
				//------------------------------------------------//
				/*            ADMIN USER INFO EXPIRED             */
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
			// if session has timed out, go to home page
			// the site should log them out.
			//url = "LoginServlet";
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
		
		// forward the URL
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
