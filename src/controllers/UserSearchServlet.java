package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.BuildingSelectQuery;
import helpers.HourCountSelectQuery;
import helpers.ReservationSelectQuery;
import helpers.RoomsSelectQuery;
import model.DateTimeConverter;
import model.DbConnect;
import model.TimeConverter;
import model.User;

/**
 * Servlet implementation class UserSearchServlet
 * @author Brian Olaogun
 */
@WebServlet("/SearchReservations")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet() {
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
		// get current session
		session = request.getSession(false);
		
		// If session is active/valid
		if(session != null){
			User user = (User) session.getAttribute("user");
			 
			if(user != null) { // run code if user object is not null
				
				// remove session variable
				session.removeAttribute("msg");
				session.removeAttribute("table");
				
				// get session and request variables + initialization of others
				String buildings = ""; // the string that contains the HTML drop down list
				String buildingID = request.getParameter("buildingID"); // get the value from request
				String buildingIDSelect = request.getParameter("buildingList"); // get the value selected from the drop down list
				String buildingIDSession = (String) session.getAttribute("buildingID"); // get the building ID from the session
				
				// get reservation request variables
				String startDate = request.getParameter("startDate");
				String endDateSlashed = request.getParameter("endDate");
				String startTime = request.getParameter("startTime");
				String endTime = "";
				String hourIncrement = request.getParameter("hourIncrement");
				String hourIncrementSelect = "";
				String msg = "";
				String table = "";
				
				// Other classes
				DateTimeConverter dtc = new DateTimeConverter();
				TimeConverter tc = new TimeConverter();
				
				// convert dates to SQL format
				String endDate = endDateSlashed;
				
				//------------------------------------------------//
				/*            BUILDING INFORMATION                */
				//------------------------------------------------//
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				// if there is no buildingID from request, then display building drop down
				if (buildingID == null){
					buildingID = Integer.toString(bsq.getFirstBuildingID());
					int bldg = Integer.parseInt(buildingID);
					// query building
					
					bsq.doBuildingRead();
					buildings = bsq.getBuildingResults(bldg);
		
				}
				// if there is a buildingID from session, it becomes the buildingID
				// if there is a buildingID selected from drop down, it becomes the buildingID
				if (buildingIDSelect != null){
					buildingID = buildingIDSelect;
					buildings = bsq.getBuildingResults(Integer.parseInt(buildingID)); // keep value selected in drop down.
				} else if (buildingIDSession != null){
					if(buildingIDSession.equalsIgnoreCase(buildingID)){
						buildingID = buildingIDSession;
					}
				} 
				
				// drop down for hour increment
				HourCountSelectQuery hcsq = new HourCountSelectQuery();
				hourIncrementSelect = hcsq.hourIncrementSelect();
				
				// check inputs
				if(buildingIDSelect == null || startDate == null || endDate == null || startTime == null ||
						hourIncrement == null || buildingIDSelect.isEmpty() || startDate.isEmpty() || 
						endDate.isEmpty() || startTime.isEmpty() || hourIncrement.isEmpty()){
					
					msg = "Please enter all values to begin searching for reservations.";
					url = "user/search.jsp";
					
				} else {
					//----------------------------------------------//
					/*            Reservation Search				*/
					//----------------------------------------------//
					startDate = dtc.slashDateConvert(startDate);
					endDate = dtc.slashDateConvert(endDateSlashed);
					
					// convert start time to 24-hour time
					startTime = tc.convertTimeTo24(startTime);
					
					// get end time from start time and hour increment
					endTime = TimeConverter.addTime(startTime, Integer.parseInt(hourIncrement));
					
					System.out.println("User Search Reservations --> start time = " + startTime + " end time = " + endTime);
					
					// query for reservation check and listing of all rooms in a building.
					ReservationSelectQuery res = new ReservationSelectQuery();
					RoomsSelectQuery rsq = new RoomsSelectQuery();
					
					// list for the room number.  Below will print all times, inclusive between start and end
					List<String> roomNumber = rsq.roomList(Integer.parseInt(buildingID));
					List<String> times = tc.timeRangeList(startTime, endTime);
					
					// date range list.  Print all dates between start and end date inclusive
					List<String> dates = dtc.dateRangeList(startDate, endDate);
					
					table += "<table id='' class='mdl-data-table' cellspacing='0' width='95%'>";
					table += "<thead>";
					table += "<tr>";
					table += "<th></th>";
					table += "<th>Room #</th>";
					table += "<th>Start Date</th>";
					table += "<th>Start Time</th>";
					table += "<th>End Date</th>";
					table += "<th>End Time</th>";
					table += "<th></th>";
					table += "</tr>";
					table += "</thead>";
					table += "<tbody>";
					
					// loop through each room after all times have been checked 
					int h = 1; // counter
					
					for (int i = 0; i < roomNumber.size(); i++){
						
						// loop through each date
						for (int k = 0; k < dates.size(); k++) {
							
							// loop through each time then increment room
							for (int j =0; j < times.size(); j++){
								/*
								 * Check if there is a reservation at the time.
								 * If there isn't then returned is an empty string.
								 */
								res.doReservationRead(dates.get(k), times.get(j), roomNumber.get(i));
								String reservationCheck = res.doReservationResults2();
								
								if(reservationCheck.isEmpty()){
									// index 0 is the start time of the reservation
									if(j == 0){
										// testing - printing to console
										System.out.println();
										System.out.println("DATE " + dates.get(k));
										System.out.println("END " + endDate);
										System.out.println("ROOM NUMBER " + roomNumber.get(i));
										System.out.println("TIME " + times.get(j));
										System.out.println();
										
										table += "<tr>";
										table += "<td data-sort='" + h + "'>" + h + "</td>";
										table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
										table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
										table += "<td>" + tc.convertTimeTo12(startTime) + "</td>";
										table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
										table += "<td>" + tc.convertTimeTo12(endTime) + "</td>";
										table += "<td>";
										table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
										table += "<input type='hidden' name='roomNumber' value='" + roomNumber.get(i) + "'>";
										table += "<input type='hidden' name='startTime' value='" + startTime + "'>";
										table += "<input type='hidden' name='endTime' value='" + endTime + "'>";
										table += "<input type='hidden' name='startDate' value='" + dates.get(k) + "'>";
										table += "<input type='hidden' name='endDate' value='" + dates.get(k) + "'>";
										table += "<input type='hidden' name='buildingID' value='" + buildingID + "'>";
										table += "<input type='hidden' name='hourIncrement' value='" + hourIncrement + "'>";
										table += "<input type='submit' value='Make Reservation'>";
										table += "</form>";
										table += "</td>";
										table += "</tr>";
										h++;
									}
								} else if (!reservationCheck.isEmpty()){
									// testing - printing to console
									System.out.println("******* RESERVED ********");
									System.out.println("RESERVE ID = " + reservationCheck);
									System.out.println("DATE " + startDate);
									System.out.println("END " + endDate);
									System.out.println("ROOM NUMBER " + roomNumber.get(i));
									System.out.println("TIME " + times.get(j));
									System.out.println("******* RESERVED ********");
									System.out.println();
									
									table += "<tr>";
									table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
									table += "<td data-sort='" + h + "'>" + h + "</td>";
									table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
									table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
									table += "<td>" + tc.convertTimeTo12(startTime) + "</td>";
									table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
									table += "<td>" + tc.convertTimeTo12(endTime) + "</td>";
									table += "<td> **RESERVED**</td>"; 
									table += "</form>";
									table += "</tr>";
									h++; 
								}
							}
						}
					}
					table += "</tbody>";
					table += "</table>";
					
					// forwarding URL
					url = "user/search.jsp";
					
				}
				
				// set session and request variables
				session.setAttribute("table", table);
				session.setAttribute("msg", msg);
				session.setAttribute("hourIncrementSelect", hourIncrementSelect);
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildings", buildings);
				session.setAttribute("startDate", startDate);
				session.setAttribute("endDate", endDateSlashed);
				session.setAttribute("startTime", startTime);
				session.setAttribute("endTime", endTime);
				session.setAttribute("tc", tc);
				
			} else {
				//------------------------------------------------//
				/*               USER INFO EXPIRED                */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				
				session.invalidate();
				CASLogoutServlet.clearCache(request, response);
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
			
		} else {
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
		
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
	
		// forward the request
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
