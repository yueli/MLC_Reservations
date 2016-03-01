/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DbConnect;
import model.Reservation;

/**
 * @author Brian Olaogun
 * This class will be used for view reservations made (admin).
 *
 */
public class AdminReservationsSelectQuery {
	private Connection connection;
	private ResultSet results;
	/**
	 * Set up the JDBC Driver and connect to the database.
	 */
	public AdminReservationsSelectQuery() {

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
	 * @param currentDate
	 * @param time
	 */
	public void doReservationRead(String currentDate, String time){
		
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
				+ "ORDER BY tomcatdb.Rooms.roomNumber, "
				+ "tomcatdb.Reservations.reserveStartDate, "
				+ "tomcatdb.Reservations.reserveStartTime DESC";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in AdminReservationsSelectQuery.java: doReservationRead method. Please check connection or SQL statement: " + query);
		} 
	}
	
	/**
	 * 
	 * @return Resultset from doReservationRead method/query.
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
		
		return results;
	}
}
