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
		int buildingID = 0;

		
		/////////
		
		
		
		
		//get our current session
		session = request.getSession();

		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
		System.out.println("RoomsListServlet: logged in admin user adminMyID = " + loggedInAdminUser.getAdminMyID());
				
		
		// get parameter 'prev' which will be 'building' or 'RoomsServlet'
		// and get the parameter 'buildingID' 
		buildingID = Integer.parseInt(request.getParameter("buildingID")); 
		cancelAction = request.getParameter("cancelAction"); 
		
			
		// check to see if there is a parameter
		if(cancelAction != null && !cancelAction.isEmpty()){
			//======================//
			//===== PARAMETERS =====//
			//======================//
			
			// if so, 
			// cancelAction = parameter prev value and		
			// buildingID = parameter buildingID all from above
						
			
		}else{	
			//========================//
			//===== SESSION VARS =====//
			//========================//
			
			// else we have a session var to use so,
			// cancelAction = session var cancelAction from RoomAddSaveServlet and RoomSaveServlet
			
			cancelAction = (String) session.getAttribute("cancelAction"); 
			buildingID = (int) session.getAttribute("buildingID");

		}
		
		// clear out session var cancelAction 
		request.setAttribute("cancelAction", "");
					
		// we now have the cancel action (which servlet called this one) 
		// and the building ID used to save rooms records
			
		System.out.println("Rooms List Servlet: prev = " + cancelAction	);		
		System.out.println("Rooms List Servlet: building id = " + buildingID);
		
		// using building id, create a table of a list of all the rooms in that building
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		
		// cancelAction will be part of the url for the sending back to prev page for
		// the cancel/go back button and buildingID for saving room record
		
		table = rsq.ListRoomsInBuilding(buildingID, cancelAction);
		
		//forward our request along
		request.setAttribute("table", table);
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		
		url = "admin/roomsList.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
