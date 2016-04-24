/*
 * @author: Ginger Nix
 * 
 * Servlet to take the room data in the edit form, checks to see if that room already
 * exists and saves it if it doesn't exists. If does exist, sets message. 
 * Goes back to listing the rooms with the message. If room was added it will show
 * up in the list of rooms. 
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

import helpers.BuildingListQuery;
import helpers.RoomsSelectQuery;
import model.Admin;
import model.DbConnect;
import model.Rooms;

/**
 * Servlet implementation class RoomSaveServlet
 */
@WebServlet("/RoomSaveServlet")
public class RoomSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomSaveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		String table = "";
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
				
						
					Rooms room = new Rooms();
					
					System.out.println("In Room Save Servlet");
					
					// get the paramter cancel action and building ID to pass on as a session var to RoomsListServlet
					cancelAction = request.getParameter("cancelAction"); 
					buildingID = Integer.parseInt(request.getParameter("buildingID"));
					
					//pull the hidden field for the user being edited's record id from the form adminEdit.jsp
					room.setRoomID(Integer.parseInt(request.getParameter("roomID")));
					room.setBuildingID(Integer.parseInt(request.getParameter("buildingID")));
					
					System.out.println("In Room Save Servlet: buildingID = " + room.getBuildingID());
					
					//pull the fields from the form roomEdit.jsp to populate the room being edited's object
					room.setRoomNumber(request.getParameter("roomNumber"));
					room.setRoomFloor(Integer.parseInt(request.getParameter("roomFloor")));
					room.setRoomStatus(Integer.parseInt(request.getParameter("roomStatus")));
					room.setAdminID(loggedInAdminUser.getAdminID()); // get the admin user ID from the admin user who is logged in
			
					System.out.println("In Room Save Servlet: adminID  = " + room.getAdminID());

					// get the building name for message
					BuildingListQuery blq = new BuildingListQuery();
					
					String buildingName =  blq.getBuildingName(room.getBuildingID());
					
					// check if a room w/ this room number on this floor in this building already exists
					// send it the buildingID, roomFloor, and roomNumber
					
					RoomsSelectQuery rsq = new RoomsSelectQuery();
					
					boolean roomAlreadyExists = rsq.roomAlreadExists(room);
					
					if (roomAlreadyExists){
						// send back to edit form w/ message
						
						message = "<br /><br /><div align='center'><h3>A room in the "
								+ buildingName  + " building with the room number "
								+ room.getRoomNumber() + " on the "
								+ room.getRoomFloor() + " floor already exists."
								+ "</h3></div>";
					
						url = "AdminAddServlet";
						request.setAttribute("message", message);
						request.setAttribute("loggedInUser", loggedInAdminUser);
						
					}else{
						// update the room record with the edited data
						rsq.updateRoomTable
								(
								room.getRoomID(),
								room.getRoomNumber(),
								room.getRoomFloor(),
								room.getRoomStatus(),
								room.getAdminID()
								);
				
						message = "<br /><br /><div align='center'><h3>Room in the "
								+ buildingName  + " building with the room number "
								+ room.getRoomNumber() + " on the "
								+ room.getRoomFloor() + " floor was updated."
								+ "</h3></div>";
						
						url = "RoomsListServlet";

						
					}
					
					request.setAttribute("message", message);
					request.setAttribute("loggedInAdminUser", loggedInAdminUser);
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
