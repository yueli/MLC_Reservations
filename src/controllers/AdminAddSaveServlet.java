/**
 * @author: Ginger Nix
 * 
 * AdminSaveServlet is called when the admin user add form is filled out and the user clicks on save.
 * If the admin user being added is already in the admin table, the user is sent back to the admin
 * user add form with a message indicating that the admin user already exists.
 * 
 **/

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
import model.Admin;
import model.DbConnect;

/**
 * Servlet implementation class AdminAddSaveServlet
 */
@WebServlet("/AdminAddSaveServlet")
public class AdminAddSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddSaveServlet() {
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
		
		//get our current session
		this.session = request.getSession(false); 
		
		// if this session is not null (active/valid)
		if (this.session != null){	
			
				message = (String) request.getAttribute("message"); 
				
				// blank the message if nothing gotten in message attribute
				if (message == null || message.isEmpty()) {
					 message = "";
				}
				
		
				// create admin user object w/ session data on the logged in user's info
				Admin loggedInAdminUser = (Admin) request.getAttribute("loggedInAdminUser");		
		
				// create new admin object to hold data entered in the form to add an admin user
				Admin adminUserBeingAdded = new Admin();
				
				//pull the fields from the form adminEdit.jsp to populate the user being edited's object
				adminUserBeingAdded.setFname(request.getParameter("fname"));
				adminUserBeingAdded.setLname(request.getParameter("lname"));
				adminUserBeingAdded.setAdminMyID(request.getParameter("adminMyID"));
				adminUserBeingAdded.setRole(request.getParameter("role"));
				adminUserBeingAdded.setAdminStatus(Integer.parseInt(request.getParameter("status")));
		
				AdminUserHelper adminUserHelper = new AdminUserHelper();
			
				// check to see if an admin user already exists with this myID which is supposed to be unique
				boolean alreadyInTable = false;
				
				System.out.println("AdminAddSaveServlet: before call to alreadyInTable: MyID to be checked: " + adminUserBeingAdded.getAdminMyID());
				
				alreadyInTable = adminUserHelper.inAdminTable(adminUserBeingAdded.getAdminMyID());
				
				if (alreadyInTable){
					// don't add, set message
					message = "<br /><br /><div align='center'><h3>The admin user " +
								adminUserBeingAdded.getFname() + " " + adminUserBeingAdded.getLname()   + 
								" with the UGA MyID ' " + adminUserBeingAdded.getAdminMyID() + 
								"' already exists!</h3></div>";
					
					url = "AdminAddServlet";
					
				}else{
					//add new admin user 
					adminUserHelper.insertAdminTable(
							adminUserBeingAdded.getAdminMyID(), 
							adminUserBeingAdded.getFname(), 
							adminUserBeingAdded.getLname(), 
							adminUserBeingAdded.getRole(),
							adminUserBeingAdded.getAdminStatus()
							);
					
					message = "Admin added";
					url = "AdminListServlet";
				}	
				
			request.setAttribute("message", message);
			request.setAttribute("loggedInUser", loggedInAdminUser);
			
		} else { // there isn't an active session.
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
