

package helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import model.Reservation;
import model.Rooms;
import model.User;

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



	public String ListUserReservations(int userRecordID){
		
		String table = "";
		this.reserveID = 0;
		String userPlace = ""; //either primary or secondary
		
		System.out.println("list user reservations");   

		//get today's date to list the reservations today or later
		String currentDate = "";	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();	
		currentDate = dateFormat.format(date);
		
		System.out.println("List User Resv: current date  = " + currentDate);
		
		
		//===== GET NUMBER OF RESERVATIONS USING TODAY's DATE =====//
		// get all reservations for today and forward where user is primary or secondary - can't search on time yet
		// and the room is free (free = 0) ( which means they didn't fail to check into the room and they didn't cancel the reservation)
		// and the room info for the reservation
		// and the date is today or later (will have to deal with time later)
		
		String query = "SELECT * FROM tomcatdb.Reservations, tomcatdb.Rooms, tomcatdb.Building "
						+ "WHERE Reservations.Rooms_roomID = Rooms.roomID "
						+ "AND Rooms.Building_buildingID = Building.buildingID "
						+ " AND (Reservations.primaryUser = '" + userRecordID + "' OR Reservations.secondaryUser = '" + userRecordID + "')"
						+ " AND Reservations.free = 0"
						+ " AND Reservations.reserveStartDate >= '" + currentDate + "'"
						+ " ORDER BY reserveStartDate, reserveStartTime";

	
		System.out.println("list user resv list user resv query " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
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
					
					
					
					while(this.results.next()){ //go through all records returned
						Reservation resv = new Reservation();
						resv.setReserveID(this.results.getInt("reserveID"));
						System.out.println("In WHILE in List User Resv.java: list user reservations method. ReserveID = " + resv.getReserveID());
						
						// if the record is the current date, check to see if time is => current time
						// and if so, add it to the table
						// we have the current date from above
						// get the resv date
						//resv.setReserveStartDate(this.results.getString("reserveStartDate"));
						
						
						//grab the data we want to display
						// get the primary and secondary user
						
						//get whether they are primary or secondary - USE reservation resv later???						
						if (userRecordID == this.results.getInt("primaryUser")){
							System.out.println("In List User Resv.java: user is primary user");	
							userPlace = "Primary User";		
						}else{
							System.out.println("In List User Resv.java: user is secondary user");	
							userPlace = "Seconday User";
						}
						
	
						resv.setReserveStartDate(this.results.getString("reserveStartDate"));
						resv.setReserveEndDate(this.results.getString("reserveEndDate"));
						System.out.println("In WHILE in List User Resv.java: reserve start date = " + resv.getReserveStartDate());
						System.out.println("In WHILE in List User Resv.java: end date = " + resv.getReserveEndDate());
						
						resv.setReserveStartTime(this.results.getString("reserveStartTime"));			
						resv.setReserveEndTime(this.results.getString("reserveEndTime"));
						
						String building = this.results.getString("buildingName");
						String roomFloor = this.results.getString("roomFloor");
						String roomNumber = this.results.getString("roomNumber");

						String resvStartDate = resv.getReserveStartDate();
						System.out.println("List User Resv: 2 current date  = " + currentDate);
						System.out.println("List User Resv: 2 start date  = " + resvStartDate);
									
						
						if (Objects.equals(resvStartDate, currentDate)) { // if this reservation is for today
							System.out.println("In WHILE in List User Resv.java: resvStartDate = currentDate");
							
							// We have a reservation for today. Let's check to see if it is later or before the current hour
							
							resv.setReserveStartTime(this.results.getString("reserveStartTime"));
							
							// grab the reservation's start time's hour
							String resvHour = resv.getReserveStartTime();
							System.out.println("1 In WHILE in List User Resv.java: reserve start hour = " + resvHour);
							resvHour = resvHour.substring(0, 2);  		//need to grab just the hour
							System.out.println("2 In WHILE in List User Resv.java: reserve hour = " + resvHour);
							
							// convert hours to integers to compare
							int currentHourInt = Integer.parseInt(currentHour);
							System.out.println("3 In WHILE in List User Resv.java: current hour INT = " + currentHourInt);
							int resvHourInt = Integer.parseInt(resvHour);
							System.out.println("4 In WHILE in List User Resv.java: reserve hour INT = " + resvHourInt);
							
							//is the current hour > reservation's hour?
							if (currentHourInt > resvHourInt){
								//ignore this record since reservation before now
								System.out.println("5 In WHILE in List User Resv.java: current hour > resv hour " + this.results.getInt("reserveID"));
								
								
							}else if (currentHourInt < resvHourInt){
								System.out.println("6 In WHILE in List User Resv.java: current hour < resv hour " + this.results.getInt("reserveID"));
								//keep this record, will have a cancel button
								
								if (firstTime){

									table = "<table>";
									table += "<tr>";
									table += "<td> Start Date</td>";
									table += "<td> End Date</td>";
									table += "<td> Start Time</td>";
									table += "<td> End Time </td>"; 
									table += "<td> Building </td>";
									table += "<td> Room Floor</td>";
									table += "<td> Room Number</td>";
									table += "<td> Primary/Secondary</td>";
									table += "</tr>";
									
									firstTime = false;
									
								}
								
								
								table += "<tr>";
								table += "<td>" + resv.getReserveStartDate() + "</td>";
								table += "<td>" + resv.getReserveEndDate() + "</td>";
								table += "<td>" + resv.getReserveStartTime() + "</td>";
								table += "<td>" + resv.getReserveEndTime() + "</td>"; 
								table += "<td>" + building + "</td>";
								table += "<td>" + roomFloor + "</td>";
								table += "<td>" + roomNumber + "</td>";
								table += "<td>" + userPlace + "</td>";
								
				
								table += "<td> CANCEL </td>"; //TODO add link	
								
							}else {// else current hour = reservations hour - check to see if it is within the 
								
								// check-in time (up to 10 minutes after the hour) and if it is, have CHECKIN button,
								// else list but have no active button - maybe say "missed check-in time"
								
								System.out.println("7 In WHILE in List User Resv.java: current hour = resv hour " + this.results.getInt("reserveID"));
								
								// we know the hours are the same, so check to see if the current minute
								// is greater than 10 (check to see if it's past 10 minutes after the hour)
								String currentMinute = "";
								DateFormat minuteFormat = new SimpleDateFormat("mm");
								Date minute = new Date();
								currentMinute = minuteFormat.format(minute);
								System.out.println("List User Resv: current minute = " + currentMinute);
								
								// convert minute to integer to see if more than 10
								int currentMinuteInt = Integer.parseInt(currentMinute);
								System.out.println("In WHILE in List User Resv.java: current hour INT = " + currentMinuteInt);	
								
								if (firstTime){

									table = "<table>";
									table += "<tr>";
									table += "<td> Start Date</td>";
									table += "<td> End Date</td>";
									table += "<td> Start Time</td>";
									table += "<td> End Time </td>"; 
									table += "<td> Building </td>";
									table += "<td> Room Floor</td>";
									table += "<td> Room Number</td>";
									table += "<td> Primary/Secondary</td>";
									table += "</tr>";
									
									firstTime = false;
									
								}
								// add the fields to the string table that we need to display
								// already know we want to display this reservation
								// but check below on whether to have a 'cancel' button or a 'check-in' button
								table += "<tr>";
								table += "<td>" + resv.getReserveStartDate() + "</td>";
								table += "<td>" + resv.getReserveEndDate() + "</td>";
								table += "<td>" + resv.getReserveStartTime() + "</td>";
								table += "<td>" + resv.getReserveEndTime() + "</td>"; 
								table += "<td>" + building + "</td>";
								table += "<td>" + roomFloor + "</td>";
								table += "<td>" + roomNumber + "</td>";
								table += "<td>" + userPlace + "</td>";
								
								if (currentMinuteInt <= 10){
									// it's not past 10 after the hour, so let them check-in
									// display the check-in button that points to the check-in servlet
									
									table += "<td> CHECK IN </td>"; //TODO add ink
								
								}else{
									table += "<td> *Too late to check in* </td>";
								}	
								
							}//end current hour = resv hour
								
							
						}else {// end if record's date > current date 
							if (firstTime){

								table = "<table>";
								table += "<tr>";
								table += "<td> Start Date</td>";
								table += "<td> End Date</td>";
								table += "<td> Start Time</td>";
								table += "<td> End Time </td>"; 
								table += "<td> Building </td>";
								table += "<td> Room Floor</td>";
								table += "<td> Room Number</td>";
								table += "<td> Primary/Secondary</td>";
								table += "</tr>";
								
								firstTime = false;
								
							}
							// add the fields to the string table that we need to display
							// already know we want to display this reservation
							// but check below on whether to have a 'cancel' button or a 'check-in' button
							table += "<tr>";
							table += "<td>" + resv.getReserveStartDate() + "</td>";
							table += "<td>" + resv.getReserveEndDate() + "</td>";
							table += "<td>" + resv.getReserveStartTime() + "</td>";
							table += "<td>" + resv.getReserveEndTime() + "</td>"; 
							table += "<td>" + building + "</td>";
							table += "<td>" + roomFloor + "</td>";
							table += "<td>" + roomNumber + "</td>";
							table += "<td>" + userPlace + "</td>";
							
							table += "<td> CANCEL </td>";  //TODO add link
						
						}
						
						table += "</tr>"; //end this row for this reservation
						
					}// end while going through records returned
					if (!firstTime){ //headers written to table, so close table
						table += "</table>";
					}
					
					this.results.beforeFirst(); //reset pointer back to the beginning of the records returned just in case
				
				
			}else{//no records returned
				System.out.println("Success in List User Resv.java: no results found");
				return table;
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in List User Resv.java: get records. Query = " + query);
		}
		
		return table;	// may return records or an empty table
	}
	

}


