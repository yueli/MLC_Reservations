/**
 * @author: Ginger Nix
 *
 * This class contains methods to use with viewing, adding, and editing admin users.
 * 
 **/

package helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import model.Admin;
import model.DbConnect;

public class AdminUserHelper {
	private java.sql.Connection connection;
	private ResultSet results;

	public AdminUserHelper(){
		
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
	 * ListAdmins gets all the admin users and puts them nicely into a table
	 * 
	 * @author: Ginger Nix
	 * @parameters: none
	 * @return: table of all the admin users
	 */
	
	public String ListAdmins(){
		String table = "";
		String adminActiveStatus = "";
		String adminRole = "";
		
		System.out.println("AdminUserHelper: ListAdmins");

		table += "<div align='center'><h2>Admins</h2>";
		table += "<br /><br />";
		table += "<form action='AdminAddServlet' method = 'post'>" +
				"<input class='btn btn-lg btn-red' type='submit' value='Add An Admin'>" +
				"</form>";

		table += "<table id = '' class = 'mdl-data-table' cellspacing = '0' width = '95%'>";
		table += "<thead>";
		table += "<th> MyID</th>";
		table += "<th> First Name</th>";
		table += "<th> Last Name</th>";
		table += "<th> Role</th>";
		table += "<th> Status</th>"; 
		table += "<th>&nbsp;</th>"; 
		table += "</thead>";
		table += "<tbody>";	
		
		String query = "SELECT * FROM tomcatdb.Admin ORDER BY lname";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
			while(this.results.next()){ //go through all records returned	
						
				int adminID = this.results.getInt("adminID");	
				String adminMyID = this.results.getString("adminMyID");	
				String fname = this.results.getString("fname");	
				String lname = this.results.getString("lname");	
				String role = this.results.getString("role");	
				String adminStatus = this.results.getString("adminStatus");	
		
				if (Objects.equals("1", adminStatus)){ // the admin is active
					adminActiveStatus = "Active";
				}else{
					adminActiveStatus = "Inactive";
				}
						
				
				if (Objects.equals("S", role)){ // role is super admin
					adminRole = convertAdminRole("S");
					
				}else if (Objects.equals("A", role)){  // plain ole admin
					adminRole = convertAdminRole("A");
					
				}else if (Objects.equals("V", role)){
					adminRole = convertAdminRole("V"); //  view only privileges 
					
				}else{ // else this is a role we don't know about
					adminRole = convertAdminRole("U");
				}
				
				System.out.println("AdminUserHelper: ListAdmins: adminID for user to list from table=  " + adminID);
				
				
				table += "<tr>";
				table += "<td>" + adminMyID + "</td>";
				table += "<td>" + fname + "</td>";	
				table += "<td>" + lname + "</td>";
				table += "<td>" + adminRole + "</td>";
				table += "<td>" + adminActiveStatus + "</td>";
				
				table += "<td><form action='AdminEditServlet' method = 'post'>" +
						"<input type='hidden' name='adminID' value='" + adminID + "'>" +
						"<input class='btn btn-lg btn-red' type='submit' value='Edit Admin'>" +
						"</form></td>";		
			
				table += "</tr>";
			} //end while
			
			table += "</tbody>";
			
			table += "<tfoot>";
			table += "<tr>";
			table += "<td></td>";
			table += "<td></td>";
			table += "<td></td>";
			table += "<td></td>";
			table += "<td></td>";
			table += "<td></td>";

			table += "</tr>";
			table += "</tfoot>";
			
			table += "</table>";
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper:  Query = " + query);
		}
		
		return table;
		
	}
	
	
	/**
	 * getAdminData gets the record data for the admin recd id passed to it
	 * and returns an Admin object w the data
	 * 
	 * @author: Ginger Nix
	 * @parameter: the admin's record ID
	 * @return: admin object w/ all the data for that admin recd id
	 */
	
