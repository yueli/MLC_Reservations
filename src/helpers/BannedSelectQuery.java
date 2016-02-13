package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Banned;
import model.DbConnect;


/**
 * @author Ronnie Xu
 * Helper for the Admin side of the website.
 *
 */

public class BannedSelectQuery {
	
		private Connection connection;
		private ResultSet results;

		/**
		 * 
		 * @param dbName
		 * @param user
		 * @param pwd
		 */
		public BannedSelectQuery() {
	
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
		
		

		public void doRead(){

			String query = "SELECT banned.bannedID, banned.Student_studentID, banned.Admin_adminID, banned.banStart, banned.banEnd, banned.penaltyCount, banned.description, banned.status FROM banned";
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
			
			String table = "<table>";
			
		
			try{
				
				
				table += "<tr><td><a href='/admin/ban.jsp'>Ban Someone</td></tr>";
				table += "<tr><td>Ban#</td><td>Student ID</td><td>Admin ID</td><td>Ban Start</td><td>Ban End</td><td>Penalty Count</td><td>Description</td><td>Status</td></tr>";
				while(results.next()){

					
					
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
					
					table += "<tr>";
					
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
	
					
					table += "</tr>";
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
