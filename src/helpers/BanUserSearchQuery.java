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
		String query = "SELECT u.userID , u.myID, u.fname, u.lname, u.lastLogin, u.email FROM tomcatdb.User u, tomcatdb.banned b  where  u.userID = b.User_userID and b.status = 0 and fname LIKE '"+fname+"%' and lname LIKE '"+lname+"%'";
		System.out.println(query);
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			results = ps.executeQuery();
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
		} 
	}
	
	
	
	public String getHTMLTable(){ 
		//Return table of banned students
		
		String table = "<center><table>";
		
	
		try{
			
			
			table += "<tr><td><a href=banUser><button type='submit' value=''>Ban A User</button></a></td>";
			table += "<td><a href=unbanall><button type='submit' value=''>Unban All</button></a></td></tr>";
			table += "<tr></tr>";
			table += "<tr><td>User ID</td><td>User My ID</td><td>First Name</td><td>Last Name</td><td>Last Login</td><td>E-mail</td><td></td></tr>";
			while(results.next()){
				//userID , myID, fname, lname, lastLogin, email
				
				User user = new User();
				user.setUserRecordID(results.getInt("userID"));
				user.setMyID(results.getString("userID"));
				user.setUserFirstName(results.getString("fname"));
				user.setUserLastName(results.getString("lname"));
				user.setLastLogin(results.getString("lastLogin"));
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
			
				
				table += "<td><a href=ban?userID=" + user.getMyID() + "> <button type='submit' value='Ban'>Ban</button></a></td>";

				
				table += "</tr>";
			}
			table+="</table></center>";
		}
		catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
			
		}
		
		return table;
	}
	
}