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
				String endTime = request.getParameter("endTime");
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
						endTime = tc.convertTimeTo24(endTime);
						
						System.out.println("User Search Reservations --> start time = " + startTime + " end time = " + endTime);
						
						// query for reservation check and listing of all rooms in a building.
						ReservationSelectQuery res = new ReservationSelectQuery();
						RoomsSelectQuery rsq = new RoomsSelectQuery();
						
						// list for the room number.  Below will print all times, inclusive between start and end
						List<String> roomNumber = rsq.roomList(Integer.parseInt(buildingID));
						
						List<String> times = tc.timeRangeList(startTime, TimeConverter.subtractTime(endTime, Integer.parseInt(hourIncrement))); 
						List<String> times2 = tc.timeRangeList(startTime, TimeConverter.subtractTime("00:00:00", Integer.parseInt(hourIncrement)));
						List<String> times3 = tc.timeRangeList("00:00:00", TimeConverter.subtractTime("00:00:00", Integer.parseInt(hourIncrement)));
						List<String> times4 = tc.timeRangeList("00:00:00", TimeConverter.subtractTime(endTime, Integer.parseInt(hourIncrement)));
						
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
						
						
						int h = 1; // row counter & used to make a unique form ID
							
						if(startDate.equals(endDate)){
							
							// loop through each room after all times have been checked 
							for (int i = 0; i < roomNumber.size(); i++){
								
								// loop through each date
								for (int k = 0; k < dates.size(); k++) {
									
									// loop through each time then increment room
									for (int j =0; j < times.size(); j++){
										
										String innerEndTime = TimeConverter.addTime(times.get(j), Integer.parseInt(hourIncrement));
										System.out.println("Loop Start time = " + times.get(j) + " Loop END TIME = " + innerEndTime);
										
										List<String> innerTimes = tc.timeRangeList(times.get(j), innerEndTime);
										
										// loop through/display times in range based off hour increment (reservation length)
										// ex. if hour increment = 1, start time = 9am, end time = 11am --> 9am - 10am, next row = 10am - 11am, etc.
										// ex. if hour increment = 2, 9am - 11am, increment room --> 9am - 11am
										for(int l = 0; l < innerTimes.size(); l++){
											
											/*
											 * Check if the building selected is open at the time
											 * if there is, the building ID is returned
											 */
											String buildingCheck = bsq.buildingScheduleCheck(dates.get(k), innerTimes.get(l), buildingID);
											if(!buildingCheck.isEmpty()){
												
												res.doReservationRead(dates.get(k), innerTimes.get(l), roomNumber.get(i));
											   	/*
												* Check if there is a reservation at the time.
												* If there isn't then returned is an empty string.
												*/
												String check = res.doReservationResults2();
												if(check.isEmpty()){
													// index 0 is the start time of the reservation
													if(l == 0){
														//if (!innerTimes.get(l).equals(endTime)){
															// testing - printing to console
															System.out.println();
															System.out.println("DATE " + dates.get(k));
															System.out.println("END " + endDate);
															System.out.println("ROOM NUMBER " + roomNumber.get(i));
															System.out.println("TIME " + innerTimes.get(l));
															System.out.println();
															
															table += "<tr>";
															table += "<td data-sort='" + h + "'>" + h + "</td>";
															table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
															table += "<td>";
															table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
															table += "<input type='hidden' name='roomNumber' value='" + roomNumber.get(i) + "'>";
															table += "<input type='hidden' name='startTime' value='" + innerTimes.get(l) + "'>";
															table += "<input type='hidden' name='endTime' value='" + innerEndTime + "'>";
															table += "<input type='hidden' name='startDate' value='" + dates.get(k) + "'>";
															table += "<input type='hidden' name='endDate' value='" + dates.get(k) + "'>";
															table += "<input type='hidden' name='buildingID' value='" + buildingID + "'>";
															table += "<input type='hidden' name='hourIncrement' value='" + hourIncrement + "'>";
															table += "<input class='btn btn-lg btn-red' type='submit' value='Make Reservation'>";
															table += "</form>";
															table += "</td>";
															table += "</tr>";
															h++;
														//}
													}
												} else if (!check.isEmpty()){
													if(l == 0){
														// testing - printing to console
														System.out.println("******* RESERVED ********");
														System.out.println("RESERVE ID = " + check);
														System.out.println("DATE " + dates.get(k));
														System.out.println("END " + dates.get(k));
														System.out.println("ROOM NUMBER " + roomNumber.get(i));
														System.out.println("ST TIME " + innerTimes.get(l));
														System.out.println("END TIME " + innerEndTime);
														System.out.println("******* RESERVED ********");
														System.out.println();
														
														table += "<tr>";
														table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
														table += "<td data-sort='" + h + "'>" + h + "</td>";
														table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
														table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
														table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
														table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
														table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
														table += "<td> **RESERVED**</td>"; 
														table += "</form>";
														table += "</tr>";
														h++; 
													}
												}
											}
										}
									}
								}
							}
						} else {
							/*
							 * From the start date, loop through all times between the user
							 * inputted start time - end time of midnight - hour increment
							 */
							// loop through each room after all times have been checked 
							//for (int i = 0; i < roomNumber.size(); i++){
							for (int k = 0; k < dates.size(); k++) {	
							
								// loop through each date
								//for (int k = 0; k < dates.size(); k++) {
								for (int i = 0; i < roomNumber.size(); i++){
									System.out.println("*************************");
									System.out.println();
									System.out.println("Loop Date = " + dates.get(k));
									System.out.println();
									System.out.println("*************************");
									if(dates.get(k).equals(startDate)){
										
										// loop through each time then increment room
										for (int j =0; j < times2.size(); j++){
											
											String innerEndTime = TimeConverter.addTime(times2.get(j), Integer.parseInt(hourIncrement));
											System.out.println("Times 1: Start Date --> Loop Start time = " + times2.get(j) + " Loop END TIME = " + innerEndTime);
											
											List<String> innerTimes = tc.timeRangeList(times2.get(j), innerEndTime);
											
											// loop through/display times in range based off hour increment (reservation length)
											// ex. if hour increment = 1, start time = 9am, end time = 11am --> 9am - 10am, next row = 10am - 11am, etc.
											// ex. if hour increment = 2, 9am - 11am, increment room --> 9am - 11am
											for(int l = 0; l < innerTimes.size(); l++){
												
												/*
												 * Check if the building selected is open at the time
												 * if there is, the building ID is returned
												 */
												String buildingCheck = bsq.buildingScheduleCheck(dates.get(k), innerTimes.get(l), buildingID);
												if(!buildingCheck.isEmpty()){
													
													res.doReservationRead(dates.get(k), innerTimes.get(l), roomNumber.get(i));
												   	/*
													* Check if there is a reservation at the time.
													* If there isn't then returned is an empty string.
													*/
													String check = res.doReservationResults2();
													if(check.isEmpty()){
														// index 0 is the start time of the reservation
														if(l == 0){
															//if (!innerTimes.get(l).equals(endTime)){
																// testing - printing to console
																System.out.println();
																System.out.println("DATE " + dates.get(k));
																//System.out.println("END " + endDate);
																System.out.println("END " + dates.get(k));
																System.out.println("ROOM NUMBER " + roomNumber.get(i));
																//System.out.println("TIME " + innerTimes.get(l));
																System.out.println("ST TIME " + innerTimes.get(l));
																System.out.println("END TIME " + innerEndTime);
																System.out.println();
																
																table += "<tr>";
																table += "<td data-sort='" + h + "'>" + h + "</td>";
																table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
																table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
																table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
																table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
																table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
																table += "<td>";
																table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
																table += "<input type='hidden' name='roomNumber' value='" + roomNumber.get(i) + "'>";
																table += "<input type='hidden' name='startTime' value='" + innerTimes.get(l) + "'>";
																table += "<input type='hidden' name='endTime' value='" + innerEndTime + "'>";
																table += "<input type='hidden' name='startDate' value='" + dates.get(k) + "'>";
																table += "<input type='hidden' name='endDate' value='" + dates.get(k) + "'>";
																table += "<input type='hidden' name='buildingID' value='" + buildingID + "'>";
																table += "<input type='hidden' name='hourIncrement' value='" + hourIncrement + "'>";
																table += "<input class='btn btn-lg btn-red' type='submit' value='Make Reservation'>";
																table += "</form>";
																table += "</td>";
																table += "</tr>";
																
																h++;
															//}
														}
													} else if (!check.isEmpty()){
														if(l == 0){
															// testing - printing to console
															System.out.println("******* RESERVED ********");
															System.out.println("RESERVE ID = " + check);
															System.out.println("DATE " + dates.get(k));
															System.out.println("END " + dates.get(k));
															System.out.println("ROOM NUMBER " + roomNumber.get(i));
															System.out.println("ST TIME " + innerTimes.get(l));
															System.out.println("END TIME " + innerEndTime);
															System.out.println("******* RESERVED ********");
															System.out.println();
															
															table += "<tr>";
															table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
															table += "<td data-sort='" + h + "'>" + h + "</td>";
															table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
															table += "<td> **RESERVED**</td>"; 
															table += "</form>";
															table += "</tr>";
														
															h++; 
														}
													}
												}
											}
										}
										
									}
									/*
									 * Any date in between the start date and end date, exclusive, will loop
									 * through start time midnight to the end time of midnight - hour increment
									 */
									if (!dates.get(k).equals(startDate) && !dates.get(k).equals(endDate)) {
										
										// loop through each time then increment room
										for (int j =0; j < times3.size(); j++){
											
											String innerEndTime = TimeConverter.addTime(times3.get(j), Integer.parseInt(hourIncrement));
											System.out.println("Times 2: Middle Date --> Loop Start time = " + times3.get(j) + " Loop END TIME = " + innerEndTime);
											
											List<String> innerTimes = tc.timeRangeList(times3.get(j), innerEndTime);
											
											// loop through/display times in range based off hour increment (reservation length)
											// ex. if hour increment = 1, start time = 9am, end time = 11am --> 9am - 10am, next row = 10am - 11am, etc.
											// ex. if hour increment = 2, 9am - 11am, increment room --> 9am - 11am
											for(int l = 0; l < innerTimes.size(); l++){
												
												/*
												 * Check if the building selected is open at the time
												 * if there is, the building ID is returned
												 */
												String buildingCheck = bsq.buildingScheduleCheck(dates.get(k), innerTimes.get(l), buildingID);
												if(!buildingCheck.isEmpty()){
													
													res.doReservationRead(dates.get(k), innerTimes.get(l), roomNumber.get(i));
												   	/*
													* Check if there is a reservation at the time.
													* If there isn't then returned is an empty string.
													*/
													String check = res.doReservationResults2();
													if(check.isEmpty()){
														// index 0 is the start time of the reservation
														if(l == 0){
															//if (!innerTimes.get(l).equals(endTime)){
																// testing - printing to console
																System.out.println();
																System.out.println("DATE " + dates.get(k));
																//System.out.println("END " + endDate);
																System.out.println("END " + dates.get(k));
																System.out.println("ROOM NUMBER " + roomNumber.get(i));
																//System.out.println("TIME " + innerTimes.get(l));
																System.out.println("ST TIME " + innerTimes.get(l));
																System.out.println("END TIME " + innerEndTime);
																System.out.println();
																
																table += "<tr>";
																table += "<td data-sort='" + h + "'>" + h + "</td>";
																table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
																table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
																table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
																table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
																table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
																table += "<td>";
																table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
																table += "<input type='hidden' name='roomNumber' value='" + roomNumber.get(i) + "'>";
																table += "<input type='hidden' name='startTime' value='" + innerTimes.get(l) + "'>";
																table += "<input type='hidden' name='endTime' value='" + innerEndTime + "'>";
																table += "<input type='hidden' name='startDate' value='" + dates.get(k) + "'>";
																table += "<input type='hidden' name='endDate' value='" + dates.get(k) + "'>";
																table += "<input type='hidden' name='buildingID' value='" + buildingID + "'>";
																table += "<input type='hidden' name='hourIncrement' value='" + hourIncrement + "'>";
																table += "<input class='btn btn-lg btn-red' type='submit' value='Make Reservation'>";
																table += "</form>";
																table += "</td>";
																table += "</tr>";
																
																h++;
															//}
														}
													} else if (!check.isEmpty()){
														if(l == 0){
															// testing - printing to console
															System.out.println("******* RESERVED ********");
															System.out.println("RESERVE ID = " + check);
															System.out.println("DATE " + dates.get(k));
															System.out.println("END " + dates.get(k));
															System.out.println("ROOM NUMBER " + roomNumber.get(i));
															System.out.println("ST TIME " + innerTimes.get(l));
															System.out.println("END TIME " + innerEndTime);
															System.out.println("******* RESERVED ********");
															System.out.println();
															
															table += "<tr>";
															table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
															table += "<td data-sort='" + h + "'>" + h + "</td>";
															table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
															table += "<td> **RESERVED**</td>"; 
															table += "</form>";
															table += "</tr>";
															
															h++; 
														}
													}
												}
											}
										}
									}
									/*
									 * If the end date in the loop reaches the user inputted end date
									 * then loop from times midnight to user inputted end time.
									 */
									if (dates.get(k).equals(endDate)){
										
										// loop through each time then increment room
										for (int j =0; j < times4.size(); j++){
											
											String innerEndTime = TimeConverter.addTime(times4.get(j), Integer.parseInt(hourIncrement));
											System.out.println("Times 3: End Date --> Loop Start time = " + times4.get(j) + " Loop END TIME = " + innerEndTime);
											
											List<String> innerTimes = tc.timeRangeList(times4.get(j), innerEndTime);
											
											// loop through/display times in range based off hour increment (reservation length)
											// ex. if hour increment = 1, start time = 9am, end time = 11am --> 9am - 10am, next row = 10am - 11am, etc.
											// ex. if hour increment = 2, 9am - 11am, increment room --> 9am - 11am
											for(int l = 0; l < innerTimes.size(); l++){
												
												/*
												 * Check if the building selected is open at the time
												 * if there is, the building ID is returned
												 */
												String buildingCheck = bsq.buildingScheduleCheck(dates.get(k), innerTimes.get(l), buildingID);
												if(!buildingCheck.isEmpty()){
													
													res.doReservationRead(dates.get(k), innerTimes.get(l), roomNumber.get(i));
												   	/*
													* Check if there is a reservation at the time.
													* If there isn't then returned is an empty string.
													*/
													String check = res.doReservationResults2();
													if(check.isEmpty()){
														// index 0 is the start time of the reservation
														if(l == 0){
															//if (!innerTimes.get(l).equals(endTime)){
																// testing - printing to console
																System.out.println();
																System.out.println("DATE " + dates.get(k));
																//System.out.println("END " + endDate);
																System.out.println("END " + dates.get(k));
																System.out.println("ROOM NUMBER " + roomNumber.get(i));
																//System.out.println("TIME " + innerTimes.get(l));
																System.out.println("ST TIME " + innerTimes.get(l));
																System.out.println("END TIME " + innerEndTime);
																System.out.println();
																
																table += "<tr>";
																table += "<td data-sort='" + h + "'>" + h + "</td>";
																table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
																table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
																table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
																table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
																table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
																table += "<td>";
																table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
																table += "<input type='hidden' name='roomNumber' value='" + roomNumber.get(i) + "'>";
																table += "<input type='hidden' name='startTime' value='" + innerTimes.get(l) + "'>";
																table += "<input type='hidden' name='endTime' value='" + innerEndTime + "'>";
																table += "<input type='hidden' name='startDate' value='" + dates.get(k) + "'>";
																table += "<input type='hidden' name='endDate' value='" + dates.get(k) + "'>";
																table += "<input type='hidden' name='buildingID' value='" + buildingID + "'>";
																table += "<input type='hidden' name='hourIncrement' value='" + hourIncrement + "'>";
																table += "<input class='btn btn-lg btn-red' type='submit' value='Make Reservation'>";
																table += "</form>";
																table += "</td>";
																table += "</tr>";
													
																h++;
															//}
														}
													} else if (!check.isEmpty()){
														if(l == 0){
															// testing - printing to console
															System.out.println("******* RESERVED ********");
															System.out.println("RESERVE ID = " + check);
															System.out.println("DATE " + dates.get(k));
															System.out.println("END " + dates.get(k));
															System.out.println("ROOM NUMBER " + roomNumber.get(i));
															System.out.println("ST TIME " + innerTimes.get(l));
															System.out.println("END TIME " + innerEndTime);
															System.out.println("******* RESERVED ********");
															System.out.println();
															
															table += "<tr>";
															table += "<form name='searchReserve' id='searchReserve" + h + "' action='SearchReservation-MakeReservation' method='post'>";
															table += "<td data-sort='" + h + "'>" + h + "</td>";
															table += "<td data-sort='" + roomNumber.get(i) + "'>" + roomNumber.get(i) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerTimes.get(l)) + "</td>";
															table += "<td>" + dtc.convertDateLong(dates.get(k)) + "</td>";
															table += "<td>" + tc.convertTimeTo12(innerEndTime) + "</td>";
															table += "<td> **RESERVED**</td>"; 
															table += "</form>";
															table += "</tr>";
															
															h++; 
														}
													}
												}
											}
										}
									} 
								}
							}
						}
						table += "</tbody>";
						table += "</table>";
						
						// forwarding URL
						url = "user/search.jsp";
					
					}
				}
				
				// set session and request variables
				session.setAttribute("table", table);
				session.setAttribute("msg", msg);
				session.setAttribute("hourIncrementSelect", hourIncrementSelect);
				session.setAttribute("buildingID", buildingID);
				session.setAttribute("buildings", buildings);
				session.setAttribute("startDate", startDateSlashed);
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
