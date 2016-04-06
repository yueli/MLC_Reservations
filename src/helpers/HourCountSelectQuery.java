package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DateTimeConverter;
import model.DbConnect;
/**
 * Used to sum the hour count of a reservations a user has.
 * @author Brian Olaogun
 *
 */
public class HourCountSelectQuery {
	private Connection connection;
	private ResultSet results;
	
	public HourCountSelectQuery (){
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
	 * This query will sum the hours of reservations for a user for the date selected. 
	 * 0 == no reservations made for that day.
	 * @param userID User ID of the person who wants to reserve room (primary user)
	 */
	public void doIncrementRead(int userID){
		DateTimeConverter dtc = new DateTimeConverter();
		String currentDate = dtc.parseDate(dtc.datetimeStamp());
		
		String query = "SELECT SUM(tomcatdb.Reservations.hourIncrement) as incrementSum "
				+ "FROM tomcatdb.User, tomcatdb.Reservations "
				+ "WHERE tomcatdb.Reservations.primaryUser = tomcatdb.User.userID "
				+ "AND tomcatdb.Reservations.primaryUser = ? "
				+ "AND tomcatdb.Reservations.reserveStartDate = ? "
				+ "AND tomcatdb.Reservations.reserveEndDate = ? "
				+ "AND tomcatdb.Reservations.free = ?";
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setString(2, currentDate);
			ps.setString(3, currentDate);
			ps.setString(4, "N");
			
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in HourCountSelectQuery.java: doIncrementRead method. Please check connection or SQL statement: " + query);
		}
	}
	/**
	 * 
	 * @return sum of the hours of the reservation the user made for the date selected
	 */
	public int incrementResult(){
		int incrementSum = 0;
	
		try {
		
			if (!this.results.next()) {
				  incrementSum = 0;
			} else {
				  //display results
				  do {
				    incrementSum = this.results.getInt(1);
				    return incrementSum;
				  } while (this.results.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return incrementSum;
		
	}
}
