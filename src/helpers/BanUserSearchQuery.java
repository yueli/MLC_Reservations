package helpers;
/**
 * @author: Ronnie Xu
 * @contributer: Ginger Nix - added comments, made queries safe, cleaned code
 * 
 * This helper contains methods retrieve the banned users.
 * 
 */

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
	
	/**
	 * The method getHTMLTable gets a table with all the currently banned students.
	 * @param: none
	 * @return: all the currently banned students
	 */
	
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