package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TeacherView extends JPanel {
	private JTabbedPane tabbedPane;
	private JPanel assignedCoursesPanel, enrolledStudentsPanel;

	// teacher course assignment panel
	private JTable assignedCoursesTable;
	private DefaultTableModel assignedCoursesTableModel;

	// enrolled students panel
	private JTable enrolledStudentsTable;
	private DefaultTableModel enrolledStudentsTableModel;

	public TeacherView() {
		// construct our panels and add them to the tabbed pane
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();

		buildAssignedCoursesPanel();
		buildEnrolledStudentsPanel();

		tabbedPane.addTab("Assigned Courses", assignedCoursesPanel);
		tabbedPane.addTab("Enrolled Students", enrolledStudentsPanel);

		add(tabbedPane, BorderLayout.CENTER);
	}

	// build our assigned course panel
	private void buildAssignedCoursesPanel() {
		assignedCoursesPanel = new JPanel(new BorderLayout());
		assignedCoursesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// construct our list model and add a scrollpane
		String[] cols = { "Course Code", "Name", "Description", "Capacity", "Status" };
		assignedCoursesTableModel = new DefaultTableModel(cols, 0);
		assignedCoursesTable = new JTable(assignedCoursesTableModel);
		assignedCoursesTable.setAutoCreateRowSorter(true);

		JScrollPane scroll = new JScrollPane(assignedCoursesTable);
		assignedCoursesPanel.add(scroll, BorderLayout.CENTER);
	}

	private void buildEnrolledStudentsPanel() {

		// construct our enrolledstudents panel and add a scroll pane
		enrolledStudentsPanel = new JPanel(new BorderLayout());
		enrolledStudentsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		String[] cols = { "Student ID", "First Name", "Last Name", "Email", "Course Code" };
		enrolledStudentsTableModel = new DefaultTableModel(cols, 0);
		enrolledStudentsTable = new JTable(enrolledStudentsTableModel);
		enrolledStudentsTable.setAutoCreateRowSorter(true);

		JScrollPane scroll = new JScrollPane(enrolledStudentsTable);
		enrolledStudentsPanel.add(scroll, BorderLayout.CENTER);
	}
	
	//getters and setters

	public JTable getAssignedCoursesTable() {
		return assignedCoursesTable;
	}

	public JTable getEnrolledStudentsTable() {
		return enrolledStudentsTable;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	// view testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Teacher View");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new TeacherView());
			frame.setSize(800, 600);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

}
