package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.BuildingListQuery;
import helpers.SearchGetBuildingsQuery;

/**
 * Servlet implementation class SearchGetBuildingsServlet
 */
@WebServlet({ "/SearchGetBuildingsServlet", "/searchbuildings" })
public class SearchGetBuildingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchGetBuildingsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		//Get all buildings && Create list for user to pick
		SearchGetBuildingsQuery sgbq = new SearchGetBuildingsQuery();
		sgbq.doRead();

		//get the html table of banned users
		//String table = sgbq.getHTMLTable();
		String table = sgbq.getBuildingResults();
		//dispatch to the Search - Building Selection view
		request.setAttribute("table", table);
		
		String url = "/user/searchgetbuildings.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
		
		
	}
}
