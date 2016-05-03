/**
 * @author: Ginger Nix
 * This class contains methods to help with listing a user's reservations.
 */

package helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import model.Reservation;
import model.TimeConverter;
import model.User;
import model.DateTimeConverter;
import model.DbConnect;

public class ListUserReservationsQuery {
	private java.sql.Connection connection;
	private ResultSet results;
	private int reserveID;

	public ListUserReservationsQuery(){
		
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
	 * This method returns a user's reservations from this time forward based on the
	 * user's recd ID
	 * @param userRecordID user record ID for a user
	 * @return a table listing all the user's reservations
	 */
	public String ListUserReservations(int userRecordID){
		
		String table = "";
		this.reserveID = 0;
		String userPlace = ""; //either primary or secondary
		
		//get today's date to list the reservations today or later
		String currentDate = "";	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();	
		currentDate = dateFormat.format(date);
		
		// Used to convert date and time from SQL format to more standard format for display.
		TimeConverter tc = new TimeConverter();
		DateTimeConverter dtc = new DateTimeConverter();
		
		System.out.println("List User Resv: current date  = " + currentDate);
		
		
		//===== GET NUMBER OF RESERVATIONS USING TODAY's DATE =====//
		// get all reservations for today and forward where user is primary or secondary - can't search on time yet
		// and the room is free (free = 0) ( which means they didn't fail to check into the room and they didn't cancel the reservation)
		// and the room info for the reservation
		// and the date is today or later (will have to deal with time later)
		
		String query = "SELECT * FROM tomcatdb.Reservations, tomcatdb.Rooms, tomcatdb.Building"
					+ " WHERE Reservations.Rooms_roomID = Rooms.roomID"
					+ " AND Rooms.Building_buildingID = Building.buildingID"
					+ " AND (Reservations.primaryUser = ? OR Reservations.secondaryUser = ? ) "
					+ " AND Reservations.free = 'N' "
					+ " AND Reservations.reserveStartDate >= ? "
					+ " ORDER BY reserveStartDate, reserveStartTime";

	
		System.out.println("list user resv list user resv query " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, userRecordID);
			ps.setInt(2, userRecordID);
			ps.setString(3, currentDate);
			
			this.results = ps.executeQuery();
			System.out.println("Success in List User Resv.java: list user reservations method. Query = " + query);
		
			if (this.results.next()) { 
				this.reserveID = this.results.getInt("reserveID");	
				System.out.println("Success in List User Resv.java: list user reservations method. ReserveID = " + this.reserveID);
				
					this.results.beforeFirst();
					
					boolean firstTime = true;
					
					//get current time to check to see if record is => current time
					String currentHour = "";
					DateFormat hourFormat = new SimpleDateFormat("HH");
					Date time = new Date();
					currentHour = hourFormat.format(time);
					System.out.println("List User Resv: current hour = " + currentHour);
					
					// get the user's name to be able to display on page
					UserHelper uh = new UserHelper();
					User user = uh.getUserInfoFromRecdID(userRecordID);
					
					table += "<div align='center'><h2>Reservations for: " 
							+ user.getUserFirstName() + " "  
							+ user.getUserLastName() + "</h2></div>"; 
					
					while(this.results.next()){ //go through all records returned
						Reservation resv = new Reservation();
						resv.setReserveID(this.results.getInt("reserveID"));
						System.out.println("In WHILE in List User Resv.java: list user reservations method. ReserveID = " + resv.getReserveID());
						
						// get both the primary and secondary on the reservation to list
						// get the primary and secondary user record id's to get more info
						
						// get primary and secondary's names to list
						
						int primaryOnResvRecdID = this.results.getInt("primaryUser");
						int secondaryOnResvRecdID = this.results.getInt("secondaryUser");
						
						User primary = new User();
						User secondary = new User();
						
						primary = uh.getUserInfoFromRecdID(primaryOnResvRecdID);
						secondary = uh.getUserInfoFromRecdID(secondaryOnResvRecdID);
						
						String primaryName = primary.getUserFirstName() + " " + primary.getUserLastName();
						String secondaryName = secondary.getUserFirstName() + " " + secondary.getUserLastName();
						
						
						// if the record is the current date, check to see if time is => current time
						// and if so, add it to the table
						// we have the current date from above
						// get the resv date
						
						resv.setReserveStartDate(this.results.getString("reserveStartDate"));			
						resv.setReserveStartTime(this.results.getString("reserveStartTime"));			
						resv.setReserveEndTime(this.results.getString("reserveEndTime"));
						
						String building = this.results.getString("buildingName");
						String roomFloor = this.results.getString("roomFloor");
						String roomNumber = this.results.getString("roomNumber");

						String resvStartDate = resv.getReserveStartDate();
						
						if (Objects.equals(resvStartDate, currentDate)) { // if this reservation is for today
							System.out.println("In WHILE in List User Resv.java: resvStartDate = currentDate");
							
							// We have a reservation for today. Let's check to see if it is later or before the current hour
							
							resv.setReserveStartTime(this.results.getString("reserveStartTime"));
							
							// grab the reservation's start time's hour
							String resvHour = resv.getReserveStartTime();
							resvHour = resvHour.substring(0, 2);  		//need to grab just the hour
							
							// convert hours to integers to compare
							int currentHourInt = Integer.parseInt(currentHour);
							int resvHourInt = Integer.parseInt(resvHour);
							
							//is the current hour > reservation's hour?
							if (currentHourInt > resvHourInt){
								//ignore this record since reservation before now
																
							}else if (currentHourInt < resvHourInt){
								//keep this record, will have a cancel button
								
								if (firstTime){

									table += "<table id = '' class = 'mdl-data-table' cellspacing = '0' width = '95%'>";
									table += "<thead>";
									table += "<tr>";
									table += "<th> Date</th>";									
									table += "<th> Start Time</th>";
									table += "<th> End Time </th>"; 
									table += "<th> Building </th>";
									table += "<th> Room Floor</th>";
									table += "<th> Room Number</th>";
									table += "<th> Primary </th>";
									table += "<th> Secondary </th>";
									table += "<th></th>";
									table += "</tr>";
									table += "</thead>";
									table += "<tbody>";
									
									firstTime = false;
									
								}
								
								
								table += "<tr>";
								table += "<td>" + dtc.convertDateLong(resv.getReserveStartDate()) + "</td>";
								table += "<td>" + tc.convertTimeTo12(resv.getReserveStartTime()) + "</td>";
								table += "<td>" + tc.convertTimeTo12(TimeConverter.addOneSecondToTime(resv.getReserveEndTime())) + "</td>"; 
								table += "<td>" + building + "</td>";
								table += "<td>" + roomFloor + "</td>";
								table += "<td>" + roomNumber + "</td>";
								table += "<td>" + primaryName + "</td>";
								table += "<td>" + secondaryName + "</td>";
								
								table += "<td><form action='CancelConfirmServlet' method = 'post'>" +
										"<input type='hidden' name='resv_id' value='" + resv.getReserveID()+ "'>" +
										"<input class='btn btn-lg btn-red' type='submit' value='Cancel Reservation'>" +
										"</form></td>";		
								
							
								
							}else {// else current hour = reservations hour - check to see if it is within the 
								
								// check-in time (up to 10 minutes after the hour) and if it is, have CHECKIN button,
								// else list but have no button 
								
								System.out.println("7 In WHILE in List User Resv.java: current hour = resv id " + this.results.getInt("reserveID"));
								
								// we know the hours are the same, so check to see if the current minute
								// is greater than 10 (check to see if it's past 10 minutes after the hour)
								String currentMinute = "";
								DateFormat minuteFormat = new SimpleDateFormat("mm");
								Date minute = new Date();
								currentMinute = minuteFormat.format(minute);
								System.out.println("List User Resv: current minute = " + currentMinute);
								
								
								// convert minute to integer to see if more than 10
								int currentMinuteInt = Integer.parseInt(currentMinute);
								
								//FOR TESTING 
								//currentMinuteInt = 2; 
								//currentMinuteInt = 20; 
								
								System.out.println("In WHILE in List User Resv.java: current minute INT = " + currentMinuteInt);	
								
								// if this is the first time around, print out the table headers
								if (firstTime){

									table = "<table id = '' class = 'mdl-data-table' cellspacing = '0' style='width:95%;'>";
									table += "<thead>";
									table += "<tr>";
									table += "<th> Date</th>";
									table += "<th> Start Time</th>";
									table += "<th> End Time </th>"; 
									table += "<th> Building </th>";
									table += "<th> Room Floor</th>";
									table += "<th> Room Number</th>";
									table += "<th> Primary </th>";
									table += "<th> Secondary </th>";
									table += "<th></th>";
									table += "</tr>";
									table += "</thead>";
									table += "<tbody>";
									firstTime = false;
									
								}
								// add the fields to the string table that we need to display
								// already know we want to display this reservation
								// but check below on whether to have a 'cancel' button or a 'check-in' button
								
								table += "<tr>";
								table += "<td>" + dtc.convertDateLong(resv.getReserveStartDate()) + "</td>";
								table += "<td>" + tc.convertTimeTo12(resv.getReserveStartTime()) + "</td>";
								table += "<td>" + tc.convertTimeTo12(TimeConverter.addOneSecondToTime(resv.getReserveEndTime())) + "</td>";  
								table += "<td>" + building + "</td>";
								table += "<td>" + roomFloor + "</td>";
								table += "<td>" + roomNumber + "</td>";
								table += "<td>" + primaryName + "</td>";
								table += "<td>" + secondaryName + "</td>";
								
								ReservationQuery rq = new ReservationQuery(); // to check below if already checked in 
								
								if ( (currentMinuteInt <= 10) && (!rq.alreadyCheckedIn(resv.getReserveID() )) ){
									// it's not past 10 after the hour, so let them check-in
									// display the check-in button that points to the check-in servlet
				
									table += "<td><form action='OnlineCheckInServlet' method = 'post'>" +
									"<input type='hidden' name='resv_id' value='" + resv.getReserveID() + "'>" +
											"<input class='btn btn-lg btn-red' type='submit' value='Check In'>" +
											"</form></td>";	
									
									System.out.println("*******List User Resv.java: in cur min <= 10 and not checked in w/ resv id= " + resv.getReserveID());	
									
								}else if (rq.alreadyCheckedIn(resv.getReserveID())) {
									table += "<td><h4> *Successfully Checked In* </h4></td>";
									System.out.println("*******List User Resv.java: in cur min <= 10 and already checked in w/ resv id= " + resv.getReserveID());	
									
								}else{
									table += "<td><h4> *Too late to check in* </h4> </td>";
									System.out.println("*******List User Resv.java: in cur min <= 10 and TOO LATE TO CHECK IN");	
									
								}	
							
								table += "</tr>";
								
							}//end current hour = resv hour
								
							
						}else {// end if record's date > current date 
							if (firstTime){

								table = "<table id = '' class = 'mdl-data-table' cellspacing = '0' style='width:95%;'>";
								table += "<thead>";
								table += "<tr>";
								table += "<th> Date</th>";
								table += "<th> Start Time</th>";
								table += "<th> End Time </th>"; 
								table += "<th> Building </th>";
								table += "<th> Room Floor</th>";
								table += "<th> Room Number</th>";
								table += "<th> Primary </th>";
								table += "<th> Secondary </th>";
								table += "<th></th>";
								table += "</tr>";
								table += "</thead>";
								table += "<tbody>";
								firstTime = false;
								
							}
							// add the fields to the string table that we need to display
							// already know we want to display this reservation
							// but check below on whether to have a 'cancel' button or a 'check-in' button
							//table = "<table id = '' class = 'mdl-data-table' cellspacing = '0' width = '95%'>";
							
							table += "<tr>";
							table += "<td>" + dtc.convertDateLong(resv.getReserveStartDate()) + "</td>";	
							table += "<td>" + tc.convertTimeTo12(resv.getReserveStartTime()) + "</td>";
							table += "<td>" + tc.convertTimeTo12(TimeConverter.addOneSecondToTime(resv.getReserveEndTime())) + "</td>";  
							table += "<td>" + building + "</td>";
							table += "<td>" + roomFloor + "</td>";
							table += "<td>" + roomNumber + "</td>";
							table += "<td>" + primaryName + "</td>";
							table += "<td>" + secondaryName + "</td>";
								
							table += "<td><form action='CancelConfirmServlet' method = 'post'>" +
									"<input type='hidden' name='resv_id' value='" + resv.getReserveID()+ "'>" +
									"<input class='btn btn-lg btn-red' type='submit' value='Cancel Reservation'>" +
									"</form></td>";		

						
						}
						
						table += "</tr>"; //end this row for this reservation
						
						
					}// end while going through records returned
					
					if (!firstTime){ //headers written to table, so close table
						table += "</tbody>";
						table += "</table>";
					}else{
						table = "";
					}
					

					
					
					this.results.beforeFirst(); //reset pointer back to the beginning of the records returned just in case
				
				
			}else{//no records returned
				System.out.println("Success in List User Resv.java: no results found");
				table = "";
				return table;
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in List User Resv.java: get records. Query = " + query);
		}
		
		return table;	// may return records or an empty table
	}
	
	
	/**
	 * This method gets a user's single reservation info from the reservation record id 
	 * @param resv_id the table record ID in the reservations table in the database
	 * @return table containing the user's reservation
	 */
	public String GetUserReservation(int resv_id){
		String table = "";
		// Used to convert date and time from SQL format to more standard format for display.
		TimeConverter tc = new TimeConverter();
		DateTimeConverter dtc = new DateTimeConverter();
		
		String query = "SELECT * FROM tomcatdb.Reservations, tomcatdb.Rooms, tomcatdb.Building "
				+ "WHERE Reservations.Rooms_roomID = Rooms.roomID "
				+ "AND Rooms.Building_buildingID = Building.buildingID "
				+ "AND Reservations.reserveID = ? ";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, resv_id);
			
			this.results = ps.executeQuery();
			this.results.next();
			
			this.reserveID = this.results.getInt("reserveID");	
			System.out.println("List User Resv: get user resv reserve ID " + this.reserveID);
			
			
			String resvStartDate = this.results.getString("reserveStartDate");
			String resvStartTime = this.results.getString("reserveStartTime");			
			String resvEndTime = this.results.getString("reserveEndTime");			
			String building = this.results.getString("buildingName");
			String roomFloor = this.results.getString("roomFloor");
			String roomNumber = this.results.getString("roomNumber");

			// get the primary and secondary user names to list
			int primaryOnResvRecdID = this.results.getInt("primaryUser");
			int secondaryOnResvRecdID = this.results.getInt("secondaryUser");
			
			UserHelper uh = new UserHelper();
			User primary = new User();
			User secondary = new User();
			
			primary = uh.getUserInfoFromRecdID(primaryOnResvRecdID);
			secondary = uh.getUserInfoFromRecdID(secondaryOnResvRecdID);
			
			String primaryName = primary.getUserFirstName() + " " + primary.getUserLastName();
			String secondaryName = secondary.getUserFirstName() + " " + secondary.getUserLastName();
			
			table = "<table id = '' class = 'mdl-data-table' cellspacing = '0' style='width:95%;'>";
			table += "<thead>";
			table += "<tr>";
			table += "<th> Date</th>";
			table += "<th> Start Time</th>";
			table += "<th> End Time </th>"; 
			table += "<th> Building </th>";
			table += "<th> Room Floor</th>";
			table += "<th> Room Number</th>";
			table += "<th> Primary </th>";
			table += "<th> Secondary </th>";
			table += "<th>&nbsp</th>";
			table += "</tr>";
			table += "</thead>";
			
			table += "<tbody>";
			table += "<tr>";
			table += "<td>" + dtc.convertDateLong(resvStartDate) + "</td>";
			table += "<td>" + tc.convertTimeTo12(resvStartTime) + "</td>";
			table += "<td>" + tc.convertTimeTo12(TimeConverter.addOneSecondToTime(resvEndTime)) + "</td>";
			table += "<td>" + building + "</td>";
			table += "<td>" + roomFloor + "</td>";
			table += "<td>" + roomNumber+ "</td>";
			table += "<td>" + primaryName + "</td>";
			table += "<td>" + secondaryName + "</td>";
			
			table +=  "<td>"
					+ "<form action='CancelServlet' method = 'post'>" +
					"<input type='hidden' name='resv_id' value='" + resv_id+ "'>" +
					"<input class='btn btn-lg btn-red' type='submit' value='Cancel Reservation'>" +
					"</form>"
					+ "</td>";	
			
			table += "</tr></tbody></table>";

			table += "<br /><br />";
			table += "<form action='ViewServlet' method = 'post'>";
			table += "<input class='btn btn-lg btn-red' type='submit' value='Go back to viewing reservations'>";
			table += "<input type='hidden' name='noCancel' value='noCancel'>";
			table += "</form>";
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in List User Resv: get user reservation. Query = " + query);
		}
		return table;
		
		
	}

}


