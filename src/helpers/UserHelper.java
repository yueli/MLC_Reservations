/**
 * @author: Ginger Nix
 * @contributer: Brian Olaogun
 * 
 * This class contains methods that help to get user information, to add and edit
 * users, check if user is in the table, etc
 */

package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import model.Admin;
import model.DbConnect;
import model.User;


public class UserHelper {
	
	/**
	 * Prepared SQL statement (combats: SQL Injections)
	 */
	private PreparedStatement authenticateUserStatement;
	private Connection connection;
	private ResultSet results;
	//private int numRecords;

	/**
	 * Constructor which makes a connection
	 */
	public UserHelper() {
			
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
	 * This method will take the myID and check to see if this user exists already	
	 * @param myID
	 * @return true if user is in the user table, false if they are not
	 */
	
	public boolean inUserTable(String myID){
				
		String query = "SELECT * "
					+ "FROM tomcatdb.User "
					+ "WHERE myID = ? "
					+ "LIMIT 1 ";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			
			ps.setString(1, myID);
			
			this.results = ps.executeQuery();
			
			boolean results = this.results.next();
			
			if (results) {//the myID is in the user table
				System.out.println("User Helper: in user table: results found TRUE");
				return true;
			}else{
				System.out.println("User Helper: in user table: results found FALSE");
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: inUserTable method. Query = " + query);
		}
		
		return false;
	}

	/**
	 * This method gets the myID of the user and update's their last login time w/ current date
	 * @param myID
	 */
	public void updateLastLogin(String myID) {
		String today = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		today = dateFormat.format(date);
		System.out.println(dateFormat.format(date)); //2014/08/06  
		System.out.println(today); //2014/08/06  
		
		String query = "Update tomcatdb.User "
					+ "SET User.lastLogin = ? "
					+ "WHERE myID = ? ";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, today);
			ps.setString(2, myID);
			
			
			ps.executeUpdate();
			System.out.println("Success in UserHelper.java: updateLastLogin method. Query = " + query);
			System.out.println("Success in UserHelper.java: updateLastLogin method. myID = " + myID);
			System.out.println("Success in UserHelper.java: updateLastLogin method. today = " + today);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in UserHelper.java: updateLastLogin method. Query = " + query);
		}
		
	}
	
	/**
	 * This method takes the user info and inserts them into the user table
	 * @param myID
	 * @param fname
	 * @param lname
	 * @param email
	 */
	
	public void insertUserTable(String myID, String fname, String lname, String email) {

		System.out.println("uh: insertUserTable");   

		String today = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		today = dateFormat.format(date);
		
		String query = "INSERT INTO tomcatdb.User "
				+ "(myID, fname, lname, email, lastLogin) "
				+ "VALUES (?, ?, ?, ?, ?) ";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			
			ps.setString(1, myID);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setString(4, email);
			ps.setString(5, today);
			
			ps.executeUpdate();
			System.out.println("Success in UserHelper.java: insert into table method. Query = " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in UserHelper.java: insert into table method. Query = " + query);
		}
		
	}
	

	/**
	 * This method gets the user's record id based on their MyID
	 * @param myID
	 * @return user recd id
	 */
	
	public int getRecordID(String myID){
		int recordID = 0;
		String query = "SELECT userID FROM tomcatdb.User "
					+ "WHERE myID = ? "
					+ "LIMIT 1";
		
		System.out.println("UserHelper: myID = " + myID);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			
			ps.setString(1, myID);
			
			this.results = ps.executeQuery();
			
			this.results.next();
			
			recordID = results.getInt("userID");
			System.out.println("Success in UserHelper.java: get record ID method. Query = " + query);
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in UserHelper.java: get record ID method. Query = " + query);
		}
		
		return recordID;
		
	}
	
	/**
	 * This method checks to see if the user is currently banned
	 * @param userID
	 * @return true if the user is banned, false if they are not
	 */
	
	public boolean alreadyBanned(int userID){

		System.out.println("UserHelper alreadyBanned: myID = " + userID);
		
		String query = "SELECT * from tomcatdb.Banned WHERE User_userID = ? "
					+ "AND banEnd IS NULL "
					+ "AND status = ? "
					+ "AND penaltyCount > ? "
					+ "LIMIT 1";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setInt(2, 1);
			ps.setInt(3, 1);
			
			this.results = ps.executeQuery();
			if (this.results.next()) {
				return true; //they are already banned for this current period
			}else{
				return false; //they have not been banned
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: already Banned method. Query = " + query);
		}
		
		return false;
	}


	
	/**
	 * This method ges the admin user's MyID and returns an object w/ all the admin's info
	 * @param myID
	 * @return admin object w/ admin's info
	 */
	
	public Admin getAdminInfo(String myID) {
		
		Admin adminUser = new Admin();
		
		String query = "SELECT * FROM tomcatdb.Admin "
					+ "WHERE adminMyID = ? "
					+ "LIMIT 1 ";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);

			ps.setString(1, myID);
			
			this.results = ps.executeQuery();
			this.results.next();
			
			adminUser.setAdminID(results.getInt("adminID"));
			adminUser.setFname(results.getString("fname"));
			adminUser.setLname(results.getString("lname"));
			adminUser.setRole(results.getString("role"));
			adminUser.setAdminStatus(results.getInt("adminStatus"));
			adminUser.setCantBeDeleted(results.getInt("cantBeDeleted"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: get admin info method. Query = " + query);
		}
		
		
		return adminUser;
		
	}
	
	/**
	 * 
	 * @param myID of the user/student
	 * @return all of their info in a user object
	 * @author Brian Olaogun
	 */
	public User getUserInfoFromMyID(String myID){
		User user = new User();
		String query = "SELECT * FROM tomcatdb.User WHERE myID = ? LIMIT 1";
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, myID);
			
			this.results = ps.executeQuery();
			while(this.results.next()){
				user.setUserRecordID(this.results.getInt("userID"));
				user.setMyID(this.results.getString("myID"));
				user.setUserEmail(this.results.getString("email"));
				user.setUserFirstName(this.results.getString("fname"));
				user.setUserLastName(this.results.getString("lname"));
				user.setLastLogin(this.results.getString("lastLogin"));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: get admin info method. Query = " + query);
		}
		
		return user;
	}
	
	/**
	 * This method gets a user's info based on the user's recd number
	 * @param recd ID of the user/student
	 * @return all of their info in a user object
	 * @author Ginger Nix
	 */
	
	public User getUserInfoFromRecdID(int recdID){
		User user = new User();
		String query = "SELECT * FROM tomcatdb.User "
					+ "WHERE userID = ? "
					+ "LIMIT 1";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, recdID);
			
			this.results = ps.executeQuery();
			while(this.results.next()){
				user.setUserRecordID(this.results.getInt("userID"));
				user.setMyID(this.results.getString("myID"));
				user.setUserEmail(this.results.getString("email"));
				user.setUserFirstName(this.results.getString("fname"));
				user.setUserLastName(this.results.getString("lname"));
				user.setLastLogin(this.results.getString("lastLogin"));				
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: get admin info method via user recd ID. Query = " + query);
		}
		
		return user;
	}
}
