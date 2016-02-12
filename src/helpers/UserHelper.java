//MLC USER HELPER

package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.tomcat.jni.Time;

import model.Reservation;
import model.User;


/**
 * This class is used to insert, retrieve, and update users in the user table.
 */
public class UserHelper {
	
	/**
	 * Prepared SQL statement (combats: SQL Injections)
	 */
	private PreparedStatement authenticateUserStatement;
	private Connection connection;
	private ResultSet results;
	//private int numRecords;

	/**
	 * Constructor which makes a connection
	 */
	public UserHelper() {
/*		try {
			//Set up connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tomcatdb", "root", "");
			
			//NEED THIS CODE???
			//Create the preparedstatement(s)
			authenticateUserStatement = conn.prepareStatement("select * from user where username=? and password=?");
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}*/
		
			String url = "jdbc:mysql://localhost:3306/" + "tomcatdb";
			
			// set up the driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				this.connection = DriverManager.getConnection(url, "root", "");
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
	
	/**
	 * Authenticates a user in the database.
	 * @return A user object if successful, null if unsuccessful.
	 */
	public User authenticateUser(String myID, String encryptedPass) {
		User su = new User(); //set to null to return null in case not authenticated
		//su = null; ///GINGER HERE UNCOMMENTED THIS
		
		System.out.println("UserHelper: authentication myID = " + myID); //MyID the student logged in with
		
		boolean valid = false; //assume not valid

		//--------------------------------------------
		//TODO
		// call some authentication server/code to check if UGA student w/ login name and password
		// send it myID and loginPassword
		// get back first name, last name, and email back from authentication server
		// returns w/ data or null
		//--------------------------------------------
		
		//purely for testing, set the valid to true until authentication in place
		valid = true;
		//valid = false;
		
		String studentUserMyID = "ganix";
		String studentUserFirstName = "Ginger";
		String studentUserLastName = "Nix";
		String userEmail = "ganix@uga.edu";
		
		System.out.println("UserHelper: auth userMyID = " + studentUserMyID);
		
		//if true, set user object info and return login user data
		if (valid){
			//User su = new User(); //GINGER UNCOMMENTED THIS
			//put all the student's data into the login user object
			su.setMyID(studentUserMyID);
			su.setUserLastName(studentUserLastName);
			su.setUserFirstName(studentUserFirstName);
			su.setUserEmail(userEmail);	
			System.out.println("UserHelper: auth in valid if userMyID = " + studentUserMyID);
		}		
		System.out.println("UserHelper: auth su = " + su);
		return su; //will be null if user wasn't valid
	}
	
	public boolean inUserTable(String myID){

		System.out.println("**UserHelper inUserTable: myID = " + myID);
		
		String query = "SELECT * from tomcatdb.user WHERE myID = ?";
		
		// securely run query
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1, myID);
			this.results = ps.executeQuery();
			if (this.results.next()) {//the myID is already being used
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in UserHelper.java: inStudentTable method. Query = " + query);
		}
		
		return false;
	}

	public void updateLastLogin(String myID) {
		System.out.println("**UserHelper: updateLastLogin myID = " + myID);
		String today = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		today = dateFormat.format(date);
		System.out.println(dateFormat.format(date)); //2014/08/06  
		System.out.println(today); //2014/08/06  
		
		String query = "Update tomcatdb.user SET user.lastLogin = '" + today + "' WHERE myID = '" + myID + "'";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			ps.executeUpdate();
			System.out.println("Success in UserHelper.java: updateLastLogin method. Query = " + query);
			System.out.println("Success in UserHelper.java: updateLastLogin method. myID = " + myID);
			System.out.println("Success in UserHelper.java: updateLastLogin method. today = " + today);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in UserHelper.java: updateLastLogin method. Query = " + query);
		}
		
	}
	
	public void insertUserTable(String myID, String fname, String lname, String email) {

		System.out.println("uh: insertUserTable");   

		String today = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		today = dateFormat.format(date);
		
		String query = "INSERT INTO tomcatdb.User (myID, fname, lname, email, lastLogin) VALUES ('" + myID + "','" + fname + "','" + lname + 
				"','" + email + "','"+ today + "')";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
		
			ps.executeUpdate();
			System.out.println("Success in UserHelper.java: insert into table method. Query = " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in UserHelper.java: insert into table method. Query = " + query);
		}
		
	}
	
	
	public int getRecordID(String myID){
		int recordID = 0;
		String query = "SELECT userID FROM tomcatdb.User WHERE myID = '" + myID + "'";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
	
			ps.executeQuery();
			System.out.println("Success in UserHelper.java: get record ID method. Query = " + query);
			
			recordID = results.getInt("userID");

		} catch (SQLException e) {
			e.printStackTrace();
		System.out.println("Error in UserHelper.java: get record ID method. Query = " + query);
			}
		
		return recordID;
		
	}
	
}
