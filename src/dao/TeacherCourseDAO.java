package dao;

import model.AssignedCourse;
import my_util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class TeacherCourseDAO {
    private final Connection connect;

  //SQL should throw an error should there be a duplicate being entered since we have the primary keys set
    public TeacherCourseDAO() {
    	//attempt DB connection
        try {
            this.connect = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database: " + e.getMessage(), e);
        }
    }

    //get all assignments in the table
    public List<AssignedCourse> getAllAssignments() {
        String sql = "SELECT id, teacher_id, course_code FROM tb_teacher_courses";
        List<AssignedCourse> list = new ArrayList<>();
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToAssignedCourse(rs));
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting assignments: " + e.getMessage(), e);
        }
        return list;
    }

    //retrieve teacher course assignments for specific teacherID
    public List<AssignedCourse> getByTeacherId(int teacherId) {
        String sql = "SELECT id, teacher_id, course_code FROM tb_teacher_courses WHERE teacher_id = ?";
        List<AssignedCourse> list = new ArrayList<>();
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToAssignedCourse(rs));
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting assignments by teacher: " + e.getMessage(), e);
        }
        return list;
    }

    //get all teacher assignments for one specific course
    public List<AssignedCourse> getByCourseCode(String courseCode) {
        String sql = "SELECT id, teacher_id, course_code FROM tb_teacher_courses WHERE course_code = ?";
        List<AssignedCourse> list = new ArrayList<>();
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, courseCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToAssignedCourse(rs));
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting assignments by course: " + e.getMessage(), e);
        }
        return list;
    }

    // assign course to teacher
    public boolean assignCourse(int teacherId, String courseCode) {
        String sql = "INSERT INTO tb_teacher_courses (teacher_id, course_code) VALUES (?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ps.setString(2, courseCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error assigning course: " + e.getMessage(), e);
        }
    }

    //delete/unassign course assignment
    public boolean unassignById(int assignmentId) {
        String sql = "DELETE FROM tb_teacher_courses WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, assignmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error unassigning course: " + e.getMessage(), e);
        }
    }

    //unassign course by teacher/coursecode selection
    public boolean unassignCourse(int teacherId, String courseCode) {
        String sql = "DELETE FROM tb_teacher_courses WHERE teacher_id = ? AND course_code = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ps.setString(2, courseCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error unassigning course: " + e.getMessage(), e);
        }
    }

    //map resultset row to assignedcourse object
    private AssignedCourse mapRowToAssignedCourse(ResultSet rs) throws SQLException {
        AssignedCourse ac = new AssignedCourse();
        ac.setId(rs.getInt("id"));
        ac.setTeacherId(rs.getInt("teacher_id"));
        ac.setCourseCode(rs.getString("course_code"));
        return ac;
    }
}
