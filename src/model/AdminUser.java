package model;

public class AdminUser {
	
	private int adminID;
	private String adminMyID;
	private String adminFirstName;
	private String adminLastName;
	private String adminRole;
	private int adminStatus;
	private int cantBeDeleted;
	
	public int getAdminID() {
		return adminID;
	}
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
	public String getAdminMyID() {
		return adminMyID;
	}
	public void setAdminMyID(String adminMyID) {
		this.adminMyID = adminMyID;
	}
	public String getAdminFirstName() {
		return adminFirstName;
	}
	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}
	public String getAdminLastName() {
		return adminLastName;
	}
	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}
	public String getAdminRole() {
		return adminRole;
	}
	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}
	public int getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(int adminStatus) {
		this.adminStatus = adminStatus;
	}
	public int getCantBeDeleted() {
		return cantBeDeleted;
	}
	public void setCantBeDeleted(int cantBeDeleted) {
		this.cantBeDeleted = cantBeDeleted;
	}
	
	
	
}
