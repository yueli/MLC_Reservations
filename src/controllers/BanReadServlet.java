package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BannedSelectQuery;
import model.Admin;
import model.DbConnect;
import model.User;

//**By Ronnie Xu~****/
/**
 * @author: Ginger Nix - fixed all bugs and contributed code
 * 
 * This servlet gets all the currently banned students and puts them
 * in a table for the user to view. If there are no banned students, the
 * table is empty w/ a message
 */

/**
 * Servlet implementation class BanReadServlet
 */
@WebServlet({ "/BanReadServlet", "/banread" })
public class BanReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private String url = "";
	private String message = "";
	
    public BanReadServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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
					
					System.out.println("**BanReadServlet: message at beg = " + message);
					
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}
						
					//Create a Read helper object to perform query
					BannedSelectQuery bsq = new BannedSelectQuery(); 
					bsq.doRead();
					
					System.out.println("BanReadServlet: doPost: back from doRead");
					
					//get the html table of banned users
					String table = bsq.getHTMLTable();
					
					System.out.println("BanReadServlet: doPost: after getHTMLTable");
					
					request.setAttribute("table", table);
					request.setAttribute("message", message);
					request.setAttribute("loggedInAdminUser", loggedInAdminUser);
					
					url = "/admin/banList.jsp";
					
					
				}  else if (role.equalsIgnoreCase("C") && status == 1){ 
					//------------------------------------------------//
					/*                VIEW FOR CLERK                  */
					//------------------------------------------------//
					
					// forwarding URL
					url = "AdminViewReservations";
					System.out.println("BanReadServlet: 4");
				} else {
					//------------------------------------------------//
					/*              NOT A VALID ROLE                  */
					//------------------------------------------------//
					// if a new session is created with no user object passed
					// user will need to login again
					session.invalidate();
					System.out.println("BanReadServlet: 5");
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
				System.out.println("BanReadServlet: 6");
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
			System.out.println("BanReadServlet: 7");
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}

		System.out.println("BanReadServlet: 8");
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}



}
