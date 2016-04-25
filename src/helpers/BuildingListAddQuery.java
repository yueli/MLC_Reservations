package helpers;

/**
 * @author: Ronnie Xu
 * @author: Ginger Nix - commented, cleaned, secured queries, and added methods
 * 
 * This helper is used to see if a building is already in the building table and to insert new buildings
 * 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.Building;
import model.DbConnect;

public class BuildingListAddQuery {

	private Connection connection;
	private ResultSet results;
	private int buildingCount;

	/**
	 * Connect to database.  This is hard coded in DBConnect.java
	 */
	public BuildingListAddQuery() {
		
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
		 * This method takes the new building's info and stores it in the building table
		 * @param buildingName
		 * @param buildingStatus
		 * @param buildingCalName
		 * @param buildingCalUrl
		 * @param adminID
		 * @param buildingQRName
		 * @throws SQLException
		 * @return: nothing
		 */
	
		public void addBuilding(String buildingName, int buildingStatus, 
				String buildingCalName, String buildingCalUrl, int adminID, String buildingQRName) throws SQLException{

			String query = "INSERT INTO tomcatdb.Building "
					+ "(buildingName, buildingStatus, buildingCalName, buildingCalUrl, Admin_adminID, buildingQRName)"
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			System.out.println("BuildingListAddQuery: addBuilding: query = " + query);
			
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				
				ps.setString(1, buildingName);
				ps.setInt(2, buildingStatus);
				ps.setString(3, buildingCalName);
				ps.setString(4, buildingCalUrl);
				ps.setInt(5, adminID);
				ps.setString(6, buildingQRName);
								
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}


		/**
		 * This method will take the building's name and check to see if a building with this
		 * name already exists
		 * @author: Ginger Nix
		 * @param: bulding name to check
		 * @return: true if a building w/ this name is in the building table, else
		 * false if it is not
		 */
		
		public boolean inBuildingTable(String buildingName){
			
			String query = "SELECT * from tomcatdb.Building WHERE buildingName = ? LIMIT 1";
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				
				ps.setString(1, buildingName);
				this.results = ps.executeQuery();
				
				boolean results = this.results.next();
				System.out.println("BuildingListAddQuery: in building table: results found " + results + " query = " + query);
				
				if (results) {//the myID is in the user table
					System.out.println("BuildingListAddQuery: in building table: returning true");
					
					return true;
				}else{
					System.out.println("BuildingListAddQuery: in building table: returning false");
					return false;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("****Error in BuildingListAddQuery: inBuildingTable method. Query = " + query);
			}
			
			return false;
		}



}


