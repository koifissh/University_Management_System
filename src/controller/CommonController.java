package controller;

import java.awt.CardLayout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;
import view.AuthView;
import view.CommonView;
import view.ProfileView;

import view.*;

public class CommonController {

	private CommonView view;
	private User model;

	// controller constructor
	public CommonController(CommonView view, User model) {
		this.view = view;
		this.model = model;

		initializeEventListeners();
		labelListener();
	}

	private void initializeEventListeners() {
		setupActionPanelButtonListener();

		setupMessagePanelButtonListener();
		setupLogoutButtonListener();
		setupProfilePanelButtonListener();
		setupImageButtonListener();

	}

	public void setupImageButtonListener() {
		JButton imgBtn = view.getImageButton();
		imgBtn.addActionListener(e -> {
			// Create a file chooser
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select Profile Image");
			// Filter for PNG/JPEG only
			chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg"));

			int result = chooser.showOpenDialog(view);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selected = chooser.getSelectedFile();
				String path = selected.getAbsolutePath();
				// Update the button’s icon
				view.setBtnImage(imgBtn, path);
			}
		});
	}

	public void setupLogoutButtonListener() {
		view.getBtnLogout().addActionListener(e -> {
			System.out.println("logout clicked");

			// Show confirmation dialog
			int response = javax.swing.JOptionPane.showConfirmDialog(view, "Are you sure you want to logout?",
					"Confirm Logout", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);

			// Check the user's response for yes
			if (response == javax.swing.JOptionPane.YES_OPTION) {
				// logout and send the user back to the login screen
				javax.swing.SwingUtilities.getWindowAncestor(view).dispose();

				// Create a new login view
				AuthView authView = new AuthView();
				User model = new User();

				// Create and start the controller
				AuthController controller = new AuthController(authView, model);

				// Display the login view
				authView.setVisible(true);
			}
		});
	}

	// listen to the dashboard menu items
	public void setupActionPanelButtonListener() {
		view.getBtnAction().addActionListener(e -> {
			System.out.println("action clicked");
			JPanel mainContentPanel = view.getMainContentPanel();
			CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
			mainContentPanel.removeAll();

			// check who the user that clicked is.
			switch (model.getRoleType()) {
			case "admin":

				AdminView adminView = new AdminView();
				adminView.setName("adminPanel");
				new AdminController(adminView);
				mainContentPanel.add(adminView);
				break;

			case "teacher":
				// if it is a teacher, we create a teacher panel
				TeacherView teacherView = new TeacherView();
				teacherView.setName("teacherPanel");
				new TeacherController(teacherView, model);
				mainContentPanel.add(teacherView);

				break;

			case "student":
				// if it is a student, we create a student panel
				StudentView studentView = new StudentView();
				studentView.setName("studentPanel");
				new StudentController(studentView, model);
				mainContentPanel.add(studentView);

				break;

			default:
				System.out.println("Unknown role type: " + model.getRoleType());
				JPanel defaultPanel = new JPanel();
				defaultPanel.add(new JLabel("Unknown role type. Please contact administrator."));
				mainContentPanel.add(defaultPanel, "actions");
			}

			// display our action panel
			cardLayout.show(mainContentPanel, "actions");
			mainContentPanel.revalidate();
			mainContentPanel.repaint();

		});
	}

	// check if messages button is clicked
	public void setupMessagePanelButtonListener() {
		view.getBtnMessage().addActionListener(e -> {

			// if message button is clicked, we construct a card layout and display our
			// current message panel
			JPanel mainContentPanel = view.getMainContentPanel();
			CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
			mainContentPanel.removeAll();

			MessageView messageView = new MessageView();
			messageView.setName("messagePanel");
			new MessageController(messageView, model.getId());
			mainContentPanel.add(messageView);

			cardLayout.show(mainContentPanel, "messages");
			mainContentPanel.revalidate();
			mainContentPanel.repaint();

		});

	}

	// check if profile button is clicked
	public void setupProfilePanelButtonListener() {

		view.getBtnProfile().addActionListener(e -> {

			// if profile button is clicked, we construct a card layout and display our
			// current profile panel
			JPanel mainContentPanel = view.getMainContentPanel();
			CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
			mainContentPanel.removeAll();

			ProfileView profileView = new ProfileView();
			profileView.setName("messagePanel");
			new ProfileController(profileView, model.getId());
			mainContentPanel.add(profileView);

			cardLayout.show(mainContentPanel, "profile");
			mainContentPanel.revalidate();
			mainContentPanel.repaint();

		});

	}

	// change dashboard label info based on user info
	void labelListener() {
		view.getNameLabel().setText(model.getFirstName());
		view.getRoleLabel().setText(model.getRoleType());
		view.getLastNameLabel().setText(model.getLastName());
	}

}
