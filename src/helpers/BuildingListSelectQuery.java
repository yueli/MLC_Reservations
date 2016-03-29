package helpers;
//**By Ronnie Xu~****/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

		String query = "SELECT building.buildingID, building.buildingName, building.buildingStatus, building.buildingCalName, building.buildingCalUrl, building.Admin_adminID FROM building where building.buildingID="+buildingID+" ORDER BY buildingID LIMIT 1";
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
				//building.setAdmin(admin);
				buildingReturn = building;
			}

		}
		catch(SQLException e) {
			e.printStackTrace();
			//System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
			
		}
		
		return buildingReturn;
	}

}
