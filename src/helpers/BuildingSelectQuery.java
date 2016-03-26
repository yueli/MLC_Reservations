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
		String select = "<select id='buildingList' name='buildingList'>";
		
		// HTML for dropdown list
		try {
			while(this.results.next()){
				// place results in a building object
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
		}
		select += "</select>";
		
		return select;
	}
	
	public String getBuildingResults(int selected){
		// Create the String for HTML
		String select = "<select id='buildingList' name='buildingList'>";
		
		// HTML for dropdown list
		try {
			while(this.results.next()){
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
			} this.results.beforeFirst(); // resets the cursor to 0
		} catch (SQLException e) {
			e.printStackTrace();
		}
		select += "</select>";
		
		return select;
	}
	
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
	
	
}
