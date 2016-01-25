//MLC USER HELPER

package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/**
 * This class is used to insert, retrieve, and update users in the user table.
 */
public class UserHelper {
	
	/**
	 * Prepared SQL statement (combats: SQL Injections)
	 */
	private PreparedStatement authenticateUserStatement;
	
	/**
	 * Constructor which makes a connection
	 */
	public UserHelper() {
		try {
			//Set up connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tomcatdb", "root", "");
			
			//Create the preparedstatement(s)
			authenticateUserStatement = conn.prepareStatement("select * from user where username=? and password=?");
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Authenticates a user in the database.
	 * @param username  The username for the user.
	 * @param password  The password for the user.
	 * @return A user object if successful, null if unsuccessful.
	 */
	public User authenticateUser(String username, String password) {
		User user = null;
		//try {
			//Add parameters to the ?'s in the preparedstatement and execute
		//	authenticateUserStatement.setString(1, username);
		//	authenticateUserStatement.setString(2, password);
		//	ResultSet rs = authenticateUserStatement.executeQuery();
			
			//if we've returned a row, turn that row into a new user object
			//if (rs.next()) {
				//user = new User(rs.getInt("userID"), rs.getString("username"), rs.getString("password"), rs.getString("user_name"), rs.getString("user_email"));
			//}
		//} catch (SQLException e) {
			//System.out.println(e.getClass().getName() + ": " + e.getMessage());
		//}
		
		//HARD CODED FOR NOW
		int ID;
		String testuser;
		String testPw;
		String userName;
		String userEmail;
		
		ID = 1;
		testuser = "tester";
		testPw = "testpw";
		userName = "Test User";
		userEmail = "tester@uga.edu";
		
		user = new User(ID,testuser,testPw,userName,userEmail);
		return user;
	}
}

