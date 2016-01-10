/**
 * 
 */
package model;

/**
 * @author Brian Olaogun
 *
 */
public class Message {
	private String message;
	
	public Message(){
		this.message = "";
	}
	
	public Message (String message){
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
