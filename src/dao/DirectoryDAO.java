package dao;

import model.User;
import my_util.DatabaseUtil;
import my_util.Security;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DirectoryDAO {
    private final Connection connect;

    public DirectoryDAO() {
    	//try connection to database
        try {
            this.connect = DatabaseUtil.getConnection();
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error connecting to database: " + e.getMessage(), e);
        }
    }

   //get all users in the database
    public List<User> getAllUsers() {
        String sql = "SELECT id, first_name, last_name, email, password, role_type FROM tb_user";
        List<User> users = new ArrayList<>();
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User u = mapRowToUser(rs);
                users.add(u);
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting all users: " + e.getMessage(), e);
        }
        return users;
    }
    
    
    //get a specific user by userID
    public User getUserById(int userId) {
        String sql = "SELECT id, first_name, last_name, email, role_type FROM tb_user WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setRoleType(rs.getString("role_type"));
                    return user;
                } else {
                    return null;  //no user found
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user by ID: " + e.getMessage(), e);
        }
    }

    //get all users by specific role
    public List<User> getUsersByRole(String role) {
        String sql = "SELECT id, first_name, last_name, email, password, role_type FROM tb_user WHERE role_type = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(mapRowToUser(rs));
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting users by role: " + e.getMessage(), e);
        }
        return users;
    }

    //create a new user
    public boolean createUser(User user, String plainPassword) {
        String sql = "INSERT INTO tb_user (first_name, last_name, email, password, role_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, Security.hashPassword(plainPassword)); //we hash our password using the util
            ps.setString(5, user.getRoleType());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    //update user info
    public boolean updateUser(User user) {
        String sql = "UPDATE tb_user SET first_name = ?, last_name = ?, email = ?, role_type = ? WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRoleType());
            ps.setInt(5, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    //delete user
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM tb_user WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    //map our resultset object to the user object
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User userMapped = new User();
        userMapped.setId(rs.getInt("id"));
        userMapped.setFirstName(rs.getString("first_name"));
        userMapped.setLastName(rs.getString("last_name"));
        userMapped.setEmail(rs.getString("email"));
        userMapped.setPassword(rs.getString("password"));
        userMapped.setRoleType(rs.getString("role_type"));
        return userMapped;
    }
    
    
    //get the user detail by email
    public User getUserByEmail(String email) {
        String sql = "SELECT id, first_name, last_name, email, password, role_type "
                   + "FROM tb_user WHERE email = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting user by email: " + e.getMessage(), e);
        }
        return null;
    }
    
    
    
    //authenticate user login
    public User authenticateUser(String email, String hashedPassword) {
        String query = "SELECT * FROM tb_user WHERE email = ?";
        ResultSet rs = null;
        try (PreparedStatement stm = connect.prepareStatement(query)) {
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                //compare the provided hashed password with stored hash
                if (hashedPassword.equals(storedHashedPassword)) {
                    //if passwords match, create and return User object with data from database
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setRoleType(rs.getString("role_type"));
                    //don't set the password in the returned object for security
                    return user;
                }
            }
            //authentication failed
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Database error during authentication: " + e.getMessage(), e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
        }
    }
    
    //change the password
    public boolean changePassword(int userId, String currentPass, String newPass) {
        //get the stored password
        String sql = "SELECT password FROM tb_user WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
            	//no such user check
                if (!rs.next()) {
                    return false;
                }
                String storedHash = rs.getString("password");
                //verify our old password
                String currentHash = Security.hashPassword(currentPass);
                if (!currentHash.equals(storedHash)) {
                    return false; //old password didnt match
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error verifying current password: " + e.getMessage(), e);
        }

        //update the old password to the new password
        String updateSql = "UPDATE tb_user SET password = ? WHERE id = ?";
        try (PreparedStatement ps = connect.prepareStatement(updateSql)) {
            ps.setString(1, Security.hashPassword(newPass));
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating password: " + e.getMessage(), e);
        }
    }
}
