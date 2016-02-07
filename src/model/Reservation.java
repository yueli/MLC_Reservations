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
	private Student primaryStudent;
	private Student secondaryStudent;
	private Admin adminID;
	private Rooms roomsID;
	private String reserveStartDate;
	private String reserveEndDate;
	private String reserveStartTime;
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
		this.primaryStudent = null;
		this.secondaryStudent = null;
		this.adminID = null;
		this.roomsID = null;
		this.reserveStartDate = null;
		this.reserveEndDate = null;
		this.reserveStartTime = null;
		this.hourIncrement = 0;
		this.adminReserve = 0;
		this.reserveName = null;
		this.buidlingID = null;
		this.scheduleID = null;
	}
	
	/**
	 * 
	 * @param primaryStudent
	 * @param secondaryStudent
	 * @param roomsID
	 * @param reserveStartDate
	 * @param reserveEndDate
	 * @param reserveStartTime
	 * @param hourIncrement
	 * @param adminReserve
	 */
	public Reservation(Student primaryStudent, Student secondaryStudent, Rooms roomsID, String reserveStartDate,
			String reserveEndDate, String reserveStartTime, int hourIncrement, int adminReserve) {
		this.primaryStudent = primaryStudent;
		this.secondaryStudent = secondaryStudent;
		this.roomsID = roomsID;
		this.reserveStartDate = reserveStartDate;
		this.reserveEndDate = reserveEndDate;
		this.reserveStartTime = reserveStartTime;
		this.hourIncrement = hourIncrement;
		this.adminReserve = adminReserve;
	}
	
	/**
	 * 
	 * @param primaryStudent
	 * @param secondaryStudent
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
	public Reservation(Student primaryStudent, Student secondaryStudent, Admin adminID, Rooms roomsID,
			String reserveStartDate, String reserveEndDate, String reserveStartTime, int hourIncrement,
			int adminReserve, String reserveName, Building buidlingID, Schedule scheduleID) {
		this.primaryStudent = primaryStudent;
		this.secondaryStudent = secondaryStudent;
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
	 * @return the primaryStudent
	 */
	public Student getPrimaryStudent() {
		return primaryStudent;
	}

	/**
	 * @param primaryStudent the primaryStudent to set
	 */
	public void setPrimaryStudent(Student primaryStudent) {
		this.primaryStudent = primaryStudent;
	}

	/**
	 * @return the secondaryStudent
	 */
	public Student getSecondaryStudent() {
		return secondaryStudent;
	}

	/**
	 * @param secondaryStudent the secondaryStudent to set
	 */
	public void setSecondaryStudent(Student secondaryStudent) {
		this.secondaryStudent = secondaryStudent;
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
