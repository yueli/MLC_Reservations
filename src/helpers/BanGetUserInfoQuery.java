package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.DbConnect;
import model.User;

public class BanGetUserInfoQuery {
	
	/**
	 * Prepared SQL statement (combats: SQL Injections)
	 */

	private Connection connection;
	private ResultSet results;
	private ResultSet banResults;
	private ResultSet adminResults;
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
		String query = "SELECT User.userID,User.myID,User.fname,User.lname,User.email,User.lastLogin FROM tomcatdb.User WHERE User.userID = "+userID+";";
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
	 
	 
	 public Admin adminData(int adminID){
			
			Admin adminData = new Admin();
			String query = "SELECT Admin.adminID,Admin.adminMyID,Admin.fname,Admin.lname,Admin.role,Admin.adminStatus,Admin.cantBeDeleted FROM tomcatdb.Admin WHERE Admin.adminID = "+adminID+";";
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
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
				System.out.println("****Error in UserHelper.java: inUserTable method. Query = " + query);
			}
			
			return adminData;
		}
	 
	 /*
	  * Check if user is already banned
	  */
	 public boolean isUserBannedAlready(int userID){
		 boolean banned = false;
		 	
		 
		 String query = "SELECT Banned.bannedID,Banned.User_userID,Banned.Admin_adminID,Banned.banStart,Banned.banEnd,Banned.penaltyCount,Banned.description,Banned.status FROM tomcatdb.Banned WHERE Banned.status = 1 AND Banned.User_userID = "+userID+";";
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
			
			
