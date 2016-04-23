package helpers;

/** 
 * @creator: Ronnie Xu
 * @author: Ginger Nix - added input box, added comments, cleaned code
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
	
	
	public void doRead(int buildingID){

		String query = "SELECT Building.buildingID, Building.buildingName, Building.buildingStatus, Building.buildingCalName, Building.buildingCalUrl, Building.Admin_adminID FROM tomcatdb.Building where Building.buildingID= " + buildingID + " ORDER BY buildingID LIMIT 1";
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			results = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
		} 
	}
	
	
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
			
			form += "Building Name:<br>";
			form +=  "<input type='text' name = 'buildingName' value = '" + buildingName + "' required>";
			form += "<br />";
			
			form += "Building Status:<br>";
			
			form += "<select name = 'status' required>";
			
			// check to see the building's status and put into a pull-down
			// with the current status being the default
			if (Objects.equals("1", buildingStatus)){ //default to Active
				form += "<option value='1' selected>Active</option>";
				form += "<option value='0'>Inactive</option>";
				
			}else{ //default to Inactive
				form += "<option value='1'>Active</option>";
				form += "<option value='0' selected>Inactive</option>";		
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
			
			form += "<br />";
			form += "<input class='btn btn-lg btn-red' type = 'submit' value = Update>";
			form += "<input type = 'hidden' name = 'buildingID' value='" + buildingID + "'>";
			form += "</form>";
			
			form += "<br />";
			form += "<form action='BuildingListServlet' method = 'post'>";
			form += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
			form += "</form>";

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("** Error in BuildingListServlet: buildingEditForm w/ query");
		} 

	
		return form;
		
		
	}

}
