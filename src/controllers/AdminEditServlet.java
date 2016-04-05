/* @author: Ginger Nix
 * 
 * This servlet creates a form for admins to add other admins
 * 
 */
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
 * Servlet implementation class AdminEditServlet
 */
@WebServlet("/AdminEditServlet")
public class AdminEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
	      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEditServlet() {
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

		String table = "";
		String message = "";
		
		//get our current session
		session = request.getSession();
		message = (String) request.getAttribute("message"); 
		
		// blank message if nothing gotten in message attribute
		
		if (message == null || message.isEmpty()) {
			 message = "";
		}
		
			
		System.out.println("AdminEditServlet: beginning - message = " + message);

		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");		
			
		System.out.println("AdminEditServlet: loggedInAdminUser adminMyID = " + loggedInAdminUser.getAdminMyID());

		 
		//hidden parameter = admin record id of the person to edit from jsp
		int adminID = Integer.parseInt(request.getParameter("adminID")); 
		
		System.out.println("AdminEditServlet: adminID = " + adminID);
		
		AdminUserHelper adminUserHelper = new AdminUserHelper();
	
		// creates table w/ admin user's info to edit
		// some of the pull down options are determined by the logged in admin's role
       table = adminUserHelper.getAdminInfo(adminID, loggedInAdminUser);
		
       System.out.println("AdminEditServlet: after getAdminInfo - message = " + message);

       	request.setAttribute("message", message);
        request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		request.setAttribute("table", table);

		url = "admin/adminEdit.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

		

	}

}
