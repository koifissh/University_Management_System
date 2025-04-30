package dao;

import model.Course;
import my_util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CourseDAO {
    private final Connection connect;

    public CourseDAO() {
    	
    	//try to make a connection to the database
        try {
            this.connect = DatabaseUtil.getConnection();
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error connecting to database: " + e.getMessage(), e);
        }
    }

    //retrieve all courses
    public List<Course> getAllCourses() {
        String sql = "SELECT code, name, description, max_capacity, status FROM tb_course";
        List<Course> courses = new ArrayList<>();
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(mapRowToCourse(rs));
            }
        } catch (SQLException e) {
        	//output error to console and user
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting courses: " + e.getMessage(), e);
        }
        return courses;
    }

    //find course by code
    public Course getCourseByCode(String code) {
        String sql = "SELECT code, name, description, max_capacity, status FROM tb_course WHERE code = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCourse(rs);
                }
            }
        } catch (SQLException e) {
        	//output error to console and user
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting course by code: " + e.getMessage(), e);
        }
        return null;
    }

    //create a course
    public boolean createCourse(Course course) {
        String sql = "INSERT INTO tb_course (code, name, description, max_capacity, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
        	//create our course
            ps.setString(1, course.getCode());
            ps.setString(2, course.getName());
            ps.setString(3, course.getDescription());
            ps.setInt(4, course.getCapacity());
            ps.setString(5, course.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	//output error to console and user
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error creating course: " + e.getMessage(), e);
        }
    }

 //update course
    public boolean updateCourse(Course course) {
        String sql = "UPDATE tb_course SET name = ?, description = ?, max_capacity = ?, status = ? WHERE code = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, course.getName());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getCapacity());
            ps.setString(4, course.getStatus());
            ps.setString(5, course.getCode());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	//output error to console and user
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error updating course: " + e.getMessage(), e);
        }
    }

   //delete course by code
    public boolean deleteCourse(String code) {
        String sql = "DELETE FROM tb_course WHERE code = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, code);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	//output error to console and user
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error deleting course: " + e.getMessage(), e);
        }
    }

    //map a resultset row to a course object
    private Course mapRowToCourse(ResultSet rs) throws SQLException {
        Course courseMapped = new Course();
        courseMapped.setCode(rs.getString("code"));
        courseMapped.setName(rs.getString("name"));
        courseMapped.setDescription(rs.getString("description"));
        courseMapped.setCapacity(rs.getInt("max_capacity"));
        courseMapped.setStatus(rs.getString("status"));
        return courseMapped;
    }
}
