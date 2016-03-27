/*
 * @author: Ginger Nix
 * 
 * This servlet takes a filled out edit rooms form
 * and processes it by updating the admin users table
 * 
 */
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
		
		//get our current session
		session = request.getSession();	
		
		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
		
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
	
		System.out.println("AdminSaveServlet: fname = " + adminUserBeingEdited.getFname());
		
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
		}
		
		request.setAttribute("message", message);
		request.setAttribute("editedUser", adminUserBeingEdited);
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);	
				
	}

}
