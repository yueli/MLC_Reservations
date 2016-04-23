/**
 * @author: Ginger Nix
 * 
 * The AdminAddServlet is called to create an empty form for the user to add another admin user.
 * From this page the user can fill out the form and click add (which calls the AdminAddSaveServlet) or
 * they can cancel and go back to the list of admins.
 * 
 **/
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
import model.DbConnect;

/**
 * Servlet implementation class AdminAddServlet
 */
@WebServlet("/AdminAddServlet")
public class AdminAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddServlet() {
        super();
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
		
		// get the current session
		session = request.getSession(false);
	
		
		// check to see if there is a valid session
		if (session != null){ // there is an active session

			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			if (loggedInAdminUser != null){
				
				// get info for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
				
					message = (String) request.getAttribute("message"); 
					
					System.out.println("BanUserFormServ: message at beg  = " + message);

					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}
				

					AdminUserHelper adminUserHelper = new AdminUserHelper();
			
					// creates table to display an empty form
					// some of the pull down options are determined by the logged in admin's role
					
			       table = adminUserHelper.createAddAdminForm(loggedInAdminUser);
			      
			       System.out.println("AdminAddServlet: logged in admin user's myid AT END = " + loggedInAdminUser.getAdminMyID());
					
			       request.setAttribute("message", message);
			       request.setAttribute("loggedInAdminUser", loggedInAdminUser);
			       request.setAttribute("table", table);
			
					url = "admin/adminAdd.jsp";
			
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
			
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);	
		
	}

}
