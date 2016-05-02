package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Admin;
import model.Banned;
import model.DbConnect;
import model.TimeConverter;


/**
 * @author Ronnie Xu
 * Helper for the Admin side to ban users
 * 
 * @author: Ginger Nix: fixed bugs and contributed methods
 * 
 **/

public class BanUserQuery {
	
	private Connection connection;

	/**
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BanUserQuery() {
	
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
	 * The method banUser takes the banned user object and stores the
	 * data in the banned table thereby banning this user
	 * 
	 * @param ban banned user object
	 */
	public void banUser(Banned ban){

		String query = "INSERT INTO tomcatdb.Banned "
				+ "(`User_userID`, `Admin_adminID`, `banStart`, `penaltyCount`, `description`, `status`) "
				+ "VALUES (?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			
			ps.setInt(1, ban.getUserRecdID());
			ps.setInt(2, ban.getAdminID());
			ps.setString(3, ban.getBanStart());
			ps.setInt(4, ban.getPenaltyCount());
			ps.setString(5, ban.getDescription());
			ps.setInt(6, ban.getStatus());
			
			System.out.println(ps);
			ps.execute();
			
		} catch (SQLException e) {
			System.out.println("****ERROR: BanUSerQuery: banUser error w/ query results");
			e.printStackTrace();
		}
	}		
	
	
		/**
		 * This method creates a blank form for admin to search for a user to ban
		 * @author Ginger Nix
		 * @return the blank form for searching for a user to ban
		 */
		public String createBanUserForm(){
			String form = "";
			
			form += "<div align='center'><h3>Search For a Student to Ban</h3>";
			form += "<br />";
			
			form += "<form action='BanUserServlet' method = 'post'>";
			
			form += "Student's UGA MyID:<br>";
			form +=  "<input type='text' id = 'userMyID' name = 'userMyID' required>";
			form += "<br /> <br />";
			form += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Ban Student'>";
			form += "</form>";
			form += "<br />";
			form += "<form action='BanReadServlet' method = 'post'>";
			form += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
			form += "</form>";
	
			return form;
	
		}
	
		/**
		 * This method cancels all future reservations for the banned user - 
		 * looks for any reservations that this user has as a primary user
		 * and set the free field to yes, indicating that the reservation
		 * is no longer valid, thus freeing the time/building/room up
		 * for other users
		 * 
		 * @author: Ginger Nix
		 * @param userRecdID of the banned user 
		 **/
		public void cancelBannedUserReservations(int userRecdID){
			// look for any reservations that this user has as a primary user
			// and set the free field to yes indicating that the reservation
			// is no longer valid, thus freeing the time/building/room up
			// for other users
	
			String query = "UPDATE tomcatdb.Reservations "
							+ "SET free = 'Y' "
							+ "WHERE primaryUser = ?";
			
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				
				ps.setInt(1, userRecdID);
			System.out.println(ps);
				ps.execute();
				
			} catch (SQLException e) {
				System.out.println("****ERROR: BanUSerQuery: cancel resv: query = " + query);
				e.printStackTrace();
			}
						
		}	


}
