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

/**
 * @author Brian Olaogun
 * Servlet implementation class BrowseServlet2
 */
@WebServlet({ "/BrowseServlet2", "/Browse2", "/BrowseFloors" })
public class BrowseServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
	private String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseServlet2() {
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
		
		// there should be a value from select.  If not, go back to BrowseServlet
		if(request.getParameter("buildingList") == null || request.getParameter("buildingList").isEmpty()){
			url = "Browse";
		} else {
			// get request variables		
			int buildingSelected = Integer.parseInt(request.getParameter("buildingList"));
			String floorSelected = (String) request.getParameter("floorList");
			
			// loads building list from db, create drop down for list, and output as String
			BuildingSelectQuery bsq = new BuildingSelectQuery();
			bsq.doBuildingRead();
			String buildings = bsq.getBuildingResults(buildingSelected);
			
			// loads floor list from db, create drop down for list, and output as String
			FloorSelectQuery fsq = new FloorSelectQuery();
			fsq.doFloorRead(buildingSelected);
			String floor = fsq.getFloorResults();
			
			// URL of the view to forward
			url = "/user/browse.jsp";
			
			// set session attribute
			session.setAttribute("buildings", buildings); 
			session.setAttribute("buildingSelected", buildingSelected);
			session.setAttribute("floorSelected", floorSelected);
			session.setAttribute("floor", floor);
		}
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
