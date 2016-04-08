/* @author: Ginger Nix
 * 
 * This helper has several methods that help the servlets for canceling a reservation, checking into
 * a reservation, and getting the building and room record id's.
 */

package helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.DbConnect;
import model.Reservation;

public class ReservationQuery {
		private java.sql.Connection connection;
		/**
		 * Prepared SQL statement (combats: SQL Injections)
		 */
		private PreparedStatement authenticateUserStatement;
		//private Connection connection; //NEED THIS OR THE ONE ABOVE????
		private ResultSet results;

		public ReservationQuery(){
			
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


		public void cancelReservation(int resv_id){
			// this method cancels the reservation by setting the free field to 'Y'
			// showing that the day, time, and room are now free for someone to reserve
			
			String query = "UPDATE tomcatdb.Reservations SET Reservations.free = 'Y' "
					+ "WHERE Reservations.reserveID = '" + resv_id + "'";
			
			System.out.println("cancel query - cancel reservation. Query = " + query);
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
			
				ps.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in Cancel Query cancelReservation. Query = " + query);
			}			
		}
		

		// This method returns the record ID for this building having this QR name
		public int getBuildingID(String buildingQRName){
			int buildingID = 0;		
			
			String query = "SELECT buildingID FROM tomcatdb.Building WHERE buildingQRName = '" + buildingQRName + "' LIMIT 1";
			System.out.println("Reservation: buildingQRName = " + buildingQRName);
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
		
				this.results = ps.executeQuery();
				
				this.results.next();
				
				buildingID = results.getInt("buildingID");
				System.out.println("Success in Reservation.java: get building ID method. Query = " + query + " building id = " + buildingID);
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in Reservation.java: get building ID method. Query = " + query);
			}
			
			return buildingID;	
			
		}
	
		// This method returns the record ID for this room having this QR room number
		public int getRoomID(String roomQRNum, int buildingID){
			int roomID = 0;		
			
			String query = "SELECT roomID FROM tomcatdb.Rooms WHERE roomNumber = '" + roomQRNum + "'"
					+ " AND Building_buildingID = '" + buildingID + "' LIMIT 1";
			
			System.out.println("Reservation: get room id: roomQR = " + roomQRNum + " bulding ID = " + buildingID);
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
		
				this.results = ps.executeQuery();
				
				this.results.next();
				
				roomID = results.getInt("roomID");
				System.out.println("Success in Reservation.java: get room ID method. Query = " + query);
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in Reservation.java: get room ID method. Query = " + query);
			}
			
			return roomID;	
			
		}
		
		// This method get the user, building, and room and checks to see if there is a reservation at this time
		// @return: record id of the reservation, or 0 if none found
		
		public int getUserReservation(int userRecdID, int buildingRecdID, int roomRecdID){
			
			int reservationID = 0;
			
			//get today's date to find the reservations for today
			String currentDate = "";	
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();	
			currentDate = dateFormat.format(date);

			DateFormat hourFormat = new SimpleDateFormat("HH"); // get current hour
			Date time = new Date(); // convert to Date object
			
			// get current time, format as XX:00:00, and use it in query			
			String currentTimeToCheck = "";
			currentTimeToCheck = hourFormat.format(time); // for example, currentTime = 11
			currentTimeToCheck = currentTimeToCheck + ":00:00"; // format current time to be XX:00:00;
			System.out.println("Reservation: current time to check = " + currentTimeToCheck);	

			// search for reservation..... it won't be free if it is too late to check in
			
			String query = "SELECT * FROM tomcatdb.Reservations" 
							+ " WHERE Building_buildingID = '" + buildingRecdID + "'" // in this building
							+ " AND (primaryUser = '" + userRecdID + "' OR secondaryUser = '" + userRecdID + "')" // with this user
								// the check in script will set free to Yes when they don't check-in in time 
							+ " AND free = 'N' " 
							+ " AND reserveStartDate = '" + currentDate + "'" // today
							+ " AND reserveStartTime = '" + currentTimeToCheck + "'" //for this hour
							+ " LIMIT 1";
			
			System.out.println("Reservation: getUserReservation query: " + query);
			
			try {
				
				PreparedStatement ps = this.connection.prepareStatement(query);

				this.results = ps.executeQuery();
				if (this.results.next()) {
					// found user's reservation, so get the record id to send back
					reservationID = this.results.getInt("reserveID");
					return reservationID;
					
				}else{
					return reservationID; //which is 0
				}

				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in Reservation.java: get room ID method. Query = " + query);
			}
			
			return reservationID;
	

		}

		
		//------------------------------------------------------------------
		
				public boolean checkInUser(int reservationID, int userRecordID){

					DateFormat currentTime = new SimpleDateFormat("H:mm:ss"); // get current time format ex/ 13:02:23
					Date time = new Date(); // convert to Date object
					String currentTimeForQuery = currentTime.format(time);

					System.out.println("ReservationQuery: check in user: current time = " + currentTimeForQuery);			
					
					String query = "INSERT INTO tomcatdb.CheckIn (Reservation_reserveID, User_userID) "
							+ "VALUES ('" + reservationID + "','" + userRecordID + "')";
						
					try {
						PreparedStatement ps = this.connection.prepareStatement(query);
					
						ps.executeUpdate();
						System.out.println("Success in Reservation Query: check in user: Query = " + query);

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("******* ERROR: in Reservation Query: check in user: Query = " + query);
						return false;
					}
					
					return true;
					
				}
		
		

		//------------------------------------------------------------------
		
				public boolean alreadyCheckedIn(int reservationID){

					String query = "SELECT * FROM tomcatdb.CheckIn "
							+ "WHERE Reservation_reserveID = '" + reservationID +"'";
							
					System.out.println("~~~~~~~~~~Resv Query: already checked in " + query);
					
					try {
						PreparedStatement ps = this.connection.prepareStatement(query);
					
						this.results = ps.executeQuery();
						
						if (this.results.next()) {
							// found user's reservation is checked in
							System.out.println("~~~~~~~~~~Resv Query: already checked in returning TRUE");
							return true;
							
						}else{
							System.out.println("~~~~~~~~~~Resv Query: already checked in returning FALSE");
							
							return false;
						}

					} catch (SQLException e) {
						e.printStackTrace();

						System.out.println("~~~~~~~~~~ERROR: Resv Query: " + query);
						
						return false;
					}
					
			}
				
}// end class
