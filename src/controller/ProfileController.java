package controller;

import view.ProfileView;
import dao.DirectoryDAO;
import model.User;
import my_util.Security;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//controller for profile panel
public class ProfileController {
	private final ProfileView view;
	private final DirectoryDAO directoryDao;
	private final int userId;

	// construct profile controller
	public ProfileController(ProfileView view, int userId) {
		this.view = view;
		this.directoryDao = new DirectoryDAO();
		this.userId = userId;

		loadUserProfile();
		setupChangePasswordListener();
	}

	// load user details into the panels
	private void loadUserProfile() {
		// Fetch user by ID
		User user = directoryDao.getUserById(userId);
		if (user == null) {
			JOptionPane.showMessageDialog(view, "Failed to load profile.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// get the user's info
		view.getFirstNameField().setText(user.getFirstName());
		view.getLastNameField().setText(user.getLastName());
		view.getEmailField().setText(user.getEmail());
		view.getRoleField().setText(user.getRoleType());
	}

	// change password listener
	private void setupChangePasswordListener() {
		view.getChangePasswordButton().addActionListener(e -> {
			// get our input fields
			String current = new String(view.getCurrentPasswordField().getPassword());
			String next = new String(view.getNewPasswordField().getPassword());
			String confirm = new String(view.getConfirmPasswordField().getPassword());

			// input validation
			if (current.isEmpty() || next.isEmpty() || confirm.isEmpty()) {
				JOptionPane.showMessageDialog(view, "All password fields are required.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if new passwords match
			if (!next.equals(confirm)) {
				JOptionPane.showMessageDialog(view, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Attempt password change update, hashing, and comparison to original password
			boolean success = directoryDao.changePassword(userId, current, next);
			if (success) {
				JOptionPane.showMessageDialog(view, "Password changed successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				view.getCurrentPasswordField().setText("");
				view.getNewPasswordField().setText("");
				view.getConfirmPasswordField().setText("");
			} else {
				JOptionPane.showMessageDialog(view, "Current password is incorrect or update failed.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
