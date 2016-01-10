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

/**
 * Servlet implementation class BrowseServlet
 */
@WebServlet(
		description = "Servlet for browse.jsp", 
		urlPatterns = { 
				"/BrowseServlet", 
				"/Browse"
		})
public class BrowseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseServlet() {
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
		BuildingSelectQuery bsq = new BuildingSelectQuery("tomcatdb", "root", "");
		bsq.doBuildingRead();
		String buildings = bsq.getBuildingResults();


		// set session attribute
		session.setAttribute("buildings", buildings);
		
		// URL of the view to forward
		String url = "/browse.jsp";
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
