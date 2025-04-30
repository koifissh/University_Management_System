package controller;

import view.TeacherView;
import dao.TeacherCourseDAO;
import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.DirectoryDAO;
import model.User;
import model.Course;
import model.AssignedCourse;
import model.Enrollment;

import javax.swing.table.DefaultTableModel;
import java.util.List;



//teacher controller
public class TeacherController {
    private final TeacherView view;
    private final int teacherId;
    private final TeacherCourseDAO teachercourseDao;
    private final CourseDAO courseDao;
    private final EnrollmentDAO enrollDao;
    private final DirectoryDAO directoryDao;

    //construct our controller
    public TeacherController(TeacherView view, User model) {
    	
        this.view = view;
        this.teacherId = model.getId();
        this.teachercourseDao = new TeacherCourseDAO();
        this.courseDao = new CourseDAO();
        this.enrollDao = new EnrollmentDAO();
        this.directoryDao = new DirectoryDAO();

        //load data in from the database into our lists
        loadAssignedCourses();
        loadEnrolledStudents();
    }

    //load the teacher's assigned courses
    private void loadAssignedCourses() {
        DefaultTableModel model = (DefaultTableModel) view.getAssignedCoursesTable().getModel();
        model.setRowCount(0);
        List<AssignedCourse> assignments = teachercourseDao.getByTeacherId(teacherId);
        for (AssignedCourse ac : assignments) {
            Course course = courseDao.getCourseByCode(ac.getCourseCode());
            if (course != null) {
            	//create our course objects
                model.addRow(new Object[]{
                    course.getCode(),
                    course.getName(),
                    course.getDescription(),
                    course.getCapacity(),
                    course.getStatus()
                });
            }
        }
    }

    //load our enrolled students based on the enrollment list and the course code
    private void loadEnrolledStudents() {
        DefaultTableModel model = (DefaultTableModel) view.getEnrolledStudentsTable().getModel();
        model.setRowCount(0);
        // For each assigned course, fetch enrollments then student details
        List<AssignedCourse> assignments = teachercourseDao.getByTeacherId(teacherId);
        for (AssignedCourse assignedCourse : assignments) {
            String courseCode = assignedCourse.getCourseCode();
            List<Enrollment> enrollments = enrollDao.getByCourseCode(courseCode);
            for (Enrollment enrolled : enrollments) {
                User student = directoryDao.getUserById(enrolled.getStudent_id());
                if (student != null) {
                	//create our student objects
                    model.addRow(new Object[]{
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        courseCode
                    });
                }
            }
        }
    }
}
