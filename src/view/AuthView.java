package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;

public class AuthView extends JFrame {
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JPanel loginPanel, welcomePanel, mainPanel;
	private JButton btnLogin, btnClear, btnForgot;

	public AuthView() {
		setTitle("School Management System - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel(new GridLayout(1, 2));

		buildWelcomePanel();
		buildLoginPanel();

		mainPanel.add(welcomePanel);
		mainPanel.add(loginPanel);

		add(mainPanel, BorderLayout.CENTER);

		// Set the size and position
		setSize(600, 400);
		centerWindow();

		// Make the window visible
		setVisible(true);
	}

	public void buildWelcomePanel() {
		welcomePanel = new JPanel(new BorderLayout());
		welcomePanel.setBackground(Color.BLACK); // Light blue

		JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
		welcomeLabel.setForeground(Color.WHITE);

		welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
	}

	public void buildLoginPanel() {
		loginPanel = new JPanel();
		loginPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

		// Single credentials panel with 2 rows, 2 columns
		// add fields and set color
		JPanel credentialsPanel = new JPanel(new GridLayout(4, 2));
		credentialsPanel.setBackground(Color.WHITE);
		credentialsPanel.add(new JLabel("Email:"));
		txtEmail = new JTextField();
		credentialsPanel.add(txtEmail);
		credentialsPanel.add(new JLabel("Password:"));
		txtPassword = new JPasswordField();
		credentialsPanel.add(txtPassword);

		// Button interaction panel

		JPanel btnPanel = new JPanel();
		btnClear = new JButton("Clear");
		btnLogin = new JButton("Login");
		btnPanel.add(btnClear);
		btnPanel.add(btnLogin);
		btnPanel.setBackground(Color.WHITE);

		JPanel forgotPanel = new JPanel();
		btnForgot = new JButton("Forgot Password?");
		forgotPanel.add(btnForgot);
		forgotPanel.setBackground(Color.WHITE);

		loginPanel.add(Box.createVerticalGlue());

		// add the panels to our login
		loginPanel.add(credentialsPanel);
		loginPanel.add(btnPanel);
		loginPanel.add(forgotPanel);

		loginPanel.add(Box.createVerticalGlue());
	}

	//center the window
	private void centerWindow() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
	}

	//getters and setters
	public JTextField getTxtEmailField() {
		return txtEmail;
	}

	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	public JPasswordField getTxtPasswordField() {
		return txtPassword;
	}

	public void setTxtPassword(JPasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JButton getBtnClear() {
		return btnClear;
	}

	public JButton getBtnForgot() {
		return btnForgot;
	}

	public void setBtnForgot(JButton btnForgot) {
		this.btnForgot = btnForgot;
	}

	// view testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AuthView();
			}
		});
	}

}
