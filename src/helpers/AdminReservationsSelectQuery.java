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
	 * 
	 * @param currentDate
	 * @param time
	 */
	public void doUserReservationRead(int buildingID, String currentDate){
		
		// The query below pulls shows the primary & secondary's myID
		// TODO add the current date / date range of current date - 2 weeks from current date.
		String query = "SELECT a.myID as 'primary', "
				+ "b.myID as 'secondary',"
				+ "tomcatdb.Rooms.roomNumber, "
				+ "tomcatdb.Reservations.reserveStartDate, "
				+ "tomcatdb.Reservations.reserveEndDate, "
				+ "tomcatdb.Reservations.reserveStartTime, "
				+ "tomcatdb.Reservations.reserveEndTime, "
				+ "tomcatdb.Reservations.hourIncrement "
				+ "FROM tomcatdb.Reservations, tomcatdb.Rooms, tomcatdb.Building, tomcatdb.User AS a, tomcatdb.User AS b "
				+ "WHERE tomcatdb.Reservations.Building_buildingID = tomcatdb.Building.buildingID = '" + buildingID + "' "
				+ "AND tomcatdb.Reservations.Rooms_roomID = tomcatdb.Rooms.roomID "
				+ "AND tomcatdb.Reservations.primaryUser = a.userID "
				+ "AND tomcatdb.Reservations.secondaryUser = b.userID "
				+ "AND tomcatdb.Reservations.reserveStartDate = '" + currentDate + "' "
				+ "AND tomcatdb.Reservations.free = 'N' "
				+ "ORDER BY tomcatdb.Rooms.roomNumber, "
				+ "tomcatdb.Reservations.reserveStartDate, "
				+ "tomcatdb.Reservations.reserveStartTime DESC";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
			System.out.println("User Query: " + query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in AdminReservationsSelectQuery.java: doUserReservationRead method. Please check connection or SQL statement: " + query);
		} 
	}
	public void doAdminReservationRead(int buildingID, String currentDate){
		
		// The query below pulls shows the primary & secondary's myID
		// TODO add the current date / date range of current date - 2 weeks from current date.
		String query = "SELECT tomcatdb.Admin.adminMyID, "
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
				+ "WHERE tomcatdb.Reservations.Building_buildingID = tomcatdb.Building.buildingID = '" + buildingID + "' "
				+ "AND tomcatdb.Reservations.Rooms_roomID = tomcatdb.Rooms.roomID "
				+ "AND tomcatdb.Reservations.Admin_adminID = tomcatdb.Admin.adminID "
				+ "AND tomcatdb.Reservations.reserveStartDate = '" + currentDate + "' "
				+ "ORDER BY tomcatdb.Rooms.roomNumber ASC, "
				+ "tomcatdb.Reservations.reserveStartDate, "
				+ "tomcatdb.Reservations.reserveStartTime DESC";
		// securely run query
		try {
			PreparedStatement ps = this.connection2.prepareStatement(query);
			this.results2 = ps.executeQuery();
			System.out.println("Admin Query: " + query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in AdminReservationsSelectQuery.java: doAdminReservationRead method. Please check connection or SQL statement: " + query);
		} 
	}
	/**
	 * 
	 * @return Resultset from doReservationRead method/query.
	 */
	public String doAdminReservationResults(){
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
				room.setRoomNumber(Integer.parseInt(this.results2.getString("roomNumber")));
				reservation.setReserveStartDate(this.results2.getString("reserveStartDate"));
				reservation.setReserveEndDate(this.results2.getString("reserveEndDate"));
				reservation.setReserveStartTime(this.results2.getString("reserveStartTime"));
				reservation.setReserveEndTime(this.results2.getString("reserveEndTime"));
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
		System.out.println(table);
		return table;
	}
	// TODO fix result set for admin
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
				
				room.setRoomNumber(Integer.parseInt(this.results.getString("roomNumber")));
				primary.setMyID(this.results.getString("primary"));
				secondary.setMyID(this.results.getString("secondary"));
				reservation.setReserveStartDate(this.results.getString("reserveStartDate"));
				reservation.setReserveEndDate(this.results.getString("reserveEndDate"));
				reservation.setReserveStartTime(this.results.getString("reserveStartTime"));
				reservation.setReserveEndTime(this.results.getString("reserveEndTime"));
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
