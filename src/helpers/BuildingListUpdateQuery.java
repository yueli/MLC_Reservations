package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import model.Building;

public class BuildingListUpdateQuery {
	private Connection connection;


	/**
	 * 
	 * @param dbName
	 * @param user
	 * @param pwd
	 */
	public BuildingListUpdateQuery(String dbName, String user, String pwd) {
		String url = "jdbc:mysql://localhost:3306/" + dbName;
		
		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, user, pwd);
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
