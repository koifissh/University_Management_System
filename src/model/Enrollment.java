package model;

import java.util.Date;

public class Enrollment {

	// fields
	int id;
	int student_id;
	String course_code;
	Date enrollment_date;

	// constructor
	public Enrollment() {
	}

	public Enrollment(int id, int student_id, String course_code, Date enrollment_date) {
		this.id = id;
		this.student_id = id;

		this.course_code = course_code;
		this.enrollment_date = enrollment_date;
	}

	// setters and getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public Date getEnrollment_date() {
		return enrollment_date;
	}

	public void setEnrollment_date(Date enrollment_date) {
		this.enrollment_date = enrollment_date;
	}

	@Override
	public String toString() {
		return "Course{" + "id='" + id + '\'' + ", student_id='" + student_id + '\'' + ", course_code='" + course_code
				+ '\'' + ", enrollment_date=" + enrollment_date + '}';
	}

}
