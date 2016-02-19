package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import model.Building;
import model.DbConnect;

public class BuildingListUpdateQuery {
	private Connection connection;


	/**
	 * 
	 * @param dbName
	 * @param user
	 * @param pwd
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
	
	
	
	public void doUpdate(Building building){
		
		
		
		String query = "UPDATE tomcatdb.building SET buildingName='"+building.getBuildingName()+"', buildingCalName='"+building.getBuildingCalName()+"', buildingCalUrl='"+building.getBuildingCalUrl()+"', Admin_adminID=1 WHERE buildingID="+building.getBuildingID()+";";

		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.executeUpdate();
			
			
			
		
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Please check connection or SQL statement.");
		} 
	}

}
