/**
 * 
 */
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DbConnect;
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
	 * This will run a query to get the floors of a building
	 * @param building Integer building ID of the building
	 */
	public void doFloorRead(int building){
		String query = "SELECT roomFloor "
				+ "FROM tomcatdb.Rooms, tomcatdb.Building "
				+ "WHERE tomcatdb.Rooms.Building_buildingID = tomcatdb.Building.buildingID "
				+ "AND tomcatdb.Building.buildingID = ? " 
				+ "GROUP BY roomFloor "
				+ "ORDER BY roomFloor";
	
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, building);
			
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in FloorSelectQuery.java: doFloorRead method. Please check connection or SQL statement.");
		}
	}
	/**
	 * String result set of the query above placed in an html select (drop down) list
	 */
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
	
	/**
	 * This method will create a new drop down with the selected floor listed first.
	 * @param selected the floor the user select
	 * @return new html select with the floor the user selected still displayed
	 */
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
