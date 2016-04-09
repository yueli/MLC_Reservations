/*
 * @author: Ginger Nix
 * 
 * Servlet to take the room data in the edit form, save it, and go back to listing the rooms
 * where the edited room will be listed with the new data. 
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
		
		//get our current session
		session = request.getSession();
		message = (String) request.getAttribute("message"); 
		
		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");

		
		// blank message if nothing gotten in message attribute		
		if (message == null || message.isEmpty()) {
			 message = "";
		}
	
		
		
		Rooms room = new Rooms();
		
		System.out.println("In Room Save Servlet");

		
		//pull the hidden field for the user being edited's record id from the form adminEdit.jsp
		room.setRoomID(Integer.parseInt(request.getParameter("roomID")));
		room.setBuildingID(Integer.parseInt(request.getParameter("buildingList")));
		
		System.out.println("In Room Save Servlet: buildingID = " + room.getBuildingID());
		
		//pull the fields from the form roomEdit.jsp to populate the room being edited's object
		room.setRoomNumber(request.getParameter("roomNumber"));
		room.setRoomFloor(Integer.parseInt(request.getParameter("roomFloor")));
		room.setRoomStatus(Integer.parseInt(request.getParameter("roomStatus")));
		room.setAdminID(loggedInAdminUser.getAdminID()); // get the admin user ID from the admin user who is logged in

		System.out.println("In Room Save Servlet: adminID  = " + room.getAdminID());
		
		// update the room record with the edited data
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		rsq.updateRoomTable
				(
				room.getRoomID(),
				room.getRoomNumber(),
				room.getRoomFloor(),
				room.getRoomStatus(),
				room.getAdminID()
				);

		message = ""; // need this?
		url = "RoomsListServlet";

		request.setAttribute("message", message);
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);	


	
	}

}
