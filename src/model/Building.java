package model;

/**
 * 
 * @author Brian Olaogun
 *
 */

public class Building {
	// Fields
	private int buildingID;
	private String buildingName;
	private int buildingStatus;
	private String buildingCalName;
	private String buildingCalUrl;
	private String admin;
	private String buildingQRName; //Ginger added this for QR checking in - needed on the url sent to QR login to check reservation
	
	/**
	 * 
	 * @param buildingID the building table record ID
	 * @param buildingName the name of the building
	 * @param buildingStatus 0 if offline and 1 if online
	 * @param buildingCalName the name of the google calendar
	 * @param buildingCalUrl URL of the google calenadar
	 * @param admin the admin record ID
	 * @param buildingQRName the QR code name of the building
	 */
	//public Building(int buildingID, String buildingName, int buildingStatus, String buildingCalName,
	//		String buildingCalUrl, String admin, String buildingQRName) {
	public Building(int buildingID, String buildingName, int buildingStatus, String buildingCalName,
			String buildingCalUrl, String admin, String buildingQRName) { // Ginger made this change - not sure needed
		this.buildingID = buildingID;
		this.buildingName = buildingName;
		this.buildingStatus = buildingStatus;
		this.buildingCalName = buildingCalName;
		this.buildingCalUrl = buildingCalUrl;
		this.admin = admin;
	}
	
	/**
	 * 
	 * @param buildingName the name of the building
	 * @param buildingStatus of if offline and 1 if online
	 * @param buildingCalName the name of the google calendar
	 * @param buildingCalUrl URL of the google calendar
	 * @param admin the admin record ID
	 */
	public Building(String buildingName, int buildingStatus, String buildingCalName,
			String buildingCalUrl, String admin) {
		this.buildingName = buildingName;
		this.buildingStatus = buildingStatus;
		this.buildingCalName = buildingCalName;
		this.buildingCalUrl = buildingCalUrl;
		this.admin = admin;
	}
	
	/**
	 * 
	 * @param buildingName the name of the building
	 * @param buildingStatus 0 for offline, 1 for online
	 * @param admin the admin record ID
	 */
	public Building(String buildingName, int buildingStatus, String admin) {
		this.buildingName = buildingName;
		this.buildingStatus = buildingStatus;
		this.admin = admin;
	}
	
	public Building(){
		this.buildingID = 0;
		this.buildingName = null;
		this.buildingStatus = 0;
		this.buildingCalName = null;
		this.buildingCalUrl = null;
	}

	/**
	 * @return the buildingID
	 */
	public int getBuildingID() {
		return buildingID;
	}

	/**
	 * @param buildingID the buildingID to set
	 */
	public void setBuildingID(int buildingID) {
		this.buildingID = buildingID;
	}

	/**
	 * @return the buildingName
	 */
	public String getBuildingName() {
		return buildingName;
	}

	/**
	 * @param buildingName the buildingName to set
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	/**
	 * @return the buildingStatus
	 */
	public int getBuildingStatus() {
		return buildingStatus;
	}

	/**
	 * @param buildingStatus the buildingStatus to set
	 */
	public void setBuildingStatus(int buildingStatus) {
		this.buildingStatus = buildingStatus;
	}

	/**
	 * @return the buildingCalName
	 */
	public String getBuildingCalName() {
		return buildingCalName;
	}

	/**
	 * @param buildingCalName the buildingCalName to set
	 */
	public void setBuildingCalName(String buildingCalName) {
		this.buildingCalName = buildingCalName;
	}

	/**
	 * @return the buildingCalUrl
	 */
	public String getBuildingCalUrl() {
		return buildingCalUrl;
	}

	/**
	 * @param buildingCalUrl the buildingCalUrl to set
	 */
	public void setBuildingCalUrl(String buildingCalUrl) {
		this.buildingCalUrl = buildingCalUrl;
	}

	/**
	 * @return the admin
	 */
	public String getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getBuildingQRName() {
		return buildingQRName;
	}

	public void setBuildingQRName(String buildingQRName) {
		this.buildingQRName = buildingQRName;
	}
	
}
