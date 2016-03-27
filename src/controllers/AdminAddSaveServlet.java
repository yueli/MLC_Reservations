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
		String table = "";
		
		//get our current session
		session = request.getSession();
		message = (String) request.getAttribute("message"); 
		
		// blank message if nothing gotten in message attribute
		
		if (message == null || message.isEmpty()) {
			 message = "";
		}
		

		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");		

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
		
		alreadyInTable = adminUserHelper.inAdminTable(adminUserBeingAdded.getAdminMyID());
		
		if (alreadyInTable){
			// don't add, set message
			message = "The admin " + adminUserBeingAdded.getAdminMyID() + " already exists!";
			
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
		}
		
		// regardless if already exists or admin added, go back to the listing admins page w/ message
		
		url = "AdminListServlet";	
		
		request.setAttribute("message", message);
		request.setAttribute("loggedInUser", loggedInAdminUser);

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);			
		
	}

}
