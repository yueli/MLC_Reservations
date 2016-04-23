package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DbConnect;
import model.User;

public class BanUserSearchQuery{
	
	private Connection connection;
	private ResultSet results;

	/**
	 * 
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BanUserSearchQuery() {

		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DbConnect.devCredentials();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	

	public void doRead(String fname, String lname){

		//String query = "SELECT banned.bannedID, banned.Student_studentID, banned.Admin_adminID, banned.banStart, banned.banEnd, banned.penaltyCount, banned.description, banned.status FROM banned";
		String query = "SELECT u.userID , u.myID, u.fname, u.lname, u.email,u.lastLogin  FROM tomcatdb.User u where u.userID not IN (select b.User_userID FROM tomcatdb.Banned b where b.status=1) and fname LIKE '"+fname+"%' and lname LIKE '"+lname+"%' GROUP BY u.userID";
		System.out.println(query);
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			results = ps.executeQuery();
			System.out.println("Excute finished");
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
		} 
	}
	
	
	
	public String getHTMLTable(){ 
		//Return table of banned students
		
		String table = "<center><table id='' class='mdl-data-table' cellspacing='0' width='95%'>";
		
	
		try{
			System.out.println("In try");
			
	
			table += "<thead>"
					+ "<th>User ID</th>"
					+ "<th>User My ID</th>"
					+ "<th>First Name</th>"
					+ "<th>Last Name</th>"
					+ "<th>Last Login</th>"
					+ "<th>E-mail</th>"
					+ "<th></th>"
					+ "</thead>"
					+ "<tbody>";
			while(results.next()){
				
				
				System.out.println("In while");
				//userID , myID, fname, lname, lastLogin, email
				
				User user = new User();
				user.setUserRecordID(results.getInt("userID"));
				user.setMyID(results.getString("myID"));
				user.setUserFirstName(results.getString("fname"));
				user.setUserLastName(results.getString("lname"));
				user.setLastLogin(results.getString("lastLogin"));
				
				/*
				if(results.getString("lastLogin").toString().equals("0000-00-00")){
					user.setLastLogin("0000-00-00");
				}
				else{
					user.setLastLogin(results.getDate("lastLogin").toString());
				}
						*/
			
				//if()
				
				//results.getString("lastLogin"));
						
			
				
				//user.setLastLogin(results.getString("lastLogin"));
				System.out.println("User Set Last Login");
				
				user.setUserEmail(results.getString("email"));
				
		
				
				table += "<tr>";
				
				table += "<td>";
				table += user.getUserRecordID();
				table += "</td>";
				table += "<td>";
				table += user.getMyID();
				table += "</td>";
				table += "<td>";
				table += user.getUserFirstName();
				table += "</td>";
				table += "<td>";
				table += user.getUserLastName();
				table += "</td>";
				table += "<td>";
				table += user.getLastLogin();
				table += "</td>";
				table += "<td>";
				table += user.getUserEmail();
				table += "</td>";
			
				
				table += "<td><a href=ban?userID=" + user.getUserRecordID() + "> <button type='submit' value='Ban'>Ban</button></a></td>";

				
				table += "</tr>";
			}
			table+="</tbody></table></center>";
		}
		catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
			
		}
		
		return table;
	}
	
}