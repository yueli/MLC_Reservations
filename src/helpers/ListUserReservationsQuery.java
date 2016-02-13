//// LEFT OFF TUES EVENING w/ NOT DISPLAYING ALL RECORDS IN TABLE - OVERWRITING??
// NOTE: BACK UP BEFORE MERGING
// ALSO CHANGE all reservations obj to use user and not student
// ALSO CHANGE resv end time to use the getEndTime method


package helpers;

import java.sql.DriverManager;
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

public class ListUserReservationsQuery {
	private java.sql.Connection connection;
	private ResultSet results;
	private int reserveID;

	public ListUserReservationsQuery(){
		String url = "jdbc:mysql://localhost:3306/" + "tomcatdb";
		
		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, "root", ""); // credentials for Brian, Ginger, & Victoria for local server
			//this.connection = DriverManager.getConnection(url, "tomcatuser", "bu11fr0g"); // credentials for dev server
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

/*	public void doGetStudentReservations(String currentDate, String time, int roomNumber){
		String query = "SELECT Reservation.reserveID FROM tomcatdb.Reservation, tomcatdb.Rooms WHERE Reservation.reserveStartDate = '" + currentDate + "'" + "AND ((Reservation.reserveStartTime = '" + time + "') OR ('" + time + "' BETWEEN reserveStartTime AND reserveEndTime)) AND Rooms.roomID = Reservation.Rooms_roomID and Rooms.roomNumber = " + roomNumber;

		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
		} 
	}*/


	public String ListUserReservations(int userRecordID){
		
		String table = "";
		this.reserveID = 0;
		String userPlace = ""; //either primary or secondary
		
		System.out.println("uh: list user reservations");   

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
		
		String query = "SELECT * FROM tomcatdb.Reservation, tomcatdb.Rooms, tomcatdb.Building "
						+ "WHERE Reservation.Rooms_roomID = Rooms.roomID "
						+ "AND Rooms.Building_buildingID = Building.buildingID "
						+ " AND (Reservation.primaryUser = '" + userRecordID + "' OR Reservation.secondaryUser = '" + userRecordID + "')"
						+ " AND Reservation.free = 0"
						+ " AND Reservation.reserveStartDate >= '" + currentDate + "'"
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

					//get current time to check to see if record is => current time
					
					String currentHour = "";
					
					DateFormat timeFormat = new SimpleDateFormat("HH");
					Date time = new Date();
					currentHour = timeFormat.format(time);
					System.out.println("List User Resv: current time = " + currentHour);
					
					// start the table since we have at least one record
					table = "<table>";
					table += "<tr>";
					table += "<td> Start Date</td>";
					table += "<td> End Date</td>";
					table += "<td> Start Time</td>";
					table += "<td> End Time </td>"; //TODO
					table += "<td> Building </td>";
					table += "<td> Room Floor</td>";
					table += "<td> Room Number</td>";
					table += "<td> Primary/Secondary</td>";
					table += "</tr>";
					
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
						
						//TODO???
						//resv.setReserveEndTime(this.results.getString("reserveEndTime"));
						String RESERVE_END_TIME = this.results.getString("reserveEndTime");
						
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
							
							// grab the reservation's start time
							String resvHour = resv.getReserveStartTime();
							System.out.println("1 In WHILE in List User Resv.java: reserve start time = " + resvHour);
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
								
								
							}else if (currentHourInt < resvHourInt){// if current hour > reservations hour
								//else current hour < reservations hour - keep this record, will have a cancel button
								System.out.println("6 In WHILE in List User Resv.java: current hour < resv hour " + this.results.getInt("reserveID"));
								
								//add the fields to the string table that we need to display
								table += "<tr>";
								table += "<td>" + resv.getReserveStartDate() + "</td>";
								table += "<td>" + resv.getReserveEndDate() + "</td>";
								table += "<td>" + resv.getReserveStartTime() + "</td>";
								table += "<td>" + RESERVE_END_TIME + "</td>"; //TODO
								table += "<td>" + building + "</td>";
								table += "<td>" + roomFloor + "</td>";
								table += "<td>" + roomNumber + "</td>";
								table += "<td>" + userPlace + "</td>";
								table += "<td> CANCEL </td>";
								table += "</tr>";
								
							}else {
								// else current hour = reservations hour - keep this too, special and will have a check-in button
								System.out.println("7 In WHILE in List User Resv.java: current hour = resv hour " + this.results.getInt("reserveID"));
								
								//add the fields to the string table that we need to display
								table += "<tr>";
								table += "<td>" + resv.getReserveStartDate() + "</td>";
								table += "<td>" + resv.getReserveEndDate() + "</td>";
								table += "<td>" + resv.getReserveStartTime() + "</td>";
								table += "<td>" + RESERVE_END_TIME + "</td>"; //TODO
								table += "<td>" + building + "</td>";
								table += "<td>" + roomFloor + "</td>";
								table += "<td>" + roomNumber + "</td>";
								table += "<td>" + userPlace + "</td>";
								table += "<td> CHECK IN </td>";
								table += "</tr>";
								
							}
								
							
						}else {// end if record's date = current date // reservation must be later than today
							//add the fields to the string table that we need to display
							table += "<tr>";
							table += "<td>" + resv.getReserveStartDate() + "</td>";
							table += "<td>" + resv.getReserveEndDate() + "</td>";
							table += "<td>" + resv.getReserveStartTime() + "</td>";
							table += "<td>" + RESERVE_END_TIME + "</td>"; //TODO
							table += "<td>" + building + "</td>";
							table += "<td>" + roomFloor + "</td>";
							table += "<td>" + roomNumber + "</td>";
							table += "<td>" + userPlace + "</td>";
							table += "<td> CANCEL </td>";
							table += "</tr>";
							
						}
						

						
					}// end while going through records returned
					
					table += "</table>";
					
					this.results.beforeFirst();
				
				
			}else{//no records returned
				System.out.println("Success in List User Resv.java: no results found");
				return table;
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("!!!!Error in List User Resv.java: get number of records. Query = " + query);
		}
		
		
		return table;	
		
		
	}
	
