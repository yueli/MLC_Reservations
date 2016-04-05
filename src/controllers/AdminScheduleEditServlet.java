package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminScheduleSelectQuery;
import helpers.BuildingSelectQuery;
import model.Admin;
import model.DbConnect;

/**
 * Servlet implementation class AdminScheduleEditServlet
 * @author Brian Olaogun
 */
@WebServlet({ "/AdminScheduleEditServlet", "/Schedule" })
public class AdminScheduleEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminScheduleEditServlet() {
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
				
				// get the role for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
			
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
					
					//------------------------------------------------//
					/*                EDIT SCHEDULE                   */
					//------------------------------------------------//
					
					// get session and request variables
					String buildings = ""; // the string that contains the html dropdown list
					String buildingID = request.getParameter("buildingID"); // get the value from
					String buildingIDSelect = request.getParameter("buildingList"); // get the value selected from the dropdown list
					String buildingIDSession = (String) session.getAttribute("buildingID"); // get the building ID from the session
					
					String to = request.getParameter("to");
					String from = request.getParameter("from");
					System.out.println();
					
					BuildingSelectQuery bsq = new BuildingSelectQuery();
					// if there is no buildingID from request, then display building dropdown
					if (buildingID == null || buildingID.equals("0")){
						buildingID = Integer.toString(bsq.getFirstBuildingID());
						int bldg = Integer.parseInt(buildingID);
						// query building
						
						bsq.doAdminBuildingRead();
						buildings = bsq.getBuildingResults(bldg);
					}
					// if there is a buildingID from session, it becomes the buildingID
					// if there is a buildingID selected from dropdown, it becomes the buildingID
					if (buildingIDSelect != null){
						buildingID = buildingIDSelect;
						buildings = bsq.getBuildingResults(Integer.parseInt(buildingID)); // keep value selected in dropdown.
					} else if (buildingIDSession != null){
						buildingID = buildingIDSession;
					} else {
						
					}
					
					// query the database to get
					AdminScheduleSelectQuery ssq = new AdminScheduleSelectQuery();
					ssq.doRead(buildingID, to, from);
					String schedule = ssq.listSchedule();
					
					// set session and request variables
					session.setAttribute("buildingID", buildingID);
					session.setAttribute("buildings", buildings);
					session.setAttribute("schedule", schedule);
					
					// set the forwarding URL
					url = "/admin/schedule.jsp";
					
				}  else if (role.equalsIgnoreCase("C") && status == 1){ 
					//------------------------------------------------//
					/*                VIEW FOR CLERK                  */
					//------------------------------------------------//
					
					// forwarding URL
					url = "AdminViewReservations";
				} else {
					//------------------------------------------------//
					/*              NOT A VALID ROLE                  */
					//------------------------------------------------//
					// if a new session is created with no user object passed
					// user will need to login again
					session.invalidate();
					//url = "LoginServlet"; // USED TO TEST LOCALLY
					response.sendRedirect(DbConnect.urlRedirect());
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
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
