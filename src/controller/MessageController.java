package controller;

import view.MessageView;
import dao.MessageDAO;
import dao.DirectoryDAO;
import dao.TeacherCourseDAO;
import model.Message;
import model.User;
import model.AssignedCourse;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.List;

//controller for CRUD messages and user message viewing
public class MessageController {
    private final MessageView messageView;
    private final MessageDAO messageDao;
    private final DirectoryDAO directoryDao;
    private final TeacherCourseDAO teacherCourseDao;
    private final int currentUserId;

    private List<Message> inboxList;

    //construct our message controller
    public MessageController(MessageView view, int currentUserId) {
        this.messageView = view;
        this.currentUserId = currentUserId;
        this.messageDao = new MessageDAO();
        this.directoryDao = new DirectoryDAO();
        this.teacherCourseDao = new TeacherCourseDAO();

        //initialize tab listeners
        initInboxTab();
        initComposeTab();
    }

    
    private void initInboxTab() {
    	//load the user's messages into the list
        loadInbox();
        messageView.getInboxTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = messageView.getInboxTable().getSelectedRow();
                    if (row >= 0) {
                        showMessageDetail(row);
                    }
                }
            }
        });
        //listen for user actions
        messageView.getMarkReadBtn().addActionListener(e -> markSelectedAsRead());
        messageView.getReplyBtn().addActionListener(e -> openReply());
    }
    
    
    //load user inbox
    private void loadInbox() {
        DefaultTableModel model = messageView.getInboxTableModel();
        model.setRowCount(0);
        inboxList = messageDao.getMessagesForUser(currentUserId);
        for (Message msg : inboxList) {
            if (msg.getRecipientId() != currentUserId) continue;
            String senderEmail = findEmailById(msg.getSenderId());
            String recipientEmail = findEmailById(msg.getRecipientId());
            model.addRow(new Object[]{
                msg.getId(), senderEmail, recipientEmail, msg.getCourseCode(), msg.getSubject(), msg.getTimestamp(), msg.getStatus()
            });
        }
    }

    //show the message selected
    private void showMessageDetail(int row) {
        // Get the message ID from the table model
        int messageId = (int) messageView.getInboxTable().getValueAt(row, 0);
        
        // Find the corresponding message in inboxList
        Message msg = null;
        for (Message message : inboxList) {
            if (message.getId() == messageId) {
                msg = message;
                break;
            }
        }
        
        if (msg != null) {
            messageView.getLblSender().setText("Sender: " + findEmailById(msg.getSenderId()));
            messageView.getLblRecipient().setText("Recipient: " + findEmailById(msg.getRecipientId()));
            messageView.getLblSubject().setText("Subject: " + msg.getSubject());
            messageView.getLblTime().setText("Time: " + msg.getTimestamp());
            messageView.getLblStatus().setText("Status: " + msg.getStatus());
            messageView.getLblCode().setText("Code: " + msg.getCourseCode());
            messageView.getDetailMessageArea().setText(msg.getMessage());
        }
    }
    

    //check for email in the directory for message labeling
    private String findEmailById(int userId) {
        for (User user : directoryDao.getAllUsers()) {
            if (user.getId() == userId) return user.getEmail();
        }
        return "";
    }

    //mark the message as read
    private void markSelectedAsRead() {
        int row = messageView.getInboxTable().getSelectedRow();
        if (row < 0) return;
        int messageId = (int) messageView.getInboxTableModel().getValueAt(row, 0);
        messageDao.updateStatus(messageId, "read");
        loadInbox();
    }

    //reply button when clicked
    private void openReply() {
    	
        int row = messageView.getInboxTable().getSelectedRow();
        if (row < 0) return;
        
        //get the message ID from the table model
        int messageId = (int) messageView.getInboxTable().getValueAt(row, 0);
        
        //find the corresponding message in inboxList
        Message msg = null;
        for (Message message : inboxList) {
            if (message.getId() == messageId) {
                msg = message;
                break;
            }
        }
        
        if (msg != null) {
            messageView.getTabbedPane().setSelectedIndex(1);
            messageView.getRecipientIdField().setText(findEmailById(msg.getSenderId()));
            messageView.getCourseCodeField().setText(msg.getCourseCode());
            messageView.getSubjectField().setText("Re: " + msg.getSubject());
            messageView.getMessageArea().setText("\n\n--- Original ---\n" + msg.getMessage());
        }
    }

    //initialize message compose
    private void initComposeTab() {
        messageView.getSendBtn().addActionListener(e -> sendMessage());
    }
    
    
    //send message functionality
    private void sendMessage() {
        //get input fields
        String recipientEmail = messageView.getRecipientIdField().getText().trim();
        String courseCode = messageView.getCourseCodeField().getText().trim();
        String subject = messageView.getSubjectField().getText().trim();
        String body = messageView.getMessageArea().getText().trim();

        //validation
        if (recipientEmail.isEmpty() && courseCode.isEmpty()) {
            JOptionPane.showMessageDialog(messageView, "Enter recipient email or course code.", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //determine the recipient ID
        int recipientId = -1;
        
        //check if email provided
        if (!recipientEmail.isEmpty()) {
            // Try to find the user by email
            User recipient = directoryDao.getUserByEmail(recipientEmail);
            if (recipient == null) {
                JOptionPane.showMessageDialog(messageView, "No user found with email " + recipientEmail, 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            recipientId = recipient.getId();
        } 
        // If no email but course code provided, send to course instructor
        else if (!courseCode.isEmpty()) {
            List<AssignedCourse> list = teacherCourseDao.getByCourseCode(courseCode);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(messageView, "No instructor found for course " + courseCode, 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            recipientId = list.get(0).getTeacherId();
        }
        
        //double-check for a valid recipient
        if (recipientId == -1) {
            JOptionPane.showMessageDialog(messageView, "Could not determine recipient.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create and send the message
        Message msg = new Message();
        msg.setSenderId(currentUserId);
        msg.setRecipientId(recipientId);
        msg.setCourseCode(courseCode);
        msg.setSubject(subject);
        msg.setMessage(body);
        msg.setTimestamp(new Date());
        msg.setStatus("unread");

        // Send the message
        if (messageDao.createMessage(msg)) {
            String recipientName = findEmailById(recipientId);
            JOptionPane.showMessageDialog(messageView, "Message sent to " + recipientName + "!");
            
            // Clear fields
            messageView.getRecipientIdField().setText("");
            messageView.getCourseCodeField().setText("");
            messageView.getSubjectField().setText("");
            messageView.getMessageArea().setText("");
            messageView.getTabbedPane().setSelectedIndex(0);
            loadInbox();
        } else {
            JOptionPane.showMessageDialog(messageView, "Failed to send message.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    //find user in database through the email information provided and provide validation
    private int findUserIdByEmail(String email) {
        for (User user : directoryDao.getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) return user.getId();
        }
        return -1;
    }
}
