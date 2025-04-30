package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminView extends JPanel {
	private JTabbedPane tabbedPane;
	private JPanel directoryPanel, coursePanel, assignedCoursesPanel, actionPanel;

	// user directory tab
	private JTextField searchField;
	private JButton searchButton;
	private JTable directoryTable;
	private DefaultTableModel directoryTableModel;
	private JTextField directoryFirstNameField, directoryLastNameField, directoryEmailField;
	private JRadioButton studentRadio, teacherRadio;
	private ButtonGroup roleGroup;

	// course tab
	private JTable courseTable;
	private DefaultTableModel courseTableModel;
	private JTextField courseCodeField, courseNameField, courseDescriptionField;
	private JSpinner courseCapacitySpinner;
	private JComboBox<String> statusComboBox;

	// assigned course tab
	private JTable assignedCoursesTable;
	private DefaultTableModel assignedCoursesTableModel;
	private JTextField assignTeacherIdField, assignCourseCodeField;

	// common CRUD components
	private JButton createBtn, updateBtn, deleteBtn, clearBtn;

	public AdminView() {
		//build our panels
		setLayout(new BorderLayout());
		buildDirectoryPanel();
		buildCoursePanel();
		buildAssignedCoursesPanel();

		//add panels to our tabbedpane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Directory", directoryPanel);
		tabbedPane.addTab("Courses", coursePanel);
		tabbedPane.addTab("Assigned Courses", assignedCoursesPanel);

		//build our CRUD button panel
		buildActionPanel();

		add(tabbedPane, BorderLayout.CENTER);
		add(actionPanel, BorderLayout.SOUTH);
	}

	//construct directory panel
	private void buildDirectoryPanel() {
		directoryPanel = new JPanel(new BorderLayout());
		directoryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		//add fields and radio button
		JPanel input = new JPanel(new GridLayout(4, 2, 5, 5));
		input.add(new JLabel("First Name:"));
		directoryFirstNameField = new JTextField();
		input.add(directoryFirstNameField);
		input.add(new JLabel("Last Name:"));
		directoryLastNameField = new JTextField();
		input.add(directoryLastNameField);
		input.add(new JLabel("Email:"));
		directoryEmailField = new JTextField();
		input.add(directoryEmailField);
		input.add(new JLabel("Role:"));
		JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		studentRadio = new JRadioButton("student");
		teacherRadio = new JRadioButton("teacher");
		
		//add our radio buttons to the group and to the panel
		roleGroup = new ButtonGroup();
		roleGroup.add(studentRadio);
		roleGroup.add(teacherRadio);
		rolePanel.add(studentRadio);
		rolePanel.add(teacherRadio);
		input.add(rolePanel);
		topPanel.add(input);

		//add our search panel
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		searchPanel.add(new JLabel("Search by Email:"));
		searchField = new JTextField(20);
		searchPanel.add(searchField);
		searchButton = new JButton("Search");
		searchPanel.add(searchButton);
		topPanel.add(searchPanel);

		directoryPanel.add(topPanel, BorderLayout.NORTH);

		directoryTableModel = new DefaultTableModel(new String[] {"First Name", "Last Name", "Email", "Role", "ID" },
				0);
		directoryTable = new JTable(directoryTableModel);
		directoryTable.setAutoCreateRowSorter(true);
		directoryPanel.add(new JScrollPane(directoryTable), BorderLayout.CENTER);
	}

	//construct our course panel
	private void buildCoursePanel() {
		coursePanel = new JPanel(new BorderLayout());
		coursePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		courseTableModel = new DefaultTableModel(new String[] {"Code", "Name", "Description", "Capacity", "Status" },
				0);
		courseTable = new JTable(courseTableModel);
		courseTable.setAutoCreateRowSorter(true);
		coursePanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

		//add course fields
		JPanel input = new JPanel(new GridLayout(5, 2, 5, 5));
		input.add(new JLabel("Code:"));
		courseCodeField = new JTextField();
		input.add(courseCodeField);
		input.add(new JLabel("Name:"));
		courseNameField = new JTextField();
		input.add(courseNameField);
		input.add(new JLabel("Description:"));
		courseDescriptionField = new JTextField();
		input.add(courseDescriptionField);
		input.add(new JLabel("Capacity:"));
		courseCapacitySpinner = new JSpinner(new SpinnerNumberModel(30, 1, 500, 1));
		input.add(courseCapacitySpinner);
		input.add(new JLabel("Status:"));
		statusComboBox = new JComboBox<>(new String[] {"active", "inactive" });
		input.add(statusComboBox);
		coursePanel.add(input, BorderLayout.NORTH);
	}

	//construct our course assignments table
	private void buildAssignedCoursesPanel() {
		assignedCoursesPanel = new JPanel(new BorderLayout());
		assignedCoursesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		assignedCoursesTableModel = new DefaultTableModel(new String[] {"Assignment ID", "Teacher ID", "Course Code" },
				0);
		assignedCoursesTable = new JTable(assignedCoursesTableModel);
		assignedCoursesTable.setAutoCreateRowSorter(true);
		assignedCoursesPanel.add(new JScrollPane(assignedCoursesTable), BorderLayout.CENTER);

		//add fields
		JPanel input = new JPanel(new GridLayout(2, 2, 5, 5));
		input.add(new JLabel("Teacher ID:"));
		assignTeacherIdField = new JTextField();
		input.add(assignTeacherIdField);
		input.add(new JLabel("Course Code:"));
		assignCourseCodeField = new JTextField();
		input.add(assignCourseCodeField);
		
		assignedCoursesPanel.add(input, BorderLayout.NORTH);
	}

	//construct our CRUD operations button panel
	private void buildActionPanel() {
		
		//add CRUD buttons
		actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		createBtn = new JButton("Create");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		clearBtn = new JButton("Clear");
		actionPanel.add(clearBtn);
		actionPanel.add(deleteBtn);
		actionPanel.add(updateBtn);
		actionPanel.add(createBtn);

	}

	//getters and setters
	public JTextField getSearchField() {
		return searchField;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public JTable getDirectoryTable() {
		return directoryTable;
	}

	public JTextField getDirectoryFirstNameField() {
		return directoryFirstNameField;
	}

	public JTextField getDirectoryLastNameField() {
		return directoryLastNameField;
	}

	public JTextField getDirectoryEmailField() {
		return directoryEmailField;
	}

	public JRadioButton getRadioStudent() {
		return studentRadio;
	}

	public JRadioButton getRadioTeacher() {
		return teacherRadio;
	}

	public ButtonGroup getRoleGroup() {
		return roleGroup;
	}

	// Getters for Course tab
	public JTable getCourseTable() {
		return courseTable;
	}

	public JTextField getCourseCodeField() {
		return courseCodeField;
	}

	public JTextField getCourseNameField() {
		return courseNameField;
	}

	public JTextField getCourseDescriptionField() {
		return courseDescriptionField;
	}

	public JSpinner getCourseCapacitySpinner() {
		return courseCapacitySpinner;
	}

	public JComboBox<String> getStatusComboBox() {
		return statusComboBox;
	}

	// Getters for Assigned Courses tab
	public JTable getAssignedCoursesTable() {
		return assignedCoursesTable;
	}

	public JTextField getAssignTeacherIdField() {
		return assignTeacherIdField;
	}

	public JTextField getAssignCourseCodeField() {
		return assignCourseCodeField;
	}

	// Getters for shared actions
	public JButton getBtnCreate() {
		return createBtn;
	}

	public JButton getBtnUpdate() {
		return updateBtn;
	}

	public JButton getBtnDelete() {
		return deleteBtn;
	}

	public JButton getBtnClear() {
		return clearBtn;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	//view testing
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Admin View");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new AdminView());
			frame.setSize(900, 600);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
