package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.BanUserQuery;
import model.Banned;

/**
 * Servlet implementation class BanCompleted
 */
@WebServlet(
		name = "BanSuccess", 
		urlPatterns = { 
				"/BanSuccess", 
				"/bansuccess"
		})
public class BanCompletedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanCompletedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		//Get Data
		int userID = Integer.parseInt(request.getParameter("userID"));
		int adminID = Integer.parseInt(request.getParameter("adminID"));
		String banStart = request.getParameter("banStart");
		String banEnd = request.getParameter("banEnd");
		int penaltyCount = Integer.parseInt(request.getParameter("penaltyCount"));
		String description = request.getParameter("description");
		int status = Integer.parseInt(request.getParameter("status"));
		
		//Set up banned Object
		Banned banned = new Banned(userID, adminID, banStart, banEnd, penaltyCount, description, status);
		//Run insert query for banned
		BanUserQuery buq = new BanUserQuery();
		buq.banUser(banned);
		
		String table = "Ban is successful";
		
		
		request.setAttribute("table", table);
		
		String url = "/admin/bansuccess.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	
		
	}

}
