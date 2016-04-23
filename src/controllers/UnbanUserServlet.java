package controllers;
/**
 * @author: Ginger Nix added session checks, admin role checks, logged in user checks, messages to forward, etc
 * @creator: Ronnie Xu
 * 
 * This servlet is called when the admin cicks on Unban button on the row of a banned
 * user. It takes the user and unbans them and sends them back to view the banned users
 * with a message.
 * 
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.UnbanUserQuery;
import helpers.UserHelper;
import model.Admin;
import model.DbConnect;
import model.User;

/**
 * Servlet implementation class UnbanUserServlet
 */
@WebServlet({ "/UnbanUserServlet", "/unban" })
public class UnbanUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;  
    private String url = "";
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnbanUserServlet() {
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

					// unban the user
					int bannedRecdID = Integer.parseInt(request.getParameter("bannedRecdID"));
					UnbanUserQuery uuq = new UnbanUserQuery();		
					uuq.unbanUser(bannedRecdID);
					
					// get the unbanned user's my id to get more info for the message
					String myID = request.getParameter("myID");

					UserHelper uh = new UserHelper();
					User user = new User();
					user = uh.getUserInfo(myID);
					
					message = "<br /><br /><div align='center'><h3>User with the MyID '"
							+ user.getMyID() + "' and name is '"
							+ user.getUserFirstName() + " " + user.getUserLastName()
							+ "' is no longer banned.</h3></div>";							
					
					url = "/banread";
		
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
					
		request.setAttribute("message", message);	

		//Redirect to BanList
		url = "/banread";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}



}
