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
 * @author Brian-MBP
 *
 */
public class AdminScheduleUpdateQuery {
	private Connection connection;
	
	/**
	 * Database connection
	 */
	public AdminScheduleUpdateQuery() {
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
				+ "summary = ? "
				+ "WHERE scheduleID ='" + schedule.getScheduleID() + "'";
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, schedule.getStartTime());
			ps.setString(2, schedule.getEndTime());
			ps.setString(3, schedule.getStartDate());
			ps.setString(4, schedule.getStartDate());
			ps.setString(5, schedule.getSummary());
			ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Error in AdminScheduleInsertQuery.java: doScheduleUpdate method. Please check connection or SQL statement: " + query);
		}
	}
	
}
