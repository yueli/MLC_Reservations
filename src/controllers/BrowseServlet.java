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
import model.DbConnect;
import model.User;

/**
 * @author Brian Olaogun
 * Servlet implementation class BrowseServlet
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
				System.out.println("USER INFO FROM BROWSE: " + user.getUserRecordID() + ", " + user.getMyID() + ", " + user.getLastLogin());
				// Check if any buildings are open
				
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				boolean isOpen = bsq.buildingsOnline();
				if (isOpen){
					session.removeAttribute("msg");
					// Headers and Submit button
					String buildingHeader = "Please Select Building";
					String buildingSubmit = "<input class='btn btn-lg btn-red' name='enterBuilding' type='submit' value='Enter'>";
					
					// loads building list from db, create dropdown for list, and output as String
					
					bsq.doBuildingRead();
					String buildings = bsq.getBuildingResults();
					
					// set session attribute
					session.setAttribute("user", user);
					session.setAttribute("buildingHeader", buildingHeader);
					session.setAttribute("buildingSubmit", buildingSubmit);
					session.setAttribute("buildings", buildings);
					url = "/user/browse.jsp";
				
				} else {
					// if no buildings are online.
					String msg = "No Buildings are currently open at this time.  Please check again.";
					session.setAttribute("msg", msg);
					url = "/user/browse.jsp";
				}
			} else {
				//------------------------------------------------//
				/*               USER INFO EXPIRED                */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				//url = "LoginServlet"; // USED TO TEST LOCALLY
				response.sendRedirect(DbConnect.urlRedirect());
			}
			
		} else {
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
