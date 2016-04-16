/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.DateTimeConverter;
import model.DbConnect;
import model.Reservation;
import model.Rooms;
import model.TimeConverter;
import model.User;

/**
 * @author Brian Olaogun
 * This class will be used for view reservations made (admin).
 *
 */
public class AdminReservationsSelectQuery {
	// used for user query and results
	private Connection connection;
	private ResultSet results;
	
	// used for admin query and results
	private Connection connection2;
	private ResultSet results2;
	/**
	 * Set up the JDBC Driver and connect to the database.
	 */
	public AdminReservationsSelectQuery() {

		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// hard coded the connection in DbConnect class
			this.connection = DbConnect.devCredentials();
			this.connection2 = DbConnect.devCredentials();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This is the query to get all user/student reservations
	 * @param buildingID the primary key ID of the building
	 * @param currentDate String date in sql format
	 */
	public void doUserReservationRead(int buildingID, String currentDate){
		
		// The query below pulls shows the primary & secondary's myID
		// TODO add the current date / date range of current date - 2 weeks from current date.
		String query = "SELECT a.myID AS 'primary', "
				+ "b.myID AS 'secondary',"
				+ "Rooms.roomNumber, "
				+ "Reservations.reserveStartDate, "
				+ "Reservations.reserveEndDate, "
				+ "Reservations.reserveStartTime, "
				+ "Reservations.reserveEndTime, "
				+ "Reservations.hourIncrement "
				+ "FROM tomcatdb.Reservations, tomcatdb.Rooms, tomcatdb.Building, tomcatdb.User AS a, tomcatdb.User AS b "
				+ "WHERE Reservations.Building_buildingID = tomcatdb.Building.buildingID "
				+ "AND Building.buildingID = ? "
				+ "AND Reservations.Rooms_roomID = tomcatdb.Rooms.roomID "
				+ "AND Reservations.primaryUser = a.userID "
				+ "AND Reservations.secondaryUser = b.userID "
				+ "AND Reservations.reserveStartDate = ? "
				+ "AND Reservations.free = 'N' "
				+ "ORDER BY Rooms.roomNumber, "
				+ "Reservations.reserveStartDate, "
				+ "Reservations.reserveStartTime DESC";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, buildingID);
			ps.setString(2, currentDate);
			
			this.results = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error in AdminReservationsSelectQuery.java: doUserReservationRead method. Please check connection or SQL statement: " + query);
		} 
	}
	/**
	 * This is the query to get all admin reservations
	 * @param buildingID int buildingID primary key
	 * @param currentDate String date in sql format
	 */
	public void doAdminReservationRead(int buildingID, String currentDate){
		
		// The query below pulls shows the primary & secondary's myID
		// TODO add the current date / date range of current date - 2 weeks from current date.
		String query = "SELECT tomcatdb.Reservations.reserveID, "
				+ "tomcatdb.Admin.adminMyID, "
				+ "tomcatdb.Admin.fname, "
				+ "tomcatdb.Admin.lname, "
				+ "tomcatdb.Reservations.reserveName, "
				+ "tomcatdb.Rooms.roomNumber, "
				+ "tomcatdb.Reservations.reserveStartDate, "
				+ "tomcatdb.Reservations.reserveEndDate, "
				+ "tomcatdb.Reservations.reserveStartTime, "
				+ "tomcatdb.Reservations.reserveEndTime, "
				+ "tomcatdb.Reservations.hourIncrement "
				+ "FROM tomcatdb.Reservations, tomcatdb.Rooms, tomcatdb.Building, tomcatdb.Admin "
				+ "WHERE tomcatdb.Reservations.Building_buildingID = tomcatdb.Building.buildingID "
				+ "AND tomcatdb.Building.buildingID = ? "
				+ "AND tomcatdb.Reservations.Rooms_roomID = tomcatdb.Rooms.roomID "
				+ "AND tomcatdb.Reservations.Admin_adminID = tomcatdb.Admin.adminID "
				+ "AND tomcatdb.Reservations.free = 'N' "
				+ "AND tomcatdb.Reservations.reserveStartDate = ? "
				+ "ORDER BY tomcatdb.Rooms.roomNumber ASC, "
				+ "tomcatdb.Reservations.reserveStartDate, "
				+ "tomcatdb.Reservations.reserveStartTime DESC";
		// securely run query
		try {
			PreparedStatement ps = this.connection2.prepareStatement(query);
			ps.setInt(1, buildingID);
			ps.setString(2, currentDate);
			
			this.results2 = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error in AdminReservationsSelectQuery.java: doAdminReservationRead method. Please check connection or SQL statement: " + query);
		} 
	}
	/**
	 * This will be the results of admin reservations for the clerk (read only view)
	 * @return Resultset from doAdminReservationRead method/query.
	 */
	public String doAdminReservationResults(String role){
		String table = "";
		if (role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")){
			System.out.println("FORWARD TO doAdminReservationsWithCancel");
			table = doAdminReservationResultsWithCancel();
			return table;
			
		} else {
			
			
			try {
				table += "<table id='' class='display'>";
				table += "<thead>";
				table += "<tr>";
				table += "<th>Room Number </th>";
				table += "<th>MyID </th>";
				table += "<th>First</th>";
				table += "<th>Last</th>";
				table += "<th>Reserve Name</th>";
				table += "<th>Start Date</th>";
				table += "<th>End Date</th>";
				table += "<th>Start Time</th>";
				table += "<th>End Time</th>";
				table += "<th>Hours</th>";
				table += "</tr>";
				table += "</thead>";
				table += "<tbody>";
				while(this.results2.next()){
					Admin admin = new Admin();
					Reservation reservation = new Reservation();
					Rooms room = new Rooms();
					
					DateTimeConverter dtc = new DateTimeConverter();
					TimeConverter tc = new TimeConverter();
					

					admin.setAdminMyID(this.results2.getString("adminMyID"));
					admin.setFname(this.results2.getString("fname"));
					admin.setLname(this.results2.getString("lname"));
					reservation.setReserveName(this.results2.getString("reserveName"));
					room.setRoomNumber(this.results2.getString("roomNumber"));
					reservation.setReserveStartDate(this.results2.getString("reserveStartDate"));
					reservation.setReserveEndDate(this.results2.getString("reserveEndDate"));
					reservation.setReserveStartTime(this.results2.getString("reserveStartTime"));
					reservation.setReserveEndTime(TimeConverter.addOneSecondToTime(this.results2.getString("reserveEndTime")));
					reservation.setHourIncrement(this.results2.getInt("hourIncrement"));
					
					table += "<tr>";
					table += "<td>" + room.getRoomNumber() + "</td>";
					table += "<td data-search='" + admin.getAdminID() + "'>" + admin.getAdminMyID() + "</td>";
					table += "<td data-search='" + admin.getFname() + "'>" + admin.getFname() + "</td>";
					table += "<td data-search='" + admin.getLname() + "'>" + admin.getLname() + "</td>";
					table += "<td data-search='" + reservation.getReserveName() + "'>" + reservation.getReserveName() + "</td>";
					table += "<td>" + dtc.convertDateLong(reservation.getReserveStartDate()) + "</td>";
					table += "<td>" + dtc.convertDateLong(reservation.getReserveEndDate()) + "</td>";
					table += "<td data-order='" + reservation.getReserveStartTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(reservation.getReserveStartTime()) + "</td>";
					table += "<td data-order='" + reservation.getReserveEndTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(reservation.getReserveEndTime()) + "</td>";
					table += "<td>" + reservation.getHourIncrement() + "</td>";
					table += "</tr>";
				} this.results2.beforeFirst();
				table += "</tbody>";
				table += "</table>";
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return table;
		}
		
	}
	/**
	 * This is the result set for admins and super admins for admin reservations.  
	 * This table includes a cancel button to cancel an admins reservation.
	 * @return table of admin reservations with a cancellation button for admin and super admins
	 * to cancel admin reservations.
	 */
	public String doAdminReservationResultsWithCancel(){
		System.out.println("IN doAdminReservationResultsWithCancel");
		String table = "";
		try {
			table += "<table id='' class='display'>";
			table += "<thead>";
			table += "<tr>";
			table += "<th>Room Number </th>";
			table += "<th>MyID </th>";
			table += "<th>First</th>";
			table += "<th>Last</th>";
			table += "<th>Reserve Name</th>";
			table += "<th>Start Date</th>";
			table += "<th>End Date</th>";
			table += "<th>Start Time</th>";
			table += "<th>End Time</th>";
			table += "<th>Hours</th>";
			table += "<th></th>";
			table += "</tr>";
			table += "</thead>";
			table += "<tbody>";
			int j = 0;
			while(this.results2.next()){
				Admin admin = new Admin();
				Reservation reservation = new Reservation();
				Rooms room = new Rooms();
				
				DateTimeConverter dtc = new DateTimeConverter();
				TimeConverter tc = new TimeConverter();
				
				reservation.setReserveID(this.results2.getInt("reserveID"));
				admin.setAdminMyID(this.results2.getString("adminMyID"));
				admin.setFname(this.results2.getString("fname"));
				admin.setLname(this.results2.getString("lname"));
				reservation.setReserveName(this.results2.getString("reserveName"));
				room.setRoomNumber(this.results2.getString("roomNumber"));
				reservation.setReserveStartDate(this.results2.getString("reserveStartDate"));
				reservation.setReserveEndDate(this.results2.getString("reserveEndDate"));
				reservation.setReserveStartTime(this.results2.getString("reserveStartTime"));
				reservation.setReserveEndTime(TimeConverter.addOneSecondToTime(this.results2.getString("reserveEndTime")));
				reservation.setHourIncrement(this.results2.getInt("hourIncrement"));
				
				table += "<tr>";
				table += "<td>" + room.getRoomNumber() + "</td>";
				table += "<td data-search='" + admin.getAdminID() + "'>" + admin.getAdminMyID() + "</td>";
				table += "<td data-search='" + admin.getFname() + "'>" + admin.getFname() + "</td>";
				table += "<td data-search='" + admin.getLname() + "'>" + admin.getLname() + "</td>";
				table += "<td data-search='" + reservation.getReserveName() + "'>" + reservation.getReserveName() + "</td>";
				table += "<td>" + dtc.convertDateLong(reservation.getReserveStartDate()) + "</td>";
				table += "<td>" + dtc.convertDateLong(reservation.getReserveEndDate()) + "</td>";
				table += "<td data-order='" + reservation.getReserveStartTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(reservation.getReserveStartTime()) + "</td>";
				table += "<td data-order='" + reservation.getReserveEndTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(reservation.getReserveEndTime()) + "</td>";
				table += "<td>" + reservation.getHourIncrement() + "</td>";
				table += "<td>";
				table += "<form name='adminCancel' id='cancelReserve" + j + "' action='AdminCancel' method='post'>";
				table += "<input type='hidden' name='reserveID' value='" + reservation.getReserveID() + "'>";
				table += "<input type='hidden' name='free' value='Y'>";
				table += "<input class='btn btn-lg btn-red' type='submit' value='Cancel'>";
				table += "</form>";
				table += "</td>";
				table += "</tr>";
				j++;
			} this.results2.beforeFirst();
			table += "</tbody>";
			table += "</table>";
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return table;
	}
	/**
	 * The user reservations query result set is put into an html table
	 * @return String HTML table with query resultset
	 */
	public String doUserReservationResults(){
		String table = "";
		try {
			table += "<table id='' class='display'>";
			table += "<thead>";
			table += "<tr>";
			table += "<th>Room Number </th>";
			table += "<th>Primary </th>";
			table += "<th>Secondary</th>";
			table += "<th>Start Date</th>";
			table += "<th>End Date</th>";
			table += "<th>Start Time</th>";
			table += "<th>End Time</th>";
			table += "<th>Hours</th>";
			table += "</tr>";
			table += "</thead>";
			table += "<tbody>";
			
			while(this.results.next()){
				User primary = new User();
				User secondary = new User();
				Reservation reservation = new Reservation();
				Rooms room = new Rooms();
				
				// used to format date
				// being used to convert time from 24-hour to 12-hour
				// used to convert date from yyyy-MM-dd to Month day, year format.
				DateTimeConverter dtc = new DateTimeConverter();
				TimeConverter tc = new TimeConverter();
				
				room.setRoomNumber(this.results.getString("roomNumber"));
				primary.setMyID(this.results.getString("primary"));
				secondary.setMyID(this.results.getString("secondary"));
				reservation.setReserveStartDate(this.results.getString("reserveStartDate"));
				reservation.setReserveEndDate(this.results.getString("reserveEndDate"));
				reservation.setReserveStartTime(this.results.getString("reserveStartTime"));
				reservation.setReserveEndTime(TimeConverter.addOneSecondToTime(this.results.getString("reserveEndTime")));
				reservation.setHourIncrement(this.results.getInt("hourIncrement"));
				
				table += "<tr>";
				table += "<td data-search='" + room.getRoomNumber() + "'>" + room.getRoomNumber() + "</td>";
				table += "<td data-search='" + primary.getMyID() + "'>" + primary.getMyID() + "</td>";
				table += "<td data-search='" + secondary.getMyID() + "'>" + secondary.getMyID() + "</td>";
				table += "<td data-search='" + reservation.getReserveStartDate()+ "'>" + dtc.convertDateLong(reservation.getReserveStartDate()) + "</td>";
				table += "<td data-search='" + reservation.getReserveEndDate() + "'>" + dtc.convertDateLong(reservation.getReserveEndDate()) + "</td>";
				table += "<td data-order='" + reservation.getReserveStartTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(reservation.getReserveStartTime()) + "</td>";
				table += "<td data-order='" + reservation.getReserveEndTime().replace(":", "").trim() + "'>" + tc.convertTimeTo12(reservation.getReserveEndTime()) + "</td>";
				table += "<td>" + reservation.getHourIncrement() + "</td>";
				table += "</tr>";
			
			} this.results.beforeFirst(); 
			table += "</tbody>";
			table += "</table>";
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return table;
	}
}
