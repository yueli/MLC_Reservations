package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingListQuery;
import model.Admin;

/**
 * Servlet implementation class BuildingListServlet
 */
@WebServlet({ "/BuildingListServlet", "/buildinglist", "/buildings" })
public class BuildingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildingListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//get our current session
		this.session = request.getSession(); 
		
		// get admin user object from session
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 

		BuildingListQuery blq = new BuildingListQuery();
		blq.doRead();
		
		//get the html table of banned users
		String table = blq.getHTMLTable();
		
		//dispatch to the admin view
		request.setAttribute("table", table);
		
		String url = "/admin/buildings.jsp";
		
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		

		
		
	}
}