	public Admin getAdminData(String adminMyID){
		
		Admin adminUser = new Admin();
		
		String query = "SELECT * FROM tomcatdb.Admin WHERE adminMyID = ? LIMIT 1";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
	
			ps.setString(1, adminMyID);
			this.results = ps.executeQuery();
			
			this.results.next();
			
			// set this object's my id w/ the one that was passed to us
			adminUser.setAdminMyID(adminMyID);
			
			// fill in the admin user's object w/ data from admin user table
			adminUser.setAdminID(this.results.getInt("adminID"));
			adminUser.setFname(this.results.getString("fname"));
			adminUser.setLname(this.results.getString("lname"));
			adminUser.setRole(this.results.getString("role"));
			adminUser.setAdminStatus(this.results.getInt("adminStatus"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper getAdminData:  Query = " + query);
		}
		return adminUser;
		
	}
	
	
	
	
	/**
	 * This method takes the admin to be edited record id and based on the
	 * role of the logged in admin user it creates a form
	 * pre-populated with the admin to be edited data
	 * 	
	 * @author: Ginger Nix
	 * @parameter: the admin's record ID
	 * @return: admin object w/ all the data for that admin recd id
	 */
		
	public String getAdminInfo(int adminID, Admin loggedInAdminUser){

		Admin checkLoggedInAdminUser = new Admin();
		checkLoggedInAdminUser = loggedInAdminUser;

		String table = "";
		
		String query = "SELECT * FROM tomcatdb.Admin WHERE adminID = ? LIMIT 1";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setInt(1, adminID);
			this.results = ps.executeQuery();
			
			this.results.next();
		
			// grab the results we need to display in form
			String adminMyID = this.results.getString("adminMyID");	
			String fname = this.results.getString("fname");	
			String lname = this.results.getString("lname");	
			String role = this.results.getString("role");	
			String adminStatus = this.results.getString("adminStatus");	
		
			// create an HTML form with this information

			table += "<div align='center'><h3>Edit Admin</h3>";
			table += "<br />";
			table += "<form action='AdminSaveServlet' method = 'post'>";
			
			table += "First name:<br>";
			table +=  "<input type='text' id = 'fname' name = 'fname' value = '" + fname + "' required>";
			table += "<br />";
			
			table += "Last name:<br>";
			table +=  "<input type='text' name = 'lname' value = '" + lname + "'required>";
			table += "<br />";
			
			table += "MyID:<br>";
			table +=  "<input type='text' name = 'adminMyID' value = '" + adminMyID + "'required>";
			table += "<br />";
			
			table += "Role:<br>";

			// pull down of all the roles based on logged in admin's role, w/ the role being the default
			
			String adminUserRole = loggedInAdminUser.getRole();
			System.out.println("AdminUserHelper: getAdminInfo role = " + adminUserRole);
	
			// if the admin logged in has a user role = super
			// then list all the roles w/ the current role being the default
			
			table += "<select name='role'required>";
			
			
			// based on the admin user who is logged in, create the pull down list
			// for the user being edited's role
			// if the role of the logged in admin user is View Only, they will never get to this page
			
			// convertAdminRole takes the letter in the role field and makes it human readable
			
			if (Objects.equals("S", adminUserRole)){ // the logged in admin's role is super admin
				if (Objects.equals("S", role)){
					table += "<option value='S' selected>" + convertAdminRole("S") + "</option>";
					table += "<option value='A'>" + convertAdminRole("A") + "</option>";
					table += "<option value='V'>" + convertAdminRole("V") + "</option>";
					
				}else if (Objects.equals("A", role)){ // role is admin
					table += "<option value='S'>" + convertAdminRole("S") + "</option>";
					table += "<option value='A' selected>" + convertAdminRole("A") + "</option>";
					table += "<option value='V'>" + convertAdminRole("V") + "</option>";
					
				}else if (Objects.equals("V", role)){ // role is view only
					table += "<option value='S'>" + convertAdminRole("S") + "</option>";
					table += "<option value='A'>" + convertAdminRole("A") + "</option>";
					table += "<option value='V' selected>" + convertAdminRole("V") + "</option>";
					
				}else{ // else the role is unknown
					table += "<option value='U' selected>" + convertAdminRole("U") + "</option>";
				}
				
			}else{ // the admin making the changes is an admin only so can't set anyone to a super user
				
				if (Objects.equals("S", role)){
					//HERE
					table += "<option disabled value='S' selected>" + convertAdminRole("S") + "</option>";
					
				}else if (Objects.equals("A", role)){ // role is admin
					//HERE
					table += "<option disabled value='S'>" + convertAdminRole("S") + "</option>";
					table += "<option value='A' selected>" + convertAdminRole("A") + "</option>";
					table += "<option value='V'>" + convertAdminRole("V") + "</option>";
					
				}else if (Objects.equals("V", role)){ // role is view only
					table += "<option disabled value='S'>Super</option>";
					table += "<option value='A'>Admin</option>";
					table += "<option value='V' selected>View Only</option>";
					
				}else{ // else the role is unknown
					table += "<option value='U' selected>" + convertAdminRole("U") + "</option>";
				}				
				
			}
					
			table += "</select>";
			table += "<br />";
			
			// based on the admin user being edited's status, list the status with the default selected
			
			table += "Admin Status:<br>";
			table += "<select name = 'status'required>";
			
			// if the admin being edited is active (=1)
			if (Objects.equals("1", adminStatus)){
				table += "<option value='1' selected>Active</option>";
				table += "<option value='0'>Inactive</option>";
				
			}else{// the admin being edited is inactive (=0)
				table += "<option value='1'>Active</option>";
				table += "<option value='0' selected>Inactive</option>";		
			}		
			
			table += "</select>";
			table += "<br />";	
			table += "<br />";
			
			System.out.println("AdminUserHelper: getAdminInfo adminID = " + adminID);
			
					
			table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Save'>";
			table += "<input type = 'hidden' name = 'adminID' value='" + adminID + "'>";
			table += "</form>";
			
			table += "<br />";
			table += "<form action='AdminListServlet' method = 'post'>";
			table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
			table += "</form>";
	
	
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper getAdminInfo:  Query = " + query);
		}
		
		return table;
		
	}
	
	
	/**
	 * This method takes the role letter (like 'A') and converts it to human readable text	(like "Super User")
	 *
	 * @author: Ginger Nix
	 * @parameter: the admin's role in the admin's record (S, A, V, etc)
	 * @return: the admin's role as humn readable text (like "Super User")
	 */
	public String convertAdminRole (String role){
		
		// takes the role and makes it human readable
		String roleDesc = "";
		
		if (Objects.equals("S", role)){ // if the role is super admin
			roleDesc = "Super Admin";
			
		}else if (Objects.equals("A", role)){ // role is admin
			roleDesc = "Admin";
			
		}else if (Objects.equals("V", role)){ //role is view only
			roleDesc = "View Only";
			
		}else{
			roleDesc = "Unknown Role"; // else this is a new one where we don't know the desc
		}
		
		return roleDesc;
	}
	
	
	/**
	 * This method takes the admin's record id and the new info and updates
	 * the admin table
	 * 
	 * @author: Ginger Nix
	 * @parameter: the record id of the admin to update's record
	 * @parameter: admin's first name
	 * @parameter: admin's last name
	 * @parameter: admin's MyID
	 * @parameter: admin's role
	 * @parameter: admin's status
	 * @return: nothing
	 */
	
