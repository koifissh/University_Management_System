package model;

import java.util.Date;

public class Message {
	private int id;
	private int sender_id;
	private int recipient_id;
	private String course_code;
	private String subject;
	private String message;
	private Date timestamp;
	private String status; // read/unread

	public Message() {
	}

	public Message(int id, int senderId, int recipientID, String courseCode, String subject, String message,
			Date timestamp, String status) {

		this.id = id;
		this.sender_id = senderId;
		this.recipient_id = recipientID;
		this.message = message;
		this.timestamp = timestamp;
		this.status = status;

	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public int getSenderId() {
		return sender_id;
	}

	
	public void setSenderId(int senderId) {
		this.sender_id = senderId;
	}

	
	public int getRecipientId() {
		return recipient_id;
	}

	
	public void setRecipientId(int recipientId) {
		this.recipient_id = recipientId;
	}

	
	public String getCourseCode() {
		return course_code;
	}

	
	public void setCourseCode(String courseCode) {
		this.course_code = courseCode;
	}

	
	public String getSubject() {
		return subject;
	}

	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	public String getMessage() {
		return message;
	}

	
	public void setMessage(String message) {
		this.message = message;
	}

	
	public Date getTimestamp() {
		return timestamp;
	}

	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	
	public String getStatus() {
		return status;
	}

	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Message{" + "id=" + id + ", senderId=" + sender_id + ", recipientId=" + recipient_id + ", courseCode='"
				+ course_code + '\'' + ", subject='" + subject + '\'' + ", message='" + message + '\'' + ", timestamp="
				+ timestamp + ", status='" + status + '\'' + '}';
	}

}