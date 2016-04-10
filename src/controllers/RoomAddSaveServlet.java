/*
 * @author: Ginger Nix
 * 
 * This class gets the room info from the room add form
 * and saves it as a new record in the room table
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
		
		// get the parameters of the room they are adding		
		room.setRoomNumber(request.getParameter("roomNumber"));
		room.setRoomFloor(Integer.parseInt(request.getParameter("roomFloor")));
		room.setRoomStatus(Integer.parseInt(request.getParameter("roomStatus")));
		room.setBuildingID(Integer.parseInt(request.getParameter("buildingID")));
		room.setAdminID(loggedInAdminUser.getAdminID()); // get the admin user ID from the admin user who is logged in
	
		// check to see if this room in this building in this floor already exists
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		boolean roomExists = rsq.roomAlreadExists(room);
		
		if (roomExists){
			// don't add, set message
			message = "This room already exists!";
			
		}else{
			//add new room 
			
			rsq.insertRoomsTable(room);
			
			message = "Room added";			
		}
		
		url = "RoomsListServlet";	
		
		request.setAttribute("message", message);
		request.setAttribute("loggedInUser", loggedInAdminUser);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);			

		
	}

}
