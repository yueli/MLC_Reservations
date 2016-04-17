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

		System.out.println("Login Controller 1");				
		
		//get our current session
		session = request.getSession();
			//see if they are UGA affiliated
		System.out.println("Login Controller 2");	
		//set up connection to the database
		UserHelper uh = new UserHelper(); 
		System.out.println("Login Controller 3");	
		boolean authenticated = true;
		
				message = "UGA person!!"; //for testing
				
				//TODO
				// get user's MyID from CAS authentication
				String userMyID = "ganix";
				String fname = "Ginger";
				String lname = "Nix";
				String email = "ganix@uga.edu";
				
				User user = new User(); // create the user object to pass forward
				
				user.setMyID(userMyID);
				user.setUserFirstName(fname);
				user.setUserLastName(lname);
				user.setUserEmail(email);
	
				AdminUserHelper auh = new AdminUserHelper();
				
				Admin loggedInAdminUser = new Admin(); // create the user object to pass forward

				loggedInAdminUser = auh.getAdminData(user.getMyID());
				
				//----------------
				// once this person is authenticated as being UGA affiliated, check to is they have been banned
				// and if so, send them to the banned page to let them know and to have them log out
								
					// send the logged in user's myID  to check if in the user's table
					
					boolean inTable = uh.inUserTable(user.getMyID());
					
					System.out.println("Login Controller returned from inUserTable " + inTable);				
					
					if (inTable){
	
						// get the user's record ID from the user table to check to see if banned
						
						int recordID = uh.getRecordID(user.getMyID());
						
						System.out.println("Login Controller get user Record ID 1 " + recordID);
					
						if(uh.alreadyBanned(recordID)) {
							// since they have already been banned, send them to a page telling them 

							session.setAttribute("user", user); //send info from CAS info
							System.out.println("Login Controller already banned! " + userMyID);
							
							url="user/bannedUser.jsp";
							
						}else{ // they are in the table and not banned

							//they have not been banned for this period, so update the last time they logged in
							uh.updateLastLogin(user.getMyID());	
							
						}
						
					}else{ //authenticated but not in user table, so add them

						// have logged in user's object populated above from CAS returned info
						
						//TODO last logged in date?		
						uh.insertUserTable(user.getMyID(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail()); //need to send last logged in date?	
						
					}

					
					//invalidate current session, then get new session for our user (combats: session hijacking)
					session.invalidate();
					session=request.getSession(true);
					
					//url = "admin/adminHome.jsp";
					url = "index.html";
					
					user.setUserRecordID(19);
					
					
					System.out.println("Login Controller user recd id " + user.getUserRecordID() );
					session.setAttribute("loggedInAdminUser", loggedInAdminUser);
					session.setAttribute("loggedInUser", user);
					session.setAttribute("message", message);
					session.setAttribute("user", user);
					
					//forward our request along
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);

				
			

				
		}
	

}
