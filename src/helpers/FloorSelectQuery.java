/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Rooms;

/**
 * @author Brian Olaogun
 * Helper for the Student side of the website.
 *
 */
public class FloorSelectQuery {
	private Connection connection;
	private ResultSet results;
	
	public FloorSelectQuery() {
		String url = "jdbc:mysql://localhost:3306/" + "tomcatdb";

		
		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, "root", ""); // credentials for Brian, Ginger, & Victoria for local server
			//this.connection = DriverManager.getConnection(url, "tomcatuser", "bu11fr0g"); // credentials for the dev server
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
	
	public void doFloorRead(String building){
		String query = "SELECT roomFloor FROM tomcatdb.Rooms, tomcatdb.Building WHERE tomcatdb.Rooms.Building_buildingID = tomcatdb.Building.buildingID AND tomcatdb.Building.buildingName = '" + building + "'" + " GROUP BY roomFloor ORDER BY roomFloor";
	
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in FloorSelectQuery.java: doFloorRead method. Please check connection or SQL statement.");
		}
	}
	public String getFloorResults(){
		// Create the String for HTML
		String select = "<select id='floorList' name='floorList' onchange='this.form.submit()'>";
		select += "<option></option>";
		
		// 
		
		try {
			while(this.results.next()){
				// place results in a building object
				Rooms room = new Rooms();
				room.setRoomFloor(Integer.parseInt(this.results.getString("roomFloor")));
			
				// HTML for dropdown list
				select += "<option value=" + "'" + room.getRoomFloor() + "'" + ">";
				select += room.getRoomFloor();
				select += "</option>";
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		select += "</select>";
		
		return select;
	}
	
	public String getFloorResults(String selected){
		// Create the String for HTML
		String select = "<select id='floorList' name='floorList' onchange='this.form.submit()'>";
	
		// HTML for dropdown list
		try {
			while(this.results.next()){
				// place results in a Rooms object
				Rooms room = new Rooms();
				room.setRoomFloor(Integer.parseInt(this.results.getString("roomFloor")));
				
				if(room.getRoomFloor() == Integer.parseInt(selected)){
					select += "<option selected='selected' value=" + "'" + room.getRoomFloor() + "'" + ">";
					select += room.getRoomFloor();
					select += "</option>";
				} else {
					select += "<option value=" + "'" + room.getRoomFloor() + "'" + ">";
					select += room.getRoomFloor();
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
