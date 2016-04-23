package controllers;

/**
 * @author: Ginger Nix
 * 
 * @creator: Ronnie Xu - I rewrote almost all of it, adding sessions checks, adding check to see
 * if the building's name already exists, sending messages to display, etc
 * 
 * This BuildingListAddServlet takes the data from the admin filling out
 * the form to add a building. It checks to see if a building w/ that name already exists
 * and if it does the user is sent back to the add a building form w/ a message. Else
 * the building is added and the user is sent back to the building listing page.
 * 
 */

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminUserHelper;
import helpers.BuildingListAddQuery;
import helpers.BuildingListUpdateQuery;
import model.Admin;
import model.Building;
import model.DbConnect;

/**
 * Servlet implementation class BuildingListAddServlet
 */
@WebServlet({ "/BuildingListAddServlet", "/addbuilding" })
public class BuildingListAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListAddServlet() {
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
		String message = "";
		int adminID = 0;
		
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
					session.removeAttribute("message");
					
					String buildingName = request.getParameter("buildingName");
					int buildingStatus =  Integer.parseInt(request.getParameter("buildingStatus"));
					String buildingCalName = request.getParameter("buildingCalName");
					String buildingCalUrl = request.getParameter("buildingCalUrl");
					String buildingQRName = request.getParameter("buildingQRName");

					BuildingListAddQuery buildingListAddQuery = new BuildingListAddQuery();

					// see if building's name already exists
					boolean alreadyInTable = false;

					alreadyInTable = buildingListAddQuery.inBuildingTable(buildingName);
					
					if (alreadyInTable){
						// don't add, set message
						message = "<br /><br /><div align='center'><h3>A building with this name " + 
								buildingName + " already exits!</h3></div>";
						
						request.setAttribute("message", message);
						request.setAttribute("loggedInAdminUser", loggedInAdminUser);	
						
						url = "BuildingListForm";
						System.out.println("BuildingListAddServlet: Building already exisits!");
						
					}else{
						//add new building
						System.out.println("BuildingListAddServlet: adding new building");

						adminID = loggedInAdminUser.getAdminID();
						
						try {
							buildingListAddQuery.addBuilding(buildingName, buildingStatus, buildingCalName, buildingCalUrl, adminID, buildingQRName);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						message = "<br /><br /><div align='center'><h3>The building "
								+  buildingName
								+ " has been added.</h3></div><br />";
						
						url = "BuildingListServlet";
						request.setAttribute("message", message);
						request.setAttribute("loggedInAdminUser", loggedInAdminUser);	
						
					}		      
			       	

				}  else if (role.equalsIgnoreCase("C") && status == 1){ 
					//------------------------------------------------//
					/*                VIEW FOR CLERK                  */
					//------------------------------------------------//
					System.out.println("BuildingListForm: - admin user is a clerk");
					
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
