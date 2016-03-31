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
		session = request.getSession();
		
		// If session is active/valid
		if(session != null){
			// Check if any buildings are open
			BuildingSelectQuery bsq = new BuildingSelectQuery();
			boolean isOpen = bsq.buildingsOnline();
			if (isOpen){
				// Headers and Submit button
				String buildingHeader = "Please Select Building";
				String buildingSubmit = "<input class='btn btn-lg btn-red' name='enterBuilding' type='submit' value='Enter'>";
				
				// loads building list from db, create dropdown for list, and output as String
				
				bsq.doBuildingRead();
				String buildings = bsq.getBuildingResults();
				
				// set session attribute
				session.setAttribute("buildingHeader", buildingHeader);
				session.setAttribute("buildingSubmit", buildingSubmit);
				session.setAttribute("buildings", buildings);
				url = "/user/browse.jsp";
				
			} else {
				
				String msg = "No Buildings are currently open at this time.  Please check again.";
				session.setAttribute("msg", msg);
				url = "/user/browse.jsp";
			}
			
		} else {
			// if session isnt active, go to home page
			// the app should log them out.
			url = "UserHome";
		}
	
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
