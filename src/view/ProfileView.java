package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileView extends JPanel {
    private JLabel lblFirstName, lblLastName, lblEmail, lblRole;
    private JTextField txtFirstName, txtLastName;
    private JTextField txtEmail, txtRole;

    private JPasswordField txtCurrentPassword, txtNewPassword, txtConfirmPassword;
    private JButton btnChangePassword;

    public ProfileView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // User info panel
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Profile Information"));

        //add our user details such as first name, last name, email, role
        lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField();
        txtFirstName.setEditable(false);
        infoPanel.add(lblFirstName);
        infoPanel.add(txtFirstName);

        lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField();
        txtLastName.setEditable(false);
        infoPanel.add(lblLastName);
        infoPanel.add(txtLastName);

        lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        txtEmail.setEditable(false);
        infoPanel.add(lblEmail);
        infoPanel.add(txtEmail);

        lblRole = new JLabel("Role:");
        txtRole = new JTextField();
        txtRole.setEditable(false);
        infoPanel.add(lblRole);
        infoPanel.add(txtRole);

        add(infoPanel, BorderLayout.NORTH);

        //add our panel to change the password
        JPanel passwordPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Change Password"));

        passwordPanel.add(new JLabel("Current Password:"));
        txtCurrentPassword = new JPasswordField();
        passwordPanel.add(txtCurrentPassword);

        passwordPanel.add(new JLabel("New Password:"));
        txtNewPassword = new JPasswordField();
        passwordPanel.add(txtNewPassword);

        passwordPanel.add(new JLabel("Confirm Password:"));
        txtConfirmPassword = new JPasswordField();
        passwordPanel.add(txtConfirmPassword);

        btnChangePassword = new JButton("Change Password");
        passwordPanel.add(new JLabel());
        passwordPanel.add(btnChangePassword);

        add(passwordPanel, BorderLayout.CENTER);
    }

  //getters and setters
    public JTextField getFirstNameField() {
        return txtFirstName;
    }

    public JTextField getLastNameField() {
        return txtLastName;
    }

    public JTextField getEmailField() {
        return txtEmail;
    }

    public JTextField getRoleField() {
        return txtRole;
    }

    public JPasswordField getCurrentPasswordField() {
        return txtCurrentPassword;
    }

    public JPasswordField getNewPasswordField() {
        return txtNewPassword;
    }

    public JPasswordField getConfirmPasswordField() {
        return txtConfirmPassword;
    }

    public JButton getChangePasswordButton() {
        return btnChangePassword;
    }

    //view testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Profile View");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ProfileView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
