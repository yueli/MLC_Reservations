package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BanGetUserInfoQuery;
import helpers.BanUserQuery;
import helpers.UserHelper;
import model.Admin;
import model.Banned;
import model.DbConnect;
import model.User;


/**
 * @author: Ginger Nix - rewrote servlet
 * This servlet gets the user's my id to search in the user
 * table to see if the user exists, make sure the user is not already banned,
 * and ban the user
 * 
 */

/**
 * Servlet implementation class banUserServlet
 */
@WebServlet(
		description = "Ban User", 
		urlPatterns = { 
				"/BanUserServlet",  "/ban"
			
		})
public class BanUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;  
    private String url = "";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String table ="";
		String message = "";
		
		// get the current session
		session = request.getSession(false);
	
		
		// check to see if there is a valid session
		if (session != null){ // there is an active session

			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			if (loggedInAdminUser != null){
				
				// get the role for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){

					message = (String) request.getAttribute("message"); 
					
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}
					
					BanGetUserInfoQuery bguiq = new BanGetUserInfoQuery();
					User user = new User();
					
					//Get data from form
					String MyID = request.getParameter("userMyID");
					System.out.println("BanUserServlet: after getting user myID from form: "  + MyID);
					
					//Check to see if user is in the user table
					UserHelper userHelper = new UserHelper();
					boolean inUserTable = userHelper.inUserTable(MyID);
					
					System.out.println("BanUserServlet: after in user table : "  + inUserTable);
					
					if (!inUserTable){
						// they are not in the user table, so can't ban them
						message = "<br /><br /><div align='center'><h3>The user with the UGA My Id '" 
									+ MyID  + "' is not a user of this application (they have never logged into this site)."
									+ "</h3></div>";
						url = "BanUserFormServlet";
						
						
					}else{
						// user to be banned is in the user table
					
						// need to get the user's record id to see if in banned table already
						int userRecdID = userHelper.getRecordID(MyID)	;
						
						user = bguiq.userData(userRecdID);						
						
						//Check if student is already banned or not
						
						boolean bannedAlready  = userHelper.alreadyBanned(userRecdID);
						
					
						if(bannedAlready){
							
							message = "<br /><br /><div align='center'><h3>The user with the UGA MyId '" 
									+ MyID  + "' is already banned."
									+ "</h3></div>";
							url = "BanUserFormServlet";
							
						}
						
						else{ //User is not already banned, so ban them

							// get date for start banned date							
							String currentDate = "";	
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date date = new Date();	
							currentDate = dateFormat.format(date);
							
							Banned userToBan = new Banned();
							
							userToBan.setUserRecdID(userRecdID);
							userToBan.setAdminID(loggedInAdminUser.getAdminID());
							userToBan.setBanStart(currentDate);
							userToBan.setPenaltyCount(2); // default to 2 so this user will be listed in the view banned users page
							userToBan.setDescription("Banned by Admin: " + loggedInAdminUser.getFname() + " " + loggedInAdminUser.getLname());
							userToBan.setStatus(1);
							
							BanUserQuery buq = new BanUserQuery();
							
							// ban the user
							buq.banUser(userToBan);
							
							// free up banned user's resv where they are the primary user
							buq.cancelBannedUserReservations(userRecdID);
							
							message = "<br /><br /><div align='center'><h3>The user with the UGA MyId " 
									+ MyID  + " has been banned."
									+ "</h3></div>";
							url = "BanReadServlet";
							
							request.setAttribute("table", table);
							request.setAttribute("message", message);

							
						}
					
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
			//url = "LoginServlet";
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
			
			
			System.out.println("BanUserServlet: message at end = " + message);
			System.out.println("BanUserServlet: url at end = " + url);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			
		
		
	
	}



}
