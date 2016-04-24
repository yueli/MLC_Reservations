/*
 *  @author: Ginger Nix
 *  
 *  This servlet gets the building record id from the RoomsServlet or BuildingSelectServlet
 *  and goes to rooms.jsp to list all the rooms with that building record id
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
import model.DbConnect;

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
		String message = "";

		System.out.println("RoomsListServlet: beginning");
		
		// get the current session
		session = request.getSession(false);
	
		
		// check to see if there is a valid session
		if (session != null){ // there is an active session

			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			if (loggedInAdminUser != null){
				
				// get info for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
				
					message = (String) request.getAttribute("message"); 
				
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}
				
			
					
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
					request.setAttribute("message", message);
					request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		
					url = "admin/roomsList.jsp";
						
				}  else if (role.equalsIgnoreCase("C") && status == 1){ 
					//------------------------------------------------//
					/*                VIEW FOR CLERK                  */
					//------------------------------------------------//
					
					// forwarding URL
					url = "AdminViewReservations";
					
				} else {
					//------------------------------------------------//
					/*              NOT A VALID ROLE                  */
					//------------------------------------------------//
					// if a new session is created with no user object passed
					// user will need to login again
					session.invalidate();
					
					response.sendRedirect(DbConnect.urlRedirect());
					return;
				}
			} else {
				//------------------------------------------------//
				/*            ADMIN USER INFO EXPIRED             */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}					
						
					
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