//-----------------------------------------------------------------------------------------	
	//CHANGE THIS TO CREATE TABLES OF RECORDS!!!!!
	// this method knows already that the user has records
	// it gets all the reservations for this user and
	// formats them into a table that is returned 
	/*public String listUserReservations(int userRecordID) {

		String table = "";
		
		this.numRecords = 0;
		
		System.out.println("uh: list user reservations");   

		//get today's date to list the reservations today or later
		String currentDate = "";	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();	
		currentDate = dateFormat.format(date);
		
		//get all reservations for today and forward where user is primary or secondary - can't search on time yet
		// and the room is free (free = 0) ( which means they didn't fail to check into the room and they didn't cancel the reservation)
		//and the room info for the reservation
		// and the date is today or later (will have to deal with time later)
		
		String query = "SELECT * FROM tomcatdb.Reservation, tomcatdb.Rooms WHERE Reservation.Rooms_roomID = Rooms.roomID"
				+ " AND (Reservation.primaryUser = '" + userRecordID + "' OR Reservation.secondaryUser = '" + userRecordID + "')"
						+ " AND Reservation.free = 0"
						+ " AND Reservation.reserveStartDate >= '" + currentDate + "'";
		
		System.out.println("uh list user resv query " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			//ps.executeQuery();
			this.results= ps.executeQuery();
			System.out.println("Success in UserHelper.java: list user reservations method. Query = " + query);
			
			if (!results.next() ) {
				   System.out.println("no data");
				   System.out.println("== 1 In UserHelper.java: list user reservations method: num recds should be 0 = "+ this.numRecords);
					
			}
			
			//get returned date results, which may be more than just one record
			// go through all the results returned, if any
			
			//MAY HAVE TO RESET pointer beforeFirst() 		
			
			while(this.results.next()){
				
				Reservation reservation = new Reservation();
				reservation.setReserveID(this.results.getInt("reserveID"));
				
				this.numRecords = this.results.getInt("numRecords");
				
				System.out.println("==In UserHelper.java: list user reservations method: reserve ID from result = "+ reservation.getReserveID());
				System.out.println("== 2 In UserHelper.java: list user reservations method: num recds = "+ this.numRecords);
				
				//product.setProd_id(this.results.getInt("prod_id"));
				//product.setProd_name(this.results.getString("prod_name"));
				//product.setProd_image_name(this.results.getString("prod_image_name"));
				//product.setProd_price(this.results.getDouble("prod_price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in UserHelper.java: list user reservations method. Query = " + query);
		}
		


		
		//this.userRecordID = userRecordID;
		
		//recordID = results.getInt("userID");

		// now get the reservations from the records returned that are now or later than the current time
		// the start and end times stored in reservations always have 00 for seconds
		
		//get the current time this method is being called
		
		//may need later
		String currentTime = "";
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date time = new Date();
		currentTime = timeFormat.format(time);
		
		System.out.println("uh list user resv current time " + currentTime);
		
		
		return table;
		
	}*/
}


