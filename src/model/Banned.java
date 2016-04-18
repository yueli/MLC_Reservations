package model;

public class Banned {
	
		private int banID;
		private int studentID;
		private int adminID;
		private String banStart;
		private String banEnd;
		private int penaltyCount;
		private String description;
		private int status;
		
		public Banned(){
			
		}
		
		public Banned(int banID, int studentID, int adminID, String banStart, String banEnd, int penaltyCount,
				String description, int status) {
			super();
			this.banID = banID;
			this.studentID = studentID;
			this.adminID = adminID;
			this.banStart = banStart;
			this.banEnd = banEnd;
			this.penaltyCount = penaltyCount;
			this.description = description;
			this.status = status;
		}
		
		public Banned(int studentID, int adminID, String banStart, String banEnd, int penaltyCount,
				String description, int status) {
			super();
			this.studentID = studentID;
			this.adminID = adminID;
			this.banStart = banStart;
			this.banEnd = banEnd;
			this.penaltyCount = penaltyCount;
			this.description = description;
			this.status = status;
		}
		
		public int getBanID() {
			return banID;
		}
		public void setBanID(int banID) {
			this.banID = banID;
		}
		public int getStudentID() {
			return studentID;
		}
		public void setStudentID(int studentID) {
			this.studentID = studentID;
		}
		public int getAdminID() {
			return adminID;
		}
		public void setAdminID(int adminID) {
			this.adminID = adminID;
		}
		public String getBanStart() {
			return banStart;
		}
		public void setBanStart(String banStart) {
			this.banStart = banStart;
		}
		public String getBanEnd() {
			return banEnd;
		}
		public void setBanEnd(String banEnd) {
			this.banEnd = banEnd;
		}
		public int getPenaltyCount() {
			return penaltyCount;
		}
		public void setPenaltyCount(int penaltyCount) {
			this.penaltyCount = penaltyCount;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "Banned [banID=" + banID + ", studentID=" + studentID + ", adminID=" + adminID + ", banStart="
					+ banStart + ", banEnd=" + banEnd + ", penaltyCount=" + penaltyCount + ", description="
					+ description + ", status=" + status + "]";
		}
		
		
		
			
		
}
