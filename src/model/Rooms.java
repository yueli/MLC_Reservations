package model;

/**
 * @author Brian Olaogun
 */
public class Rooms {
	//Fields
	private int roomID;
	private Admin admin;
	private Building building;
	private int roomNumber;
	private int roomFloor;
	private int roomStatus;
	private String qrUrl;
	
	//Constructors
	/**
	 * @param roomID
	 * @param adminID
	 * @param buildingID
	 * @param roomNumber
	 * @param roomFloor
	 * @param roomStatus
	 * @param qrUrl
	 */
	public Rooms(int roomID, Admin adminID, Building buildingID, int roomNumber, int roomFloor, int roomStatus,
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
	 * @param buildingID
	 * @param roomNumber
	 * @param roomFloor
	 * @param roomStatus
	 */
	public Rooms(Building buildingID, int roomNumber, int roomFloor, int roomStatus) {
		this.building = buildingID;
		this.roomNumber = roomNumber;
		this.roomFloor = roomFloor;
		this.roomStatus = roomStatus;
	}
	
	/**
	 *  No parameter constructor
	 */
	public Rooms() {
		this.roomID = 0;
		this.admin = new Admin();
		this.building = new Building();
		this.roomNumber = 0;
		this.roomFloor = 0;
		this.roomStatus = 0;
		this.qrUrl = "";
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
	public Admin getAdminID() {
		return admin;
	}

	/**
	 * @param adminID the adminID to set
	 */
	public void setAdminID(Admin adminID) {
		this.admin = adminID;
	}

	/**
	 * @return the buildingID
	 */
	public Building getBuildingID() {
		return building;
	}

	/**
	 * @param buildingID the buildingID to set
	 */
	public void setBuildingID(Building buildingID) {
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
