package helpers;

/** 
 * This helper is used to get building information and create forms
 * @creator: Ronnie Xu
 * @author: Ginger Nix - added input box, added comments, cleaned code, secured queries
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import model.Building;
import model.DbConnect;

public class BuildingListSelectQuery {
	private Connection connection;
	private ResultSet results;

	/**
	 * 
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BuildingListSelectQuery() {
		
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
	 * This method get the building record info based on the building record ID
	 * @param: buildingID
	 * @return: nothing
	 */
	
	public void doRead(int buildingID){

		String query = "SELECT * FROM tomcatdb.Building "
						+ "WHERE buildingID = ? "
						+ "LIMIT 1";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, buildingID);
			
			results = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
		} 
	}
	
	
	/**
	 * This method gets all the buildings
	 */
	
	public Building getBuilding(){ 
				
		Building buildingReturn = new Building();
	
		try{
						
			while(results.next()){

				Building building = new Building();
				building.setBuildingID(results.getInt("buildingID"));
				building.setBuildingName(results.getString("buildingName"));
				building.setBuildingStatus(results.getInt("buildingStatus"));
				building.setBuildingCalName(results.getString("buildingCalName"));
				building.setBuildingCalUrl(results.getString("buildingCalUrl"));
				building.setBuildingQRName(results.getString("buildingQRName"));
				
				buildingReturn = building;
			}

		}
		catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
			
		}
		
		return buildingReturn;
	}
	
	
	/**
	 * This method takes a building record ID and pre-populates a table for the admi
	 * user to edit the bulding's information
	 * @param: buildingID
	 * @return: the pre-populated building edit form
	 */
	
	public String buildingEditForm (int buildingID) {
		String form = "";
		
		String query = "SELECT * FROM tomcatdb.Building WHERE buildingID = ? LIMIT 1";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, buildingID);
			
			this.results = ps.executeQuery();
			
			this.results.next();
		
			// grab the results we need to display in form
			String buildingName = this.results.getString("buildingName");	
			String buildingStatus = this.results.getString("buildingStatus");	
			String buildingCalName = this.results.getString("buildingCalName");	
			String buildingCalUrl = this.results.getString("buildingCalUrl");	
			String buildingQRName = this.results.getString("buildingQRName");
			
			form += "<br />";
			form += "<div align='center'><h3>Edit Building</h3>";
			form += "<br />";
			form += "<form action='BuildingListBuildingUpdateServlet' method = 'post'>";
			
			form += "<div class='col-md-3 col-md-offset-5 col-sm-offset-5'>";
			form += "Building Name:<br>";
			form +=  "<input type='text' id = 'buildingName' name = 'buildingName' value = '" + buildingName + "' required>";
			form += "<br />";
			
			form += "Building Status:<br>";
			
			form += "<select name = 'status' required>";
			
			// check to see the building's status and put into a pull-down
			// with the current status being the default
			if (Objects.equals("1", buildingStatus)){ //default to Active
				form += "<option value='1' selected>Online</option>";
				form += "<option value='0'>Offline</option>";
				
			}else{ //default to Inactive
				form += "<option value='1'>Online</option>";
				form += "<option value='0' selected>Offline</option>";		
			}					
			form += "</select>";
			form += "<br />";
			
			form += "Building Calendar's Name:<br>";
			form +=  "<input type='text' name = 'buildingCalName' value = '" + buildingCalName + "' required>";
			form += "<br />";
			
			form += "Building Calendar's URL:<br>";
			form +=  "<input type='text' name = 'buildingCalUrl' value = '" + buildingCalUrl + "' required>";
			form += "<br />";
			
			form += "Building QR Name:<br>";
			form +=  "<input type='text' name = 'buildingQRName' value = '" + buildingQRName + "' required>";
			form += "<br />";
			form += "</div>";
			
			form += "<div class='col-md-12' align='center'>";
			form += "<br />";
			form += "<input class='btn btn-lg btn-red' type = 'submit' value = Update>";
			form += "<input type = 'hidden' name = 'buildingID' value='" + buildingID + "'>";
			form += "</form>";
			
			form += "<br />";
			form += "<form action='BuildingListServlet' method = 'post'>";
			form += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
			form += "</form>";
			form += "</div>";

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("** Error in BuildingListServlet: buildingEditForm w/ query");
		} 
	
		return form;		
		
	}

}
