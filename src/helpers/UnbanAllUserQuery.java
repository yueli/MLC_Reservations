package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import model.DbConnect;



/**
 * @author Ronnie Xu
 * Helper for the Admin side of the website.
 **/

public class UnbanAllUserQuery {
	
	private Connection connection;

	/**
	 * 
	 * @param dbName
	 * @param user
	 * @param pwd
	 */
	public UnbanAllUserQuery() {
		
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
	
	public void unbanUser(){
		//Set Date to CurrentTime
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		

		String query = "UPDATE tomcatdb.banned SET banEnd='"+ date + "', status=0 WHERE status=1";
		

		
		try {
			PreparedStatement ps = connection.prepareStatement(query);


			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
