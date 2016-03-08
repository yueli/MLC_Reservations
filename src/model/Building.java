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
	private Admin admin = new Admin();
	private String buildingQRName; //Ginger added this for QR checking in - needed on the url sent to QR login to check reservation
	
	/**
	 * 
	 * @param buildingID
	 * @param buildingName
	 * @param buildingStatus
	 * @param buildingCalName
	 * @param buildingCalUrl
	 * @param admin
	 */
	public Building(int buildingID, String buildingName, int buildingStatus, String buildingCalName,
			String buildingCalUrl, Admin admin) {
		this.buildingID = buildingID;
		this.buildingName = buildingName;
		this.buildingStatus = buildingStatus;
		this.buildingCalName = buildingCalName;
		this.buildingCalUrl = buildingCalUrl;
		this.admin = admin;
	}
	
	/**
	 * 
	 * @param buildingName
	 * @param buildingStatus
	 * @param buildingCalName
	 * @param buildingCalUrl
	 * @param admin
	 */
	public Building(String buildingName, int buildingStatus, String buildingCalName,
			String buildingCalUrl, Admin admin) {
		this.buildingName = buildingName;
		this.buildingStatus = buildingStatus;
		this.buildingCalName = buildingCalName;
		this.buildingCalUrl = buildingCalUrl;
		this.admin = admin;
	}
	
	/**
	 * 
	 * @param buildingName
	 * @param buildingStatus
	 * @param admin
	 */
	public Building(String buildingName, int buildingStatus, Admin admin) {
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
	public Admin getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getBuildingQRName() {
		return buildingQRName;
	}

	public void setBuildingQRName(String buildingQRName) {
		this.buildingQRName = buildingQRName;
	}
	
}
