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
import model.Admin;

/**
 * Servlet implementation class AdminReservations
 */
@WebServlet({ "/AdminReservations", "/make-reservations" })
public class AdminReservationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;  
    private String url = "";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReservationsServlet() {
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
		if (session != null){ // there is an active session
			
			// get the role for the currently logged in admin user.
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
			/*
			Admin loggedInAdminUser = new Admin();
			loggedInAdminUser.setAdminID(1);
			loggedInAdminUser.setAdminMyID("bbo89");
			loggedInAdminUser.setAdminStatus(1);
			loggedInAdminUser.setRole("admin"); */
			String role = loggedInAdminUser.getRole();
			int status = loggedInAdminUser.getAdminStatus();
			
			// push content based off role
			if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
				//------------------------------------------------//
				/*               VIEW FOR ADMIN                   */
				//------------------------------------------------//
				
				// get session and request variables + initialization of others
				String buildings = ""; // the string that contains the HTML drop down list
				String buildingID = request.getParameter("buildingID"); // get the value from request
				String buildingIDSelect = request.getParameter("buildingList"); // get the value selected from the drop down list
				String buildingIDSession = (String) session.getAttribute("buildingID"); // get the building ID from the session
				
				//------------------------------------------------//
				/*            BUILDING INFORMATION                */
				//------------------------------------------------//
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				// if there is no buildingID from request, then display building drop down
				if (buildingID == null){
					buildingID = Integer.toString(bsq.getFirstBuildingID());
					int bldg = Integer.parseInt(buildingID);
					// query building
					
					bsq.doAdminBuildingRead();
					buildings = bsq.getBuildingResults(bldg);
		
				}
				// if there is a buildingID from session, it becomes the buildingID
				// if there is a buildingID selected from drop down, it becomes the buildingID
				if (buildingIDSelect != null){
					buildingID = buildingIDSelect;
					buildings = bsq.getBuildingResults(Integer.parseInt(buildingID)); // keep value selected in drop down.
				} else if (buildingIDSession != null){
					buildingID = buildingIDSession;
				} 
				
				
				// forwarding URL
				url = "admin/reservations.jsp";
				
				// set session and request variables
				session.setAttribute("adminUser", loggedInAdminUser);
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildings", buildings);
			} else { 
				//------------------------------------------------//
				/*                VIEW FOR CLERK                  */
				//------------------------------------------------//
				
				// forwarding URL
				url = "AdminViewReservations";
				
				// set session attributes
			}
			
		} else { // there isn't an active session.
			//------------------------------------------------//
			/*           VIEW FOR INVALID SESSION             */
			//------------------------------------------------//
			url = "AdminHome";
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
