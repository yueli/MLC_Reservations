package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.SearchReservationsResultsQuery;
import model.Reservation;

/**
 * Servlet implementation class SearchReservationsResultsServlet
 */
@WebServlet({ "/SearchReservationsResultsServlet", "/searchresults" })
public class SearchReservationsResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchReservationsResultsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Pulling Search Credential 
			int buildingid = Integer.parseInt(request.getParameter("buildingid"));
			String beginDate = request.getParameter("beginDate");
			String beginTime = request.getParameter("beginTime");
			String endDate = request.getParameter("endDate");
			String endTime = request.getParameter("endTime");
			int hourIncrement = Integer.parseInt(request.getParameter("hourIncrement"));
				
			//Pull opening slots from database
			SearchReservationsResultsQuery srrs = new SearchReservationsResultsQuery();
			
			//Pull all rooms from Building in form of Array
			ArrayList<Integer> roomsAL = new ArrayList<Integer>();
			roomsAL = srrs.getAllRooms(buildingid);
			
			//Set Result Set with all reservation within credentials
			//Set ResultsList of all reservation into Array
			srrs.doRead(buildingid, beginDate, beginTime, endDate, endTime, hourIncrement);		
			ArrayList<Reservation> reservationAL = new ArrayList<Reservation>();
			reservationAL = srrs.getReservationList();
			
			//Create Table
			String table="";
			try {
				table = srrs.getHTMLTable(roomsAL, reservationAL, beginDate, beginTime, endDate, endTime, hourIncrement);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//srrs.createEmptyTable(sDate, eDate, sTime, eTime);
			
			request.setAttribute("table", table);
			
			String url = "/user/searchresults.jsp";
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		
		

		
		
	}



}
