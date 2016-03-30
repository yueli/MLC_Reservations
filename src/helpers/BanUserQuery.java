package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Banned;
import model.DbConnect;


/**
 * @author Ronnie Xu
 * Helper for the Admin side of the website.
 **/

public class BanUserQuery {
	
	private Connection connection;

	/**
	 * 
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BanUserQuery() {
	
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
	
	public void banUser(Banned ban){
		
		String query = "INSERT INTO `tomcatdb`.`banned` (`bannedID`, `Student_studentID`, `Admin_adminID`, `banStart`, `banEnd`, `penaltyCount`, `description`, `status`) VALUES (?,?,?,?,?,?,?,?)";;
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1,ban.getBanID());
			ps.setInt(2, ban.getStudentID());
			ps.setInt(3, ban.getAdminID());
			ps.setString(4, ban.getBanStart());
			ps.setString(5, ban.getBanEnd());
			ps.setInt(6, ban.getPenaltyCount());
			ps.setString(7, ban.getDescription());
			ps.setInt(8, ban.getStatus());
			
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
