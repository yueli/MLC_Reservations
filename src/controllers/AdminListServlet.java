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

		// create admin user object
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");
		
		System.out.println("AdminListServlet: logged in user's role = " + loggedInAdminUser.getRole());
	
		// use the class AdminUserHelper
		AdminUserHelper adminHelper = new AdminUserHelper();
		
		// get the admin users
		String table = "";
		table = adminHelper.ListAdmins();
		
		//forward our request along
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		request.setAttribute("table", table);

		url = "admin/adminList.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

		
		
		
	}

}
