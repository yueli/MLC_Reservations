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
import model.PasswordService;
import model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(description = "This is called by login.jsp, gets & validates username and passwd, then goes to LoginServlet", urlPatterns = { "/LoginController" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session; 
	private String url;
	private int loginAttempts;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
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
		//User has clicked the logout link
		session = request.getSession();

		//check to make sure we've clicked link
		if(request.getParameter("logout") !=null){

			//logout and redirect to frontpage
			logout();
			url="user/login.jsp";
		}

		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
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
			User loginUser = uh.authenticateUser(username, encryptedPass); //see if the person is UGA affiliated
			//NOTE: the authenticate method will not return the user's record ID
			
			//student user will come back null if not authenticated and come back w/ data if authenticated
			System.out.println("Login Controller returned from authentication studentUser.myID =  " + loginUser.getMyID());	
			
			if (loginUser.getMyID() != null){ //if a non-empty object sent back (has user data meaning they were authenticated)
				// create a Student User object 
				User user = new User();
				
				user.setMyID(loginUser.getMyID());
				user.setUserFirstName(loginUser.getUserFirstName());
				user.setUserLastName(loginUser.getUserLastName());
				user.setUserEmail(loginUser.getUserEmail());
				
				message = "UGA person!!"; //for testing
				
				//----------------
				// once this person is authenticated as being UGA affiliated, check to is they have been banned
				// and if so, send them to the banned page to let them know and to have them log out
				
		
					//---------------
					//check to see if user is in the users table, and if not add, and if so, set last login time
			
					boolean inTable = uh.inUserTable(user.getMyID());
					System.out.println("Login Controller returned from inUserTable " + inTable);				
					
					if (inTable){
					
						
						//--------------------------------
						//get the user record id to pass through sessions to make queries easier
						int recordID = uh.getRecordID(user.getMyID());
						user.setUserRecordID(recordID);
						System.out.println("Login Controller get user Record ID 1 " + recordID);
						
						if(uh.alreadyBanned(user.getUserRecordID())) {
							// since they have already been banned, send them to a page telling them 
							
							session.setAttribute("user", user);
							System.out.println("Login Controller already banned! " + user.getMyID() + " " + user.getUserFirstName() + " " + user.getUserLastName() + " " + user.getUserEmail());				
							
							url="user/bannedUser.jsp";
							//forward our request along
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);
							
						}else{
							//they have not been banned for this period, so update the last time they logged in
							uh.updateLastLogin(user.getMyID());	//update the last login field
						}
						
					}else{ //not in user table, so add them
						uh.insertUserTable(user.getMyID(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail());
					}
					
					
					
					
					System.out.println("Login Controller get user Record ID 2 " + user.getUserRecordID());				
					
					//--------------------------------
					
					loginUser = null; //null out values in object since don't want the password to stick around
					
					//invalidate current session, then get new session for our user (combats: session hijacking)
					session.invalidate();
					session=request.getSession(true);
					session.setAttribute("user", user);
					session.setAttribute("message", message);

					System.out.println("Login Controller MyID and first name " + user.getMyID() + " " + user.getUserFirstName() + " " + user.getUserLastName() + " " + user.getUserEmail());				
					
					url="user/home.jsp";
				
				
			
			}else{// user doesn't exist, redirect to previous page and show error
			 
				message = "Error: Not Valid UGA Credentials";
				session.setAttribute("message", message);

				System.out.println("Login Controller: user doesn't exist!");

				url = "user/login.jsp";
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
