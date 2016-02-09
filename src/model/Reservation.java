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
	private int adminReserve;
	private String reserveName;
	private Building buidlingID;
	private Schedule scheduleID;
	
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
		this.adminReserve = 0;
		this.reserveName = null;
		this.buidlingID = null;
		this.scheduleID = null;
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
	 * @param adminReserve
	 */
	public Reservation(User primaryUser, User secondaryUser, Rooms roomsID, String reserveStartDate,
			String reserveEndDate, String reserveStartTime, String reserveEndTime, int hourIncrement, int adminReserve) {
		this.primaryUser = primaryUser;
		this.secondaryUser = secondaryUser;
		this.roomsID = roomsID;
		this.reserveStartDate = reserveStartDate;
		this.reserveEndDate = reserveEndDate;
		this.reserveStartTime = reserveStartTime;
		this.reserveEndTime = reserveEndTime;
		this.hourIncrement = hourIncrement;
		this.adminReserve = adminReserve;
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
	 * @param adminReserve
	 * @param reserveName
	 * @param buidlingID
	 * @param scheduleID
	 */
	public Reservation(User primaryUser, User secondaryUser, Admin adminID, Rooms roomsID,
			String reserveStartDate, String reserveEndDate, String reserveStartTime, int hourIncrement,
			int adminReserve, String reserveName, Building buidlingID, Schedule scheduleID) {
		this.primaryUser = primaryUser;
		this.secondaryUser = secondaryUser;
		this.adminID = adminID;
		this.roomsID = roomsID;
		this.reserveStartDate = reserveStartDate;
		this.reserveEndDate = reserveEndDate;
		this.reserveStartTime = reserveStartTime;
		this.hourIncrement = hourIncrement;
		this.adminReserve = adminReserve;
		this.reserveName = reserveName;
		this.buidlingID = buidlingID;
		this.scheduleID = scheduleID;
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
	 * @return the adminReserve
	 */
	public int getAdminReserve() {
		return adminReserve;
	}

	/**
	 * @param adminReserve the adminReserve to set
	 */
	public void setAdminReserve(int adminReserve) {
		this.adminReserve = adminReserve;
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
	 * @return the buidlingID
	 */
	public Building getBuidlingID() {
		return buidlingID;
	}

	/**
	 * @param buidlingID the buidlingID to set
	 */
	public void setBuidlingID(Building buidlingID) {
		this.buidlingID = buidlingID;
	}

	/**
	 * @return the scheduleID
	 */
	public Schedule getScheduleID() {
		return scheduleID;
	}

	/**
	 * @param scheduleID the scheduleID to set
	 */
	public void setScheduleID(Schedule scheduleID) {
		this.scheduleID = scheduleID;
	}
	
	
}
