package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminReservationsSelectQuery;
import helpers.BuildingSelectQuery;
import model.Admin;
import model.DateTimeConverter;
import model.DbConnect;

/**
 * Servlet implementation class AdminViewReservations2
 */
@WebServlet({ "/AdminViewReservations2", "/view-reservations?update" })
public class AdminViewReservations2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private String url;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminViewReservations2() {
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
			
			// get the role for the currently logged in admin user.
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); // USED FOR TESTING
			String role = loggedInAdminUser.getRole();
			int status = loggedInAdminUser.getAdminStatus();
			
			// push content based off role
			if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S") || role.equalsIgnoreCase("C")) && status == 1){
		
				// get session variables
				int bldg = (Integer) session.getAttribute("bldg");
				String currentDate = (String) session.getAttribute("currentDate");
				String inputtedDate = request.getParameter("datepicker");
				
				if (inputtedDate != null){
					currentDate = inputtedDate;
				}
				// transform date into long format for displaying
				DateTimeConverter dtc = new DateTimeConverter();
				String currentDateLong = dtc.convertDateLong(currentDate);
				
				// query building
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				bsq.doAdminBuildingRead();
				String buildings = bsq.getBuildingResults(bldg);
				
				// query reservations
				AdminReservationsSelectQuery arsq = new AdminReservationsSelectQuery();
				arsq.doUserReservationRead(bldg, currentDate);
				arsq.doAdminReservationRead(bldg, currentDate);
				
				String userReservations = arsq.doUserReservationResults();
				String adminReservations = arsq.doAdminReservationResults();
				
				// set the forwarding URL
				url = "admin/view-reservations.jsp";
				
				// set session and request variables
				session.setAttribute("buildings", buildings);
				session.setAttribute("bldg", bldg);
				session.setAttribute("currentDate", currentDate);
				session.setAttribute("currentDateLong", currentDateLong);
				session.setAttribute("adminReservations", adminReservations);
				session.setAttribute("userReservations", userReservations);
			} else {
				//------------------------------------------------//
				/*            ADMIN USER INFO EXPIRED             */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				//url = "LoginServlet"; // USED TO TEST LOCALLY
				response.sendRedirect(DbConnect.urlRedirect());
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			//url = "LoginServlet";
			response.sendRedirect(DbConnect.urlRedirect());
		}
				
				
		// forward the URL
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
