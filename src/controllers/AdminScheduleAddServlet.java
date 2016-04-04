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
 * Servlet implementation class AdminScheduleAddServlet2
 */
@WebServlet({ "/AdminScheduleAddServlet", "/add-schedule" })
public class AdminScheduleAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminScheduleAddServlet() {
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
		
		// if this session is not null (active/valid)
		if(this.session != null){
			// get admin user from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
			String role = loggedInAdminUser.getRole();
			int status = loggedInAdminUser.getAdminStatus();
			
			// push content based off role
			if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
				// get session and request variables + initialization of others
				String buildings = ""; // the string that contains the HTML drop down list
				String buildingID = request.getParameter("buildingID"); // get the value from 
				String buildingIDSelect = request.getParameter("buildingList"); // get the value selected from the drop down list
				String buildingIDSession = (String) session.getAttribute("buildingID"); // get the building ID from the session
				String msg = (String) session.getAttribute("msg");
				
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
				
				// forward the URL
				url = "admin/schedule-add.jsp";
				
				// set session and request variables
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildings", buildings);
				session.setAttribute("msg", msg);
			
			} else if (role.equalsIgnoreCase("C") && status == 1) { 
				//------------------------------------------------//
				/*                VIEW FOR CLERK                  */
				//------------------------------------------------//
				
				// forwarding URL
				url = "AdminViewReservations";
				
				// set session attributes
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
			
		} else { // there isn't an active session.
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
