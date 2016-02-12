package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DateTimeConverter;
import model.TimeConverter;

/**
 * @author Brian Olaogun
 * Servlet implementation class BrowseReserve
 */
@WebServlet({ "/BrowseReserve", "/BrowseReservation", "/Reservation", "/Browse_Reservation" })
public class BrowseReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseReserveServlet() {
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
		// get the current session
		HttpSession session = request.getSession();
		
		// get parameters from request & session
		String startTime = (String) request.getParameter("startTime");
		String roomNumber = (String) request.getParameter("roomNumber");
		String currentDate = (String) request.getParameter("currentDate");
		String building = (String) session.getAttribute("building");
		
		// change date into long format
		DateTimeConverter dtc = new DateTimeConverter();
		currentDate += " 00:00:00"; // to make it a date time to parse into long format
		currentDate = dtc.parseDateLong(currentDate);
		
		// change time to 12 hour format
		TimeConverter tc = new TimeConverter();
		startTime = tc.convertTimeTo12(startTime);
		
		// TODO query to see if user has any reservations for current day
		
		// set session attributes
		session.setAttribute("startTime", startTime);
		session.setAttribute("roomNumber", roomNumber);
		session.setAttribute("currentDate", currentDate);
		session.setAttribute("building", building);
		
		// set the forwarding url
		String url = "user/reservation.jsp";
		
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
				
	}

}
