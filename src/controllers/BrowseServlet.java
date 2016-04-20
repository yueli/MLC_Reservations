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
import model.DateTimeConverter;
import model.DbConnect;
import model.User;

/**
 * 
 * Servlet implementation class BrowseServlet.  This is used for the student/user browse page.
 * This is the first servlet called for Browse.  It just pushed down the building drop down list and headers if any buildings are open.
 * If no buildings are open, then it will display a message to the user that no buildings are open
 * @author Brian Olaogun
 */
@WebServlet(
		description = "Servlet for browse.jsp", 
		urlPatterns = { 
				"/BrowseServlet", 
				"/Browse"
		})
public class BrowseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session; 
    private String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseServlet() {
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
		// get current session
		session = request.getSession(false);
		
		// If session is active/valid
		if(session != null){
			User user = (User) session.getAttribute("user");
			 
			if(user != null) { // run code if user object is not null
				// remove anything from previous sessions
				session.removeAttribute("currentDay");
				session.removeAttribute("building");
				session.removeAttribute("buildingID");
				session.removeAttribute("buildingHeader");
				session.removeAttribute("buildingSubmit");
				session.removeAttribute("buildings");
				session.removeAttribute("floor");
				session.removeAttribute("floorSelected");
				session.removeAttribute("floorHeader");
				session.removeAttribute("table");
				
				System.out.println("USER INFO FROM BROWSE: " + user.getUserRecordID() + ", " + user.getMyID() + ", " + user.getLastLogin());
				
				// Check if any buildings are open
				DateTimeConverter dtc = new DateTimeConverter();
				String currentDay = "";
				
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				boolean isOpen = bsq.buildingsOnline();
				if (isOpen){
					session.removeAttribute("msg");
					
					
					// Headers and Submit button
					currentDay = "<h2>Browse reservations for today, " + dtc.convertDateLong(dtc.parseDate(dtc.datetimeStamp())) + "</h2><br>";
					String buildingHeader = "Please Select Building";
					String buildingSubmit = "<input class='btn btn-lg btn-red' name='enterBuilding' type='submit' value='Enter'>";
					
					// loads building list from db, create dropdown for list, and output as String
					
					bsq.doBuildingRead();
					String buildings = bsq.getBuildingResults();
					
					// set session attribute
					session.setAttribute("currentDay", currentDay);
					session.setAttribute("user", user);
					session.setAttribute("buildingHeader", buildingHeader);
					session.setAttribute("buildingSubmit", buildingSubmit);
					session.setAttribute("buildings", buildings);
					url = "/user/browse.jsp";
				
				} else {
					// if no buildings are online.
					currentDay = "<h2>Browse reservations for today, " + dtc.convertDateLong(dtc.parseDate(dtc.datetimeStamp())) + "</h2><br>";
					String msg = "No Buildings are currently open at this time.  Please check again.";
					session.setAttribute("msg", msg);
					session.setAttribute("currentDay", currentDay);
					url = "/user/browse.jsp";
				}
			} else {
				//------------------------------------------------//
				/*               USER INFO EXPIRED                */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				
				session.invalidate();
				CASLogoutServlet.clearCache(request, response);
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
			
		} else {
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
