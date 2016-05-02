	
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Building;
import model.DateTimeConverter;
import model.DbConnect;
import model.Rooms;
import model.TimeConverter;

/**
 * @author Brian Olaogun
 * Helper for the Student side of the website.
 * 
 * @contributer: Ginger Nix
 *
 */
public class RoomsSelectQuery {
	// initialize fields
		private Connection connection;
		private ResultSet results;
		
		/**
		 * Default Constructor
		 */
		public RoomsSelectQuery() {
	
			// set up the driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				// hard coded the connection in DbConnect class
				this.connection = DbConnect.devCredentials();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * Get the roomID from the buildingID and room number.
		 * @param buildingID Integer buildingID
		 * @param roomNumber String roomNumber
		 * @return Integer roomID
		 * @author Brian Olaogun
		 */
		public Integer getRoomID (int buildingID, String roomNumber){
			int roomID;
			
			String query = "SELECT tomcatdb.Rooms.roomID FROM tomcatdb.Rooms, tomcatdb.Building "
					+ "WHERE tomcatdb.Building.buildingID = tomcatdb.Rooms.Building_buildingID "
					+ "AND tomcatdb.Building.buildingID = ? "
					+ "AND tomcatdb.Rooms.roomNumber = ?";
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				ps.setInt(1, buildingID);
				ps.setString(2, roomNumber);
				this.results = ps.executeQuery();
				
				// get roomID from result set
				while(this.results.next()){
					roomID = Integer.parseInt(this.results.getString("roomID"));
					return roomID;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in RoomSelectQuery.getRoomID. Please check connection or SQL statement");
			} 
			
			return null;
		}
		
		/**
		 * This method will return an array list of all rooms in a building.
		 * @param buildingID Integer buildingID of building
		 * @return String Array List
		 * @author Brian Olaogun
		 */
		public List<String> roomList (int buildingID){
			String query = "SELECT roomNumber FROM tomcatdb.Rooms, tomcatdb.Building "
					+ "WHERE tomcatdb.Rooms.Building_buildingID = tomcatdb.Building.buildingID "
					+ "AND tomcatdb.Building.buildingID = ?";
			
			List<String> roomsList = new ArrayList<String>();
			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				ps.setInt(1, buildingID);
				this.results = ps.executeQuery();
				
				// add rooms to list array
				while(this.results.next()){
					roomsList.add(this.results.getString("roomNumber"));
				}
				return roomsList;
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
			} 
			
			return roomsList;
		}
		/**
		 * Run the query to get roomID and roomNumber for getRoomsTable method.
		 * @param buildingID Integer buildingID of building
		 * @param floor String floor number
		 * @author Brian Olaogun
		 */
		public void doRoomRead(int buildingID, String floor){
			//String query = "SELECT * FROM tomcatdb.Rooms WHERE roomStatus = 1";
			String query = "SELECT Building.buildingID, roomID, roomNumber "
					+ "FROM tomcatdb.Rooms, tomcatdb.Building "
					+ "WHERE tomcatdb.Rooms.Building_buildingID = tomcatdb.Building.buildingID "
					+ "AND tomcatdb.Building.buildingID = ? "
					+ "AND tomcatdb.Rooms.roomStatus = ? "
					+ "AND tomcatdb.Rooms.roomFloor = ? "
					+ "ORDER BY roomNumber";

			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				ps.setInt(1, buildingID);
				ps.setString(2, "1");
				ps.setString(3, floor);
				
				this.results = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement: " + query);
			} 
		}
		
		
		/**
		 * This method constructs the tables that you see in browse-reservations.
		 * Here I create the structure for the jQuery tabs for the rooms
		 * Each tab will show the table/availability schedule for the selected room number.
		 * To show the times, I created a string table block that is used in the loop to query if a room is available at the time
		 * if the room is unavailable, the cell will have an ID of red
		 * if the room is available, the cell will have and ID of green
		 * if the time select (in the loop) is less than the current time, the cell ID is gray.
		 * If it is 11 minutes past current time, the cell will have in ID of yellow.
		 * Yellow means that the room was reserved but is now open for everyone (without a reservation)
		 * Gray means that time has past so you can reserve a room at that time
		 * information is sent via hidden forms to make a Browse Reserve Servlet 
		 * uses Date Time Converter and Time converter classes to convert, parse, of format date or times
		 * a key table is at the bottom of each schedule table in the tabs.
		 * @author Brian Olaogun
		 * @return String HTML table
		 */
		public String getRoomsTable(){
			// create the times to display in a table.  
			String[] timeBlock = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", 
					"11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			
			// Create an HTML table
			String table = "";
			
			table += "<div id='tabs'>";
			table += "<ul>";
			
			int h = 0; // used for jQuery tabs creation
			try {
				while(this.results.next()){
					Rooms rooms = new Rooms();
					rooms.setRoomNumber(this.results.getString("roomNumber"));
					table += "<li><a href='#tabs-" + h + "'" + ">" + rooms.getRoomNumber() + "</a></li>";
					h++;
				}
				
				this.results.beforeFirst();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			table += "</ul>";
			// Class to convert 24 hour time to 12 hour
			TimeConverter tc = new TimeConverter();
			int j = 0; // used for jQuery tabs creation
			try {
				while(this.results.next()){
					// get the buildingID
					Building building = new Building();
					building.setBuildingID(this.results.getInt("buildingID"));
					String buildingID = Integer.toString(building.getBuildingID());
					
					// Java class that contains method to query building hours
					BuildingSelectQuery bsq = new BuildingSelectQuery();
					
					// get the room number and reserved rooms from the query results
					Rooms room = new Rooms();
					room.setRoomID(this.results.getInt("roomID")); 
					room.setRoomNumber(this.results.getString("roomNumber"));
					
					// display results in a table
					table += "<div id='tabs-" + j + "'" + ">"; // used for the jQuery tabs
					table += "<table border=1>";
					table += "<tbody class='room'>";
					table += "<tr>";
					table += "<th COLSPAN=12 ALIGN=CENTER>" + room.getRoomNumber() + "</th>";
					table += "</tr>";
					table += "</tbody>";
					
					table += "<tbody class='subcategory'>";
					table += "<tr>";
					for(int i = 0; i < 12; i++){
						
						// get results for reservations
						ReservationSelectQuery rsq = new ReservationSelectQuery();
						
						// class used to get the current datetime and parse date
						DateTimeConverter dtc = new DateTimeConverter();
						
						// building hours check
						String scheduleCheck = bsq.buildingScheduleCheck(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i], buildingID);
						
						// check to see if room is reserved at the current hour at the current date
						rsq.doReservationRead(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i], room.getRoomNumber());
						String reservation = rsq.doReservationResults();
						
						// used for hour comparison
						String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp());
						int currentHour = Integer.parseInt(currentTime.substring(0, Math.min(currentTime.length(), 2)));
						int reserveHour = Integer.parseInt(timeBlock[i].substring(0, Math.min(timeBlock[i].length(), 2)));
						
						// if result set IS NOT empty, then there IS a reservation at that time
						// if there IS a reservation, then color cells red for unavailable.
						if(!reservation.isEmpty()){
							table += "<td id='red'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// compare the current hour with the hour of the reservation
						// user can only make a reservation for current hour and beyond for the day
						// also if building is not open at current hour, color cells gray	
						} else if ((reserveHour < currentHour) || scheduleCheck.isEmpty()){
							table += "<td id='gray'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// if result set IS empty, then there IS NOT a reservation at that time
						} else {
							if (reserveHour == currentHour){
								if (tc.currentMinutes() > 10){
									table += "<td id='yellow'>";
									table += tc.convertTimeTo12(timeBlock[i]);
								} else {
									table += "<td id='green'>";
									table += "<form name='fwdReserve' id='fwdReserve" + i + room.getRoomNumber() + "' action='BrowseReserve' method='post'>";
									table += "<input type='hidden' name='roomID' value='" + room.getRoomID() + "'>";
									table += "<input type='hidden' name='startTime' value='" + timeBlock[i] + "'>";
									table += "<input type='hidden' name='roomNumber' value='" + room.getRoomNumber() + "'>";
									table += "<input type='hidden' name='currentDate' value='" + dtc.parseDate(dtc.datetimeStamp()) + "'>";
									table += "<a href='javascript: submitform(" + i + ", " + room.getRoomNumber() + ")'>" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
									table += "</form> ";
								}
							} else {
								table += "<td id='green'>";
								table += "<form name='fwdReserve' id='fwdReserve" + i + room.getRoomNumber() + "' action='BrowseReserve' method='post'>";
								table += "<input type='hidden' name='roomID' value='" + room.getRoomID() + "'>";
								table += "<input type='hidden' name='startTime' value='" + timeBlock[i] + "'>";
								table += "<input type='hidden' name='roomNumber' value='" + room.getRoomNumber() + "'>";
								table += "<input type='hidden' name='currentDate' value='" + dtc.parseDate(dtc.datetimeStamp()) + "'>";
								table += "<a href='javascript: submitform(" + i + ", " + room.getRoomNumber() + ")'>" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
								table += "</form> ";
								//table += "<a href=Browse_Reservation?startTime=" + timeBlock[i] + "&roomNumber=" + room.getRoomNumber() + "&currentDate=" + dtc.parseDate(dtc.datetimeStamp()) + " onclick='document.getElementById('reserve_submit').submit(); return false;'" + ">" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
							}
						}

						table += "</td>";
					}
					table += "</tr>";
					table += "<tr>";
					for(int i = 12; i < timeBlock.length; i++){
						// get results for reservation
						ReservationSelectQuery rsq = new ReservationSelectQuery();
						
						// class used to get the current datetime and parse date
						DateTimeConverter dtc = new DateTimeConverter();
						
						// building hours check
						String scheduleCheck = bsq.buildingScheduleCheck(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i], buildingID);
						
						// check to see if room is reserved at the current hour at the current date
						rsq.doReservationRead(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i], room.getRoomNumber());
						String reservation = rsq.doReservationResults();
						
						// used for hour comparison
						String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp());
						int currentHour = Integer.parseInt(currentTime.substring(0, Math.min(currentTime.length(), 2)));
						int reserveHour = Integer.parseInt(timeBlock[i].substring(0, Math.min(timeBlock[i].length(), 2)));
						
						// if result set IS NOT empty, then there IS a reservation at that time
						// if there IS a reservation, then color cells red for unavailable.
						if(!reservation.isEmpty()){
							table += "<td id='red'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// compare the current hour with the hour of the reservation
						// user can only make a reservation for current hour and beyond for the day
						// also if building is not open at current hour, color cells gray	
						}else if ((reserveHour < currentHour) || scheduleCheck.isEmpty()){
							table += "<td id='gray'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// if result set IS empty, then there IS NOT a reservation at that time
						} else {
							if (reserveHour == currentHour){
								if (tc.currentMinutes() > 10){
									table += "<td id='yellow'>";
									table += tc.convertTimeTo12(timeBlock[i]);
								} else {
									table += "<td id='green'>";
									table += "<form name='fwdReserve' id='fwdReserve" + i + room.getRoomNumber() + "' action='BrowseReserve' method='post'>";
									table += "<input type='hidden' name='roomID' value='" + room.getRoomID() + "'>";
									table += "<input type='hidden' name='startTime' value='" + timeBlock[i] + "'>";
									table += "<input type='hidden' name='roomNumber' value='" + room.getRoomNumber() + "'>";
									table += "<input type='hidden' name='currentDate' value='" + dtc.parseDate(dtc.datetimeStamp()) + "'>";
									table += "<a href='javascript: submitform(" + i + ", " + room.getRoomNumber() + ")'>" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
									table += "</form> ";
								}
							} else {
								table += "<td id='green'>";
								table += "<form name='fwdReserve' id='fwdReserve" + i + room.getRoomNumber() + "' action='BrowseReserve' method='post'>";
								table += "<input type='hidden' name='roomID' value='" + room.getRoomID() + "'>";
								table += "<input type='hidden' name='startTime' value='" + timeBlock[i] + "'>";
								table += "<input type='hidden' name='roomNumber' value='" + room.getRoomNumber() + "'>";
								table += "<input type='hidden' name='currentDate' value='" + dtc.parseDate(dtc.datetimeStamp()) + "'>";
								table += "<a href='javascript: submitform(" + i + ", " + room.getRoomNumber() + ")'>" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
								table += "</form> ";
								//table += "<a href=Browse_Reservation?startTime=" + timeBlock[i] + "&roomNumber=" + room.getRoomNumber() + "&currentDate=" + dtc.parseDate(dtc.datetimeStamp()) + ">" + tc.convertTimeTo12(timeBlock[i]) + "</a>";
							}
						}
						
					}
					table += "</tr>";
					table += "</tbody>";
					table += "</table>";
					
					// Key Table for rooms table
					table += "<br><br><br>";
					table += "<table>";
					table += "<tbody class='room'>";
					table += "<tr>";
					table += "<th COLSPAN=4 ALIGN=CENTER>";
					table += "Key";
					table += "</th>";
					table += "</tr>";
					table += "</tbody>";
					table += "<tbody class='subcategory'>";
					table += "<tr>";
					table += "<td id='gray'>" + "Time Unavailable" + "</td>";
					table += "<td id='red'>" + "Time Reserved" + "</td>";
					table += "<td id='yellow'>" + "Open Room" + "</td>";
					table += "<td id='green'>" + "Time Available" + "</td>";
					table += "</tr>";
					table += "<tr>";
					table += "<td COLSPAN=4 ALIGN=CENTER>" + "Rooms with open room times are free to use without a reservation" + "</td>";
					table += "</tr>";
					table += "<tr>";
					table += "<td COLSPAN=4 ALIGN=CENTER>" + "<b>Reservations are limited to 2 hours per day</b>" + "</td>";
					table += "</tr>";
					table += "</tbody>";
					table += "</table>";
					table += "</div>";
					j++; // used for jQuery tabs creation
				}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			table += "</div>";
			return table;
		}
		

		/**
		 * This method takes a building record id and lists all the rooms in that building
		 * @author: Ginger Nix
		 * @param buildingID, action (where to send it when canceling/going back to previous calling page
		 * @param cancelAction send Cancel action redirect to buildings or rooms servlet
		 * @return the html table containing the rooms in the building
		 */
		public String ListRoomsInBuilding(int buildingID, String cancelAction){
			
			BuildingSelectQuery bsq = new BuildingSelectQuery();
			
			String buildingName = bsq.getBuildingNameFromID(buildingID);
			
			System.out.println("Rooms Select Query: ListRoomsInBuilding buildingName = " + buildingName);
			System.out.println("Rooms Select Query: ListRoomsInBuilding buildingID = " + buildingID);
			
			String table = "";
			String roomStatus = "";
			
			table += "<div align='center'><h2>Rooms List for: " + buildingName + "</h2>";
			table += "<br /><br />";
			
			table += "<div align='center'>";
			table += "<form action='RoomAddServlet' method = 'post'>" +
					"<input type='hidden' name='buildingID' value='" + buildingID + "'>" +
					"<input type='hidden' name='cancelAction' value='" + cancelAction + "'>" +
					"<input class='btn btn-lg btn-red' type='submit' value='Add A Room'>" +
					"</form>";
			table += "</div>";
			
			table += "<br />";			
			
			// 'cancelAction' will be 'buildings' if came from list of all buildings 
			// or 'RoomsServlet' if came from user selecting a building
			table += "<div class='col-md-12' align='center'>";
			table += "<form action='" + cancelAction + "' method = 'post	'>" +
					"<input class='btn btn-lg btn-red' type='submit' value='Go Back'>" +
					"</form>";
			table += "</div>";
			
			System.out.println("RoomsSelQ: ListRoomsInBuilding - action = " + cancelAction);

			table += "</div>";		
			
			table += "<table id = '' class = 'mdl-data-table' cellspacing = '0' width = '95%'>";			
			table += "<thead>";
			table += "<th>Room Floor</th>";
			table += "<th>Room Number</th>";
			table += "<th>Status</th>";
			table += "<th>&nbsp;</th>";
			table += "</thead>";
			
			table += "<tbody>";
			
			String query = "SELECT * FROM tomcatdb.Rooms "
					+ "WHERE Building_buildingID = '" + buildingID + "' " 
					+ "ORDER BY roomNumber";

			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				this.results = ps.executeQuery();
				while(this.results.next()){ //go through all records returned	
	
					Rooms room = new Rooms();
					room.setRoomID(this.results.getInt("roomID"));
					room.setRoomFloor(this.results.getInt("roomFloor"));
					room.setRoomNumber(this.results.getString("roomNumber"));
					room.setRoomStatus(this.results.getInt("roomStatus"));

					if (room.getRoomStatus() == 1){ // the room is active
						roomStatus = "Online";
					}else{
						roomStatus = "Offline";
					}
					
					
					table += "<tr>";
						
					table += "<td>" + room.getRoomFloor() + "</td>";
					table += "<td>" + room.getRoomNumber() + "</td>";
					table += "<td>" + roomStatus + "</td>";
									
					table += "<td><form action='RoomEditServlet' method = 'post'>" +
							"<input type='hidden' name='roomID' value='" + room.getRoomID() + "'/>" +
							"<input type='hidden' name='cancelAction' value='" + cancelAction + "'>" +
							"<input type='hidden' name='buildingID' value='" + buildingID + "'>" +
							"<input class='btn btn-lg btn-red' type='submit' value='Edit Room' />" +
							"</form></td>";		
				
					table += "</tr>";
				
				} //end while
				
				table += "</tbody>";
				table += "<tfoot>";
				table += "<tr>";
				table += "<td></td>";
				table += "<td></td>";
				table += "<td></td>";
				table += "<td></td>";
				
				table += "</tr>";
				table += "</tfoot>";
				
				table += "</table>";
				
			} catch (SQLException e) {
				e.printStackTrace();

				System.out.println("***Error in RoomsSelectQuery:  Query = " + query);
			}			


			
			System.out.println("Rooms Select Query: ListRoomsInBuilding buildingID AT END= " + buildingID);
			
			return table;
			
		}
		
		/**
		 * This method takes the room ID and creates a form already populated
		 * with the room info. The form is meant to edit the room data by an amin.	 
		 * @param roomID ID of the room the user wants to edit
		 * @param cancelAction send cancellation
		 * @return String HTML table for the edited room
		 * @author: Ginger Nix
		 */
		public String createEditRoomForm (int roomID, String cancelAction){
		
			String table = "";
			
			String query = "SELECT * FROM tomcatdb.Rooms WHERE roomID = ? LIMIT 1";
			
			try {

				PreparedStatement ps = this.connection.prepareStatement(query);
				ps.setInt(1, roomID);
				this.results = ps.executeQuery();
				this.results.next();

				// grab the results we need to display in form
				String roomNumber = this.results.getString("roomNumber");	
				int roomFloor = this.results.getInt("roomFloor");	
				int roomStatus = this.results.getInt("roomStatus");	
				int buildingID = this.results.getInt("Building_buildingID");
				
				System.out.println("RoomsSelectQuery: createEditRoomForm: buildingID START = " + buildingID);
				
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				String buildingName = bsq.getBuildingNameFromID(buildingID); //get human readable bldg name
					
				System.out.println("RoomsSelectQuery: createEditRoomForm:  room number= " + roomNumber);
				System.out.println("RoomsSelectQuery: createEditRoomForm:  room floor= " + roomFloor);
				System.out.println("RoomsSelectQuery: createEditRoomForm:  room status= " + roomStatus);
				
				table += "<div align='center'><h2>Building: " + buildingName + "</h2></div><br />";
				table += "<div align='center'><h3>Edit Room</h3></div><br />";
				
				table += "<form action='RoomSaveServlet' method = 'post'>";
				
				table += "Room Number: &nbsp; &nbsp;";
				table +=  "<input type='text' id = 'roomNumber' name = 'roomNumber' value = '" + roomNumber + "' required>";
				
				table += "<br />";
				table += "Room Floor: &nbsp; &nbsp;";
				table +=  "<input type='text' name = 'roomFloor' value = '" + roomFloor + "' required>";

				table += "<br />";
				table += "Room Status: &nbsp; &nbsp;";
				table += "<select name = 'roomStatus' required>";
				
				if (roomStatus == 1){
					table += "<option value='1' selected>Online</option>";
					table += "<option value='0'>Offline</option>";
					
				}else{
					table += "<option value='1'>Online</option>";
					table += "<option value='0' selected>Offline</option>";		
				}		
				
				table += "</select>";
				
				table += "<br /><br />";
				table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Save'>";
				table += "<input type = 'hidden' name = 'roomID' value='" + roomID + "'>";
				table += "<input type = 'hidden' name = 'buildingID' value='" + buildingID + "'>";
				table += "<input type = 'hidden' name = 'cancelAction' value='" + cancelAction + "'>";

				
				table += "</form>";
				
				table += "<br />";
				
				table += "<form action='RoomsListServlet' method = 'post'>";
				table += "<input type = 'hidden' name = 'buildingID' value='" + buildingID + "'>";
				table += "<input type = 'hidden' name = 'cancelAction' value='" + cancelAction + "'>";
				table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
				table += "</form>";

				
				System.out.println("RoomsSelectQuery: createEditRoomForm: buildingID END = " + buildingID);
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in RoomsSelectQuery: createEditRoomForm:  Query = " + query);
			}
		
			//System.out.println("RoomsSelectQuery: createEditRoomForm: buildingID VERY END = " + buildingID);
			
			return table;
		
			
		}
		
		/**
		 * This method takes the data about the room and updates the room's record in the Rooms table
		 * @author Ginger Nix
		 * @param roomID table record ID for room
		 * @param roomNumber room number
		 * @param roomFloor the floor of the room in the building
		 * @param roomStatus 0 for offline, 1 for online
		 * @param adminID table record ID for the admin in the database
		 */
		public void updateRoomTable(int roomID, String roomNumber, int roomFloor, int roomStatus, int adminID) {

			String query = "UPDATE tomcatdb.Rooms SET "
							+ "roomNumber = ?, roomFloor = ?,  "
							+ "roomStatus = ?, Admin_adminID = ? "
							+ "WHERE roomID = ?";		
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);		
				ps.setString(1, roomNumber);
				ps.setInt(2, roomFloor);
				ps.setInt(3, roomStatus);
				ps.setInt(4, adminID);
				ps.setInt(5, roomID);
				
				ps.executeUpdate();

					
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in RoomsSelectQuery updateRoomTable:  Query = " + query);
			}
			
		}

		/**
		 * This method creates a blank form to add a new room to the building ID provided
		 * @param buildingID table record ID in the building database table
		 * @param cancelAction send cancel action of not adding a room
		 * @return String HTML table form to add a room
		 * @author: Ginger Nix
		 */
		public String createAddRoomForm(int buildingID, String cancelAction){
			
			String table = "";
				
				BuildingSelectQuery bsq = new BuildingSelectQuery();
				String buildingName = bsq.getBuildingNameFromID(buildingID); //get human readable bldg name

				table += "<div align='center'><h2>Add a Room to: " + buildingName + "</h2></div><br />";

				
				table += "<form action='RoomAddSaveServlet' method = 'post'>";
				
				table += "Room Number: &nbsp;&nbsp;";
				table +=  "<input type='text' id = 'roomNumber' name = 'roomNumber' required>";
				table += "<br /><br />";
				
				table += "Room Floor: &nbsp;&nbsp;";
				table +=  "<input type='text' name = 'roomFloor' required>";
				table += "<br /><br />";
					
				table += "Room Status: &nbsp;&nbsp;";
				table += "<select name = 'roomStatus' required>";
				table += "<option value='1' selected>Online</option>";
				table += "<option value='0'>Offline</option>";							
				table += "</select>";	
				
				table += "<br /><br />";
				
				table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Save'>";	
				table += "<input type = 'hidden' name = 'buildingID' value='" + buildingID + "'>";
				table += "<input type = 'hidden' name = 'cancelAction' value='" + cancelAction + "'>";
				
				table += "</form>";
				
				table += "<form action='RoomsListServlet' method = 'post'>";
				table += "<input type = 'hidden' name = 'buildingID' value='" + buildingID + "'>";
				table += "<input type = 'hidden' name = 'cancelAction' value='" + cancelAction + "'>";
				table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
				table += "</form>";
	
				System.out.println("RoomsSelectQuery: createAddRoomForm: buildingID END = " + buildingID);
				System.out.println("RoomsSelectQuery: createAddRoomForm: cancelAction END = " + cancelAction);
	
			return table;
		
			
		}

		/**
		 * This method takes the room object and see if this room already exists in the rooms table
		 * having the same room number, room floor, and building
		 * @param room Room object
		 * @return boolean value of if the room exists in the room table
		 * @author: Ginger Nix
		 */
		public boolean roomAlreadExists (Rooms room){
			
			Rooms roomToCheck = new Rooms();
			roomToCheck = room;
			
			System.out.println("RoomsSelectQuery: roomAlreadyExists: room building id, rm num, flr = "
								+ roomToCheck.getBuildingID() + " "
								+ roomToCheck.getRoomNumber() + " "
								+ roomToCheck.getRoomFloor());
			
			String query = "SELECT * FROM tomcatdb.Rooms "
							+ "WHERE Building_buildingID = ? "
							+ "AND roomNumber = ? "
							+ "AND roomFloor = ? "
							+ "LIMIT 1";
							
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);		
				ps.setInt(1, roomToCheck.getBuildingID());
				ps.setString(2, roomToCheck.getRoomNumber());
				ps.setInt(3, roomToCheck.getRoomFloor());
				
				this.results = ps.executeQuery();

				boolean results = this.results.next();
				
				if (results) {//the room is in the room table already
					System.out.println("RoomsSelectQuery: roomAlreadyExists: TRUE");
					return true;
				}else{
					System.out.println("RoomsSelectQuery: roomAlreadyExists: FALSE");
					return false;
				}

					
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in RoomsSelectQuery room AlreadyExists:  Query = " + query);
			}
			
			return false;
							
		}

		/**
		 * This method take the room info and adds it to the rooms table
		 * @param room Room object with the adminID, buildingID, room number, room floor, and room status set
		 * @author: Ginger Nix
		 */
		public void insertRoomsTable(Rooms room) {
			
			Rooms roomToInsert = new Rooms();
			roomToInsert = room;
			
			System.out.println("RoomsSelectQuery: insertRoomsTable: room num = " + roomToInsert.getRoomNumber());
			
			String query = "INSERT INTO tomcatdb.Rooms "
					+ "(Admin_adminID, Building_buildingID, roomNumber, roomFloor, roomStatus) "
					+ "VALUES (?, ?, ?, ?, ?)";
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);		
				ps.setInt(1, roomToInsert.getAdminID());
				ps.setInt(2, roomToInsert.getBuildingID());
				ps.setString(3, roomToInsert.getRoomNumber());
				ps.setInt(4, roomToInsert.getRoomFloor());
				ps.setInt(5, roomToInsert.getRoomStatus());

				ps.executeUpdate();
				

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in RoomSelectQuery insert into rooms method. Query = " + query);
			}
			
		}
}
