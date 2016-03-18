package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminScheduleAddServlet2
 */
@WebServlet({ "/AdminScheduleAddServlet2", "/add-schedule" })
public class AdminScheduleAddServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminScheduleAddServlet2() {
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
		/*
		 * This is creating a new session ----> HttpSession session = request.getSession(). 
		 * 
		 * Calling the method with no arguments, creates a session if one does not already 
		 * exist which is associated with the request. 
		 * 
		 * Ideally we should create a session a login.
		 * 
		 * The false value checks if there is an existing session ----> HttpSession session = request.getSession(false).
		 * Structure your code like below in all servlets except the login servlets.
		 */
		this.session = request.getSession(false); 
		
		// if this session is not null (active/valid)
		if(this.session != null){
			
			// Put Your functions here
			
		
			
		// if session is null (not active/valid)	
		} else {
			
			// go back to login
			url = "[INSERT LOGIN PAGE HERE]";
			
		}
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
