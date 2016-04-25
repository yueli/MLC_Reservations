/*
 * @author: Ginger Nix
 * 
 * This servlet gets the room info from the room add form, checks to see if the room 
 * already exists and if not saves it as a new record in the room table. If it already 
 * exists the user is taken back to the room add form w/ a message.
 * 
 */

package controllers;

import java.io.IOException;

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
import model.Rooms;

/**
 * Servlet implementation class RoomAddSaveServlet
 */
@WebServlet("/RoomAddSaveServlet")
public class RoomAddSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomAddSaveServlet() {
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
		String message = "";
		String cancelAction = "";
		int buildingID;
		
		
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
						
						// get parameters passes by clicking the Save button
						buildingID = Integer.parseInt(request.getParameter("buildingID")); 
						cancelAction = request.getParameter("cancelAction"); 
					
					
						Rooms room = new Rooms();
						
						// get the parameters of the room they are adding		
						room.setRoomNumber(request.getParameter("roomNumber"));
						room.setRoomFloor(Integer.parseInt(request.getParameter("roomFloor")));
						room.setRoomStatus(Integer.parseInt(request.getParameter("roomStatus")));
						room.setBuildingID(Integer.parseInt(request.getParameter("buildingID")));
						room.setAdminID(loggedInAdminUser.getAdminID()); // get the admin user ID from the admin user who is logged in
					
						// check to see if this room in this building in this floor already exists
						RoomsSelectQuery rsq = new RoomsSelectQuery();
						boolean roomExists = rsq.roomAlreadExists(room);
						
						System.out.println("RoomAddSaveServlet: after check ig room exists: " + roomExists);
						
						if (roomExists){
							// don't add, set message
							
							message = "<br /><br /><div align='center'><h3>This room in this building with the room number"
									+ room.getRoomNumber() + " on the "
									+ room.getRoomFloor() + " floor already exists."
									+ "</h3></div>";
							url = "RoomAddServlet";
							
						}else{
							//add new room 
							
							rsq.insertRoomsTable(room);
							
							message = "<br /><br /><div align='center'><h3>Room was added."
									+ "</h3></div>";	
							url = "RoomsListServlet";	
						}
											
						
						request.setAttribute("message", message);
						request.setAttribute("loggedInUser", loggedInAdminUser);
						request.setAttribute("cancelAction", cancelAction);
						request.setAttribute("buildingID", buildingID);
						
					
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
