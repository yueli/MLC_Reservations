/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DbConnect;
import model.Schedule;

/**
 * @author Brian Olaogun
 *
 */
public class AdminUpdateQuery {
	private Connection connection;
	
	/**
	 * Database connection
	 */
	public AdminUpdateQuery() {
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
	
	public void doScheduleUpdate(Schedule schedule){
		
		String query = "UPDATE tomcatdb.Schedule "
				+ "SET startTime = ?, "
				+ "endTime = ?, "
				+ "startDate = ?, "
				+ "endDate = ?, "
				+ "summary = ?, "
				+ "createdBy = ? "
				+ "WHERE scheduleID = ?";
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, schedule.getStartTime());
			ps.setString(2, schedule.getEndTime());
			ps.setString(3, schedule.getStartDate());
			ps.setString(4, schedule.getStartDate());
			ps.setString(5, schedule.getSummary());
			ps.setString(6, schedule.getCreatedBy());
			ps.setInt(7, schedule.getScheduleID());
			ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Error in AdminUpdateQuery.java: doScheduleUpdate method. Please check connection or SQL statement. ");
			System.out.println();
		}
	}
	/**
	 * This query is used to cancel an admin reservation
	 * @param reserveID reservation id of the reservation the admin wants to cancel
	 * @param free this value cancels a reservation and opens up a room.  
	 * Y = room is free and reservation is canceled.
	 */
	public void cancelAdminReservation(int reserveID, String free){
		
		String query = "UPDATE tomcatdb.Reservations "
				+ "SET free = ? "
				+ "WHERE reserveID = ?";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, free);
			ps.setInt(2, reserveID);
			ps.executeUpdate();
			
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Error in AdminUpdateQuery.java: cancelAdminReservation method. Please check connection or SQL statement. ");
			System.out.println();
		}
	}
	
}
