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
import helpers.BuildingListQuery;
import helpers.BuildingSelectQuery;
import model.Admin;
import model.DbConnect;

/**
 * @author: Ginger Nix
 * This servlet creates a blank form to add a building
 */

/**
 * Servlet implementation class BuildingListUpdateServlet
 */
@WebServlet({ "/BuildingListForm", "/buildingform" })
public class BuildingListForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListForm() {
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

		this.session = request.getSession(false);				
		
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
					
					// remove message 
					//session.removeAttribute("message");
					message = (String) request.getAttribute("message"); 
					
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}

					BuildingListQuery buildingListQuery = new BuildingListQuery();
				
					// creates table to display an empty form to add a building
					table = buildingListQuery.createAddBuildingForm();

					request.setAttribute("message", message);
					request.setAttribute("loggedInAdminUser", loggedInAdminUser);
					request.setAttribute("table", table);
			
					url = "admin/buildingadd.jsp";	
				
	
					// set session and request variables
					session.setAttribute("adminUser", loggedInAdminUser);

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
					System.out.println("BuildingListForm: - not a valide role");
					
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
