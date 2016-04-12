/*
 * @author: Ginger Nix
 * 
 * This servlet displays an empty form for the admin to add a room to a building.
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

import helpers.AdminUserHelper;
import helpers.RoomsSelectQuery;
import model.Admin;

/**
 * Servlet implementation class RoomAddServlet
 */
@WebServlet("/RoomAddServlet")
public class RoomAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomAddServlet() {
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
		String message = "";
		String table = "";
		
		//get our current session
		session = request.getSession();
		message = (String) request.getAttribute("message"); 
		
		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");		
		System.out.println("RoomAddServlet: logged in admin user's myid = " + loggedInAdminUser.getAdminMyID());

		// blank message if nothing gotten in message attribute		
		if (message == null || message.isEmpty()) {
			 message = "";
		}

		// get building id from list or rooms - it's the id of the building they selected		
		int buildingID = Integer.parseInt(request.getParameter("buildingID"));
		
		// get the cancelAction for Cancel buttons to pass on
		String cancelAction = request.getParameter("cancelAction"); 

		/*		
		creates table to display an empty form
		*/		
		
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		table = rsq.createAddRoomForm(buildingID, cancelAction);

		
    	request.setAttribute("message", message);
        request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		request.setAttribute("table", table);

		url = "admin/roomAdd.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	
	}
	
	
	
	

}
