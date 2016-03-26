package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.RoomsSelectQuery;

/**
 * Servlet implementation class RoomsListServlet
 */
@WebServlet("/RoomsListServlet")
public class RoomsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomsListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		String table = "";

		//get our current session
		session = request.getSession();

		// get building id from form - it's the id of the building they selected		
		int buildingID = Integer.parseInt(request.getParameter("buildingList")); 
		
		System.out.println("Rooms List Servlet: building id = " + buildingID);
		
		// using building id, create a table of a list of all the rooms in that building
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		table = rsq.ListRoomsInBuilding(buildingID);
		
		
		//forward our request along
		request.setAttribute("table", table);

		url = "admin/roomsList.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

		
		
		
		
		
	}

}
