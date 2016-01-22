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

/**
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
		HttpSession session = request.getSession();
		
		String startTime = (String) request.getParameter("startTime");
		String roomNumber = (String) request.getParameter("roomNumber");
		String currentDate = (String) request.getParameter("currentDate");
		
		// change date into long format
		DateTimeConverter dtc = new DateTimeConverter();
		currentDate = dtc.parseDateLong(currentDate);
		
		// set session attributes
		session.setAttribute("startTime", startTime);
		session.setAttribute("roomNumber", roomNumber);
		session.setAttribute("currentDate", currentDate);
		
		// set the url
		String url = "student/reservation.jsp";
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
				
	}

}
