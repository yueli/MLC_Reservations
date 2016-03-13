package model;

/**
 * 
 * @author Brian Olaogun
 *
 */
public class Schedule {
	// fields
	private int scheduleID;
	private int allDayEvent;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String summary;
	private String createdBy;
	private int buildingID;
	
	public Schedule(){
		
	}
	
	public Schedule(int scheduleID, String startDate, String endDate,
			String startTime, String endTime, String summary, String createdBy){
		this.scheduleID = scheduleID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.summary = summary;
		this.createdBy = createdBy;
	}

	/**
	 * @return the scheduleID
	 */
	public int getScheduleID() {
		return scheduleID;
	}

	/**
	 * @param scheduleID the scheduleID to set
	 */
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	/**
	 * @return the allDayEvent
	 */
	public int getAllDayEvent() {
		return allDayEvent;
	}

	/**
	 * @param allDayEvent the allDayEvent to set
	 */
	public void setAllDayEvent(int allDayEvent) {
		this.allDayEvent = allDayEvent;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
}
