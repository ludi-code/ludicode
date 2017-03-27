package eu.ludicode.api.dto;

/*
 * 
 * 
 */
public class Feedback {
	private boolean success = true;
	private String message = "";
	private String role = "";
	
	public Feedback() {
	}
	
	public Feedback(boolean success, String message, String role) {
		this.success = success;
		this.message = message;
		this.role=role;
	}

	public Feedback(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setRole(String role) {
		this.role=role;
	}
	public String getRole(){
		return role;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
