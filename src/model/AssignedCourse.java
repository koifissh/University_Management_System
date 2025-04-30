package model;

public class AssignedCourse {

	// private fields
	private int id;
	private int teacherId;
	private String courseCode;

	// constructor
	public AssignedCourse() {
	}

	public AssignedCourse(int id, int teacherId, String courseCode) {
		this.id = id;
		this.teacherId = teacherId;
		this.courseCode = courseCode;
	}

	// setters and getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	@Override
	public String toString() {
		return "AssignedCourse [id=" + id + ", teacherId=" + teacherId + ", courseCode=" + courseCode + "]";
	}
}