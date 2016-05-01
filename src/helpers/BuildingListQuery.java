package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.Building;
import model.DbConnect;
import model.Rooms;


/**
 * Helper for the Admin side of the website.
 * @author Ronnie Xu
 * @contributor Brian Olaogun	
 * @contributor Ginger Nix
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
					+ "Building.buildingQRName, "
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
		
		
		/**
		 * Get the name of a building from it's record id
		 * @param buildingID
		 * @return name of the building
		 */
		public String getBuildingName(int buildingID){
			String bName = "";
			try {
				while(results.next()){
						Building building = new Building();
						building.setBuildingID(results.getInt("buildingID"));
						building.setBuildingName(results.getString("buildingName"));
						int bID = building.getBuildingID();
						
						if(buildingID==bID){
						bName = results.getString("buildingName");
					}
				}
			}	
				catch(SQLException e) {
					e.printStackTrace();	
				}
				return bName;
			}
		
		/**
		 * This method gets all the rooms in a building.
		 * @param buildingID
		 */
		
		public void doReadRooms(int buildingID){

			String query = "SELECT * "
					+ "FROM tomcatdb.Rooms	 "
					+ "WHERE Building_buildingID = ? ";
			
			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				ps.setInt(1, buildingID);
				
				this.results = ps.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in BuildingListQuery.java: doRoom method. Please check connection or SQL statement: " + query);
			} 
		}
		
		
		/**
		 * This method gets the room Number based on the room's record id
		 * @param roomID
		 * @return room number for this record
		 */
		
		public String getRoomName(int roomID){
			String rNumber = "";
			try {
				while(results.next()){
					Rooms room = new Rooms();
					room.setRoomID(results.getInt("roomID"));
					room.setRoomNumber("roomNumber");
					
						int rID = room.getRoomID();
						
						if(roomID==rID){
							rNumber = results.getString("roomNumber");
					}
				}
			}	
				catch(SQLException e) {
					e.printStackTrace();	
				}
				return rNumber;
			}
	
		/**
		 * This method gets all the buildings and puts them in a table.
		 * @param: none
		 * @return:This method returns a table containing all the buildings
		 */
		
		public String getHTMLTable(){ 
			//Return table of buildings			
			String table = "";
				  
			try {
				table += "<div align='center'><h2>Buildings</h2></div>";
				
				table += "<br /><br />";
				table += "<form action='buildingform' method = 'post'>" +
						"<input class='btn btn-lg btn-red' type='submit' value='Add A Building'>" +
						"</form>";
				
				table += "<div class'col-md-3 col-md-offset-5 col-sm-offset-5'>";
				table += "<table id='' class='mdl-data-table' cellspacing='0' width='95%'>";
				table += "<thead>"
						+ "<tr>"
						+ "<th>Name</th>"
						+ "<th>Status</th>"
						+ "<th>Calendar Name</th>"
						+ "<th>Calendar URL</th>"
						+ "<th>QR Code Building Name</th>" //Ginger added
						+ "<th></th>"
						+ "<th></th>"
						+ "<th></th>" //Ginger added
						+ "</tr>"
						+ "</thead>"
						+ "<tbody>";
				 table += "</div>";
				while(results.next()){

					
					String status = "";
					Building building = new Building();
					
					building.setBuildingID(results.getInt("buildingID"));
					building.setBuildingName(results.getString("buildingName"));
					building.setBuildingStatus(results.getInt("buildingStatus"));
					building.setBuildingCalName(results.getString("buildingCalName"));
					building.setBuildingCalUrl(results.getString("buildingCalUrl"));
					//building.setAdmin(results.getString("admin"));
					building.setBuildingQRName(results.getString("buildingQRName"));
		
					// html table for building list
					table += "<tr>";

					table += "<td data-search='" + building.getBuildingName() + "'>";
					table += building.getBuildingName();
					table += "</td>";
					if (building.getBuildingStatus() == 1){
						status = "Online";
					} else {
						status = "Offline";
					}	
					table += "<td data-filter='" + status + "'>";
					table += status;
					table += "</td>";
					table += "<td>";
					table += building.getBuildingCalName();
					table += "</td>";
					table += "<td>";
					table += building.getBuildingCalUrl();
					table += "</td>";
					
					table += "<td>";
					table += building.getBuildingQRName();
					table += "</td>";
					
					// calls BuildingListUpdateServlet
					table += "<div class='col-md-12' align='center'>";
					table += "<td>";
					table += "<form name='buildingID' action='updatebuilding' method='post'>";
					table += "<input type='hidden' name='buildingID' value='" + building.getBuildingID() + "'>";
					table += "<input class='btn btn-lg btn-red' type='submit' value='Edit Buiding'>";
					table += "</form>";
					table += "</td>";
					
					table += "<td>";
					table += "<form name='scheduleEditForm' action='Schedule' method='post'>";
					table += "<input type='hidden' name='buildingID' value='" + building.getBuildingID() + "'>";
					table += "<input class='btn btn-lg btn-red' type='submit' value='Edit Hours'>";
					table += "</form>";
					table += "</td>";
					//Ginger added this button to view the rooms of the building
					table += "<td>";
					table += "<form name='formForRooms' action='RoomsListServlet' method='post'>";
					table += "<input type='hidden' name='cancelAction' value='buildings'>";
					table += "<input type='hidden' name='buildingID' value='" + building.getBuildingID() + "'>";
					table += "<input class='btn btn-lg btn-red' type='submit' value='View/Edit Rooms'>";
					table += "</form>";
					table += "</td>"; //Ginger Added
					table += "</div>";
					
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
		
		/**
		 * This method creates a form for the admin user to add a building
		 * @author: Ginger Nix
		 * @param none
		 * @return table with form to add a building
		 */
		
		public String createAddBuildingForm() {
			String table = "";
		
			table += "<div align='center'><h2>Add a Building</h2>";
			table += "<br />";
			
			table += "<form action='BuildingListAddServlet' method = 'post'>";
			
			table += "Name:<br>";
			table +=  "<input type='text' id = 'buildingName' name = 'buildingName' required>";
			table += "<br />";
			
			table += "Status:<br>";
			table += "<select name = 'buildingStatus' required>";
			table += "<option value='1' selected>Active</option>";
			table += "<option value='0'>Inactive</option>";	
			table += "</select>";		
			table += "<br />";
			
			table += "Calendar Name:<br>";
			table +=  "<input type='text' name = 'buildingCalName' required>";
			table += "<br />";
			
			table += "Calendar URL:<br>";
			table +=  "<input type='text' name = 'buildingCalUrl' required>";
			table += "<br />";
			
			table += "QR Name:<br>";
			table +=  "<input type='text' name = 'buildingQRName' required>";
			table += "<br />";
			table += "<br />";	
			table += "<br />";	
			table += "</div>";
			
			table += "<div align='center'>";
			table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Add Building'>";
			table += "</form>";
			
			table += "<br>";
			table += "<form action='BuildingListServlet' method = 'post'>";
			table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
			table += "</form>";
			table += "</div>";
					
			return table;
		}

		
		

}
