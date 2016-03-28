package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Building;
import model.DateTimeConverter;
import model.DbConnect;

/**
 * @author Brian Olaogun
 * Helper for the Student side of the website.
 *
 *@contributer: Ginger Nix
 *
 */
public class BuildingSelectQuery {
	// initialize fields
	private Connection connection;
	private ResultSet results;
			
	/**
	 * 
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BuildingSelectQuery() {
	
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
	
	
	public void doBuildingRead(){
		// query to use for testing
		//String query = "SELECT buildingID, buildingName FROM tomcatdb.Building";
		
		// actual query to use when there are times in tomcatdb.Schedule.
		DateTimeConverter dtc = new DateTimeConverter();
		String currentDate = dtc.parseDate(dtc.datetimeStamp());
		String currentTime = dtc.parsedTimeTo24(dtc.datetimeStamp()); // time in 24 hour format
		
		String query = "SELECT tomcatdb.Building.buildingID, "
				+ "tomcatdb.Building.buildingName "
				+ "FROM tomcatdb.Building, tomcatdb.Schedule "
				+ "WHERE tomcatdb.Building.buildingStatus = ? "
				+ "AND tomcatdb.Building.buildingID = tomcatdb.Schedule.Building_buildingID "
				+ "AND tomcatdb.Schedule.startDate = ? "
				+ "AND ((tomcatdb.Schedule.startTime = ?) OR (? BETWEEN tomcatdb.Schedule.startTime AND tomcatdb.Schedule.endTime))";
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, "1");
			ps.setString(2, currentDate);
			ps.setString(3, currentTime);
			ps.setString(4, currentTime);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in BuildingSelectQuery.java: doBuildingRead method. Please check connection or SQL statement: " + query);
		}
		
	}
	
	public void doAdminBuildingRead(){
		// query to use for testing
		//String query = "SELECT buildingID, buildingName FROM tomcatdb.Building";
		
		// Admins can view buildings that are online
		String query = "SELECT tomcatdb.Building.buildingID, "
				+ "tomcatdb.Building.buildingName "
				+ "FROM tomcatdb.Building "
				+ "WHERE tomcatdb.Building.buildingStatus = ? ";
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, "1");
			
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in BuildingSelectQuery.java: doBuildingRead method. Please check connection or SQL statement: " + query);
		}
		
	}
	
	public String getBuildingResults(){
		// Create the String for HTML
		String select = "";
		
		// HTML for dropdown list
		try {
			select = "<select id='buildingList' name='buildingList'>";
			
				if(this.results.next()){
					do {
						// place results in a building object
						Building building = new Building();
						building.setBuildingID(this.results.getInt("buildingID"));
						building.setBuildingName(this.results.getString("buildingName"));
					
						// HTML for dropdown list
						if(this.results.getString("buildingID") != null){
							select += "<option selected='selected' value=" + "'" + building.getBuildingID() + "'" + ">";
							select += building.getBuildingName();
							select += "</option>";
						} else {
							select += "<option value=" + "'" + building.getBuildingID() + "'" + ">";
							select += building.getBuildingName();
							select += "</option>";
						}
					} while (this.results.next());
				} else {
					select = "<h2>No Buildings Online</h2>";
				}
				select += "</select>";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return select;
	}
	
	public String getBuildingResults(int selected){
		// Create the String for HTML
		String select = "";
		
		// HTML for dropdown list
		try {
			select = "<select id='buildingList' name='buildingList'>";
			
			if(this.results.next()){
				do{
					// place results in a building object
					Building building = new Building();
					building.setBuildingID(this.results.getInt("buildingID"));
					building.setBuildingName(this.results.getString("buildingName"));
					
					// HTML for dropdown list
					if(building.getBuildingID() == selected){
						select += "<option selected='selected' value=" + "'" + building.getBuildingID() + "'" + ">";
						select += building.getBuildingName();
						select += "</option>";
					} else {
						select += "<option value=" + "'" + building.getBuildingID() + "'" + ">";
						select += building.getBuildingName();
						select += "</option>";
					}
				} while (this.results.next());
			} else {
				select = "<h2>No Buildings Online</h2>";
			}
			select += "</select>";
			this.results.beforeFirst(); // resets the cursor to 0
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return select;
	}
	/**
	 * This method takes into account the building schedule.  
	 * We get the building name from inputted buildingID
	 * @param buildingID
	 * @return buildingName
	 * 
	 */
	public String buildingName(int buildingID){
		String buildingName = "";
		doBuildingRead();
		try {
			while(this.results.next()){
				Building building = new Building();
				building.setBuildingID(this.results.getInt("buildingID"));
				building.setBuildingName(this.results.getString("buildingName"));
				
				if(building.getBuildingID() == buildingID){
					buildingName = building.getBuildingName();
				}

			} this.results.beforeFirst(); // resets the cursor to 0
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buildingName;
	}
	
	/**
	 * @author: Ginger Nix
	 * @return This method brings back all the active buildings for a pull down list
	 */
	public String getAllActiveBuildings(){
		
		String select = "<select id='buildingList' name='buildingList'>";

		// go through all the active buildings to put into a list
		String query = "SELECT * FROM tomcatdb.Building "
						+ "WHERE buildingStatus = ?";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, "1");
			
			this.results = ps.executeQuery();
			
			while(this.results.next()){
				Building building = new Building();
				building.setBuildingID(this.results.getInt("buildingID"));
				building.setBuildingName(this.results.getString("buildingName"));
				
				// HTML for dropdown list
				if(this.results.getString(1) != null){
					select += "<option selected='selected' value=" + "'" + building.getBuildingID() + "'" + ">";
					select += building.getBuildingName();
					select += "</option>";
				} else {
					select += "<option value=" + "'" + building.getBuildingID() + "'" + ">";
					select += building.getBuildingName();
					select += "</option>";
				}
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in BuildingSelectQuery.java: doBuildingRead method. Please check connection or SQL statement: " + query);
		}
		
		select += "</select>";
		
		
		return select;
	}

	/**
	 * 
	 * This method takes just a building record id and returns the human readable name
	 * @author: Ginger Nix
	 * @param buildingID
	 * @return
	 */
	public String getBuildingNameFromID (int buildingID){
		
		String buildingName = "Unknown Building";
		
		String query = "SELECT * FROM tomcatdb.Building " + 
						"WHERE buildingID = " + buildingID ;
		
		System.out.println("BuildingSelectQuery getBuildingNameFromID BEFORE executing query = " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			
			this.results = ps.executeQuery();
			
			this.results.next();
			
			buildingName = this.results.getString("buildingName");
			
			System.out.println("BuildingSelectQuery getBuildingNameFromID AFTER executing query building name = " + buildingName);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("** Error in BuildingSelectQuery getBuildingNameFromID query = " + query);
		}
		
		return buildingName;
			
		
	}
	
}
