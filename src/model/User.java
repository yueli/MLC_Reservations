package model;

public class User {

	private int userRecordID;
	private String myID;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	
	/**
	 * 
	 * @return userRecordID
	 */
	public int getUserRecordID() {
		return userRecordID;
	}
	
	public void setUserRecordID(int userRecordID) {
		this.userRecordID = userRecordID;
	}
	
	/**
	 * 
	 * @return myID
	 */
	public String getMyID() {
		return myID;
	}
	
	/**
	 * 
	 * @param myID
	 */
	public void setMyID(String myID) {
		this.myID = myID;
	}
	
	/**
	 * 
	 * @return userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}
	
	/**
	 * 
	 * @param userFirstName
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	
	/**
	 * 
	 * @return userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}
	
	/**
	 * 
	 * @param userLastName
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	/**
	 * 
	 * @return userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	
	 /**
	  * 
	  * @param userEmail
	  */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
