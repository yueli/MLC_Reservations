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
    private HttpSession session;   
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
		session = request.getSession();
		
		
		
		String buildingList = request.getParameter("buildingList");
		
		// set building & check if building is selected by user
		int bldg = 1;
		if (buildingList != null){ // if selected, get value & transform into integer
			bldg = Integer.parseInt(buildingList);
		}
		System.out.println(bldg);
		
		// get the current date
		DateTimeConverter dtc = new DateTimeConverter();
		String currentDate = dtc.parseDate(dtc.datetimeStamp());
		System.out.println(currentDate);
		
		// get the inputed date. If datepicker date isn't null, it will be now be the current date
		String inputtedDate = request.getParameter("datepicker");
		if (inputtedDate != null && !inputtedDate.isEmpty()){
			currentDate = inputtedDate;
		}
		
		// Transform current date for display
		String currentDateLong = dtc.convertDateLong(currentDate);
		
		// query building
		BuildingSelectQuery bsq = new BuildingSelectQuery();
		bsq.doAdminBuildingRead();
		String buildings = bsq.getBuildingResults(bldg); // contains the HTML drop down building list.
		
		// query reservations
		AdminReservationsSelectQuery arsq = new AdminReservationsSelectQuery();
		arsq.doUserReservationRead(bldg, currentDate); 
		arsq.doAdminReservationRead(bldg, currentDate);
		
		// contains the html table with the query results
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
