package helpers;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListUserReservationsQuery {
	private java.sql.Connection connection;
	private ResultSet results;
	

	public ListUserReservationsQuery(String dbName, String uname, String pwd){
		String url = "jdbc:mysql://localhost:3306/" + dbName;
		
		// set up the driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection(url, uname, pwd);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doGetStudentReservations(String currentDate, String time, int roomNumber){
		String query = "SELECT Reservation.reserveID FROM tomcatdb.Reservation, tomcatdb.Rooms WHERE Reservation.reserveStartDate = '" + currentDate + "'" + "AND ((Reservation.reserveStartTime = '" + time + "') OR ('" + time + "' BETWEEN reserveStartTime AND reserveEndTime)) AND Rooms.roomID = Reservation.Rooms_roomID and Rooms.roomNumber = " + roomNumber;

		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in RoomSelectQuery.java: doRoomRead method. Please check connection or SQL statement.");
		} 
	}
}

