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
import helpers.FloorSelectQuery;
import helpers.RoomsSelectQuery;
import model.DbConnect;
import model.User;

/**
 * @author Brian Olaogun
 * Servlet implementation class BrowseServlet3
 */
@WebServlet({ "/BrowseServlet3", "/Browse3", "/BrowseRooms" })
public class BrowseServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseServlet3() {
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
		this.session = request.getSession(false);
		
		// If session is active/valid
		if(session != null){
			User user = (User) session.getAttribute("user");
			
			if(user != null) { // run code if user object is not null
				
				// Check if any buildings are open
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				boolean isOpen = bsq.buildingsOnline();
				
				if (isOpen){ // a building is open
					session.removeAttribute("msg");
					// get session and request variables
					int buildingSelected = (Integer) session.getAttribute("buildingSelected");
					String floorSelected = (String) request.getParameter("floorList");
					
					// loads floor list from database, create dropdown for list, and output as String
					FloorSelectQuery fsq = new FloorSelectQuery();
					fsq.doFloorRead(buildingSelected);
					String floor = fsq.getFloorResults(floorSelected);
					
					// loads rooms from database, query reservation to get availability, outputs string table
					RoomsSelectQuery rsq = new RoomsSelectQuery ();
					rsq.doRoomRead(buildingSelected, floorSelected);
					String table = rsq.getRoomsTable();
					
					// URL of the view to forward
					url = "/user/browse.jsp";
					
					// set session attribute
					session.setAttribute("floorSelected", floorSelected);
					session.setAttribute("building", buildingSelected);
					session.setAttribute("floor", floor); 
					session.setAttribute("table", table);
					
				} else { // ALL buildings are closed
					session.removeAttribute("buildingHeader");
					session.removeAttribute("buildingSubmit");
					session.removeAttribute("floorHeader");
					
					String msg = "No Buildings are currently open at this time.  Please check again.";
					session.setAttribute("msg", msg);
					url = "/user/browse.jsp";
				}
			} else {
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				//url = "LoginServlet"; // USED TO TEST LOCALLY
				response.sendRedirect(DbConnect.urlRedirect());
			}
		} else {
			// if session isnt active, go to home page
			// the app should log them out.
			//url = "LoginServlet";
			response.sendRedirect(DbConnect.urlRedirect());
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
