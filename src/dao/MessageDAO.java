package dao;

import model.Message;
import my_util.DatabaseUtil;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDAO {
    private final Connection conn;

    public MessageDAO() {
    	//attempt DB connection
        try {
            this.conn = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Error connecting to database: " + e.getMessage(), e);
        }
    }


    //get messages for the user both as sender and receiver 
    public List<Message> getMessagesForUser(int userId) {
        String sql = "SELECT id, sender_id, recipient_id, code_code, subject, message, timestamp, status " +
                     "FROM tb_message WHERE sender_id = ? OR recipient_id = ?";
        List<Message> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToMessage(rs));
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error getting messages for user: " + e.getMessage(), e);
        }
        return list;
    }

    //create a new message in the database aka sending the message
    public boolean createMessage(Message message) {
        String sql = "INSERT INTO tb_message "
                   + " (sender_id, recipient_id, code_code, subject, message, timestamp, status) "
                   + " VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, message.getSenderId());
            ps.setInt(2, message.getRecipientId());
            ps.setString(3,  message.getCourseCode());
            ps.setString(4, message.getSubject());
            ps.setString(5, message.getMessage());
            ps.setTimestamp(6, new java.sql.Timestamp(message.getTimestamp().getTime()));
            ps.setString(7, message.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error creating message: " + e.getMessage(), e);
        }
    }


    //update status of the message
    public boolean updateStatus(int messageId, String status) {
        String sql = "UPDATE tb_message SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, messageId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
            throw new RuntimeException("Error updating message status: " + e.getMessage(), e);
        }
    }


    //map resultset object to message object
    private Message mapRowToMessage(ResultSet rs) throws SQLException {
        Message msg = new Message();
        msg.setId(rs.getInt("id"));
        msg.setSenderId(rs.getInt("sender_id"));
        msg.setRecipientId(rs.getInt("recipient_id"));
        msg.setCourseCode(rs.getString("code_code"));
        msg.setSubject(rs.getString("subject"));
        msg.setMessage(rs.getString("message"));
        msg.setTimestamp(new Date(rs.getTimestamp("timestamp").getTime()));
        msg.setStatus(rs.getString("status"));
        return msg;
    }
}


//delete message by ID
//archived unless we really need it
//public boolean deleteMessage(int messageId) {
//    String sql = "DELETE FROM tb_message WHERE id = ?";
//    try (PreparedStatement ps = conn.prepareStatement(sql)) {
//        ps.setInt(1, messageId);
//        return ps.executeUpdate() > 0;
//    } catch (SQLException e) {
//        throw new RuntimeException("Error deleting message: " + e.getMessage(), e);
//    }
//}

//get all messages
//archived unless we need it
//public List<Message> getAllMessages() {
//  String sql = "SELECT id, sender_id, recipient_id, code_code, subject, message, timestamp, status FROM tb_message";
//  List<Message> list = new ArrayList<>();
//  try (Statement stmt = conn.createStatement();
//       ResultSet rs = stmt.executeQuery(sql)) {
//      while (rs.next()) {
//          list.add(mapRowToMessage(rs));
//      }
//  } catch (SQLException e) {
//      throw new RuntimeException("Error fetching all messages: " + e.getMessage(), e);
//  }
//  return list;
//}
