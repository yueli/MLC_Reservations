package helpers;
//**By Ronnie Xu~****/

/** 
 * @author: Ginger Nix - rewrote doUpdate 
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import model.Building;
import model.DbConnect;

public class BuildingListUpdateQuery {
	private Connection connection;


	/**
	 * 
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BuildingListUpdateQuery() {
		
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
	
	
	
	public void doUpdate(Building buildingToUpdate, int adminID){
		
		Building building = new Building();
		building = buildingToUpdate;
		
		System.out.println("BuildingListUpdateQuery: doUpdate: adminID = " + adminID);

		System.out.println("BuildingListUpdateQuery: doUpdate: buildingName = " + building.getBuildingName());
		
		String query = "UPDATE tomcatdb.Building SET "
						+ "buildingName = ?, "
						+ "buildingStatus = ?, "
						+ "buildingCalName = ?, "
						+ "buildingCalUrl = ?, "
						+ "Admin_adminID = ?, "
						+ "buildingQRName = ? "
						+ "WHERE buildingID = ? ";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, building.getBuildingName());
			ps.setInt(2, building.getBuildingStatus());
			ps.setString(3, building.getBuildingCalName());
			ps.setString(4, building.getBuildingCalUrl());
			ps.setInt(5,adminID);
			ps.setString(6, building.getBuildingQRName());
			ps.setInt(7, building.getBuildingID());
					
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("**Error: BuildingListUpdateQuery - doUpdate: Error with query.");
		} 
		
		System.out.println("BuildingListUpdateQuery: doUpdate: query = " + query);
		
	}
	

}
