package helpers;
/**
 * @author: Ginger Nix
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
	 * 
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
		
		public int getLastBuildingID(){
			
		String query = "SELECT buildingID FROM tomcatdb.Building ORDER BY buildingID DESC LIMIT 1;";
		buildingCount = 0;
		int buildingID = 0;
		try{
			PreparedStatement ps = connection.prepareStatement(query);
			results = ps.executeQuery();
			while(results.next()){
				buildingID = results.getInt("buildingID");
				System.out.println("BuildingID in while:" + buildingID);
			}
			
			System.out.println("BuldingID outside:"+ buildingID);
			buildingCount = buildingID +1 ;
			
			
			
			} catch(SQLException e){
			e.printStackTrace();
			}
		
		return buildingCount;
		}

		/*
		 * 
		 */
		public void addBuilding(String buildingName, int buildingStatus, 
				String buildingCalName, String buildingCalUrl, int adminID, String buildingQRName) throws SQLException{

			String query = "INSERT INTO tomcatdb.Building "
					+ "(buildingName, buildingStatus, buildingCalName, buildingCalUrl, Admin_adminID, buildingQRName)"
					+ "VALUES ('" 
					+ buildingName + "', '" 
					+ buildingStatus  +"', '"
					+ buildingCalName + "', '" 
					+ buildingCalUrl + "', '"
					+ adminID + "', '"
					+ buildingQRName+"');";
			
			System.out.println("BuildingListAddQuery: addBuilding: query = " + query);
			
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


		/**
		 * This method will take the myID and check to see if a building with this
		 * name already exists
		 * @param myID
		 * @return
		 */
		public boolean inBuildingTable(String buildingName){
			
			String query = "SELECT * from tomcatdb.Building WHERE buildingName = '" + buildingName + "' LIMIT 1";
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);

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

		/**
		 * This method take the building info and adds it to the building table
		 **/
		
		public void insertBuildingTable(String buildingName, String buildingStatus, String buildingCalName,
						String buildingCalUrl, String buildingQRName) {
			
			String query = "INSERT INTO tomcatdb.Building (buildingName, buildingSatus, buildingCalName, buildingCalUrl, buildingQRName) "
					+ "VALUES ('" + buildingName + "','" + buildingStatus + "','" + buildingCalName + 
					"','" + buildingCalUrl + "','"+ buildingQRName + "')";
			
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
			
				ps.executeUpdate();
				System.out.println("Success in BuildingListAddQuery: insertBuilding: insert into table method. Query = " + query);

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("***Error in BuildingListAddQuery: insertBuilding:  insert into table method. Query = " + query);
			}
			
		}
	

}


