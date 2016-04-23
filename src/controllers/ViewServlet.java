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
		
		// get the current session
		session = request.getSession(false);
	
		// check to see if there is a valid session
		if (session != null){ // there is an active session
			
			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			
			if (loggedInAdminUser != null){
				// get the role for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
					
					message = (String) request.getAttribute("message");	
					
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}

					User user = (User) session.getAttribute("user");

					// if from the Cancel Confirmation Servlet via go back to view w/o canceling
					// need to clear out message
					String noCancel = request.getParameter("noCancel");
			
					if(noCancel == null || noCancel.isEmpty()){
						session.removeAttribute("message");
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

							
					//forward our request along
					request.setAttribute("user", user);
					request.setAttribute("table", table);
					request.setAttribute("message", message);
					
					url = "user/view.jsp";	
					
		
				}  else if (role.equalsIgnoreCase("C") && status == 1){ 
					//------------------------------------------------//
					/*                VIEW FOR CLERK                  */
					//------------------------------------------------//
					
					// forwarding URL
					url = "AdminViewReservations";
					System.out.println("BanReadServlet: 4");
				} else {
					//------------------------------------------------//
					/*              NOT A VALID ROLE                  */
					//------------------------------------------------//
					// if a new session is created with no user object passed
					// user will need to login again
					session.invalidate();
					System.out.println("BanReadServlet: 5");
					response.sendRedirect(DbConnect.urlRedirect());
					return;
				}
			} else {
				//------------------------------------------------//
				/*            ADMIN USER INFO EXPIRED             */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				System.out.println("BanReadServlet: 6");
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			//url = "LoginServlet";
			System.out.println("BanReadServlet: 7");
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
