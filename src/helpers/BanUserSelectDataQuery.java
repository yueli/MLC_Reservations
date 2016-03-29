package helpers;
//**By Ronnie Xu~****/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Banned;
import model.DbConnect;

public class BanUserSelectDataQuery {
	
	private Connection connection;
	private ResultSet results;

	/**
	 * 
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BanUserSelectDataQuery() {

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
	
	

	public void doRead(String userID){

		//String query = "SELECT banned.bannedID, banned.Student_studentID, banned.Admin_adminID, banned.banStart, banned.banEnd, banned.penaltyCount, banned.description, banned.status FROM banned";
		String query = "SELECT studentID , myID, fname, lname, lastLogin FROM tomcatdb.student";
		// securely run query
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
		
		String table = "<table id== '' class = 'mdl-data-table' cellspacing = '0' width = '95%'>";
		
	
		try{
			
			
			table += "<tr><td><a href=banUser><button type='submit' value=''>Ban A User(List)</button></a></td>";
			table += "<td><a href=banUser><button type='submit' value=''>Unban A User(List)</button></a></td>";
			table += "<td><a href=unbanall><button type='submit' value=''>Unban All</button></a></td></tr>";
			
			table += "<thead><tr><th>Ban#</th><th>Student ID</th><th>Admin ID</th><th>Ban Start</th><th>Ban End</th><th>Penalty Count</th><th>Description</th><th>Status</th></tr></thead>";while(results.next()){

				
				
				Banned ban = new Banned();
				ban.setBanID(results.getInt("bannedID"));
				ban.setStudentID(results.getInt("Student_studentID"));
				ban.setAdminID(results.getInt("Admin_adminID"));
				ban.setBanStart(results.getString("banStart"));
				ban.setBanEnd(this.results.getString("banEnd"));
				ban.setPenaltyCount(results.getInt("penaltyCount"));
				ban.setDescription(results.getString("description"));
				ban.setStatus(results.getInt("status"));
				
				//show only banned
				
				table += "<tbody><tr>";
				
				table += "<td>";
				table += ban.getBanID();
				table += "</td>";
				table += "<td>";
				table += ban.getStudentID();
				table += "</td>";
				table += "<td>";
				table += ban.getAdminID();
				table += "</td>";
				table += "<td>";
				table += ban.getBanStart();
				table += "</td>";
				table += "<td>";
				table += ban.getBanEnd();
				table += "</td>";
				table += "<td>";
				table += ban.getPenaltyCount();
				table += "</td>";
				table += "<td>";
				table += ban.getDescription();
				table += "</td>";
				table += "<td>";
				table += ban.getStatus();
				table += "</td>";
				
				table += "<td><a href=unban?banID=" + ban.getBanID() + "> <button type='submit' value='Unban'>Unban</button></a></td>";

				
				table += "</tr></tbody>";
			}
			table+="</table>";
		}
		catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
			
		}
		
		return table;
	}
	
}