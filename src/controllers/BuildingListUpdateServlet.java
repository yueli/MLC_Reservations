package controllers;
/**
 * @author: Ginger Nix - added session checks, role checks, etc, fixed edit form display,
 * 
 * @creator: Ronnix Xu
 * 
 * This servlet creates a pre-populated form to edit a building.
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

import helpers.BuildingListSelectQuery;
import model.Admin;
import model.Building;
import model.DbConnect;

/**
 * Servlet implementation class BuildingListUpdateServlet
 */
@WebServlet({ "/BuildingListUpdateServlet", "/updatebuilding" })
public class BuildingListUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String message = "";
	private String url = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String form = "";
		
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
						
					int buildingID = Integer.parseInt(request.getParameter("buildingID"));
					
					
					BuildingListSelectQuery blsq = new BuildingListSelectQuery();						
					blsq.doRead(buildingID);
					
					// get the building info to populate edit form
					form = blsq.buildingEditForm(buildingID);
							
					session.setAttribute("form", form);	
					session.setAttribute("message", message);	
					request.setAttribute("loggedInAdminUser", loggedInAdminUser);
					
					// displays a pre-poluated form w/ the building info to edit
					url = "/admin/buildingupdate.jsp";

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
				System.out.println("BuildingListForm: - admin user info expired");
				
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			System.out.println("BuildingListForm: - there is not an active session");
			
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}


