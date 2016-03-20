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
import model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(description = "This is called by login.jsp, gets & validates username and passwd, then goes to LoginServlet", urlPatterns = { "/LoginController" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session; 
	private String url;
	
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
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGet(request, response);
		String message = "";
		request.setAttribute("message", message); //TODO WHAT AM I DOING HERE??

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
			boolean authenticated = uh.authenticateUser(username, encryptedPass); //see if the person is UGA affiliated

			if (authenticated){ //if a non-empty object sent back (has user data meaning they were authenticated)
				message = "UGA person!!"; //for testing
				
				//TODO
				// get user's MyID from CAS authentication
				String userMyID = "ganix";
				String fname = "Ginger";
				String lname = "Nix";
				String email = "ganix@uga.edu";
				
				User loggedInUser = new User(); // create the user object to pass forward
				
				loggedInUser.setMyID(userMyID);
				loggedInUser.setUserFirstName(fname);
				loggedInUser.setUserLastName(lname);
				loggedInUser.setUserEmail(email);
						
				//----------------
				// once this person is authenticated as being UGA affiliated, check to is they have been banned
				// and if so, send them to the banned page to let them know and to have them log out
								
				 	//TODO
					boolean inTable = uh.inUserTable(loggedInUser.getMyID());
					
					System.out.println("Login Controller returned from inUserTable " + inTable);				
					
					if (inTable){
	
						// get the user's record ID from the user table to check to see if banned
						
						int recordID = uh.getRecordID(loggedInUser.getMyID());
						
						System.out.println("Login Controller get user Record ID 1 " + recordID);
					
						if(uh.alreadyBanned(recordID)) {
							// since they have already been banned, send them to a page telling them 

							session.setAttribute("loggedInUser", loggedInUser); //send info from CAS info
							System.out.println("Login Controller already banned! " + userMyID);
							
							url="user/bannedUser.jsp";
							//forward our request along
							RequestDispatcher dispatcher = request.getRequestDispatcher(url);
							dispatcher.forward(request, response);
							
						}else{ // they are in the table and not banned

							//they have not been banned for this period, so update the last time they logged in
							uh.updateLastLogin(loggedInUser.getMyID());	
							
						}
						
					}else{ //authenticated but not in user table, so add them

						// have logged in user's object populated above from CAS returned info
						
						//TODO last logged in date?		
						uh.insertUserTable(loggedInUser.getMyID(), loggedInUser.getUserFirstName(), loggedInUser.getUserLastName(), loggedInUser.getUserEmail()); //need to send last logged in date?	
						
					}

					
					//invalidate current session, then get new session for our user (combats: session hijacking)
					session.invalidate();
					session=request.getSession(true);
					
					
					session.setAttribute("loggedInUser", loggedInUser);
					session.setAttribute("message", message);

					url="index.html";
					
					//forward our request along
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);

				
			
			}else{// user doesn't exist, redirect to previous page and show error
			 
				message = "Error: Not Valid UGA Credentials";
				session.setAttribute("message", message);

				System.out.println("Login Controller: user doesn't exist!");

				url = "user/login.jsp";
				
				//forward our request along
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);

			}
				
		}
	

}
