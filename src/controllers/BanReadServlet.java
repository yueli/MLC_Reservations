package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.BannedSelectQuery;

/**
 * Servlet implementation class BanReadServlet
 */
@WebServlet({ "/BanReadServlet", "/banread" })
public class BanReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BanReadServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//Create a Read helper object to perform query
		BannedSelectQuery bsq = new BannedSelectQuery("tomcatdb", "root", "3755"); 
		bsq.doRead();
		
		//get the html table of banned users
		String table = bsq.getHTMLTable();
		
		//dispatch to the admin view
		request.setAttribute("table", table);
		
		String url = "/admin/admin.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
		
		
	}



}
