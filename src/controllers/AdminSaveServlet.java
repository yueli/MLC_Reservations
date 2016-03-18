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
 * Servlet implementation class AdminSaveServlet
 */
@WebServlet("/AdminSaveServlet")
public class AdminSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSaveServlet() {
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

		String table = "";
		String message = "";
		
		//get our current session
		session = request.getSession();	

		// create admin user object
		Admin adminUser = (Admin) session.getAttribute("adminUser");

		int adminRecordID = adminUser.getAdminID();
		
		//pull the fields from the form
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String adminMyID = request.getParameter("adminMyID");
		String role = request.getParameter("role");
		String status = request.getParameter("status");

		System.out.println("AdminSaveServlet: fname = " + fname);


		
		AdminUserHelper adminUserHelper = new AdminUserHelper();
	
		// update the admin table for this edited admin
	//HERE HERE 	SUNDAY NIGHT
		
		adminUserHelper.updateAdmin(adminRecordID, fname, lname, adminMyID, role, status);
		
		request.setAttribute("table", table);

		url = "AdminListServlet";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

		
	}

}
