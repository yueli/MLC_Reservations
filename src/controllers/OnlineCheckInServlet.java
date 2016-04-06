/* @author: Ginger Nix
 * 
 * Checks in a user online
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

import helpers.ReservationQuery;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class OnlineCheckInServlet
 */
@WebServlet("/OnlineCheckInServlet")
public class OnlineCheckInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   private HttpSession session; 
	    private String url;
	    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnlineCheckInServlet() {
        super();
        // TODO Auto-generated constructor stub
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

		String message = "";
		
    	//get our current session
        session = request.getSession();
 		
        User user = (User) session.getAttribute("user");		
        int userRecdId = user.getUserRecordID();
        
    	Reservation reservation = new Reservation();
		reservation.setReserveID(Integer.parseInt(request.getParameter("resv_id")));
		int reservationRecdId = reservation.getReserveID();
		
		ReservationQuery resvQuery = new ReservationQuery();
		
		if (resvQuery.checkInUser(reservationRecdId, userRecdId)) { //if successfully checked in user
			
			message += "<div align='center'><h2> Successfully checked in! </h2></div>";
			
			url = "user/qrCheckInSuccess.jsp";
		
		}else{
			url="user/qrError.jsp"; 
			message += "Error: The reservation can not be checked in. Please contact adminstrators for help.";
			
		}
        
		session.setAttribute("user", user);
		session.setAttribute("message", message);

		//forward our request along
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
      
	}

}
