/**
 * 
 */
package model;

public class Student {
	// Fields
	private int studentID;
	private String myID;
	private String fname;
	private String lname;
	private String lastLogin;
	
	// Constructors
	/**
	 * no parameter constructor
	 */
	public Student() {
		this.studentID = 0;
		this.myID = null;
		this.fname = null;
		this.lname = null;
		this.lastLogin = null;
	}
	
	/**
	 * 
	 * @param fname
	 * @param lname
	 */
	public Student(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}
	
	/**
	 * 
	 * @param myID
	 * @param fname
	 * @param lname
	 * @param lastLogin
	 */
	public Student(String myID, String fname, String lname, String lastLogin) {
		this.myID = myID;
		this.fname = fname;
		this.lname = lname;
		this.lastLogin = lastLogin;
	}
	
	/**
	 * 
	 * @param studentID
	 * @param myID
	 * @param fname
	 * @param lname
	 * @param lastLogin
	 */
	public Student(int studentID, String myID, String fname, String lname, String lastLogin) {
		this.studentID = studentID;
		this.myID = myID;
		this.fname = fname;
		this.lname = lname;
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the studentID
	 */
	public int getStudentID() {
		return studentID;
	}

	/**
	 * @param studentID the studentID to set
	 */
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	/**
	 * @return the myID
	 */
	public String getMyID() {
		return myID;
	}

	/**
	 * @param myID the myID to set
	 */
	public void setMyID(String myID) {
		this.myID = myID;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * @return the lastLogin
	 */
	public String getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", myID=" + myID + ", fname=" + fname + ", lname=" + lname
				+ ", lastLogin=" + lastLogin + "]";
	}
	
	
}
