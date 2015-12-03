package model;

public class Admin {
	// Fields
	private int adminID;
	private String adminMyID;
	private String fname;
	private String lname;
	private String role;
	private int adminStatus;
	private int cantBeDeleted;
	
	// Constructors
	/**
	 * No parameter constructor.
	 */
	public Admin() {
		this.adminID = 0;
		this.adminMyID = null;
		this.fname = null;
		this.lname = null;
		this.role = null;
		this.adminStatus = 0;
		this.cantBeDeleted = 0;
	}
	
	/**
	 * @param adminMyID
	 * @param fname
	 * @param lname
	 * @param role
	 * @param adminStatus
	 * @param cantBeDeleted
	 */
	public Admin(String adminMyID, String fname, String lname, String role, int adminStatus, int cantBeDeleted) {
		this.adminMyID = adminMyID;
		this.fname = fname;
		this.lname = lname;
		this.role = role;
		this.adminStatus = adminStatus;
		this.cantBeDeleted = cantBeDeleted;
	}

	/**
	 * @param adminID
	 * @param adminMyID
	 * @param fname
	 * @param lname
	 * @param role
	 * @param adminStatus
	 * @param cantBeDeleted
	 */
	public Admin(int adminID, String adminMyID, String fname, String lname, String role, int adminStatus,
			int cantBeDeleted) {
		this.adminID = adminID;
		this.adminMyID = adminMyID;
		this.fname = fname;
		this.lname = lname;
		this.role = role;
		this.adminStatus = adminStatus;
		this.cantBeDeleted = cantBeDeleted;
	}
	
	// Accessors and Mutators
	
	/**
	 * @return the adminID
	 */
	public int getAdminID() {
		return adminID;
	}

	/**
	 * @param adminID the adminID to set
	 */
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}

	/**
	 * @return the adminMyID
	 */
	public String getAdminMyID() {
		return adminMyID;
	}

	/**
	 * @param adminMyID the adminMyID to set
	 */
	public void setAdminMyID(String adminMyID) {
		this.adminMyID = adminMyID;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the adminStatus
	 */
	public int getAdminStatus() {
		return adminStatus;
	}

	/**
	 * @param adminStatus the adminStatus to set
	 */
	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}

	/**
	 * @return the cantBeDeleted
	 */
	public int getCantBeDeleted() {
		return cantBeDeleted;
	}

	/**
	 * @param cantBeDeleted the cantBeDeleted to set
	 */
	public void setCantBeDeleted(int cantBeDeleted) {
		this.cantBeDeleted = cantBeDeleted;
	}
	
	
}
