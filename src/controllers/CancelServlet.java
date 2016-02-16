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
		
		//get our current session
		session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Reservation reservation = new Reservation();
		reservation.setReserveID(Integer.parseInt(request.getParameter("resv_id")));
		
		//HERE = 02-13-16 SAT NIGHT 12:30am
		// PUT SOMETHING HERE TO SET FREE to Y/1 FOR THIS RECORD SINCE HAVE THE RESV ID
		//CancelQuery cq = new CancelQuery();
		//cq.cancelReservation(reservation.getReserveID());
		
		// cancel reservation, then go back to the view servlet to get 
		// the user's reservations to list again
		
		//forward our request along
		request.setAttribute("user", user);
			
		url = "ViewServlet";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
