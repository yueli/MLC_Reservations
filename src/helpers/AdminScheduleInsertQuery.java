/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DbConnect;
import model.Schedule;

/**
 * This class is used to insert records into the Schedule table in the database. 
 * The schedule relates to the building hours.
 * @author Brian Olaogun
 *
 */
public class AdminScheduleInsertQuery {
	private Connection connection;
	private ResultSet results;
	private Schedule schedule;
	
	public AdminScheduleInsertQuery(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// hard coded the connection in DbConnect class
			this.connection = DbConnect.devCredentials();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	/**
	 * This method will check to see if there is a schedule entry already
	 * made for the building and date.  This method uses a select query.
	 * Will return a string of false if there isn't an entry.
	 * Will return the string scheduleID if there is an entry.
	 * @param schedule Schedule object
	 */
	public String scheduleInsertCheck(Schedule schedule){
		this.schedule = schedule;
		
		String scheduleID = "";
		String value = "false";
		String query = "SELECT tomcatdb.Schedule.scheduleID, "
					+ "tomcatdb.Schedule.startDate, "
					+ "tomcatdb.Schedule.endDate, "
					+ "tomcatdb.Building.buildingID "
					+ "FROM tomcatdb.Schedule, tomcatdb.Building "
					+ "WHERE tomcatdb.Building.buildingID = tomcatdb.Schedule.Building_buildingID "
					+ "AND tomcatdb.Schedule.startDate = ? "
					+ "AND tomcatdb.Schedule.endDate = ? "
					+ "AND tomcatdb.Building.buildingID = ?";
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, this.schedule.getStartDate());
			ps.setString(2, this.schedule.getEndDate());
			ps.setInt(3, this.schedule.getBuildingID());
			this.results = ps.executeQuery();
			
			// if there is a matching record in the schedule table, return the scheduleID
			// if there isn't a matching record in the schedule table, return false
			while (this.results.next()){
				scheduleID = this.results.getString("scheduleID");
				return scheduleID;
			}
				
			return value;
		
		} catch (SQLException e){
			e.printStackTrace();
			System.err.println("Error in AdminScheduleInsertQuery.java: insertCheck method. Please check connection or SQL statement.");
		} 
		// will return false by default if the connection fails.
		value = "false";
		return value;
	}
	
	/**
	 * This method will insert schedule into the Schedule table in the database.
	 * Uses a schedule object and the accessors/getters from the Schedule class.
	 * @param schedule Schedule object.
	 */
	public void doScheduleInsert(Schedule schedule){
		this.schedule = schedule;
		//System.out.println(this.schedule.toString());
		System.out.println("Do Admin Schedule Insert Query doScheduleInsert: " + this.schedule.getAllDayEvent() + " " + 
		this.schedule.getStartDate()  + " " +
		this.schedule.getEndDate()  + " " +
		this.schedule.getStartTime()  + " " +
		this.schedule.getEndTime() + " " +
		this.schedule.getSummary() + " " +
		this.schedule.getCreatedBy() + " " +
		this.schedule.getBuildingID() + " ");  
		
		String query = "INSERT INTO tomcatdb.Schedule "
				+ "(tomcatdb.Schedule.allDayEvent, tomcatdb.Schedule.startDate, tomcatdb.Schedule.endDate, tomcatdb.Schedule.startTime, "
				+ "tomcatdb.Schedule.endTime, tomcatdb.Schedule.summary, tomcatdb.Schedule.createdBy, tomcatdb.Schedule.Building_buildingID) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, this.schedule.getAllDayEvent());
			ps.setString(2, this.schedule.getStartDate());
			ps.setString(3, this.schedule.getEndDate());
			ps.setString(4, this.schedule.getStartTime());
			ps.setString(5, this.schedule.getEndTime());
			ps.setString(6, this.schedule.getSummary());
			ps.setString(7, this.schedule.getCreatedBy());
			ps.setInt(8, this.schedule.getBuildingID());
			
			ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			System.err.println("Error in AdminScheduleInsertQuery.java: doScheduleInsert method. Please check connection or SQL statement.");
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * Main method used for testing only
	 * @param args String array
	 */
	public static void main (String [] args){
		Schedule schedule = new Schedule();
		schedule.setStartDate("2017-03-20");
		schedule.setEndDate("2017-03-20");
		int id = 1;
		schedule.setBuildingID(id);
		AdminScheduleInsertQuery siq = new AdminScheduleInsertQuery();
		String check = siq.scheduleInsertCheck(schedule);
		System.out.println("HERE IS THE VALUE: " + check);
	}
}
