package model;

/**
 * @author Brian Olaogun
 */
public class Rooms {
	//Fields
	private int roomID;
	private int admin;
	private int building;
	private int roomNumber;
	private int roomFloor;
	private int roomStatus;
	private String qrUrl;
	
	//Constructors
	/**
	 * @param roomID int room primary key
	 * @param adminID int admin foreign key
	 * @param buildingID int building ID foreign key
	 * @param roomNumber int room number
	 * @param roomFloor int room floor
	 * @param roomStatus int status - whether a room is online or not
	 * @param qrUrl the String url for the qr code
	 */
	public Rooms(int roomID, int adminID, int buildingID, int roomNumber, int roomFloor, int roomStatus,
			String qrUrl) {
		this.roomID = roomID;
		this.admin = adminID;
		this.building = buildingID;
		this.roomNumber = roomNumber;
		this.roomFloor = roomFloor;
		this.roomStatus = roomStatus;
		this.qrUrl = qrUrl;
	}

	/**
	 * @param buildingID int building ID of the building
	 * @param roomNumber int room number
	 * @param roomFloor int floor number
	 * @param roomStatus in status - whether the room is online or not.
	 */
	public Rooms(int buildingID, int roomNumber, int roomFloor, int roomStatus) {
		this.building = buildingID;
		this.roomNumber = roomNumber;
		this.roomFloor = roomFloor;
		this.roomStatus = roomStatus;
	}
	
	/**
	 *  No parameter constructor
	 */
	public Rooms() {
		
	}

	// Getters and Setters
	/**
	 * @return the roomID
	 */
	public int getRoomID() {
		return roomID;
	}

	/**
	 * @param roomID the roomID to set
	 */
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	/**
	 * @return the adminID
	 */
	public int getAdminID() {
		return admin;
	}

	/**
	 * @param adminID the adminID to set
	 */
	public void setAdminID(int adminID) {
		this.admin = adminID;
	}

	/**
	 * @return the buildingID
	 */
	public int getBuildingID() {
		return building;
	}

	/**
	 * @param buildingID the buildingID to set
	 */
	public void setBuildingID(int buildingID) {
		this.building = buildingID;
	}

	/**
	 * @return the roomNumber
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * @return the roomFloor
	 */
	public int getRoomFloor() {
		return roomFloor;
	}

	/**
	 * @param roomFloor the roomFloor to set
	 */
	public void setRoomFloor(int roomFloor) {
		this.roomFloor = roomFloor;
	}

	/**
	 * @return the roomStatus
	 */
	public int getRoomStatus() {
		return roomStatus;
	}

	/**
	 * @param roomStatus the roomStatus to set
	 */
	public void setRoomStatus(int roomStatus) {
		this.roomStatus = roomStatus;
	}

	/**
	 * @return the qrUrl
	 */
	public String getQrUrl() {
		return qrUrl;
	}

	/**
	 * @param qrUrl the qrUrl to set
	 */
	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}
	
	
	
}
