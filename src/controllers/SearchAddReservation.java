package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ReservationInsertQuery;
import model.Building;
import model.Reservation;

/**
 * Servlet implementation class SearchAddReservation
 */
@WebServlet({ "/SearchAddReservation", "/searchaddreservation" })
public class SearchAddReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAddReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("Resevation Set");
		

		int tempPUSER = 1;
		int tempSUSER = 2;

		int Rooms_roomID = Integer.parseInt(request.getParameter("Rooms_roomID"));
		String reserveStartDate =  request.getParameter("reserveStartDate");
		String reserveEndDate = request.getParameter("reserveEndDate");
		String reserveStartTime = request.getParameter("reserveStartTime");
		String reserveEndTime = request.getParameter("reserveEndTime");
		int hourIncrement =  Integer.parseInt(request.getParameter("hourIncrement"));
		int Building_buildingID =  Integer.parseInt(request.getParameter("Building_buildingID"));
		String free = request.getParameter("free");
		
		//Create Reservation Object
		System.out.println("Resevation Set");
		
		Building building = new Building();
		building.setBuildingID(Building_buildingID);
		String buildingName = building.getBuildingName();
		
		
		Reservation reservation = new Reservation(tempPUSER, tempSUSER, Rooms_roomID, reserveStartDate, reserveEndDate,reserveStartTime , reserveEndTime, hourIncrement, Building_buildingID, free);
		
		String table = "";
		table += "Reservation Succesful! <br />";
		table += "Building ID: "+Building_buildingID+"<br />";
		table += "Room ID: "+Rooms_roomID+"<br />";
		table += "Date: "+reserveStartDate+"<br />";
		table += "Time: "+ reserveStartTime+"<br />";
		table += "<br />";
		
		
		
		//Insert Data
		ReservationInsertQuery riq = new ReservationInsertQuery();
		riq.doReservationInsert(reservation);
		//Send Email
		
		//Redirect to home page? with message?
		
		
		request.setAttribute("table", table);
		
		String url = "/user/searchsuccess.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
