/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 
 * @author Brian Olaogun
 * Class used for SQL database connection
 */
public class DbConnect {
	// Fields
	private String url;
	private String database;
	private String username;
	private String password;
	
	// Constructors
	public DbConnect(){
		this.url = "";
		this.database = "";
		this.username = "";
		this.password = "";
	}
	
	/**
	 * 
	 * @param url URL of the connector and host name
	 * @param database name of the database
	 * @param username user name of a user on the database
	 * @param password password of a user on the database
	 */
	public DbConnect(String url, String database, String username, String password){
		this.url = url;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @return Driver Manager used in connection assignment.  This contains the url, database, username, and password to connect to the SQL database.
	 */
	public Connection credentials(){	
		
		try {
			return DriverManager.getConnection(this.url + this.database, this.username, this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return Driver Manager used in connection assignment.  This contains the url, database, username, and password to connect to the SQL database.
	 */
	public static Connection devCredentials(){
		String url = "jdbc:mysql://localhost:3306/tomcatdb";
		//String username = "tomcatuser";
		//String password = "bu11fr0g";
	
		String username = "root";
		String password = "";
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 
	 * @return String of the url redirect if the session is null.
	 */
	public static String urlRedirect(){
		return "http://ebus.terry.uga.edu:8080/MLC_Reservations";
	}
	
}
