package controller;

import view.StudentView;
import dao.EnrollmentDAO;
import dao.CourseDAO;
import model.Course;
import model.Enrollment;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.List;


//student controller
public class StudentController {
	private final StudentView view;
	private final EnrollmentDAO enrollmentDao;
	private final CourseDAO courseDao;
	private final int studentId;

	// construct the controller
	public StudentController(StudentView view, User model) {
		this.view = view;
		this.enrollmentDao = new EnrollmentDAO();
		this.courseDao = new CourseDAO();
		this.studentId = model.getId();

		initAvailableTab();
		initEnrolledTab();
	}

	//available course tab
	private void initAvailableTab() {
		loadAvailableCourses();
		view.getEnrollBtn().addActionListener(e -> enrollInSelectedCourse());
	}

	//load the available courses in from the model
	private void loadAvailableCourses() {
		DefaultTableModel model = view.getAvailableTableModel();
		model.setRowCount(0);
		List<Course> courses = enrollmentDao.getAvailableCourses();
		for (Course c : courses) {
			model.addRow(new Object[] { c.getCode(), c.getName(), c.getDescription(), c.getCapacity(), c.getStatus() });
		}
	}

	//check user selection and check for duplicate enrollment of the student.
	//we are also checking the count of enrollments compared to the capacity of the class in the DAO when getting
	//the available list of courses so all courses listed in the view are under capacity
	private void enrollInSelectedCourse() {
	    int row = view.getAvailableTable().getSelectedRow();
	    if (row < 0) {
	        JOptionPane.showMessageDialog(view, "Please select a course to enroll.", "No Selection",
	                JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Get the course code from the selected row
	    String code = (String) view.getAvailableTable().getValueAt(row, 0);
	    
	    // Prevent duplicate enrollment
	    List<Enrollment> existing = enrollmentDao.getByStudentId(studentId);
	    for (Enrollment enrolled : existing) {
	        if (code.equals(enrolled.getCourse_code())) {
	            JOptionPane.showMessageDialog(view, "You are already enrolled in " + code + ".", "Already Enrolled",
	                    JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	    }
	    
	    Enrollment enrollment = new Enrollment();
	    enrollment.setStudent_id(studentId);
	    enrollment.setCourse_code(code);
	    enrollment.setEnrollment_date(new Date());
	    boolean isCreatedEnrollment = enrollmentDao.createEnrollment(enrollment);
	    if (isCreatedEnrollment) {
	        JOptionPane.showMessageDialog(view, "Enrolled in " + code);
	        loadAvailableCourses();
	        loadEnrolledCourses();
	    } else {
	        JOptionPane.showMessageDialog(view, "Failed to enroll in " + code, "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	//load our enrolled courses tab
	private void initEnrolledTab() {
		loadEnrolledCourses();
		view.getRefreshBtn().addActionListener(e -> loadEnrolledCourses());
		view.getWithdrawBtn().addActionListener(e -> withdrawSelectedEnrollment());
	}

	//load the student's enrolled courses
	private void loadEnrolledCourses() {
		DefaultTableModel model = view.getEnrolledTableModel();
		model.setRowCount(0);
		List<Enrollment> list = enrollmentDao.getByStudentId(studentId);
		for (Enrollment en : list) {
			model.addRow(new Object[] { en.getId(), en.getCourse_code(), en.getEnrollment_date() });
		}
	}

	//withdraw functionality
	private void withdrawSelectedEnrollment() {
	    int row = view.getEnrolledTable().getSelectedRow();
	    if (row < 0) {
	        JOptionPane.showMessageDialog(view, "Please select an enrollment to withdraw.", "No Selection",
	                JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Get the enrollment ID from the selected row
	    int enrollId = (int) view.getEnrolledTable().getValueAt(row, 0);
	    
	    boolean isEnrollmentDeleted = enrollmentDao.deleteEnrollment(enrollId);
	    //call withdraw functionality
	    if (isEnrollmentDeleted) {
	        JOptionPane.showMessageDialog(view, "Withdrawn from enrollment ");
	        loadEnrolledCourses();
	        loadAvailableCourses();
	    } else {
	        JOptionPane.showMessageDialog(view, "Failed to withdraw.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

}
