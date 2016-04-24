package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.DbConnect;

/**
 * @author Ronnie Xu
 * Helper for the Admin side of the website.
 * @author: Ginger Nix - made queries safe, added comments
 **/

public class UnbanAllUserQuery {
	
	private Connection connection;

	/**
	 * 
	 * Connect to database.  This is hard coded in DBConnect.java
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
	
	/**
	 * This method unbans all the users who are currently banned
	 */
	
	public void unbanUser(){
		//Set Date to CurrentTime
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = dateFormat.format(d);
		
		String query = "UPDATE tomcatdb.Banned "
						+ "SET banEnd = ? , status = ? "
						+ "WHERE status = ?";
	
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setString(1, date);
			ps.setInt(2, 0);
			ps.setInt(3, 1);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
