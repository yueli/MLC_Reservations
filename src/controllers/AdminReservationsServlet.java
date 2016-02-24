package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingSelectQuery;
import model.Admin;

/**
 * Servlet implementation class AdminReservations
 */
@WebServlet({ "/AdminReservations", "/admin-reservations" })
public class AdminReservationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReservationsServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = "";
		
		// check to see if there is a valid session
		if (session != null){ // there is an active session
			
			// get the role for the currently logged in admin user.
			Admin adminUser = (Admin) session.getAttribute("adminUser");
			String role = adminUser.getRole();
			int status = adminUser.getAdminStatus();
			
			// push content based off role
			if(role.equalsIgnoreCase("admin") && status == 1){
				//---------------------------//
				     // VIEW FOR ADMIN
				//---------------------------//
				
				// loads building list from database, create dropdown for list, and output as String
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				bsq.doBuildingRead();
				String buildings = bsq.getBuildingResults();
				
				
				
				// forwarding URL
				url = "admin/reservations.jsp";
				
				// set session attributes
				session.setAttribute("buildings", buildings);
			} else { 
				//---------------------------//
				     // VIEW FOR CLERK
				//---------------------------//
				
				// forwarding URL
				url = "admin/reservations-clerk.jsp";
				
				// set session attributes
			}
			
		} else { // there isn't an active session.
			url = "admin/adminLogin.jsp";
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
