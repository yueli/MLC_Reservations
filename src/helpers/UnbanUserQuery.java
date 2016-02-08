package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import model.Banned;


/**
 * @author Ronnie Xu
 * Helper for the Admin side of the website.
 **/

public class UnbanUserQuery {
	
	private Connection connection;

	/**
	 * 
	 * @param dbName
	 * @param user
	 * @param pwd
	 */
	public UnbanUserQuery(String dbName, String user, String pwd) {
		String url = "jdbc:mysql://localhost:3306/" + dbName;
		
		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, user, pwd);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void unbanUser(int banID){
		//Set Date to CurrentTime
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		

		String query = "UPDATE tomcatdb.banned SET banEnd='"+ date + "', status=0 WHERE bannedID="+banID+"";
		

		
		try {
			PreparedStatement ps = connection.prepareStatement(query);


			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
