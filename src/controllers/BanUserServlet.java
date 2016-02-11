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
 * Servlet implementation class banUserServlet
 */
@WebServlet(
		description = "Ban User", 
		urlPatterns = { 
				"/banUserServlet", 
				"/banUser"
		})
public class BanUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BanUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// ??? response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//Get data from form
		int banID = Integer.parseInt(request.getParameter(""));
		int studentID = Integer.parseInt(request.getParameter(""));
		int adminID = Integer.parseInt(request.getParameter(""));
		String banStart = request.getParameter("");
		String banEnd = request.getParameter("");
		int penaltyCount = Integer.parseInt(request.getParameter(""));
		String description = request.getParameter("");
		int status = Integer.parseInt(request.getParameter(""));
		
		//Get helper object to Add User to ban list.
		
		Banned ban = new Banned();
		
		ban.setBanID(banID);
		ban.setStudentID(studentID);
		ban.setAdminID(adminID);
		ban.setBanStart(banStart);
		ban.setBanEnd(banEnd);
		ban.setPenaltyCount(penaltyCount);
		ban.setDescription(description);
		ban.setStatus(status);
		
		
		BanUserQuery buq = new BanUserQuery(); 
		buq.banUser(ban);
		
		String url = "/banread";
		
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
