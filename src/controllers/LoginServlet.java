// MLC LOGIN SERVLET

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Admin;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Servlet that controls the display of the login page", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String url;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		url = "UserHome";
		/*
		Admin loggedInAdminUser = new Admin();
		loggedInAdminUser.setAdminID(1);
		loggedInAdminUser.setAdminStatus(1);
		loggedInAdminUser.setRole("A");
		loggedInAdminUser.setAdminStatus(1);
		loggedInAdminUser.setAdminMyID("bbo89");
		
		User user = new User();
		user.setMyID("ganix");
		user.setUserEmail("bbo89@uga.edu");
		user.setUserRecordID(1);
		
		session.setAttribute("user", user);
		session.setAttribute("loggedInAdminUser", loggedInAdminUser); */
		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response); 
	}

}
