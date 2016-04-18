package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DbConnect;
import model.User;

public class BanGetUserInfoQuery {
	
	/**
	 * Prepared SQL statement (combats: SQL Injections)
	 */

	private Connection connection;
	private ResultSet results;
	private ResultSet banResults;
	//private int numRecords;
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
	
	

//-------------
	
/*
 * Get User Data
 */
	 public User userData(int userID){
		
		User userData = new User();
		String query = "SELECT user.userID,user.myID,user.fname,user.lname,user.email,user.lastLogin FROM tomcatdb.user WHERE user.userID = "+userID+";";
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
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
	 
	 /*
	  * Check if user is already banned
	  */
	 public boolean isUserBannedAlready(int userID){
		 boolean banned = false;
		 	
		 
			String query = "SELECT banned.bannedID,banned.User_userID,banned.Admin_adminID,banned.banStart,banned.banEnd,banned.penaltyCount,banned.description,banned.status FROM tomcatdb.banned WHERE banned.status = 1 AND banned.User_userID = "+userID+";";
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				this.banResults = ps.executeQuery();
				while(this.banResults.next()){
					
					int tempUserID = this.banResults.getInt("banned.User_userID");
					int status = this.banResults.getInt("banned.status");
					if(status==1 && userID == tempUserID){
						banned = true;
						banDescription = this.banResults.getString("banned.description");
					}
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("****Error in UserHelper.java: inUserTable method. Query = " + query);
			}
			
			return banned;
		}
	 

}
			
			
