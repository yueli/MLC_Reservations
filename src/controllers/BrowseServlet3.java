package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.FloorSelectQuery;
import helpers.RoomsSelectQuery;

/**
 * @author Brian Olaogun
 * Servlet implementation class BrowseServlet3
 */
@WebServlet({ "/BrowseServlet3", "/Browse3" })
public class BrowseServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		HttpSession session = request.getSession();
		
		// get session and request variables
		String buildingSelected = (String) session.getAttribute("buildingSelected");
		String floorSelected = (String) request.getParameter("floorList");
		
		// loads floor list from db, create dropdown for list, and output as String
		FloorSelectQuery fsq = new FloorSelectQuery();
		fsq.doFloorRead(buildingSelected);
		String floor = fsq.getFloorResults(floorSelected);
		
		// loads rooms from db, query reservation to get availability, outputs string table
		RoomsSelectQuery rsq = new RoomsSelectQuery ();
		rsq.doRoomRead(buildingSelected, floorSelected);
		String table = rsq.getRoomsTable();
		
		// URL of the view to forward
		String url = "/user/browse.jsp";
		
		// set session attribute
		session.setAttribute("floorSelected", floorSelected);
		session.setAttribute("building", buildingSelected);
		session.setAttribute("floor", floor); 
		session.setAttribute("table", table);
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
