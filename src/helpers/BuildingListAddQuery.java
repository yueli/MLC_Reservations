package helpers;
//**By Ronnie Xu~****/
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
			
		String query = "SELECT buildingID FROM tomcatdb.building ORDER BY buildingID DESC LIMIT 1;";
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

		public void addBuildingList(int buildingID, String buildingName, int buildingStatus, String buildingCalName, String buildingCalUrl) throws SQLException{
			
			Building building = new Building();

			
			building.setBuildingID(buildingID);
			building.setBuildingName(buildingName);
			building.setBuildingStatus(buildingStatus);
			building.setBuildingCalName(buildingCalName);
			building.setBuildingCalUrl(buildingCalUrl);
			//building.setAdmin(admin);
	

			String query = "INSERT INTO tomcatdb.building (buildingID, buildingName, buildingStatus, buildingCalName, buildingCalUrl, Admin_adminID)VALUES ('"+building.getBuildingID()+"', '"+building.getBuildingName()+"', '"+building.getBuildingStatus()+"', '"+building.getBuildingCalName()+"', '"+building.getBuildingCalUrl()+"', '1');";
			

			
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


	
	
	
}


