package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.Building;
import model.DbConnect;


/**
 * @author Ronnie Xu
 * Helper for the Admin side of the website.
 *
 */

public class BuildingListQuery {
	
		private Connection connection;
		private ResultSet results;

		/**
		 * 
		 * @param dbName
		 * @param user
		 * @param pwd
		 */
		public BuildingListQuery() {
			
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

			String query = "SELECT building.buildingID, building.buildingName, building.buildingStatus, building.buildingCalName, building.buildingCalUrl, building.Admin_adminID FROM building";
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
				
				
				table += "<tr><u><a href='buildingform'>Add Building Form</a></u></tr>";
				
				table += "<tr><td>Building ID#</td><td>Building Name</td><td>Building Status</td><td>Building Cal Name</td><td>Building Cal URL</td></tr>";
				while(results.next()){

					
					
					Building building = new Building();
					
					building.setBuildingID(results.getInt("buildingID"));
					building.setBuildingName(results.getString("buildingName"));
					building.setBuildingStatus(results.getInt("buildingStatus"));
					building.setBuildingCalName(results.getString("buildingCalName"));
					building.setBuildingCalUrl(results.getString("buildingCalUrl"));
					//building.setAdmin(results.getString("admin"));
					
		
		
					
					//show only banned
					
					table += "<tr>";
					
					table += "<td>";
					table += building.getBuildingID();
					table += "</td>";
					table += "<td>";
					table += building.getBuildingName();
					table += "</td>";
					table += "<td>";
					table += building.getBuildingStatus();
					table += "</td>";
					table += "<td>";
					table += building.getBuildingCalName();
					table += "</td>";
					table += "<td>";
					table += building.getBuildingCalUrl();
					table += "</td>";
					table += "<td>";
					table += "admin?";
					table += "</td>";
	
	
					
					table += "<td><a href=updatebuilding?buildingID=" + building.getBuildingID() + "> <button type='submit' value='Edit'>Edit</button></a></td>";
	
					
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
