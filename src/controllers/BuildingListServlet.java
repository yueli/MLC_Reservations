package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import helpers.BuildingListQuery;
//**By Ronnie Xu~****//
/**
 * Servlet implementation class BuildingListServlet
 */
@WebServlet({ "/BuildingListServlet", "/buildinglist", "/buildings" })
public class BuildingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		// TODO Auto-generated method stub
		//Create a Read helper object to perform query
		
		
		BuildingListQuery blq = new BuildingListQuery();
		blq.doRead();
		
		//get the html table of banned users
		String table = blq.getHTMLTable();
		
		//dispatch to the admin view
		request.setAttribute("table", table);
		
		String url = "/admin/buildings.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
		
		
	}
}
