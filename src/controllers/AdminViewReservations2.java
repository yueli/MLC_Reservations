package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.AdminReservationsSelectQuery;
import helpers.BuildingSelectQuery;
import model.DateTimeConverter;

/**
 * Servlet implementation class AdminViewReservations2
 */
@WebServlet({ "/AdminViewReservations2", "/view-reservations?building?date" })
public class AdminViewReservations2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminViewReservations2() {
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
		HttpSession session = request.getSession();
		// get session variables
		int bldg = (Integer) session.getAttribute("bldg");
		String currentDate = (String) session.getAttribute("currentDate");
		String inputtedDate = request.getParameter("datepicker");
		
		if (inputtedDate != null){
			currentDate = inputtedDate;
		}
		// transform date into long format for displaying
		DateTimeConverter dtc = new DateTimeConverter();
		String currentDateLong = dtc.convertDateLong(currentDate);
		
		// query building
		BuildingSelectQuery bsq = new BuildingSelectQuery();
		bsq.doAdminBuildingRead();
		String buildings = bsq.getBuildingResults(bldg);
		
		// query reservations
		AdminReservationsSelectQuery arsq = new AdminReservationsSelectQuery();
		arsq.doUserReservationRead(bldg, currentDate);
		arsq.doAdminReservationRead(bldg, currentDate);
		
		String userReservations = arsq.doUserReservationResults();
		String adminReservations = arsq.doAdminReservationResults();
		
		// set the forwarding URL
		String url = "admin/view-reservations.jsp";
		
		// set session and request variables
		session.setAttribute("buildings", buildings);
		session.setAttribute("bldg", bldg);
		session.setAttribute("currentDate", currentDate);
		session.setAttribute("currentDateLong", currentDateLong);
		session.setAttribute("adminReservations", adminReservations);
		session.setAttribute("userReservations", userReservations);
		
		// forward the URL
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
