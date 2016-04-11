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
		 * Connect to database.  This is hard coded in DBConnect.java
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

			String query = "SELECT Building.buildingID, "
					+ "Building.buildingName, "
					+ "Building.buildingStatus, "
					+ "Building.buildingCalName, "
					+ "Building.buildingCalUrl, "
					+ "Building.Admin_adminID "
					+ "FROM tomcatdb.Building";
			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				this.results = ps.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in BuildingListQuery.java: doRoom method. Please check connection or SQL statement: " + query);
			} 
		}
		
		
		
		public String getHTMLTable(){ 
			//Return table of buildings
			
			String table = "";
				  
			try {
				table += "<table id='' class='mdl-data-table' cellspacing='0' width='95%'>";
				table += "<thead>"
						+ "<tr>"
						+ "<th>Building ID#</th>"
						+ "<th>Building Name</th>"
						+ "<th>Building Status</th>"
						+ "<th>Building Cal Name</th>"
						+ "<th>Building Cal URL</th>"
						+ "<th></th>"
						+ "<th></th>"
						+ "<th></th>" //Ginger added
						+ "</tr>"
						+ "</thead>"
						+ "<tbody>";
				while(results.next()){

					
					String status = "";
					Building building = new Building();
					
					building.setBuildingID(results.getInt("buildingID"));
					building.setBuildingName(results.getString("buildingName"));
					building.setBuildingStatus(results.getInt("buildingStatus"));
					building.setBuildingCalName(results.getString("buildingCalName"));
					building.setBuildingCalUrl(results.getString("buildingCalUrl"));
					//building.setAdmin(results.getString("admin"));
					
		
					// html table for building list
					table += "<tr>";
					table += "<td data-order='" + building.getBuildingID() + "'>";
					table += building.getBuildingID();
					table += "</td>";
					table += "<td data-search='" + building.getBuildingName() + "'>";
					table += building.getBuildingName();
					table += "</td>";
					if (building.getBuildingStatus() == 1){
						status = "Online";
					} else {
						status = "Offline";
					}	
					table += "<td data-filter='" + status + "'>";
					//table += building.getBuildingStatus();
					table += status;
					table += "</td>";
					table += "<td>";
					table += building.getBuildingCalName();
					table += "</td>";
					table += "<td>";
					table += building.getBuildingCalUrl();
					table += "</td>";
					
					table += "<td><a href=updatebuilding?buildingID=" + building.getBuildingID() + "> <button class='btn btn-lg btn-red' type='submit' value='Edit'>Edit Building</button></a></td>";
					
					table += "<td><a href=Schedule?buildingID=" + building.getBuildingID() + "> <button class='btn btn-lg btn-red' type='submit' value='EditHours'>Edit Hours</button></a></td>";
					
					//Ginger added this button to view the rooms of the building
					table += "<td><form action = 'RoomsListServlet' method = 'post'>" +
							"<input type='hidden' name='prev' value='buildings'>" +
							"<input type='hidden' name='buildingID' value='" + building.getBuildingID() + "'>" +
							"<input class='btn btn-lg btn-red' type='submit' value='View/Edit Rooms'>" +
							"</form></td>";//Ginger Added
					
					
					table += "</tr>";
				}
				table += "</tbody>";
				table += "</table>";
			}
			catch(SQLException e) {
				e.printStackTrace();	
			}
			
			return table;
		}
		
		
		

		
		

}
