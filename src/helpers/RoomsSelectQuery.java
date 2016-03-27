/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DateTimeConverter;
import model.DbConnect;
import model.Rooms;
import model.TimeConverter;

/**
 * @author Brian Olaogun
 * Helper for the Student side of the website.
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
		 */
		public Integer getRoomID (int buildingID, String roomNumber){
			int roomID;
			
			String query = "SELECT tomcatdb.roomID FROM tomcatdb.Rooms, tomcatdb.Building "
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
		 */
		public void doRoomRead(int buildingID, String floor){
			//String query = "SELECT * FROM tomcatdb.Rooms WHERE roomStatus = 1";
			String query = "SELECT roomID, roomNumber "
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
		 * 
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
						
						// check to see if room is reserved at the current hour at the current date
						// added seconds to timeblock so that its in sql format.
						rsq.doReservationRead(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i] + ":00", room.getRoomNumber());
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
						} else if (reserveHour < currentHour){
							table += "<td id='gray'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// if result set IS empty, then there IS NOT a reservation at that time
						
						} else {
							if (reserveHour == currentHour){
								if (tc.currentMinutes() > 10){
									table += "<td id='yellow'>";
									table += tc.convertTimeTo12(timeBlock[i]);
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
						
						// check to see if room is reserved at the current hour at the current date
						// added seconds to timeblock so that its in sql format.
						rsq.doReservationRead(dtc.parseDate(dtc.datetimeStamp()), timeBlock[i] + ":00", room.getRoomNumber());
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
						} else if (reserveHour < currentHour){
							table += "<td id='gray'>";
							table += tc.convertTimeTo12(timeBlock[i]);
						// if result set IS empty, then there IS NOT a reservation at that time
						} else {
							if (reserveHour == currentHour){
								if (tc.currentMinutes() > 10){
									table += "<td id='yellow'>";
									table += tc.convertTimeTo12(timeBlock[i]);
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
					table += "<th COLSPAN=4 ALIGN=CENTER><h3>";
					table += "Key";
					table += "</h3></th>";
					table += "</tr>";
					table += "</tbody>";
					table += "<tbody class='subcategory'>";
					table += "<tr>";
					table += "<td id='gray'>" + "Time Unavailable" + "</td>";
					table += "<td id='red'>" + "Time Reserved" + "</td>";
					table += "<td id='yellow'>" + "Open Room" + "</td>";
					table += "<td id='green'>" + "Time Available to Reserve" + "</td>";
					table += "</tr>";
					table += "<tr>";
					table += "<td COLSPAN=4 ALIGN=CENTER>" + "Open room times require no reservation to use room." + "</td>";
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
		 * @param buildingID
		 * @return
		 */
		public String ListRoomsInBuilding(int buildingID){
			
			BuildingSelectQuery bsq = new BuildingSelectQuery();
			
			String buildingName = bsq.getBuildingNameFromID(buildingID);
			
			System.out.println("Rooms Select Query: ListRoomsInBuilding buildingName = " + buildingName);

			System.out.println("Rooms Select Query: ListRoomsInBuilding buildingID = " + buildingID);
			
			String table = "";
			String roomStatus = "";
			
			table += "<h2>Rooms List for Building " + buildingName + "</h2>";
			
			table += "<table>";
			
			table += "<tr>";
			table += "<td>Room Floor</td>";
			table += "<td>Room Number</td>";
			table += "<td>Status</td>";
			table += "</tr>";
				
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
						roomStatus = "Active";
					}else{
						roomStatus = "Inactive";
					}
					
					table += "<tr>";
					table += "<td>" + room.getRoomFloor() + "</td>";
					table += "<td>" + room.getRoomNumber() + "</td>";
					table += "<td>" + roomStatus + "</td>";
					
				
					table += "<td><form action='RooomEditServlet' method = 'post'>" +
							"<input type='hidden' name='roomID' value='" + room.getRoomID() + "'>" +
							"<input type='submit' value='Edit Room'>" +
							"</form></td>";		
				
					table += "</tr>";
				
				} //end while
				
				table += "</table>";
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in RoomsSelectQuery.ListRoomsInBuilding:  Query = " + query);
			}
			
			table += "<br /><br />";
			table += "<p>";
			table += "<form action='RoomAddServlet' method = 'post'>" +
					"<input type='submit' value='Add A Room'>" +
					"</form>";			
			table += "</p>";
			

			return table;
			
		}
		
		
		
		
		
}
