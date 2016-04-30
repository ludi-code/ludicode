package eu.ludicode.api.dto;

public class Feedback {
	private boolean success = true;
	private String message = "";
	
	public Feedback() {
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
