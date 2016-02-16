

package helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import model.Reservation;
import model.Rooms;
import model.User;

import model.DbConnect;

public class CancelQuery {
	private java.sql.Connection connection;
	private ResultSet results;
	private int reserveID;

	public CancelQuery(){
		
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
		
		String query = "UPDATE tomcatdb.Reservations SET Reservations.free = 'Y'"
				+ "WHERE Reservations.reserveID = '" + resv_id + "'";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			this.results = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in Cance Query cancelReservation. Query = " + query);
		}			
	}
	
}//end class CancelQuery


