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
 * Servlet implementation class AdminViewReservations
 * 
 */
@WebServlet({ "/AdminViewReservations", "/view-reservations" })
public class AdminViewReservations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminViewReservations() {
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
		String building = request.getParameter("buildingList");
		System.out.println(building);
		// get the current date
		DateTimeConverter dtc = new DateTimeConverter();
		String currentDate = dtc.parseDate(dtc.datetimeStamp());
		System.out.println(currentDate);
		// query building
		BuildingSelectQuery bsq = new BuildingSelectQuery();
		bsq.doBuildingRead();
		String buildings = bsq.getBuildingResults();
		
		// query reservations
		AdminReservationsSelectQuery arsq = new AdminReservationsSelectQuery();
		arsq.doUserReservationRead(currentDate);
		arsq.doAdminReservationRead(currentDate);
		
		String userReservations = arsq.doUserReservationResults();
		String adminReservations = arsq.doAdminReservationResults();
		
		// set the forwarding URL
		String url = "admin/view-reservations.jsp";
		
		// set session and request variables
		session.setAttribute("buildings", buildings);
		session.setAttribute("adminReservations", adminReservations);
		session.setAttribute("userReservations", userReservations);
		
		// forward the URL
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
