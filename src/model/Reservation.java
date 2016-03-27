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
	private int primaryUser;
	private int secondaryUser;
	private int adminID;
	private int roomsID;
	private String reserveStartDate;
	private String reserveEndDate;
	private String reserveStartTime;
	private String reserveEndTime;
	private int hourIncrement;
	private String reserveName;
	private int buildingID;
	private String free;
	
	// Constructors 
	/**
	 * No parameter constructor
	 */
	public Reservation() {
		this.primaryUser = 0;
		this.secondaryUser = 0;
		this.adminID = 0;
		this.roomsID = 0;
		this.reserveStartDate = null;
		this.reserveEndDate = null;
		this.reserveStartTime = null;
		this.reserveEndTime = null;
		this.hourIncrement = 0;
		this.reserveName = null;
		this.buildingID = 0;
		this.free = null;
		
	}
	
	/**
	 * This constructor is used to make user/student reservations
	 * @param primaryUser int primary user ID
	 * @param secondaryUser int secondary user ID
	 * @param roomsID int roomID foreign key
	 * @param reserveStartDate String start date of reservation
	 * @param reserveEndDate String end date of reservation
	 * @param reserveStartTime String start time of reservation
	 * @param reserveEndTime String end time of reservation
	 * @param hourIncrement int how long the reservation is
	 * @param buildingID int building ID foreign key
	 * @param free whether the room is free or not
	 */
	public Reservation(int primaryUser, int secondaryUser, int roomsID, String reserveStartDate,
			String reserveEndDate, String reserveStartTime, String reserveEndTime, int hourIncrement, int buildingID, String free) {
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
	 * This constructor is used to make admin reservations
	 * @param adminID ID of admin who made a reservation
	 * @param roomID int roomID foreign key
	 * @param startDate String start date of reservation
	 * @param endDate String end date of reservation
	 * @param startTime String start time of reservation
	 * @param endTime String end time of reservation
	 * @param hourIncrement int how long the reservation is
	 * @param reserveName String the name of the reservation - for admin reservations only
	 * @param buildingID int building ID foreign key
	 * @param free whether the room has been reserved or not. value is Y/N
	 */
	public Reservation(int adminID, int roomID, String startDate, String endDate, String startTime, String endTime,
			int hourIncrement, String reserveName, int buildingID, String free) {
		// TODO Auto-generated constructor stub
		this.adminID = adminID;
		this.roomsID = roomID;
		this.reserveStartDate = startDate;
		this.reserveEndDate = endDate;
		this.reserveStartTime = startTime;
		this.reserveEndTime = endTime;
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
	 * @param reserveID set reservation ID
	 */
	public void setReserveID(int reserveID) {
		this.reserveID = reserveID;
	}
	
	/**
	 * @return the primaryUser
	 */
	public int getPrimaryUser() {
		return primaryUser;
	}

	/**
	 * @param primaryUser the primaryUser to set
	 */
	public void setPrimaryUser(int primaryUser) {
		this.primaryUser = primaryUser;
	}

	/**
	 * @return the secondaryUser
	 */
	public int getSecondaryUser() {
		return secondaryUser;
	}

	/**
	 * @param secondaryUser the secondaryUser to set
	 */
	public void setSecondaryUser(int secondaryUser) {
		this.secondaryUser = secondaryUser;
	}

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
	 * @return the roomsID
	 */
	public int getRoomsID() {
		return roomsID;
	}

	/**
	 * @param roomsID the roomsID to set
	 */
	public void setRoomsID(int roomsID) {
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
	public int getbuildingID() {
		return buildingID;
	}

	/**
	 * @param buildingID the buildingID to set
	 */
	public void setbuildingID(int buildingID) {
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
