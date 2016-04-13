/** 
 * @author: Ginger Nix
 * 
 * This servlet lists all the admins in the admin table
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
import model.User;

/**
 * Servlet implementation class AdminListServlet
 */
@WebServlet("/AdminListServlet")
public class AdminListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminListServlet() {
        super();
        // TODO Auto-generated constructor stub
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

		//get our current session
		session = request.getSession();


		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
		System.out.println("AdminListServlet: logged in admin user adminMyID = " + loggedInAdminUser.getAdminMyID());
		System.out.println("AdminListServlet: logged in w/role = " + loggedInAdminUser.getRole());

		String message = "";
		message = (String) request.getAttribute("message"); 
		
		System.out.println("AdminListServlet: message received is: " + message);
		
		// blank out message if nothing gotten in message attribute
		
		if (message == null || message.isEmpty()) {
			 message = " ";
		}

	
		// use the class AdminUserHelper
		AdminUserHelper adminHelper = new AdminUserHelper();
		
		// get the admin users
		String table = "";
		table = adminHelper.ListAdmins();
		
		//forward our request along
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		request.setAttribute("table", table);
		request.setAttribute("message", message);


		url = "admin/adminList.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

		
		
		
	}

}
