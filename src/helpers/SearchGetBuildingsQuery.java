package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import model.Building;
import model.DbConnect;


//**By Ronnie Xu~****/

public class SearchGetBuildingsQuery {
	
		private Connection connection;
		private ResultSet results;

		/**
		 * 
		 * Connect to database.  This is hard coded in DBConnect.java
		 */
		public SearchGetBuildingsQuery() {
			
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

		public void doRead(){

			String query = "SELECT Building.buildingID, "
					+ "Building.buildingName, "
					+ "Building.buildingStatus, "
					+ "Building.buildingCalName, "
					+ "Building.buildingCalUrl, "
					+ "Building.Admin_adminID "
					+ "FROM tomcatdb.Building WHERE buildingStatus='1'";
			// securely run query
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);
				this.results = ps.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error in BuildingListQuery.java: doRoom method. Please check connection or SQL statement: " + query);
			} 
		}
		
		
		
		public String getHTMLTable(){ 
			//Return table of buildings
			
			String table = "";
				  
			try {
				table += "<table id='' class='display'>";
				table += "<thead>"
						+ "<tr>"
						+ "<th>Openings</th>"
						//+ "<th>Building ID#</th>"
						//+ "<th>Building Name</th>"
						//+ "<th>Building Status</th>"
						+ "</tr>"
						+ "</thead>"
						+ "<tbody>";
				while(results.next()){

					
					String status = "";
					Building building = new Building();
					
					building.setBuildingID(results.getInt("buildingID"));
					building.setBuildingName(results.getString("buildingName"));
					building.setBuildingStatus(results.getInt("buildingStatus"));
					//building.setAdmin(results.getString("admin"));
					
		
					//If open create Row
					if (building.getBuildingStatus() == 1){
						//status = "Online";
						
						// html table for building list
						table += "<tr>";
						table += "<td><center><a href=search?buildingID=" + building.getBuildingID() + "> <button type='submit' value='Edit'>"+building.getBuildingName()+"</button></a></center></td>";
						//table += "<td data-order='" + building.getBuildingID() + "'>";
						//table += building.getBuildingID();
						//table += "</td>";
						//table += "<td data-search='" + building.getBuildingName() + "'>";
						//table += building.getBuildingName();
						//table += "</td>";
						
					} 
					//If building is closed do not show
					else {
						//status = "Offline";
					}	
					
				
					
					table += "</tr>";
				}
				table += "</tbody>";
				table += "</table>";
			}
			catch(SQLException e) {
				e.printStackTrace();	
			}
			
			return table;
		}
		
		
		
		public String getBuildingResults(){
			
			//Get Current Date
			//DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			String startDate = dateFormat.format(d);
			
			GregorianCalendar calendar = new GregorianCalendar();
		    calendar.setTime(d);
		    
		    calendar.add(Calendar.DATE, 14);
		    
		    String endDate = dateFormat.format(calendar.getTime());
		    

		    
		    //Get Time
		    DateFormat timeFormat = new SimpleDateFormat("HH:00");
		    Date time = new Date();   // given date
		    Calendar calendarTime = GregorianCalendar.getInstance(); // creates a new calendar instance
		    calendarTime.setTime(time);   // assigns calendar to given date 
		   
		    String hour = timeFormat.format(calendarTime.getTime());
		    //calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		    //calendar.get(Calendar.HOUR);        // gets hour in 12h format
		    System.out.println("hour: "+ hour);
		   
			
			
			// Create the String for HTML
			String select = "<h2>Please Select Building</h2>";
			select += "<form name='searchForm' action='searchresults' onsubmit='return validateForm()' method='post'>";
			select += "Select building: <select name='buildingid'><br/>";
			// HTML for dropdown list
			try {
				while(this.results.next()){
					// place results in a building object
					Building building = new Building();
					building.setBuildingID(this.results.getInt("buildingID"));
					building.setBuildingName(this.results.getString("buildingName"));
					
					// HTML for dropdown list
					if(this.results.getString(1) != null){
						select += "<option selected='selected' value=" + "'" + building.getBuildingID() + "'" + ">";
						select += building.getBuildingName();
						select += "</option> <br/>";
					} else {
						select += "<option value=" + "'" + building.getBuildingID() + "'" + ">";
						select += building.getBuildingName();
						select += "</option>";
					}
					
				}
				select+= "</select>";
				select += " <br/><br /> ";
				select += "Begin Date: <input name='beginDate' type='date' min='"+startDate+"' max='"+endDate+"' value='"+startDate+"' required><br /> ";
				select += "End Date: <input name='endDate' type='date'  min='"+startDate+"' max='"+endDate+"'value='"+startDate+"' required><br /><br />  ";				
				

				select += "Begin Time: <input name='beginTime' type='time' value = '"+hour+"' required><br /> ";
				select += "End Time: <input name='endTime' type='time' value = '"+hour+"' required ><br /> <br /> ";
				
				select += "Hour Increment: <input name='hourIncrement' type='number' value='1' min='1' max='2'  required><br /><br />  ";
				
				select += "<input class='btn btn-lg btn-red' type=submit name=submit value='Search' required><br /> ";
				select += "</form>";
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			select += "</select>";
			
			return select;
		}
		


}
