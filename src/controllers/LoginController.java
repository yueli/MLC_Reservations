//MLC LOGIN CONTROLLER


package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.UserHelper;
import model.PasswordService;
import model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(description = "This is called by login.jsp, gets & validates username and passwd, then goes to LoginServlet", urlPatterns = { "/LoginController" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session; 
	private String url;
	private int loginAttempts;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
    	doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGet(request, response);
		String errorMessage = "";
		request.setAttribute("errorMessage", errorMessage);

		//get our current session
		session = request.getSession();
		
			//pull the fields from the form
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			//encrypt the password 
			PasswordService pws = new PasswordService();
			String encryptedPass = pws.encrypt(password);
			
			//create a user helper class to make database calls, and call authenticate user method
			UserHelper uh = new UserHelper();
			User user = uh.authenticateUser(username, encryptedPass); //in UserHelper.java // AUTHENTICATION HARD CODED

			//we've found a user that matches the credentials
			//if(user != null){
				//invalidate current session, then get new session for our user (combats: session hijacking)
				session.invalidate();
				session=request.getSession(true);
				session.setAttribute("user", user);
				System.out.println("LoginController just about to call login servlet");
				url="/LoginServlet";
	
			//}
			// user doesn't exist, redirect to previous page and show error
			//else{ 
				//errorMessage = "Error: Unrecognized Username or Password<br>Login attempts remaining: "+(3-(loginAttempts));
				//request.setAttribute("errorMessage", errorMessage);

				//track login attempts (combats: brute force attacks)
				//session.setAttribute("loginAttempts", loginAttempts++);
				//url = "index.jsp";
			//}
		
		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	

}