	public void updateAdminTable(int adminRecordID, String fname, String lname, String adminMyID, String role, int status) {

		String query = "UPDATE tomcatdb.Admin "
					+ "SET adminMyID = ?, " 
					+ "fname = ?, " 
					+ "lname = ?, "
					+ "role = ?, " 		
					+ "adminStatus = ? " 
					+ "WHERE adminID = ? ";
		
		System.out.println("AdminUserHelper updateAdminTable:  Query = " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);		
			ps.setString(1, adminMyID);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setString(4, role);
			ps.setInt(5, status);
			ps.setInt(6, adminRecordID);
			
			ps.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper updateAdmin:  Query = " + query);
		}
		
	}
	
	/**
	 * This method takes the admin user's MyID and checks to see if the admin user 
	 * is in the admin user table
	 * @author: Ginger Nix
	 * @parameter: admin's MyID
	 * @return: true if admin in table, false if admin not in table
	 */
	
	public boolean inAdminUserTable(String myID){
		
		
		String query = "SELECT * from tomcatdb.Admin WHERE adminMyID = ? AND adminStatus = 1 LIMIT 1";
		
		System.out.println("UserHelper.java: inAdminUserTable method. Query = " + query);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			
			ps.setString(1,myID);
			
			this.results = ps.executeQuery();
			if (this.results.next()) {
				return true;
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in UserHelper.java: inAdminUserTable method. Query = " + query);
		}
		
		return false;
	}
	
	/**
	 * This method creates a blank form to add a new admin user	
	 * @author: Ginger Nix
	 * @parameter: loggedInAdminUser object
	 * @return: a form for the admin to add another admin user 
	 */
	
	public String createAddAdminForm(Admin loggedInAdminUser){
		
		String table = "";

		table += "<div align='center'><h3>Add an Admin</h3>";
		table += "<br />";
		
		table += "<form action='AdminAddSaveServlet' method = 'post'>";
		
		table += "First name:<br>";
		table +=  "<input type='text' id='fname' name = 'fname' required>";
		table += "<br />";
		
		table += "Last name:<br>";
		table +=  "<input type='text' name = 'lname' required>";
		table += "<br />";
		
		table += "MyID:<br>";
		table +=  "<input type='text' name = 'adminMyID' required>";
		table += "<br />";
		
		table += "Role:<br>";

		// pull down of all the roles based on logged in admin's role, w/ the role being the default
		
		String adminUserRole = loggedInAdminUser.getRole();
		System.out.println("AdminUserHelper: getAdminInfo role = " + adminUserRole);

		// if the admin logged in has a user role = super
		// then list all the roles w/ the current role being the default
		
		table += "<select name='role' required>";
		
		
		// based on the admin user who is logged in, create the pull down list
		// for the user being added's role
		// if the role of the logged in admin user is View Only, they will never get to this page
		
		// convertAdminRole takes the letter in the role field and makes it human readable
		
		if (Objects.equals("S", adminUserRole)){ // the logged in admin's role is super admin
				table += "<option value='S'>" + convertAdminRole("S") + "</option>";
				table += "<option value='A'>" + convertAdminRole("A") + "</option>";
				table += "<option value='V'>" + convertAdminRole("V") + "</option>";
				
		}else{ // the admin making the changes is an admin only so can't set anyone to a super user
				table += "<option disabled value='S'>" + convertAdminRole("S") + "</option>";
				table += "<option value='A' selected>" + convertAdminRole("A") + "</option>";
				table += "<option value='V'>" + convertAdminRole("V") + "</option>";			
		}
				
		table += "</select>";
		table += "<br />";
				
		table += "Admin Status:<br>";
		table += "<select name = 'status' required>";
		table += "<option value='1' selected>Active</option>";
		table += "<option value='0'>Inactive</option>";	
		table += "</select>";
		table += "<br />";	
		table += "<br />";		
				
		table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Add Admin'>";
		table += "</form>";
		
		table += "<br />";
		table += "<form action='AdminListServlet' method = 'post'>";
		table += "<input class='btn btn-lg btn-red' type = 'submit' value = 'Cancel'>";
		table += "</form>";
				
		return table;
	}

	/**
	 * This method take the admin info and adds them to the admin user's table
	 * @author: Ginger Nix
	 * @parameter: admin to add's MyID
	 * @parameter: admin to add's first name
	 * @parameter: admin to add's last name
	 * @parameter: admin to add's role
	 * @parameter: admin to add's Status
	 */
	public void insertAdminTable(String adminMyID, String fname, String lname, String role, int adminStatus) {
		
		String query = "INSERT INTO tomcatdb.Admin (adminMyID, fname, lname, role, adminStatus) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			ps.setString(1,adminMyID);
			ps.setString(2,fname);
			ps.setString(3,lname);
			ps.setString(4,role);
			ps.setInt(5,adminStatus);
			
			ps.executeUpdate();
			System.out.println("Success in AdminUserHelper.java: insert into table method. Query = " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper.java: insert into table method. Query = " + query);
		}
		
	}	

	/**
	 * This method will take the myID and check to see if
	 * this Admin user already exists in the admin table	
	 * @author: Ginger Nix
	 * @parameter: the admin to check's myID
	 * @return: true if the admin is in the table, false if the admin is not in the table
	 */
	
	public boolean inAdminTable(String myID){
		
		System.out.println("AdminUserHelper inAdminTable: myID = " + myID);
		
		String query = "SELECT * from tomcatdb.Admin WHERE adminMyID = '" + myID + "' LIMIT 1";
		
		System.out.println("AdminUserHelper inAdminTable: query = " + query);
		
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(query);

			this.results = ps.executeQuery();
			

			boolean results = this.results.next();
			System.out.println("Admin User Helper: in admin table: results found " + results + " query = " + query);
			
			if (results) {//the myID is in the user table
				System.out.println("Admin User Helper: in admin table: results found TRUE");
				return true;
			}else{
				System.out.println("Admin User Helper: in admin table: results found FALSE");
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("****Error in Admin UserHelper.java: inAdminTable method. Query = " + query);
		}
		
		return false;
	}

		
	
} //end class
