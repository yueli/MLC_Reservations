package controllers;

/**
 * @author: Ginger Nix - added session checks, fixed bugs, cleaned up code, doGet -> doPost
 * @author: Ronnie Xu - created servlet with basic code
 * 
 * This servlet lists the table containing all the buildings w/ the ability to edit buildings,
 * add buildings, and view the rooms in that building (goes to rooms view)
 * 
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingListQuery;
import model.Admin;
import model.DbConnect;

/**
 * Servlet implementation class BuildingListServlet
 */
@WebServlet({ "/BuildingListServlet", "/buildinglist", "/buildings" })
public class BuildingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url = "";
	private String message = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListServlet() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get the current session
		session = request.getSession(false);
	
		// check to see if there is a valid session
		if (session != null){ // there is an active session
			
			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			
			if (loggedInAdminUser != null){
				// get the role for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
					
					message = (String) request.getAttribute("message");	
					
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}
						System.out.println("BuildingListServlet: message at beg = " + message);
							
						BuildingListQuery blq = new BuildingListQuery();
						blq.doRead();
						
						//get the list of all buildings, active and not active
						String table = blq.getHTMLTable();
						
						request.setAttribute("table", table);
						
						url = "/admin/buildings.jsp";
						
						request.setAttribute("table", table);
						request.setAttribute("message", message);
						request.setAttribute("loggedInAdminUser", loggedInAdminUser);	
						
						System.out.println("BuildingListServlet: message at end = " + message);
						
						
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
				System.out.println("BanReadServlet: 6");
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			//url = "LoginServlet";
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
		
}
