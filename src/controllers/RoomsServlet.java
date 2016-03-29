//@author: Ginger Nix

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
 * Servlet implementation class RoomsServlet
 */
@WebServlet("/RoomsServlet")
public class RoomsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomsServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		
		// get current session
		HttpSession session = request.getSession();
		
		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");

		// loads building list from db, create dropdown for list, and output as String
		BuildingSelectQuery bsq = new BuildingSelectQuery();
		bsq.doBuildingRead(); 
		
		// get a list of all the active building to list
		String buildings = bsq.getAllActiveBuildings();
		
		System.out.println("RoomsServlet: buildings: " + buildings);
		
		// set session attribute
		session.setAttribute("buildings", buildings);
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);
		
		String url = "/admin/rooms.jsp";

		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
		

}
