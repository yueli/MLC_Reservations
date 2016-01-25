//MLC USER MODEL

/**
 * 
 */
package model;

public class User {

	private int id;
	private String loginName;
	private String password;
	private String userName;
	private String userEmail;
	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int id, String loginName, String password, String userName, String email) {
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.userName = userName;
		this.userEmail = email;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param username the username to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the user_name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the user_email
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param user_email the user_email to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}

