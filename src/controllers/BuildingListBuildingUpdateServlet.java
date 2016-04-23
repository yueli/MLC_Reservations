package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import helpers.BuildingListUpdateQuery;
import model.Admin;
import model.Building;
import model.DbConnect;

/**
 * Servlet implementation class BuildingListUpdateServlet
 */
@WebServlet({ "/BuildingListBuildingUpdateServlet", "/updatingbuilding" })
public class BuildingListBuildingUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListBuildingUpdateServlet() {
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
		String message = "";
	
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
					
					// create new admin object to hold data entered in the form to add an admin user
					Building buildingToUpdate  = new Building();
					
					//pull the fields from the form adminEdit.jsp to populate the user being edited's object
					buildingToUpdate.setBuildingID(Integer.parseInt(request.getParameter("buildingID")));
					buildingToUpdate.setAdmin(loggedInAdminUser.getAdminMyID());
					buildingToUpdate.setBuildingName(request.getParameter("buildingName"));
					buildingToUpdate.setBuildingStatus(Integer.parseInt(request.getParameter("status")));
					buildingToUpdate.setBuildingCalName(request.getParameter("buildingCalName"));
					buildingToUpdate.setBuildingCalUrl(request.getParameter("buildingCalUrl"));
					buildingToUpdate.setBuildingQRName(request.getParameter("buildingQRName"));
					
					
					// go update this bulding's record
					BuildingListUpdateQuery bluq = new BuildingListUpdateQuery();
					bluq.doUpdate(buildingToUpdate, loggedInAdminUser.getAdminID());
					 					 
					message = "<br /><br /><div align='center'><h3>The "
							+  buildingToUpdate.getBuildingName() 
							+ " building has been updated.</h3></div><br />";
					
					url = "BuildingListServlet";

					request.setAttribute("message", message);
					request.setAttribute("loggedInAdminUser", loggedInAdminUser);	

				
					
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
		//url = "LoginServlet";
		response.sendRedirect(DbConnect.urlRedirect());
		return;
		}
		

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
