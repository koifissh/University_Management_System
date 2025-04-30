package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentView extends JPanel {
	private JTabbedPane tabbedPane;

	// available courses tab
	private JPanel availablePanel;
	private JTable availableTable;
	private DefaultTableModel availableTableModel;
	private JButton enrollBtn;

	// user course tab
	private JPanel enrolledPanel;
	private JTable enrolledTable;
	private DefaultTableModel enrolledTableModel;
	private JButton refreshBtn, withdrawBtn;

	public StudentView() {
		// construct our panels and add to the tabbedpane
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();

		buildAvailableCourseCatalogPanel();
		buildEnrolledCoursesPanel();

		tabbedPane.addTab("Available Courses", availablePanel);
		tabbedPane.addTab("My Courses", enrolledPanel);

		add(tabbedPane, BorderLayout.CENTER);
	}

	// course availability page
	private void buildAvailableCourseCatalogPanel() {
		availablePanel = new JPanel(new BorderLayout());
		availablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// create our list model and our enroll button
		availableTableModel = new DefaultTableModel(
				new String[] { "Code", "Name", "Description", "Capacity", "Status" }, 0);
		availableTable = new JTable(availableTableModel);
		availableTable.setAutoCreateRowSorter(true);
		availablePanel.add(new JScrollPane(availableTable), BorderLayout.CENTER);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		enrollBtn = new JButton("Enroll");
		btnPanel.add(enrollBtn);
		availablePanel.add(btnPanel, BorderLayout.SOUTH);
	}

	private void buildEnrolledCoursesPanel() {
		// construct our enrolled courses panel
		enrolledPanel = new JPanel(new BorderLayout());
		enrolledPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// construct our list model and add withdraw and refresh buttons
		enrolledTableModel = new DefaultTableModel(new String[] { "ID", "Course Code", "Enrollment Date" }, 0);
		enrolledTable = new JTable(enrolledTableModel);
		enrolledTable.setAutoCreateRowSorter(true);
		enrolledPanel.add(new JScrollPane(enrolledTable), BorderLayout.CENTER);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		refreshBtn = new JButton("Refresh");
		withdrawBtn = new JButton("Withdraw");
		btnPanel.add(refreshBtn);
		btnPanel.add(withdrawBtn);
		enrolledPanel.add(btnPanel, BorderLayout.SOUTH);
	}

	//getters and setters
	public JTable getAvailableTable() {
		return availableTable;
	}

	public DefaultTableModel getAvailableTableModel() {
		return availableTableModel;
	}

	public JButton getEnrollBtn() {
		return enrollBtn;
	}

	public JTable getEnrolledTable() {
		return enrolledTable;
	}

	public DefaultTableModel getEnrolledTableModel() {
		return enrolledTableModel;
	}

	public JButton getRefreshBtn() {
		return refreshBtn;
	}

	public JButton getWithdrawBtn() {
		return withdrawBtn;
	}

	// view testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Student Console");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new StudentView());
			frame.setSize(800, 500);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
