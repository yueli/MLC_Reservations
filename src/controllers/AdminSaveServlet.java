/**
 * @author: Ginger Nix
 * 
 * The AdminSaveServlet is called when a user edits an already existing admin user.
 * If a user is editing their own data, there is a check to see if they are a super user, they can't change their role, 
 * and to check that they are not making themselves inactive. These checks are to prevent the state where there
 * are no active super admin users.
 * 
 **/

package controllers;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminUserHelper;
import model.Admin;
import model.DbConnect;

/**
 * Servlet implementation class AdminSaveServlet
 */
@WebServlet("/AdminSaveServlet")
public class AdminSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSaveServlet() {
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
		boolean update = true;
		
		
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
				
		System.out.println("AdminSaveServlet: logged in admin user's record ID = " + loggedInAdminUser.getAdminID());
			
			Admin adminUserBeingEdited = new Admin();
			
			//pull the hidden field for the user being edited's record id from the form adminEdit.jsp
			adminUserBeingEdited.setAdminID(Integer.parseInt(request.getParameter("adminID")));
	
			System.out.println("AdminSaveServlet: after setting admin ID for user being edited ");
			
			//pull the fields from the form adminEdit.jsp to populate the user being edited's object
			adminUserBeingEdited.setFname(request.getParameter("fname"));
			adminUserBeingEdited.setLname(request.getParameter("lname"));
			adminUserBeingEdited.setAdminMyID(request.getParameter("adminMyID"));
			adminUserBeingEdited.setRole(request.getParameter("role"));
			adminUserBeingEdited.setAdminStatus(Integer.parseInt(request.getParameter("status")));
					
			System.out.println("AdminSaveServlet: adminRecordID for edited user = " + adminUserBeingEdited.getAdminID());
			
			AdminUserHelper adminUserHelper = new AdminUserHelper();
		
			// check to see if logged in user is editing themselves (myID's)
			// and if so, check to see if logged in user has the role of super admin
			// and if so, they can't change their role from super admin nor can they inactivate themselves
			// because the admin users may end up with no one having a super admin role
			// and that wouldn't be good
			
			// if logged in user is editing themselves
			if (Objects.equals(loggedInAdminUser.getAdminMyID(), adminUserBeingEdited.getAdminMyID())){ 
				
				// if logged in user's role is super admin
				// and they are changing their role to something other than super admin
			
				if 	(	(Objects.equals(loggedInAdminUser.getRole(), "S" )) && 
						(!Objects.equals(adminUserBeingEdited.getRole(), "S" )) 
					)
				{ 
					// they can't change their own role - send them back to the editing page w/ a message
					// and the user being edited's info object to populate the table
						
					message = "A Super Admin user may not edit their own role.";	
					url = "AdminEditServlet";	
					update = false;
					System.out.println("AdminSaveServlet: before status compare status = " + adminUserBeingEdited.getAdminStatus());
	
				}else if (Objects.equals(adminUserBeingEdited.getAdminStatus(), 0 ) ){ 
					// editing themselves but roles are the same BUT they are making themselves inactive
					// which is against the rules
					
					message = "A Super Admin user may not make themselves inactive.";
					url = "AdminEditServlet";
					update = false;
				}
			}		
			
			// if the edited user can be updated, do it with pleasure
			if (update){
				adminUserHelper.updateAdminTable
					   (adminUserBeingEdited.getAdminID(), 
						adminUserBeingEdited.getFname(), 
						adminUserBeingEdited.getLname(), 
						adminUserBeingEdited.getAdminMyID(), 
						adminUserBeingEdited.getRole(), 
						adminUserBeingEdited.getAdminStatus());
	
				message = "";
				url = "AdminListServlet";	
				request.setAttribute("message", message);
				request.setAttribute("editedUser", adminUserBeingEdited);
				request.setAttribute("loggedInAdminUser", loggedInAdminUser);
	
			}
					
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
