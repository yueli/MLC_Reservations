/* @author: Ginger Nix
 * 
 * This servlet gets the user's reservation and sends it to a jsp
 * where the user confirms that they want to cancel this reservation.
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

import helpers.ListUserReservationsQuery;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class CancelConfirmServlet
 */
@WebServlet("/CancelConfirmServlet")
public class CancelConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelConfirmServlet() {
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
		String table = "";
		
		//get our current session
		session = request.getSession();
		User user = (User) session.getAttribute("user");
		int userRecdID = user.getUserRecordID();
		
		Reservation reservation = new Reservation();
		
		reservation.setReserveID(Integer.parseInt(request.getParameter("resv_id")));
		System.out.println("CancelConfServ - resv_id = "+ reservation.getReserveID());
		
		// have reserve id from the parameter passed from view.jsp
		// use reserve id to get the rest of the record's data and format into a table	
		ListUserReservationsQuery lurq = new ListUserReservationsQuery();
		table = lurq.GetUserReservation(reservation.getReserveID(),userRecdID);
		
		System.out.println("CancelConfirmServlet: table = " + table);
		//forward our request along
		request.setAttribute("user", user);
		request.setAttribute("table",table);
		
		url = "user/confirmCancellation.jsp";	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		

	}

}
