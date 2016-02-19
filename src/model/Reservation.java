/**
 * 
 */
package model;

/**
 * @author Brian Olaogun
 *
 */
public class Reservation {
	// Fields
	private int reserveID;
	private User primaryUser;
	private User secondaryUser;
	private Admin adminID;
	private Rooms roomsID;
	private String reserveStartDate;
	private String reserveEndDate;
	private String reserveStartTime;
	private String reserveEndTime;
	private int hourIncrement;
	private String reserveName;
	private Building buildingID;
	private String free;
	
	// Constructors 
	/**
	 * No parameter constructor
	 */
	public Reservation() {
		this.primaryUser = null;
		this.secondaryUser = null;
		this.adminID = null;
		this.roomsID = null;
		this.reserveStartDate = null;
		this.reserveEndDate = null;
		this.reserveStartTime = null;
		this.reserveEndTime = null;
		this.hourIncrement = 0;
		this.reserveName = null;
		this.buildingID = null;
		this.free = null;
		
	}
	
	/**
	 * 
	 * @param primaryUser
	 * @param secondaryUser
	 * @param roomsID
	 * @param reserveStartDate
	 * @param reserveEndDate
	 * @param reserveStartTime
	 * @param hourIncrement
	 * @param buildingID
	 * @param free
	 */
	public Reservation(User primaryUser, User secondaryUser, Rooms roomsID, String reserveStartDate,
			String reserveEndDate, String reserveStartTime, String reserveEndTime, int hourIncrement, Building buildingID, String free) {
		this.primaryUser = primaryUser;
		this.secondaryUser = secondaryUser;
		this.roomsID = roomsID;
		this.reserveStartDate = reserveStartDate;
		this.reserveEndDate = reserveEndDate;
		this.reserveStartTime = reserveStartTime;
		this.reserveEndTime = reserveEndTime;
		this.hourIncrement = hourIncrement;
		this.buildingID = buildingID;
		this.free = free;
	}
	
	/**
	 * 
	 * @param primaryUser
	 * @param secondaryUser
	 * @param adminID
	 * @param roomsID
	 * @param reserveStartDate
	 * @param reserveEndDate
	 * @param reserveStartTime
	 * @param hourIncrement
	 * @param reserveName
	 * @param buildingID
	 */
	public Reservation(User primaryUser, User secondaryUser, Admin adminID, Rooms roomsID,
			String reserveStartDate, String reserveEndDate, String reserveStartTime, String reserveEndTime, int hourIncrement,
			String reserveName, Building buildingID, String free) {
		this.primaryUser = primaryUser;
		this.secondaryUser = secondaryUser;
		this.adminID = adminID;
		this.roomsID = roomsID;
		this.reserveStartDate = reserveStartDate;
		this.reserveEndDate = reserveEndDate;
		this.reserveStartTime = reserveStartTime;
		this.reserveEndTime = reserveEndTime;
		this.hourIncrement = hourIncrement;
		this.reserveName = reserveName;
		this.buildingID = buildingID;
		this.free = free;
		
	}

	// Accessors and Mutators
	/**
	 * @return the reserveID
	 */
	public int getReserveID() {
		return this.reserveID;
	}
	
	/**
	 * 
	 * @param reserveID
	 */
	public void setReserveID(int reserveID) {
		this.reserveID = reserveID;
	}
	
	/**
	 * @return the primaryUser
	 */
	public User getPrimaryUser() {
		return primaryUser;
	}

	/**
	 * @param primaryUser the primaryUser to set
	 */
	public void setPrimaryUser(User primaryUser) {
		this.primaryUser = primaryUser;
	}

	/**
	 * @return the secondaryUser
	 */
	public User getSecondaryUser() {
		return secondaryUser;
	}

	/**
	 * @param secondaryUser the secondaryUser to set
	 */
	public void setSecondaryUser(User secondaryUser) {
		this.secondaryUser = secondaryUser;
	}

	/**
	 * @return the adminID
	 */
	public Admin getAdminID() {
		return adminID;
	}

	/**
	 * @param adminID the adminID to set
	 */
	public void setAdminID(Admin adminID) {
		this.adminID = adminID;
	}

	/**
	 * @return the roomsID
	 */
	public Rooms getRoomsID() {
		return roomsID;
	}

	/**
	 * @param roomsID the roomsID to set
	 */
	public void setRoomsID(Rooms roomsID) {
		this.roomsID = roomsID;
	}

	/**
	 * @return the reserveStartDate
	 */
	public String getReserveStartDate() {
		return reserveStartDate;
	}

	/**
	 * @param reserveStartDate the reserveStartDate to set
	 */
	public void setReserveStartDate(String reserveStartDate) {
		this.reserveStartDate = reserveStartDate;
	}

	/**
	 * @return the reserveEndDate
	 */
	public String getReserveEndDate() {
		return reserveEndDate;
	}

	/**
	 * @param reserveEndDate the reserveEndDate to set
	 */
	public void setReserveEndDate(String reserveEndDate) {
		this.reserveEndDate = reserveEndDate;
	}

	/**
	 * @return the reserveStartTime
	 */
	public String getReserveStartTime() {
		return reserveStartTime;
	}

	/**
	 * @param reserveStartTime the reserveStartTime to set
	 */
	public void setReserveStartTime(String reserveStartTime) {
		this.reserveStartTime = reserveStartTime;
	}

	/**
	 * @return the reserveEndTime
	 */
	public String getReserveEndTime() {
		return reserveEndTime;
	}

	/**
	 * @param reserveEndTime the reserveEndTime to set
	 */
	public void setReserveEndTime(String reserveEndTime) {
		this.reserveEndTime = reserveEndTime;
	}

	/**
	 * @return the hourIncrement
	 */
	public int getHourIncrement() {
		return hourIncrement;
	}

	/**
	 * @param hourIncrement the hourIncrement to set
	 */
	public void setHourIncrement(int hourIncrement) {
		this.hourIncrement = hourIncrement;
	}

	/**
	 * @return the reserveName
	 */
	public String getReserveName() {
		return reserveName;
	}

	/**
	 * @param reserveName the reserveName to set
	 */
	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	/**
	 * @return the buildingID
	 */
	public Building getbuildingID() {
		return buildingID;
	}

	/**
	 * @param buildingID the buildingID to set
	 */
	public void setbuildingID(Building buildingID) {
		this.buildingID = buildingID;
	}

	/**
	 * @return the free
	 */
	public String getFree() {
		return free;
	}

	/**
	 * @param free the free to set
	 */
	public void setFree(String free) {
		this.free = free;
	}

	
}
