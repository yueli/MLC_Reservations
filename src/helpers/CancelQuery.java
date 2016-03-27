/*
 * @author: Ginger Nix
 * 
 * This class contains the method to cancel a reservation
 */

package helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;


import model.DbConnect;

public class CancelQuery {
	private java.sql.Connection connection;

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
		
		String query = "UPDATE tomcatdb.Reservations SET Reservations.free = 'Y' "
				+ "WHERE Reservations.reserveID = '" + resv_id + "'";
		
		System.out.println("cancel query - cancel reservation. Query = " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in Cance Query cancelReservation. Query = " + query);
		}			
	}
	
}//end class CancelQuery


