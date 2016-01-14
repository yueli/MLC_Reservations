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
 * Servlet implementation class BrowseServlet2
 */
@WebServlet({ "/BrowseServlet2", "/Browse2" })
public class BrowseServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		HttpSession session = request.getSession();
		
		String buildingSelected = (String) request.getParameter("buildingList");
		String floorSelected = (String) request.getParameter("floorList");
		
		// TEST
		BuildingSelectQuery bsq = new BuildingSelectQuery("tomcatdb", "root", "");
		bsq.doBuildingRead();
		String buildings = bsq.getBuildingResults(buildingSelected);
		// END TEST
		
		FloorSelectQuery fsq = new FloorSelectQuery("tomcatdb", "root", "");
		System.out.println("Building - Browse 2: " + buildingSelected);
		fsq.doFloorRead(buildingSelected);
		String floor = fsq.getFloorResults();
		
		// URL of the view to forward
		String url = "/browse.jsp";
		
		// set session attribute
		session.setAttribute("buildings", buildings); //TEST
		
		session.setAttribute("buildingSelected", buildingSelected);
		session.setAttribute("floorSelected", floorSelected);
		session.setAttribute("floor", floor);
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
