//MLC LOGIN CONTROLLER


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
import helpers.UserHelper;
import model.Admin;
import model.PasswordService;
import model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(description = "This is called by adminLogin.jsp, gets & validates username and passwd, then goes to AsminLoginServlet", urlPatterns = { "/AdminLoginController" })
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session; 
	private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	/**
	 * Process GET requests/responses (logout)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String message = "";
		request.getAttribute("message"); //TODO WHAT AM I DOING HERE??

		//get our current session
		session = request.getSession();
		
			//pull the fields from the form
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			//encrypt the password 
			PasswordService pws = new PasswordService();
			String encryptedPass = pws.encrypt(password);

			//see if they are UGA affiliated
			UserHelper uh = new UserHelper();
			
			boolean authenticated = uh.authenticateUser(username, encryptedPass); //see if the person is UGA affiliated
					
			if (authenticated){ //if a non-empty object sent back (has user data meaning they were authenticated)
				
				//TODO
				// get user's MyID from CAS authentication
				String userMyID = "ganix";
				String fname = "Ginger";
				String lname = "Nix";

				
				Admin loggedInAdminUser = new Admin(); // create the user object to populate w/ info from CAS
				loggedInAdminUser.setAdminMyID(userMyID);

				System.out.println("Admin Login Controller the user my id from CAS " + loggedInAdminUser.getAdminMyID());				
				
				//---------------
				//check to see if user is in the admin users table and if not send them back to the admin login page
							
				AdminUserHelper adminUserHelper = new AdminUserHelper();
				
				boolean inTable = adminUserHelper.inAdminUserTable(loggedInAdminUser.getAdminMyID());
				
				System.out.println("Admin Login Controller returned from inUserTable " + inTable);				
				
				if (inTable){

					// need admin user's record id, role, and status from our admin user table
					
					System.out.println("Admin Login Controller the user my id before calling get admin data " + loggedInAdminUser.getAdminMyID());				
					
					loggedInAdminUser = adminUserHelper.getAdminData(loggedInAdminUser.getAdminMyID());
					System.out.println("Admin Login Controller the user my id after calling get admin data " + loggedInAdminUser.getAdminMyID());				
								
					//send to admin home page
					session.setAttribute("loggedInAdminUser", loggedInAdminUser);
					url="admin/adminHome.jsp";
					
					//forward our request along
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
					
				}else{ 
					//send them back to the admin login page with a message
					message = "Not a valid admin user";
					
					session.setAttribute("message", message);
					System.out.println("admin login controller: message = " + message);
					
					url="admin/adminLogin.jsp";
					//forward our request along
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
				}
					
			}
		
	
	}
	
	
	
	/**
	 * Logs the user out
	 */
	public void logout() {
		session.invalidate();
		//MAY HAVE TO PUT IN CAS LOGING OUT??
	}

}
