package helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import model.Admin;
import model.DbConnect;

public class AdminUserHelper {
	private java.sql.Connection connection;
	private ResultSet results;
	private int reserveID;

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
	
	public String ListAdmins(){
		String table = "";
		System.out.println("AdminUserHelper: ListAdmins");

		table = "<table>";
		
		table += "<tr>";
		table += "<td> Admin ID</td>";
		table += "<td> MyID</td>";
		table += "<td> First Name</td>";
		table += "<td> Last Name</td>";
		table += "<td> Role</td>";
		table += "<td> Status</td>"; 
		table += "</tr>";
			
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
				String cantBeDeleted = this.results.getString("cantBeDeleted");	
				
				System.out.println("AdminUserHelper: ListAdmins: adminID =  " + adminID);
				
				table += "<tr>";
				table += "<td>" + adminID + "</td>";
				table += "<td>" + adminMyID + "</td>";
				table += "<td>" + fname + "</td>";	
				table += "<td>" + lname + "</td>";
				table += "<td>" + role + "</td>";
				table += "<td>" + adminStatus + "</td>";
				
				table += "<td><form action='AdminEditServlet' method = 'post'>" +
						"<input type='hidden' name='adminID' value='" + adminID + "'>" +
						"<input type='submit' value='Edit Admin'>" +
						"</form></td>";			
			
				table += "</tr>";
				
			} //end while
			
			table += "</table>";
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper:  Query = " + query);
		}
		
		return table;
		
	}
	
	public String getAdminInfo(int adminID, Admin adminUser){
		String table = "";
		
		String query = "SELECT * FROM tomcatdb.Admin WHERE adminID = '" + adminID + "'";

		try {
			PreparedStatement ps = this.connection.prepareStatement(query);
			this.results = ps.executeQuery();
			this.results.next();
		
			// grab the results we need to display in form
		

			String adminMyID = this.results.getString("adminMyID");	
			String fname = this.results.getString("fname");	
			String lname = this.results.getString("lname");	
			String role = this.results.getString("role");	
			String adminStatus = this.results.getString("adminStatus");	
			String cantBeDeleted = this.results.getString("cantBeDeleted");	

			// create an HTML form with this information
			
			table += "<form action='AdminSaveServlet' method = 'post'>";
			
			table += "First name:<br>";
			table +=  "<input type='text' name = 'fname' value = '" + fname + "'>";
			table += "<br />";
			
			table += "Last name:<br>";
			table +=  "<input type='text' name = 'lname' value = '" + lname + "'>";
			table += "<br />";
			
			table += "MyID:<br>";
			table +=  "<input type='text' name = 'adminMyID' value = '" + adminMyID + "'>";
			table += "<br />";
			
			table += "Role:<br>";

			// pull down of all the roles based on the current admin user's roles
			String adminUserRole = adminUser.getRole();
			System.out.println("AdminUserHelper: getAdminInfo role = " + adminUserRole);
	
			// if admin user role = super
			// then list all the roles w/ the current told being the default
			
			table += "<select name='role'>";
			
			// admin making changes is a super user they can change the role to anything
			
			if (Objects.equals("S", adminUserRole)){ // the logged in admin's role is super
				if (Objects.equals("S", role)){
					table += "<option value='S' selected>Super</option>";
					table += "<option value='A'>Admin</option>";
					table += "<option value='V'>View Only</option>";
					
				}else if (Objects.equals("A", role)){ // role is admin
					table += "<option value='S'>Super</option>";
					table += "<option value='A' selected>Admin</option>";
					table += "<option value='V'>View Only</option>";
					
				}else{ // role is view only
					table += "<option value='S'>Super</option>";
					table += "<option value='A'>Admin</option>";
					table += "<option value='V' selected>View Only</option>";
				}
				
			}else{ // the admin making the changes is an admin only so can't set anyone to a super user
				
				if (Objects.equals("S", role)){
					table += "<option disabled value='S' selected>Super</option>";
					
				}else if (Objects.equals("A", role)){ // role is admin
					table += "<option disabled value='S'>Super</option>";
					table += "<option value='A' selected>Admin</option>";
					table += "<option value='V'>View Only</option>";
					
				}else{ // role is view only
					table += "<option disabled value='S'>Super</option>";
					table += "<option value='A'>Admin</option>";
					table += "<option value='V' selected>View Only</option>";
				}				
				
			}
			
			table += "</select>";
			table += "<br />";
			
			table += "Admin Status:<br>";
			table += "<select name = 'status'>";
			
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
					
			table += "<input type = 'submit' value = 'Save'>";
			table += "</form>";
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper getAdminInfo:  Query = " + query);
		}
		
		return table;
		
	}
	
	public void updateAdmin(int adminRecordID, String fname, String lname, String adminMyID, String role, String status) {
//HERE SUNDAY NIGHT

		String query = "Update tomcatdb.Admin SET adminMyID = '" + adminMyID + "', " +
				"fname = '" + fname + "'," + 
				"lname = '" + lname + "'," + 
				"role = '" + role + "'," + 		
				"status = '" + status + "' " + 
				"WHERE adminID = '" + adminRecordID + "'";
		
		
			try {
				PreparedStatement ps = this.connection.prepareStatement(query);		
				ps.executeUpdate();
				this.results.next();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("***Error in AdminUserHelper updateAdmin:  Query = " + query);
		}
		
		return;
		
	}
	
	
	
} //end class
