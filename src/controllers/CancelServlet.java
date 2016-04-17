/** @author: Ginger Nix
 * 
 * The CancelServlet cancels the user's reservation and sends them back to the 
 * view reservations servlet that displays an edited list of their reservations (excluding the reservation
 * that was just canceled). It is called when the user confirms canceling their reservation.
 * 
 **/
package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.CancelQuery;
import model.DbConnect;
import model.Reservation;
import model.User;

/**
 * Servlet implementation class CancelServlet
 */
@WebServlet("/CancelServlet")
public class CancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	private String url;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelServlet() {
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
		this.session = request.getSession(false); 
		
		// if this session is not null (active/valid)
		if (this.session != null){	
						
			User user = (User) session.getAttribute("user");
			
			Reservation reservation = new Reservation();
			reservation.setReserveID(Integer.parseInt(request.getParameter("resv_id")));
			
			CancelQuery cq = new CancelQuery();
			cq.cancelReservation(reservation.getReserveID());
			
			// cancel reservation, then go back to the view servlet to get 
			// the user's reservations to list again
			
			message += "<div align='center'><h3> Reservation has been cancelled. </h3></div>";
			
			//forward our request along
			session.setAttribute("message", message);
			session.setAttribute("user", user);
				
			url = "ViewServlet";
			
			System.out.println("CancelServlet: message before leaving servlet = " + message);
		
		} else { // there isn't an active session.
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
