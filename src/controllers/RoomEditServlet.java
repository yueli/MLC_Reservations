/*
 * @author: Ginger Nix
 * 
 * This servlet creates a form with all the room's data pre-populated
 * in it for the admin to edit.
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

/**
 * Servlet implementation class RoomEditServlet
 */
@WebServlet("/RoomEditServlet")
public class RoomEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomEditServlet() {
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
		String message = "";
			
		//get our current session
		session = request.getSession();

		message = (String) request.getAttribute("message"); 
		// blank message if nothing gotten in message attribute		
		if (message == null || message.isEmpty()) {
			 message = "";
		}
		
		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
	
		// hidden parameters to use and to pass on
		int roomID = Integer.parseInt(request.getParameter("roomID")); 
		int buildingID = Integer.parseInt(request.getParameter("buildingID")); 
		String cancelAction = request.getParameter("cancelAction"); 
		
		System.out.println("RoomEditServlet: roomID = " + roomID);

		/*
		 * create a table of a form with all the room's data pre-populated
		 * in it to edit
		 */
		 
		RoomsSelectQuery rsq = new RoomsSelectQuery();	
		table = rsq.createEditRoomForm(roomID, cancelAction);
		
		
		//forward our request along
		request.setAttribute("table", table);
		request.setAttribute("message", message);
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);

		url = "admin/roomEdit.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	
		

		
	}

}
