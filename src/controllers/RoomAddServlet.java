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
import model.DbConnect;

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

					// get building id from list or rooms - it's the id of the building they selected		
					int buildingID = Integer.parseInt(request.getParameter("buildingID"));
					
					// get the cancelAction for Cancel buttons to pass on
					String cancelAction = request.getParameter("cancelAction"); 
	
					// create a form for the admin to add a room
					RoomsSelectQuery rsq = new RoomsSelectQuery();
					table = rsq.createAddRoomForm(buildingID, cancelAction);
								
			    	request.setAttribute("message", message);
			        request.setAttribute("loggedInAdminUser", loggedInAdminUser);
					request.setAttribute("table", table);
			
					url = "admin/roomAdd.jsp";
									
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
