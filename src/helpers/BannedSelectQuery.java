package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Admin;
import model.Banned;
import model.DateTimeConverter;
import model.DbConnect;

import model.User;


/**
 * @author Ronnie Xu
 * Helper for the Admin side of the website.
 * 
 * @contributer: Ginger Nix - fixed bugs, fixed queries, displays, cleaned up code, and added comments
 *
 */

public class BannedSelectQuery {
	
		private Connection connection;
		private ResultSet results;

		/**
		 * 
		 * Database connection.  Hard coded in DbConnect.java
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
		
		
		/**
		 * The method doRead gets all the banned users from the Banned table
		 * parameter: none
		 * return: none
		 */
		
		public void doRead(){
			String query = "SELECT * FROM tomcatdb.Banned "
					+ "WHERE status=1 "
					+ "AND penaltyCount > 1 "
					+ "AND (banEnd IS NULL "
					+ "OR banEnd = '') ";
			
			System.out.println("BanSelQ: doRead: query = " + query);
			
			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				results = ps.executeQuery();
				
			
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in BannedSelectQuery.java: getHTMLTable method. Please check connection or SQL statement: " + query);
			} 
		}
		
	
		/**
		 * The method getHTMLTable gets all teh banned students and puts them into a table
		 * parameter: none
		 * return: the table of all the banned users
		 */
			
		public String getHTMLTable(){ 
			//Return table of banned students
			
			String table = "";

			table += "<div align='center'><h3>Banning</h3>";
			table += "<br /><br />";
			table += "<center><a href=banUser><button type='submit' value=''>Ban A User</button></a>&nbsp;&nbsp";

			table += "<a href=unbanall><button type='submit' value=''>Unban All</button></a></center>";
			table += "<tr></tr>";
			try {
				table += "<table id='' class='mdl-data-table' cellspacing='0' width='95%'>";
				
				table += "<thead>";
				table += "<tr>"

						+ "<th>First Name</th>"
						+ "<th>Last Name</th>"
						+ "<th>My ID</th>"
						+ "<th>Admin</th>"
						+ "<th>Ban Start</th>"
						+ "<th>Penalty Count</th>"
						+ "<th>Description</th>"
						+ "<th></th>"
						+ "</tr>";
				table += "</thead>";
				table += "<tbody>";
				while(results.next()){

					DateTimeConverter dtc = new DateTimeConverter();
					
					Banned ban = new Banned();
					ban.setBanID(results.getInt("bannedID"));
					ban.setUserRecdID(results.getInt("User_userID"));
					ban.setAdminID(results.getInt("Admin_adminID"));
					ban.setBanStart(results.getString("banStart"));
					ban.setBanEnd(this.results.getString("banEnd"));
					ban.setPenaltyCount(results.getInt("penaltyCount"));
					ban.setDescription(results.getString("description"));
					
					//show only banned
					
					BanGetUserInfoQuery userData = new BanGetUserInfoQuery();
					User user = new User();
					user = userData.userData(ban.getUserRecdID());
					
					BanGetUserInfoQuery adminData = new BanGetUserInfoQuery();
					Admin admin = new Admin();
					admin = adminData.adminData(ban.getAdminID());
					
					System.out.println("**BanSelectQuery: getHTMLTable: admin name  = " + admin.getFname() + " " + admin.getLname());
					
					table += "<tr>";
					
					table += "<td>";
					table += user.getUserFirstName();
					table += "</td>";
					table += "<td>";
					table += user.getUserLastName();
					table += "</td>";
					table += "<td>";
					table += user.getMyID();
					table += "</td>";
					table += "<td>";
					table += admin.getFname() +" "+ admin.getLname();
					table += "</td>";
					table += "<td>";
					table += dtc.dateTimeTo12Long(ban.getBanStart());
					table += "</td>";
					table += "<td>";
					table += ban.getPenaltyCount();
					table += "</td>";
					table += "<td>";
					
					System.out.println("BannedSelectQuery: ban.getDescription = " + ban.getDescription());
						//if (ban.getDescription().isEmpty()==true)
						//if( (ban.getDescription().isEmpty()==true) || (ban.getDescription() == null))
						if  (ban.getDescription() == null)
						{
							table += "** None **";
						}
						else{
						table += ban.getDescription();
						}
					table += "</td>";
					
					//table += "<td><a href=unban?banID=" + ban.getBanID() + "> <button type='submit' value='Unban'>Unban</button></a></td>";
					table += "<td>"
							+ "<form action='unban' method = 'post'>"
							+ "<input type='hidden' name='bannedRecdID' value='" + ban.getBanID() + "'>"
							+ "<input type='hidden' name='myID' value='" + user.getMyID() + "'>"
							+ "<input class='btn btn-lg btn-red' type='submit' value='Unban'>" 
							+ "</form>"
							+ "</td>";

						
					
					table += "</tr>";
				}
				table += "</tbody>";
				table += "</table></center>";
			}
			catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Error in BannedSelectQuery.java: getHTMLTable method. Please check connection or SQL statement.");
				
			}
			
			return table;
		}
		

}
