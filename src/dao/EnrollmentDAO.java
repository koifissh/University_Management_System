package dao;

import model.Enrollment;
import model.Course;
import my_util.DatabaseUtil;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollmentDAO {
    private final Connection connect;

    //SQL should throw an error should there be a duplicate being entered since we have the primary keys set
    public EnrollmentDAO() {
    	
    	//attempt DB connection
        try {
            this.connect = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "SQL Error: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Error connecting to database: " + e.getMessage(), e);
        }
    }

    //get all enrollments
    public List<Enrollment> getAllEnrollments() {
        String sql = "SELECT id, student_id, course_code, enrollment_date FROM tb_enrollment";
        List<Enrollment> list = new ArrayList<>();
        try (Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToEnrollment(rs));
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting enrollments: " + e.getMessage(), e);
        }
        return list;
    }

    //get enrollments for specific student id
    public List<Enrollment> getByStudentId(int studentId) {
        String sql = "SELECT id, student_id, course_code, enrollment_date FROM tb_enrollment WHERE student_id = ?";
        List<Enrollment> list = new ArrayList<>();
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting enrollments by student: " + e.getMessage(), e);
        }
        return list;
    }

    //get all enrollments for a specific course
    public List<Enrollment> getByCourseCode(String courseCode) {
        String sql = "SELECT id, student_id, course_code, enrollment_date FROM tb_enrollment WHERE course_code = ?";
        List<Enrollment> list = new ArrayList<>();
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, courseCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting enrollments by course: " + e.getMessage(), e);
        }
        return list;
    }

    //get all classes that are not full
    public List<Course> getAvailableCourses() {
        String sql =
            "SELECT c.code, c.name, c.description, c.max_capacity, c.status " +
            "FROM tb_course c " +
            "LEFT JOIN tb_enrollment e ON c.code = e.course_code " +
            "WHERE c.status = 'active' " +
            "GROUP BY c.code, c.name, c.description, c.max_capacity, c.status " +
            "HAVING COUNT(e.student_id) < c.max_capacity";
        List<Course> courses = new ArrayList<>();
        try (Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Course course = new Course();
                course.setCode(rs.getString("code"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setCapacity(rs.getInt("max_capacity"));
                course.setStatus(rs.getString("status"));
                courses.add(course);
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting available courses: " + e.getMessage(), e);
        }
        return courses;
    }


    //create new class enrollment
    public boolean createEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO tb_enrollment (student_id, course_code, enrollment_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, enrollment.getStudent_id());
            ps.setString(2, enrollment.getCourse_code());
            ps.setDate(3, new java.sql.Date(enrollment.getEnrollment_date().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error creating enrollment: " + e.getMessage(), e);
        }
    }

    //update existing class enrollment
    public boolean updateEnrollment(Enrollment enrollment) {
        String sql = "UPDATE tb_enrollment SET student_id = ?, course_code = ?, enrollment_date = ? WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, enrollment.getStudent_id());
            ps.setString(2, enrollment.getCourse_code());
            ps.setDate(3, new java.sql.Date(enrollment.getEnrollment_date().getTime()));
            ps.setInt(4, enrollment.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error updating enrollment: " + e.getMessage(), e);
        }
    }

    //delete enrollment by the id
    public boolean deleteEnrollment(int id) {
        String sql = "DELETE FROM tb_enrollment WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error deleting enrollment: " + e.getMessage(), e);
        }
    }

  //map a resultset row to enrollment
    private Enrollment mapRowToEnrollment(ResultSet rs) throws SQLException {
        Enrollment e = new Enrollment();
        e.setId(rs.getInt("id"));
        e.setStudent_id(rs.getInt("student_id"));
        e.setCourse_code(rs.getString("course_code"));
        e.setEnrollment_date(new Date(rs.getDate("enrollment_date").getTime()));
        return e;
    }
}
