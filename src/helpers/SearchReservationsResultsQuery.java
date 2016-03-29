package helpers;
//**By Ronnie Xu~****/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


import model.DbConnect;
import model.Reservation;

public class SearchReservationsResultsQuery {
	

	
	// initialize fields
	private Connection connection;
	private ResultSet results;

	private ResultSet resultsRooms;
	public String startDate = "";
	public String endDate = "";
	public String startTime = "";
	public String endTime = "";
	
			
	/**
	 * Created by Ronnie Xu
	 */
	public SearchReservationsResultsQuery() {

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

	//Method to get all rooms in building
	public ArrayList<Integer> getAllRooms(int buildingid){
		String query = "SELECT rooms.roomID, rooms.Building_buildingID FROM tomcatdb.rooms WHERE rooms.Building_buildingID ='"+buildingid+"' ORDER BY rooms.roomNumber";
		System.out.println("getAllRooms:");
		System.out.println(query);
		
		//Get all rooms in building
		ArrayList<Integer> roomsArray = new ArrayList<Integer>();
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.resultsRooms = ps.executeQuery();
			
			while(resultsRooms.next()){
				//Create Array of all reservations 
				int roomid = resultsRooms.getInt("rooms.roomID");
				roomsArray.add(roomid);
			}			
		}catch(SQLException e) {
			e.printStackTrace();	
		}
	return roomsArray;		
	}
	
	
	//User Input for Results
	public void doRead(int buildingid, String beginDate, String beginTime, String endDate, String endTime, int hourIncrement ){
		String query = "SELECT reservations.Building_buildingID,  reservations.Rooms_roomID, reservations.hourIncrement, reservations.reserveStartDate, reservations.reserveEndDate, reservations.reserveStartTime, reservations.reserveEndTime"
				+ " FROM tomcatdb.reservations WHERE reservations.Building_buildingID ='"+buildingid+"' AND "
				+ "reservations.reserveStartDate>='"+ beginDate +"' AND "
				+ "reservations.reserveendDate<='"+ endDate +"' AND "
				+ "reservations.reserveStartTime>='"+ beginTime +"' AND "
				+ "reservations.reserveEndTime<='"+ endTime +"' "
				+ "ORDER BY reservations.reserveStartDate, reservations.reserveStartTime";	
		System.out.println("doRead "+ query);
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in BuildingListQuery.java: doRoom method. Please check connection or SQL statement: " + query);
		} 
	}
	
	

	
	public ArrayList<Reservation> getReservationList(){
		//ArrayList of all Reservations
			ArrayList<Reservation> reservationAL = new ArrayList<Reservation>();
		try {
			while(results.next()){
				//Create Reservation Objects
				Reservation reservation = new Reservation();	
					//Get Results Values
						int buildingid = results.getInt("Building_buildingID");
						int roomid = results.getInt("Rooms_roomID");
						String beginDate = results.getString("reserveStartDate");
						String beginTime = results.getString("reserveStartTime");
						String endDate = results.getString("reserveEndDate");
						String endTime = results.getString("reserveEndTime");
						int hourIncrement = results.getInt("hourIncrement");
				//Set Object
					reservation.setbuildingID(buildingid);
					reservation.setRoomsID(roomid);
					reservation.setReserveStartDate(beginDate);
					reservation.setReserveStartTime(beginTime);
					reservation.setReserveEndDate(endDate);
					reservation.setReserveEndTime(endTime);
					reservation.setHourIncrement(hourIncrement);
				reservationAL.add(reservation);
				}
			}
			catch(SQLException e) {
				e.printStackTrace();	
			}
		//Move Cursor back to first
		try {
			results.first();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reservationAL;
	}
	
	
	public String getHTMLTable(ArrayList<Integer> roomsArrayList, ArrayList<Reservation> reservationArrayList, String sDate , String sTime, String eDate, String eTime, int hrIncrement) throws ParseException{ 
		//Return tables of open Reservations
		
		
		//Intalize Parameters
			ArrayList<Integer> roomsAL = new ArrayList<Integer>();
			roomsAL = roomsArrayList;
	
			ArrayList<Reservation> reservationAL= new ArrayList<Reservation>();
			reservationAL = reservationArrayList;
			
			sTime = sTime.concat(":00");
			eTime = eTime.concat(":00");
			
			//if no results

			//How many columns for each hour? by begin time - end time
					
					SimpleDateFormat sdf = new SimpleDateFormat("H");
					
					java.util.Date bt = sdf.parse(sTime);
					java.util.Date et = sdf.parse(eTime);
					
					Calendar calendar1 = GregorianCalendar.getInstance();
					Calendar calendar2 = GregorianCalendar.getInstance();
					
					// creates a new calendar instance
					calendar1.setTime(bt);   // assigns calendar to given date
					calendar2.setTime(et);   // assigns calendar to given date
					
					int hourbt = calendar1.get(Calendar.HOUR_OF_DAY);
					int houret = calendar2.get(Calendar.HOUR_OF_DAY);
				
					//GIVE user extra hour on search - for possible minutes
					houret++;
	
		String table = "";
			  
			//Set date to 0
			String newDate = "0000-00-00";
			int closeTable = 0;
			
			//Converting Date Format
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date beginDate = sdfDate.parse(sDate);
			java.util.Date endDate = sdfDate.parse(eDate);

			
			//1.Go through every day user chose
			//2.Create table for each date
			//3.Create header for Table
			//4.Fill in the Table
			//5.Cycle to all rooms for the building
			//6.
			
			
			
			 ///1.Go through every day (that the user input)
			 GregorianCalendar gcal = new GregorianCalendar();
			 gcal.setTime(beginDate);
			 while (gcal.getTime().before(endDate)) {
					gcal.add(Calendar.DAY_OF_YEAR, 1);
							//Convert Format
						     String pointDate = sdfDate.format(gcal.getTime());
	
			//2.Create new table for new date
			        if(!newDate.equals(pointDate)){
						//If new date, close table and create new table date
							if(closeTable==1){
							table += "</table>";
							closeTable=0;
							}
							
						//Create new section for new date
						//Set current Date to newDate
							newDate = pointDate;

						//Create New Table
						table += "<table id='' class='display'>";
						table += "<tbody>";
						
						
				 //3. Date for Header for new Date Table
						table += "<tr><td> Date:"+ pointDate +"</td></tr>";
						table += "<tr>";
								//Create Time Columns
								//Leave space for Room #
								table +="<td>RoomID#</td>";
								for(int i=hourbt;i<=houret;i++){
									table +="<td>"+ i +":00</td>";
								}
							table += "</tr>";
				//4. ADD NEW ROWS AFTER HEADER
							
						table += "</tr>";
						
						
				//5.Cycle to all rooms for the building
						
						//Creating OPEN/CLOSE rooms -- ADD ROOMS
						for(int z=0;z<roomsAL.size();z++){
							table += "<tr>";
							table+="<td>"+roomsAL.get(z)+"</td>";
							int tempRoomAL = roomsAL.get(z);
							String tempEndTime="";
							
						
						//Only searching for hours requested	
						for(int i=hourbt;i<=houret;i++){
						
							
								
								//Mark off all reserved spots
								//if empty skip and fill table with Open
								if(reservationAL.size()>0){
								for(int y=0;y<reservationAL.size();y++){
									
									//Modify Format
									if(i<10){tempEndTime = "0"+i+":00:00";}
									else{tempEndTime =i+":00:00";}
									
									
									//Date/Time/Room must match be listed as 
									String tempDate = reservationAL.get(y).getReserveStartDate();
									String tempTime = reservationAL.get(y).getReserveStartTime();
									int tempRoom = reservationAL.get(y).getRoomsID();
									
									int a = newDate.compareTo(tempDate);
									int b = tempTime.compareTo(tempEndTime);
									
									if(a==1 && b==1 && tempRoom==tempRoomAL){
										table +="<td>";
										table +="TAKEN";
										table +="</td>";
										System.out.println("match");
										
									}
									else{
										table +="<td>";
										table +="<a href='#?'>OPEN</a>";
										table +="</td>";
									}
									}
								}
								//Else will fill empty slots instead of skipping
								else{
										table +="<td>";
										table +="<a href='#'>OPEN</a>";
										table +="</td>";
									}
									
								}
						    
						}
						table +="</tr>";
			        }
						table += "</tr>";
						table += "</tbody>";		
						table += "</table>";
						System.out.println(table);
							
						}
			   return table;
	}
}
