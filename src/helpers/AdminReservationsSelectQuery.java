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
	public void doUserReservationRead(String currentDate){
		
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
				+ "WHERE tomcatdb.Reservations.Building_buildingID = tomcatdb.Building.buildingID = 1 "
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in AdminReservationsSelectQuery.java: doUserReservationRead method. Please check connection or SQL statement: " + query);
		} 
	}
	public void doAdminReservationRead(String currentDate){
		
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
				+ "WHERE tomcatdb.Reservations.Building_buildingID = tomcatdb.Building.buildingID = 1 "
				+ "AND tomcatdb.Reservations.Rooms_roomID = tomcatdb.Rooms.roomID "
				+ "AND tomcatdb.Reservations.Admin_adminID = tomcatdb.Admin.adminID "
				+ "AND tomcatdb.Reservations.reserveStartDate = curdate() "
				+ "ORDER BY tomcatdb.Rooms.roomNumber, "
				+ "tomcatdb.Reservations.reserveStartDate, "
				+ "tomcatdb.Reservations.reserveStartTime DESC";
		// securely run query
		try {
			PreparedStatement ps = this.connection2.prepareStatement(query);
			this.results2 = ps.executeQuery();
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
			table += "<table border=1>";
			table += "<thead>";
			table += "<tr>";
			table += "<th ALIGN=CENTER>MyID </th>";
			table += "<th ALIGN=CENTER>First</th>";
			table += "<th ALIGN=CENTER>Last</th>";
			table += "<th ALIGN=CENTER>Reserve Name</th>";
			table += "<th ALIGN=CENTER>Start Date</th>";
			table += "<th ALIGN=CENTER>End Date</th>";
			table += "<th ALIGN=CENTER>Start Time</th>";
			table += "<th ALIGN=CENTER>End Time</th>";
			table += "<th ALIGN=CENTER>Hours</th>";
			table += "</tr>";
			table += "</thead>";
			table += "<tbody>";
			while(this.results2.next()){
				Admin admin = new Admin();
				Reservation reservation = new Reservation();
				
				DateTimeConverter dtc = new DateTimeConverter();
				TimeConverter tc = new TimeConverter();
				
				admin.setAdminMyID(this.results2.getString("adminMyID"));
				admin.setFname(this.results2.getString("fname"));
				admin.setLname(this.results2.getString("lname"));
				reservation.setReserveName(this.results2.getString("reserveName"));
				reservation.setReserveStartDate(this.results2.getString("reserveStartDate"));
				reservation.setReserveEndDate(this.results2.getString("reserveEndDate"));
				reservation.setReserveStartTime(this.results2.getString("reserveStartTime"));
				reservation.setReserveEndTime(this.results2.getString("reserveEndTime"));
				reservation.setHourIncrement(this.results2.getInt("hourIncrement"));
				table += "<tr>";
				table += "<td>" + admin.getAdminMyID() + "</td>";
				table += "<td>" + admin.getFname() + "</td>";
				table += "<td>" + admin.getLname() + "</td>";
				table += "<td>" + reservation.getReserveName() + "</td>";
				table += "<td>" + dtc.convertDateLong(reservation.getReserveStartDate()) + "</td>";
				table += "<td>" + dtc.convertDateLong(reservation.getReserveEndDate()) + "</td>";
				table += "<td>" + tc.convertTimeTo12(reservation.getReserveStartTime()) + "</td>";
				table += "<td>" + tc.convertTimeTo12(reservation.getReserveEndTime()) + "</td>";
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
	// TODO fix result set for admin
	public String doUserReservationResults(){
		String table = "";
		try {
			table += "<table border=1>";
			table += "<thead>";
			table += "<tr>";
			table += "<th ALIGN=CENTER>Primary </th>";
			table += "<th ALIGN=CENTER>Secondary</th>";
			table += "<th ALIGN=CENTER>Start Date</th>";
			table += "<th ALIGN=CENTER>End Date</th>";
			table += "<th ALIGN=CENTER>Start Time</th>";
			table += "<th ALIGN=CENTER>End Time</th>";
			table += "<th ALIGN=CENTER>Hours</th>";
			table += "</tr>";
			table += "</thead>";
			table += "<tbody>";
			while(this.results.next()){
				User primary = new User();
				User secondary = new User();
				Reservation reservation = new Reservation();
				
				DateTimeConverter dtc = new DateTimeConverter();
				TimeConverter tc = new TimeConverter();
				
				primary.setMyID(this.results.getString("primary"));
				secondary.setMyID(this.results.getString("secondary"));
				reservation.setReserveStartDate(this.results.getString("reserveStartDate"));
				reservation.setReserveEndDate(this.results.getString("reserveEndDate"));
				reservation.setReserveStartTime(this.results.getString("reserveStartTime"));
				reservation.setReserveEndTime(this.results.getString("reserveEndTime"));
				reservation.setHourIncrement(this.results.getInt("hourIncrement"));
				table += "<tr>";
				table += "<td>" + primary.getMyID() + "</td>";
				table += "<td>" + secondary.getMyID() + "</td>";
				table += "<td>" + dtc.convertDateLong(reservation.getReserveStartDate()) + "</td>";
				table += "<td>" + dtc.convertDateLong(reservation.getReserveEndDate()) + "</td>";
				table += "<td>" + tc.convertTimeTo12(reservation.getReserveStartTime()) + "</td>";
				table += "<td>" + tc.convertTimeTo12(reservation.getReserveEndTime()) + "</td>";
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
