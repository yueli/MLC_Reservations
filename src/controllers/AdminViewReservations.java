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
 * Servlet implementation class AdminViewReservations.  This servlet will allow admins to view all reservations.
 * @author Brian Olaogun
 * 
 */
@WebServlet({ "/AdminViewReservations", "/view-reservations" })
public class AdminViewReservations extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;   
    private String url;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminViewReservations() {
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
			
			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			if (loggedInAdminUser != null){
				
				// get the role & status for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S") || role.equalsIgnoreCase("C")) && status == 1){
					// remove message 
					session.removeAttribute("msg");
					
					//------------------------------------------------//
					/*               VIEW RESERVATIONS                */
					//------------------------------------------------//
					
					String msg = (String) session.getAttribute("msg");
					String buildingList = request.getParameter("buildingList");
					BuildingSelectQuery bsq = new BuildingSelectQuery();
					// set building & check if building is selected by user
					int bldg = bsq.getFirstBuildingID();
					if (buildingList != null){ // if selected, get value & transform into integer
						bldg = Integer.parseInt(buildingList);
					}
					System.out.println("Building Selected in View Reservations " + bldg);
					
					// get the current date
					DateTimeConverter dtc = new DateTimeConverter();
					String currentDate = dtc.parseDate(dtc.datetimeStamp());
					System.out.println("Current Date --> View Reservations " + currentDate);
					System.out.println();
					
					// get the inputed date. If datepicker date isn't null, it will be now be the current date
					String inputtedDate = request.getParameter("datepicker");
					if (inputtedDate != null && !inputtedDate.isEmpty()){
						currentDate = inputtedDate;
					}
					
					// Transform current date for display
					String currentDateLong = dtc.convertDateLong(currentDate);
					
					// query building
					bsq.doAdminBuildingRead();
					String buildings = bsq.getBuildingResults(bldg); // contains the HTML drop down building list.
					
					// query reservations
					AdminReservationsSelectQuery arsq = new AdminReservationsSelectQuery();
					arsq.doUserReservationRead(bldg, currentDate); 
					arsq.doAdminReservationRead(bldg, currentDate);
					
					// contains the html table with the query results
					String userReservations = arsq.doUserReservationResults();
					String adminReservations = arsq.doAdminReservationResults(role);
		
					// set the forwarding URL
					url = "admin/view-reservations.jsp";
					
					// set session and request variables
					session.setAttribute("msg", msg);
					session.setAttribute("buildings", buildings);
					session.setAttribute("bldg", bldg);
					session.setAttribute("currentDate", currentDate);
					session.setAttribute("currentDateLong", currentDateLong);
					session.setAttribute("adminReservations", adminReservations);
					session.setAttribute("userReservations", userReservations);
					
				} else {
					//------------------------------------------------//
					/*              NOT A VALID ROLE                  */
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
				/*            ADMIN USER INFO EXPIRED             */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				//url = "LoginServlet"; // USED TO TEST LOCALLY
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
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
