package view;

import javax.swing.*;
import java.awt.*;

public class CommonView extends JPanel {

	private JPanel sidebarPanel, mainContentPanel;
	private JLabel nameLabel, lastNameLabel, roleLabel;
	private JButton actionsButton, messagesButton, logoutButton, imageButton, profileButton;
	private static final Color SIDEBAR_COLOR = new Color(51, 51, 51); // Standard sidebar color
	private JFrame parentFrame; // Reference to the main frame

	public CommonView(JFrame frame) {
		this.parentFrame = frame;
		setLayout(new BorderLayout());
		buildSidebarPanel();
		add(sidebarPanel, BorderLayout.WEST);
		mainContentPanel = new JPanel(new CardLayout());
		add(mainContentPanel, BorderLayout.CENTER);

		((CardLayout) mainContentPanel.getLayout()).show(mainContentPanel, "default");
	}

	private void buildSidebarPanel() {
		sidebarPanel = new JPanel();
		sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
		sidebarPanel.setBackground(SIDEBAR_COLOR);
		sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		sidebarPanel.setPreferredSize(new Dimension(180, 400));

		// Add header labels
		nameLabel = new JLabel("TestName");
		styleLabel(nameLabel, 16);

		lastNameLabel = new JLabel("TestLastName");
		styleLabel(lastNameLabel, 16);

		roleLabel = new JLabel("TestRole");
		styleLabel(roleLabel, 14);

		JButton imageButton = createSquareImageButton("images/profile.png", 128);

		// Add components to sidebar
		sidebarPanel.add(imageButton);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 7)));
		sidebarPanel.add(nameLabel);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 1)));
		sidebarPanel.add(lastNameLabel);

		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		sidebarPanel.add(roleLabel);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Add navigation buttons
		actionsButton = createNavButton("Actions");
		sidebarPanel.add(actionsButton);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		messagesButton = createNavButton("Messages");
		sidebarPanel.add(messagesButton);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		profileButton = createNavButton("Profile");
		sidebarPanel.add(profileButton);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Add logout button at bottom
		sidebarPanel.add(Box.createVerticalGlue());
		logoutButton = createNavButton("Logout");
		sidebarPanel.add(logoutButton);
	}

	//style utility function for our labels
	private void styleLabel(JLabel label, int fontSize) {
		label.setForeground(Color.WHITE);
		label.setFont(label.getFont().deriveFont(Font.BOLD, fontSize));
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	//universal function for creating nav buttons
	private JButton createNavButton(String text) {
		JButton button = new JButton(text);
		Dimension buttonSize = new Dimension(160, 40);
		button.setPreferredSize(buttonSize);
		button.setMaximumSize(buttonSize);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		return button;
	}

	//create our profile image
	public JButton createSquareImageButton(String imagePath, int size) {
		// Create button
		imageButton = new JButton();

		// Set fixed size to make it square
		imageButton.setPreferredSize(new Dimension(size, size));

		// Remove default button styling
		imageButton.setFocusPainted(false);
		imageButton.setBorderPainted(false);

		// Load and set the image
		ImageIcon icon = new ImageIcon(imagePath);
		Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		imageButton.setIcon(new ImageIcon(img));

		return imageButton;
	}

	//getters and setters
	public void setBtnImage(JButton button, String imagePath) {
		if (button == null)
			return;
		int size = 128;

		// Load and set the new image
		ImageIcon icon = new ImageIcon(imagePath);
		Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(img));
	}

	public ImageIcon getBtnImage(JButton button) {
		if (button == null)
			return null;

		// Return the icon currently set on the button
		if (button.getIcon() instanceof ImageIcon) {
			return (ImageIcon) button.getIcon();
		}
		return null;
	}

	//getters and setters
	public JButton getImageButton() {
		return imageButton;
	}

	public JButton getBtnProfile() {
		return profileButton;
	}

	public JButton getBtnAction() {
		return actionsButton;
	}

	public JButton getBtnMessage() {
		return messagesButton;
	}

	public JButton getBtnLogout() {
		return logoutButton;
	}

	public JPanel getMainContentPanel() {
		return mainContentPanel;
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}

	public JLabel getLastNameLabel() {
		return lastNameLabel;
	}

	public void setTxtLastNameLabel(JLabel lastNameLabel) {
		this.lastNameLabel = lastNameLabel;
	}

	public void setTxtNameLabel(JLabel nameLabel) {
		this.nameLabel = nameLabel;
	}

	public JLabel getRoleLabel() {
		return roleLabel;
	}

	public void setTxtRoleLabel(JLabel roleLabel) {
		this.roleLabel = roleLabel;
	}

	//view testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Dashboard");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			CommonView commonPanel = new CommonView(frame);
			frame.setContentPane(commonPanel);
			frame.setSize(1000, 700);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}