package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Building;

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
	 * @param dbName
	 * @param user
	 * @param pwd
	 */
	public BuildingSelectQuery() {
		String url = "jdbc:mysql://localhost:3306/" + "tomcatdb";
		
		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, "root", ""); // credentials for Brian, Ginger, & Victoria for local server
			//this.connection = DriverManager.getConnection(url, "tomcatuser", "bu11fr0g"); // credentials for dev server
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	public void doBuildingRead(){
		// TODO redo query to check if building is available at the current date.
		String query = "SELECT buildingName FROM tomcatdb.Building";
		
		// actual query to use when there are building times.
		// this query will need the current date and current time.
		// also selected will be buildings that are online.
		//String query = "SELECT tomcatdb.Building.buildingName FROM tomcatdb.Building, tomcatdb.BuildingSchedule, tomcatdb.Schedule WHERE tomcatdb.Building.buildingStatus = 1 AND tomcatdb.Building.buildingID = tomcatdb.BuildingSchedule.Building_buildingID AND tomcatdb.Schedule.scheduleID = tomcatdb.BuildingSchedule.Schedule_scheduleID AND tomcatdb.Schedule.startDate = '2016-01-23' AND ((tomcatdb.Schedule.startTime = '18:00:00') OR ('18:00:00' BETWEEN tomcatdb.Schedule.startTime AND tomcatdb.Schedule.endTime))";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in BuildingSelectQuery.java: doBuildingRead method. Please check connection or SQL statement.");
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
				building.setBuildingName(this.results.getString("buildingName"));
				
				// HTML for dropdown list
				if(this.results.getString(1) != null){
					select += "<option selected='selected' value=" + "'" + building.getBuildingName() + "'" + ">";
					select += building.getBuildingName();
					select += "</option>";
				} else {
					select += "<option value=" + "'" + building.getBuildingName() + "'" + ">";
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
	
	public String getBuildingResults(String selected){
		// Create the String for HTML
		String select = "<select id='buildingList' name='buildingList'>";
		
		// HTML for dropdown list
		try {
			while(this.results.next()){
				// place results in a building object
				Building building = new Building();
				building.setBuildingName(this.results.getString("buildingName"));
				
				// HTML for dropdown list
				if(building.getBuildingName().equals(selected)){
					select += "<option selected='selected' value=" + "'" + building.getBuildingName() + "'" + ">";
					select += building.getBuildingName();
					select += "</option>";
				} else {
					select += "<option value=" + "'" + building.getBuildingName() + "'" + ">";
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
	
	
}
