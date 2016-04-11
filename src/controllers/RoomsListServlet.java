/*
 *  @author: Ginger Nix
 *  
 *  This servlet gets the building record id from the RoomsServlet or BuildingSelectServlet
 *   and goes to rooms.jsp to list al the rooms with that building record id
 *  
 */


package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.RoomsSelectQuery;
import model.Admin;

/**
 * Servlet implementation class RoomsListServlet
 */
@WebServlet("/RoomsListServlet")
public class RoomsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomsListServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		String url = "";
		String table = "";
		String cancelAction = "";
		
		/////////if(building != null && !building.isEmpty()){

		//get our current session
		session = request.getSession();

		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");

		// clear out the session variable cancelAction just in case there is something in it
		cancelAction = (String) session.getAttribute("cancelAction"); //NEED THIS???
		cancelAction = "";
		
		System.out.println("RoomsListServlet: logged in admin user adminMyID = " + loggedInAdminUser.getAdminMyID());
		int buildingID;

		// get building id from form - it's the id of the building they selected		
		//buildingID = Integer.parseInt(request.getParameter("buildingList")); 
		buildingID = Integer.parseInt(request.getParameter("buildingID")); //from list of buildings - user clicked on view rooms

		// get the name of the page that called this servlet to be able to format
		// the cancel/go back buttons		
		cancelAction = request.getParameter("prev"); //will be 'buildings' or 'RoomsServlet' depending on what called it
		System.out.println("Rooms List Servlet: prev = " + cancelAction	);
		
		// NEED THIS BLOCK??
/*		if (buildingID == 0) {
			System.out.println("RoomListServlet: buildingID = 0");
			buildingID = Integer.parseInt(request.getParameter("buildingID"));
			System.out.println("RoomListServlet: NEW buildingID = " + buildingID);
		}*/
		
		
		System.out.println("Rooms List Servlet: building id = " + buildingID);
		
		// using building id, create a table of a list of all the rooms in that building
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		
		// action will be part of the url for the sending back to prev page for
		// the cancel/go back button
		
		table = rsq.ListRoomsInBuilding(buildingID, cancelAction);
		
		
		//forward our request along
		request.setAttribute("table", table);
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		request.setAttribute("cancelAction", cancelAction);
		
		url = "admin/roomsList.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	
		
	}

}
