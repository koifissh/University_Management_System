package controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dao.DirectoryDAO;
import dao.MessageDAO;
import model.Message;
import model.User;
import view.AuthView;
import view.CommonView;
import my_util.Security;
import my_util.Verification;

import java.util.Date;


//controller for AuthView: handles login, clear, and password recovery.
public class AuthController {
    private final AuthView view;
    private final User model;
    private final DirectoryDAO directoryDao;
    private final MessageDAO messageDao;

    //initialize the controller
    public AuthController(AuthView view, User model) {
        this.view = view;
        this.model = model;
        this.directoryDao = new DirectoryDAO();
        this.messageDao = new MessageDAO();

        initializeEventListeners();
    }

    //setup listeners for each button
    private void initializeEventListeners() {
        setupLoginButtonListener();
        setupClearButtonListener();
        setupForgotPasswordButtonListener();
    }

   //clear email and password field
    private void setupClearButtonListener() {
        view.getBtnClear().addActionListener(event -> {
            view.getTxtEmailField().setText("");
            view.getTxtPasswordField().setText("");
        });
    }

    //authenticate using user credentials and move to dashboard
    private void setupLoginButtonListener() {
    
        view.getBtnLogin().addActionListener(event -> {
        	//get email and password from input fields
            String email = view.getTxtEmailField().getText().trim();
            String plainPassword = new String(view.getTxtPasswordField().getPassword());

            //verify fields are not empty
            if (!Verification.validateInputFields(email, plainPassword)) {
                return;
            }

            //hash the password and compare it to the password within the database
            String hashedPassword = Security.hashPassword(plainPassword);
            User authenticatedUser = directoryDao.authenticateUser(email, hashedPassword);
            
            //upon successful login confirm and move to dashboard view
            if (authenticatedUser != null) {
                JOptionPane.showMessageDialog(view, "Login Successful!");

                model.setId(authenticatedUser.getId());
                model.setFirstName(authenticatedUser.getFirstName());
                model.setLastName(authenticatedUser.getLastName());
                model.setEmail(authenticatedUser.getEmail());
                model.setRoleType(authenticatedUser.getRoleType());

                navigateToUserDashboard(authenticatedUser);
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Invalid credentials.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

   //password Recovery via Email
    private void setupForgotPasswordButtonListener() {
        view.getBtnForgot().addActionListener(event -> {
            String emailInput = JOptionPane.showInputDialog(view,
                "Enter your registered email:", "Forgot Password",
                JOptionPane.QUESTION_MESSAGE
            );
            
            //check if the dialog input is empty or nonvalid
            if (emailInput == null || emailInput.trim().isEmpty()) {
                return;
            }
            User user = directoryDao.getUserByEmail(emailInput.trim());
            if (user == null) {
                JOptionPane.showMessageDialog(view,
                    "No account found for " + emailInput + ".", "Email Not Found",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            //Send a password message to the email's inbox
            Message recoveryMessage = new Message();
            	recoveryMessage.setSenderId(user.getId());
            	recoveryMessage.setRecipientId(user.getId());
            	recoveryMessage.setCourseCode("ADMIN");
            	recoveryMessage.setSubject("Password Recovery");
            	recoveryMessage.setMessage("Your password is: " + user.getPassword());
            	recoveryMessage.setTimestamp(new Date());
            	recoveryMessage.setStatus("unread");
            	messageDao.createMessage(recoveryMessage);

            JOptionPane.showMessageDialog(view, 
            		"Your password has been sent to your inbox.", "Check Messages", 
            		JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    //move to the main frame after user login authentication is processed
    private void navigateToUserDashboard(User user) {
        view.dispose();

        JFrame mainFrame = new JFrame("School Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);

        CommonView commonView = new CommonView(mainFrame);
        new CommonController(commonView, user);

        mainFrame.setContentPane(commonView);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}