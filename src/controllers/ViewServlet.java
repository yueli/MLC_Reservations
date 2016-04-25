/* @Author Ginger Nix & Victoria Chambers */
/* 
 * This servlet lists all the reservations that a user has from this hour forward.
 * Each line for the reservation can have a 'too late to check in' message, a button to cancel the reservation,
 * and a button to check into a room.
 * 
 * 
 */

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
import model.DbConnect;
//import org.apache.tomcat.jni.User;
import model.User;

import helpers.ListUserReservationsQuery;


/**
 * Servlet implementation class ViewServlet
 */
@WebServlet(
		description = "lists the reservations for a student", 
		urlPatterns = { 
				"/ViewServlet", 
				"/View"
		})
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;

	//private String MyID;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
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
		
		String message = " ";
		String table = "";
				
		// get current session
		session = request.getSession(false);
		
		// If session is active/valid
		if(session != null){
			User user = (User) session.getAttribute("user");
			 
			if(user != null) { // run code if user object is not null
					message = (String) request.getAttribute("message"); 
					
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}
					ListUserReservationsQuery lurq = new ListUserReservationsQuery();
		
					// get all the reservations the user has primary and secondary
					// and put them into a table. If no reservations, send message
					
					try {
						System.out.println("Fell into try/catch");
						table = lurq.ListUserReservations(user.getUserRecordID());
					}
					catch (Exception e){
						System.out.println("Inside catch");
						table = "";
					}
					
					System.out.println("After try/catch");
					
					if(table == null || table.isEmpty()){
						message="<div align='center'><h3> You have no current reservations. </h3></div>";
						System.out.println("View Servlet: no records found");
						
					}
					else {
						System.out.println("View Servlet: something in table ");
						
					}
					
					url = "user/view.jsp";
					//forward our request along
					request.setAttribute("user", user);
					request.setAttribute("table", table);
					request.setAttribute("message", message);

			} else {
				//------------------------------------------------//
				/*               USER INFO EXPIRED                */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				
				session.invalidate();
				CASLogoutServlet.clearCache(request, response);
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
			
		} else {
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
		
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}

					
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}
}
