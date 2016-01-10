package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Building;

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
	public BuildingSelectQuery(String dbName, String user, String pwd) {
		String url = "jdbc:mysql://localhost:3306/" + dbName;

		
		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, user, pwd);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void doBuildingRead(){
		String query = "SELECT buildingName FROM tomcatdb.Building";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in BrowseQueries.java: doBuildingRead method. Please check connection or SQL statement.");
		}
		
	}
	
	public String getBuildingResults(){
		// Create the String for HTML
		String select = "<select id='buildingList' name='buildingList' onchange='this.form.submit()'>";
		
		// 
		
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
		String select = "<select id='buildingList' name='buildingList' onchange='this.form.submit()'>";
		
		// 
		
		try {
			while(this.results.next()){
				// place results in a building object
				Building building = new Building();
				building.setBuildingName(this.results.getString("buildingName"));
				
				// HTML for dropdown list
				if(building.getBuildingName().equalsIgnoreCase(selected) && selected != null){
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
	
	
}
