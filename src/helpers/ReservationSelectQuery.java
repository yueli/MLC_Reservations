/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.TimeConverter;
import model.DbConnect;
import model.Reservation;

/**
 * @author Brian Olaogun
 * Helper for the Student and Admin side of the website.  This is for Student Browse.
 *
 */
public class ReservationSelectQuery {
	
	// initialize fields
	private Connection connection;
	private ResultSet results;
			
	/**
	 * 
	 */
	public ReservationSelectQuery() {

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
	 * 
	 * @param currentDate Sring current date in sql format
	 * @param startTime string start time in 24-hour sql format
	 * @param roomNumber String room number
	 */
	public void doReservationRead(String currentDate, String startTime, String roomNumber){

		String query = "SELECT Reservations.reserveID "
				        + "FROM tomcatdb.Reservations, tomcatdb.Rooms "
				        + "WHERE ((Reservations.reserveStartDate = ? ) "
				       	+ "AND ((Reservations.reserveStartTime = ?) OR (? BETWEEN reserveStartTime AND reserveEndTime))) "
				       	+ "AND Rooms.roomID = Reservations.Rooms_roomID "
				       	+ "AND Rooms.roomNumber = ? "
						+ "AND tomcatdb.Reservations.free = ?";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, currentDate);
			ps.setString(2, startTime);
			ps.setString(3, startTime);
			ps.setString(4, roomNumber);
			ps.setString(5, "N");
			
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in ReservationSelectQuery.java: doReservationRead method. Please check connection or SQL statement: " + query);
		} 
	}
	
	/**
	 * Different from the version below.  The connection has to be closed
	 * since there are too many open connections in RoomsSelectQuery.
	 * @return reservation ID if there is a reservation or an empty string if there isn't.
	 */
	public String doReservationResults(){
		String results = "";
		try {
			while(this.results.next()){
				Reservation reservation = new Reservation();
				reservation.setReserveID(this.results.getInt("reserveID"));
				results += reservation.getReserveID();
			} this.results.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//System.out.println("Results = " + results);
		return results;
	}
	/**
	 * Used in Admin Reservations.  
	 * The database isn't closed so other operations can be performed.
	 * @return reservation ID if there is a reservation or an empty string if there isn't.
	 */
	public String doReservationResults2(){
		String results = "";
		try {
			while(this.results.next()){
				Reservation reservation = new Reservation();
				reservation.setReserveID(this.results.getInt("reserveID"));
				results += reservation.getReserveID();
			} this.results.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return results;
	}
	
	// Main method used for testing
	public static void main (String [] args){
		String startDate = "2016-03-17";
		String endDate = "2016-03-17";
		String startTime = "11:00:00";
		String endTime = "12:00:00";
		String buildingID = "1";
		TimeConverter tc = new TimeConverter();
		ReservationSelectQuery res = new ReservationSelectQuery();
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		
		// list for the room number.  Below will print all times, inclusive between start and end
		List<String> roomNumber = rsq.roomList(Integer.parseInt(buildingID));
		List<String> times = tc.timeRangeList(startTime, endTime);
		
		// loop through each room after all times have been checked 
		for (int i = 0; i < roomNumber.size(); i++){
			// loop through each time then increment room
			for (int j =0; j < times.size(); j++){
				/*
				 * Check if there is a reservation at the time.
				 * If there isn't then returned is an empty string.
				 */
				res.doReservationRead(startDate, times.get(j), roomNumber.get(i));
				String reservationCheck = res.doReservationResults2();
				System.out.println("RES CHECK = " + reservationCheck);
				if(reservationCheck.isEmpty()){
					// start time is at index 0.
					if(j == 0){
						// testing - printing to console
						System.out.println();
						System.out.println("DATE " + startDate);
						System.out.println("END " + endDate);
						System.out.println("ROOM NUMBER " + roomNumber.get(i));
						System.out.println("TIME " + times.get(j));
						System.out.println();
						
						}
					// If reservation check is not empty = reserveID is the result.
					} else if(!reservationCheck.isEmpty()) {
						System.out.println("***** RESERVED ********");
						System.out.println("RESERVE ID = " + reservationCheck);
						System.out.println("DATE " + startDate);
						System.out.println("END " + endDate);
						System.out.println("ROOM NUMBER " + roomNumber.get(i));
						System.out.println("TIME " + times.get(j));
						System.out.println("***** RESERVED ********");
						System.out.println();
					}
				}
			}
	}
	

}
