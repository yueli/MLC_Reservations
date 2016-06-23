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
import model.DbConnect;

/**
 * Servlet implementation class AdminReservations
 * @author Brian Olaogun
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
					
					// remove message 
					session.removeAttribute("msg");
					session.removeAttribute("table");
					session.removeAttribute("startDate");
					session.removeAttribute("endDate");
					session.removeAttribute("reserveName");
					
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
						if(buildingIDSession.equalsIgnoreCase(buildingID)){
							buildingID = buildingIDSession;
						}
					} 
					
					String startTime = "00:00:00";
					String endTime = "00:00:00";
					
					String table = "";
				
					url = "admin/reservations.jsp";
					
					// set session and request variables
					session.setAttribute("startTime", startTime);
					session.setAttribute("endTime", endTime);
					session.setAttribute("adminUser", loggedInAdminUser);
					session.setAttribute("buildingID", buildingID);
					session.setAttribute("buildings", buildings);
					session.setAttribute("table", table);
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
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
