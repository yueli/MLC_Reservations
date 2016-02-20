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

import helpers.UserHelper;
import model.Admin;
import model.PasswordService;

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
		
		doPost(request, response);
		
		//User has clicked the logout link
		//session = request.getSession();

/*		//check to make sure we've clicked link
		if(request.getParameter("logout") !=null){

			//logout and redirect to frontpage
			logout();
			url="user/login.jsp";
		}

		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGet(request, response);
		String message = "";
		request.setAttribute("message", message);

		//get our current session
		session = request.getSession();
		
			//pull the fields from the form
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			//encrypt the password 
			PasswordService pws = new PasswordService();
			String encryptedPass = pws.encrypt(password);
			
			//set up connection to the database
			UserHelper uh = new UserHelper(); 
			
			//see if they are UGA affiliated
			Admin loginUser = uh.authenticateAdminUser(username, encryptedPass); //see if the person is UGA affiliated
			// the only thing returned in the AdminUser object is their MyID or null
			
			//user will come back null if not authenticated and come back w/ data if authenticated
			
			if (loginUser.getAdminMyID() != null){ //if a non-empty object sent back (has user data meaning they were authenticated)

				
				//---------------
				//check to see if user is in the admin users table and if not send them back to the admin login page
							
				boolean inTable = uh.inAdminUserTable(loginUser.getAdminMyID());
				System.out.println("Admin Login Controller returned from inUserTable " + inTable);				
				
				if (inTable){

					message = "You are an active admin!!"; //for testing
					System.out.println("Admin Login Controller in if inTable 1 ");	
					
					Admin adminUser = new Admin(); // create the admin user object to pass forward
					adminUser.setAdminMyID(loginUser.getAdminMyID());		
					
					System.out.println("Admin Login Controller in if inTable 2 ");
					
					// get the rest of the data from the Admin (users) table to add to object
					adminUser = uh.getAdminInfo(adminUser.getAdminMyID());
								
					System.out.println("Admin Login Controller in if inTable 3 ");
					
					//send to admin home page
					loginUser = null; //null out values in object since don't want the password to stick around
					
					//invalidate current session, then get new session for our user (combats: session hijacking)
					session.invalidate();
					session=request.getSession(true);
					
					session.setAttribute("adminUser", adminUser);
					session.setAttribute("message", message);

					url="admin/adminHome.jsp";
					
					
				}else{ 
					//send them back to the admin login page with a message
					message = "Not a valid admin user";
					session.setAttribute("message", message);
					System.out.println("admin login controller: message = " + message);
					url="admin/adminLogin.jsp";
				}
					
			}
		
		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
	
	
	/**
	 * Logs the user out
	 */
	public void logout() {
		session.invalidate();
		//MAY HAVE TO PUT IN CAS LOGING OUT??
	}

}
