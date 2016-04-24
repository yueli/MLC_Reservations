/**
 * @author: Ginger Nix
 * This class contains methods for banning a user
 */

package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.DbConnect;
import model.User;

public class BanGetUserInfoQuery {
	
	private Connection connection;
	private ResultSet results;
	private ResultSet adminResults;
	public String banDescription ="";

	/**
	 * Constructor which makes a connection
	 */
	public BanGetUserInfoQuery() {
			
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
	 * The method userData takes a user recd id and gets the user data
	 * @author: Ginger Nix
	 * @parameters: the user table record's id
	 * @return: a User object w/ the user's info
	 */
	
	 public User userData(int userID){
		
		User userData = new User();
		String query = "SELECT * "
				+ "FROM tomcatdb.User "
				+ "WHERE userID = ?";
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, userID);
			
			this.results = ps.executeQuery();
			while(this.results.next()){
				userData.setUserRecordID(this.results.getInt("user.userID"));
				userData.setMyID(this.results.getString("user.myID"));
				userData.setUserFirstName(this.results.getString("user.fname"));
				userData.setUserLastName(this.results.getString("user.lname"));
				userData.setUserEmail(this.results.getString("user.email"));
				userData.setLastLogin(this.results.getString("user.lastLogin"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: inUserTable method. Query = " + query);
		}
		
		return userData;
	}
	
	 /**
	  * This method takes the user's MyID and looks gets the user's info from the user table
	 * @author: Ginger Nix
	 * @parameters: the user MyID
	 * @return: a User object w/ the user's info
	 */
	 
	 public User userDataWithMyID(String MyID){
			
		User userData = new User();
		String query = "SELECT * "
					+ "FROM tomcatdb.User WHERE User.myID = ?";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, MyID);
			
			this.results = ps.executeQuery();
			while(this.results.next()){
				userData.setUserRecordID(this.results.getInt("user.userID"));
				userData.setMyID(this.results.getString("user.myID"));
				userData.setUserFirstName(this.results.getString("user.fname"));
				userData.setUserLastName(this.results.getString("user.lname"));
				userData.setUserEmail(this.results.getString("user.email"));
				userData.setLastLogin(this.results.getString("user.lastLogin"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: inUserTable method. Query = " + query);
		}
		
		return userData;
	}
	 
	/**
	* The method userData takes an admin user's admin record ID and
	* returns the admin object w/ the admin's data
	* @author: Ginger Nix
	* @parameters: the admin table record's id
	* @return: an Admin object w/ the admin's info
	*/
		 
	 public Admin adminData(int adminID){
			
			Admin adminData = new Admin();
			String query = "SELECT * "
					+ "FROM tomcatdb.Admin WHERE Admin.adminID = ?";
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				ps.setInt(1, adminID);
				
				this.adminResults = ps.executeQuery();
				while(this.adminResults.next()){
					adminData.setAdminID(this.adminResults.getInt("Admin.adminID"));
					adminData.setAdminMyID(this.adminResults.getString("Admin.adminMyID"));
					adminData.setFname(this.adminResults.getString("Admin.fname"));
					adminData.setLname(this.adminResults.getString("Admin.lname"));
					adminData.setRole(this.adminResults.getString("Admin.role"));
					adminData.setAdminStatus(this.adminResults.getInt("Admin.adminStatus"));
					adminData.setCantBeDeleted(this.adminResults.getInt("Admin.cantBeDeleted"));

				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("****Error - BanGetUSerQuery: adminData query = " + query);
			}
			
			return adminData;
		} 
	 

}
			
			
