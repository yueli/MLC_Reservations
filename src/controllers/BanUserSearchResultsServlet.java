package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.BanUserSearchQuery;

/**
 * Servlet implementation class BanUserSearchResultsServlet
 */
@WebServlet({ "/BanUserSearchResultsServlet", "/searchuser" })
public class BanUserSearchResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanUserSearchResultsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//Get Data
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		
		
		
		
		//Pull Data from DB
		BanUserSearchQuery busq = new BanUserSearchQuery();
		busq.doRead(fname, lname);
		
		
		//Create Table with results
		String table = busq.getHTMLTable();
		
		//dispatch to the admin view
		request.setAttribute("table", table);
		//Forward to JSP
		String url = "/admin/banList.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
