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
		this.doPost(request, response);
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
				String startDateSlashed = request.getParameter("startDate");
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
				String startDate = startDateSlashed;
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
					
					// make sure start date is less than end date
					if(DateTimeConverter.isAfter(startDate, endDate) == true){
						
						msg = "Please enter a <b>start date</b> that's less than the <b>end date</b>.";
						url = "user/search.jsp";
						
					} else {
						// convert from slashed MM/dd/yyyy format to yyyy-MM-dd format
						startDate = dtc.slashDateConvert(startDate);
						endDate = dtc.slashDateConvert(endDateSlashed);
						
						// convert start time to 24-hour time
						startTime = tc.convertTimeTo24(startTime);
						
						// get end time from start time and hour increment
						endTime = TimeConverter.addTime(startTime, Integer.parseInt(hourIncrement));
						
						// User's can only make a reservation within a 24 hour period.  No rolling 24 hour reservations.
						if(startTime.equals("23:00:00") && Integer.parseInt(hourIncrement) == 2){
							msg = "The end time falls into the next day. Please create a separate reservation on the following day from 12am - 1am <br> "
									+ "or reduce the reservation length to 1 hour.";
							
							url = "user/search.jsp";
							
						} else {
							
							System.out.println("User Search Reservations --> start time = " + startTime + " end time = " + endTime);
						System.out.println("User Search Reservations --> start time = " + startTime + " end time = " + endTime););
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
							for (int i = 0; i < roomNumber.size(); i++){
								
								// loop through each date
								for (int k = 0; k < dates.size(); k++) {
									
									// loop through each time then increment room
										
												
											}
												
											}
										}
									}
								}
							}
						}
					}
				}
				
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
