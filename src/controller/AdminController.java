package controller;

import dao.CourseDAO;
import dao.DirectoryDAO;
import dao.TeacherCourseDAO;
import model.AssignedCourse;
import model.Course;
import model.User;
import my_util.EmailUtil;
import view.AdminView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;

//controller for CRUD operations on directory, course, and assignments
public class AdminController {
	private static final String DEFAULT_NEWUSER_PASSWORD = "123";

	private final AdminView adminView;
	private final DirectoryDAO directoryDAO;
	private final CourseDAO courseDAO;
	private final TeacherCourseDAO teacherCourseDAO;

	// construct the controller
	public AdminController(AdminView adminView) {
		this.adminView = adminView;
		this.directoryDAO = new DirectoryDAO();
		this.courseDAO = new CourseDAO();
		this.teacherCourseDAO = new TeacherCourseDAO();

		// load our tabs
		initializeDirectoryTab();
		initializeCourseTab();
		initializeAssignmentTab();
		initializeSharedActions();
	}

	// listen for user action
	private void initializeSharedActions() {
		adminView.getBtnCreate().addActionListener(e -> onCreate());
		adminView.getBtnUpdate().addActionListener(e -> onUpdate());
		adminView.getBtnDelete().addActionListener(e -> onDelete());
		adminView.getBtnClear().addActionListener(e -> onClear());
	}

	//create functionality
	private void onCreate() {
		String tab = adminView.getTabbedPane().getTitleAt(adminView.getTabbedPane().getSelectedIndex());

		//check user tab selection and select the appropriate action
		switch (tab) {
		case "Directory":
			handleCreateUser();
			break;
		case "Courses":
			handleCreateCourse();
			break;
		case "Assigned Courses":
			handleCreateAssignment();
			break;
		}
	}

	private void onUpdate() {
		String tab = adminView.getTabbedPane().getTitleAt(adminView.getTabbedPane().getSelectedIndex());
		
		//check user tab selection and select the appropriate action
		switch (tab) {
		case "Directory":
			handleUpdateUser();
			break;
		case "Courses":
			handleUpdateCourse();
			break;
		case "Assigned Courses":
			handleUpdateAssignment();
			break;

		}
	}

	private void onDelete() {
		String tab = adminView.getTabbedPane().getTitleAt(adminView.getTabbedPane().getSelectedIndex());
		
		//check user tab selection and select the appropriate action
		switch (tab) {
		case "Directory":
			handleDeleteUser();
			break;
		case "Courses":
			handleDeleteCourse();
			break;
		case "Assigned Courses":
			handleDeleteAssignment();
			break;
		}
	}

	private void onClear() {
		int panelSelectionId = adminView.getTabbedPane().getSelectedIndex();
		
		//check user tab selection and select the appropriate clear
		if (panelSelectionId == 0) {
			adminView.getDirectoryFirstNameField().setText("");
			adminView.getDirectoryLastNameField().setText("");
			adminView.getDirectoryEmailField().setText("");
			adminView.getRoleGroup().clearSelection();
			adminView.getSearchField().setText("");
		} else if (panelSelectionId == 1) {
			adminView.getCourseCodeField().setText("");
			adminView.getCourseNameField().setText("");
			adminView.getCourseDescriptionField().setText("");
			adminView.getCourseCapacitySpinner().setValue(30);
			adminView.getStatusComboBox().setSelectedIndex(0);
		} else {
			adminView.getAssignTeacherIdField().setText("");
			adminView.getAssignCourseCodeField().setText("");
		}
	}

	//DIRECTORY TAB
	//initialize the tab
	private void initializeDirectoryTab() {
		loadDirectoryTable();
		JTable table = adminView.getDirectoryTable();
		table.getSelectionModel().addListSelectionListener(this::onDirectoryRowSelected);
		adminView.getSearchButton().addActionListener(e -> handleSearchUsersByEmail());
	}

	//load the model list from database
	private void loadDirectoryTable() {
		DefaultTableModel model = getTableModel(adminView.getDirectoryTable());
		model.setRowCount(0);
		for (User user : directoryDAO.getAllUsers()) {
			model.addRow(new Object[] { user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoleType(),
					user.getId() });
		}
	}

	//check for user list selection
	private void onDirectoryRowSelected(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;
		int row = adminView.getDirectoryTable().getSelectedRow();
		if (row < 0)
			return;
		DefaultTableModel userModel = getTableModel(adminView.getDirectoryTable());
		adminView.getDirectoryFirstNameField().setText((String) userModel.getValueAt(row, 0));
		adminView.getDirectoryLastNameField().setText((String) userModel.getValueAt(row, 1));
		adminView.getDirectoryEmailField().setText((String) userModel.getValueAt(row, 2));
		boolean isTeacher = "teacher".equals(userModel.getValueAt(row, 3));
		adminView.getRadioTeacher().setSelected(isTeacher);
		adminView.getRadioStudent().setSelected(!isTeacher);
	}

	//create user functionality
	private void handleCreateUser() {
		//check input fields
		String first = adminView.getDirectoryFirstNameField().getText().trim();
		String last = adminView.getDirectoryLastNameField().getText().trim();
		String email = adminView.getDirectoryEmailField().getText().trim();
		String role = adminView.getRadioTeacher().isSelected() ? "teacher" : "student";
		
		//input validation
		if (first.isEmpty() || last.isEmpty() || email.isEmpty()) {
			showErrorDialog("All fields are required.");
			return;
		}
		if (!EmailUtil.isValidEmail(email)) {
			showErrorDialog("Invalid email format. Must end with @java.edu");
			return;
		}
		
		//create new user
		User newUser = new User();
		newUser.setFirstName(first);
		newUser.setLastName(last);
		newUser.setEmail(email);
		newUser.setRoleType(role);
		//creates new user if there is no duplicate email
		if (!directoryDAO.createUser(newUser, DEFAULT_NEWUSER_PASSWORD)) {
			showErrorDialog("Failed to create user.");
		}
		loadDirectoryTable();
	}

	//update user functionality
	private void handleUpdateUser() {
		
		//get user selection
		int row = adminView.getDirectoryTable().getSelectedRow();
		if (row < 0) {
			showErrorDialog("Please select a user to update.");
			return;
		}
		
		DefaultTableModel userModel = getTableModel(adminView.getDirectoryTable());
		int userId = (int) userModel.getValueAt(row, 4);
		
		//get user input fields
		String first = adminView.getDirectoryFirstNameField().getText().trim();
		String last = adminView.getDirectoryLastNameField().getText().trim();
		String email = adminView.getDirectoryEmailField().getText().trim();
		String role = adminView.getRadioTeacher().isSelected() ? "teacher" : "student";
		
		//input validation
		if (first.isEmpty() || last.isEmpty() || email.isEmpty()) {
			showErrorDialog("All fields are required.");
			return;
		}
		if (!EmailUtil.isValidEmail(email)) {
			showErrorDialog("Invalid email format. Must end with @java.edu");
			return;
		}
		
		//create user
		User user = new User();
		user.setId(userId);
		user.setFirstName(first);
		user.setLastName(last);
		user.setEmail(email);
		user.setRoleType(role);
		
		//update the user in the database
		if (!directoryDAO.updateUser(user)) {
			showErrorDialog("Failed to update user.");
		}
		loadDirectoryTable();
	}

	//delete user functionality
	private void handleDeleteUser() {
	    //check for user selection
	    int row = adminView.getDirectoryTable().getSelectedRow();
	    if (row < 0) {
	        showErrorDialog("Please select a user to delete.");
	        return;
	    }

	    //get the user ID
	    int userId = (int) adminView.getDirectoryTable().getValueAt(row, 4);

	    // Ask for confirmation
	    int choice = JOptionPane.showConfirmDialog(adminView, "Are you sure you want to delete the user entry?",
	            "Confirm Delete",
	            JOptionPane.YES_NO_OPTION);

	    // Proceed with deletion if the user confirms
	    if (choice  == JOptionPane.YES_OPTION) {
	        //delete the entry from the database
	        if (!directoryDAO.deleteUser(userId)) {
	            showErrorDialog("Failed to delete user.");
	        }
	        loadDirectoryTable();
	    }
	}

	//search functionality
	private void handleSearchUsersByEmail() {
		//get user search string and filter based on that and return the information from the database
		String userQuery = adminView.getSearchField().getText().trim().toLowerCase();
		DefaultTableModel model = getTableModel(adminView.getDirectoryTable());
		model.setRowCount(0);
		for (User user : directoryDAO.getAllUsers()) {
			if (user.getEmail().toLowerCase().contains(userQuery)) {
				model.addRow(new Object[] { user.getFirstName(), user.getLastName(), user.getEmail(),
						user.getRoleType(), user.getId() });
			}
		}
	}

	//COURSE TAB
	//Initialize course tab
	private void initializeCourseTab() {
		loadCourseTable();
		adminView.getCourseTable().getSelectionModel().addListSelectionListener(this::onCourseRowSelected);
	}

	//load course data from database
	private void loadCourseTable() {
		DefaultTableModel courseModel = getTableModel(adminView.getCourseTable());
		courseModel.setRowCount(0);
		for (Course course : courseDAO.getAllCourses()) {
			courseModel.addRow(
					new Object[] { 
							course.getCode(), 
							course.getName(), 
							course.getDescription(), 
							course.getCapacity(), 
							course.getStatus() });
		}
	}

	//check course user selection from list
	private void onCourseRowSelected(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;
		int row = adminView.getCourseTable().getSelectedRow();
		if (row < 0)
			return;
		DefaultTableModel courseModel = getTableModel(adminView.getCourseTable());
		adminView.getCourseCodeField().setText((String) courseModel.getValueAt(row, 0));
		adminView.getCourseNameField().setText((String) courseModel.getValueAt(row, 1));
		adminView.getCourseDescriptionField().setText((String) courseModel.getValueAt(row, 2));
		adminView.getCourseCapacitySpinner().setValue(courseModel.getValueAt(row, 3));
		adminView.getStatusComboBox().setSelectedItem(courseModel.getValueAt(row, 4));
	}
	
	//create course functionality
	private void handleCreateCourse() {
		//get user input fields
		String code = adminView.getCourseCodeField().getText().trim();
		String name = adminView.getCourseNameField().getText().trim();
		String desc = adminView.getCourseDescriptionField().getText().trim();
		int capacity = (int) adminView.getCourseCapacitySpinner().getValue();
		String status = (String) adminView.getStatusComboBox().getSelectedItem();
		//input validation
		if (code.isEmpty() || name.isEmpty() || desc.isEmpty()) {
			showErrorDialog("Code, Name, and Description are required.");
			return;
		}
		//create the course
		if (!courseDAO.createCourse(new Course(code, name, desc, capacity, status))) {
			showErrorDialog("Failed to create course.");
		}
		loadCourseTable();
	}
	
	
	//update course functionality
	private void handleUpdateCourse() {
		//check user list selection
		int row = adminView.getCourseTable().getSelectedRow();
		if (row < 0) {
			showErrorDialog("Please select a course to update.");
			return;
		}
		//get user input fields
		String orig = (String) adminView.getCourseTable().getValueAt(row, 0);
		String name = adminView.getCourseNameField().getText().trim();
		String desc = adminView.getCourseDescriptionField().getText().trim();
		int capacity = (int) adminView.getCourseCapacitySpinner().getValue();
		String status = (String) adminView.getStatusComboBox().getSelectedItem();
		
		//check if fields are empty
		if (name.isEmpty() || desc.isEmpty()) {
			showErrorDialog("Name and Description are required.");
			return;
		}
		//update course in database
		if (!courseDAO.updateCourse(new Course(orig, name, desc, capacity, status))) {
			showErrorDialog("Failed to update course.");
		}
		loadCourseTable();
	}

	private void handleDeleteCourse() {
		
		//check user selection
		int row = adminView.getCourseTable().getSelectedRow();
		if (row < 0) {
			showErrorDialog("Please select a course to delete.");
			return;
		}
		//get user code and ask for deletion confirmation
		String code = (String) adminView.getCourseTable().getValueAt(row, 0);
		int choice = JOptionPane.showConfirmDialog(adminView, "Delete course '" + code + "'?", "Confirm Delete",
				JOptionPane.YES_NO_OPTION);
		
		//delete course in database
		if (choice == JOptionPane.YES_OPTION && !courseDAO.deleteCourse(code)) {
			showErrorDialog("Failed to delete course.");
		}
		loadCourseTable();
	}

	//TEACHER ASSIGNMENTS TAB
	//initialize tab
	private void initializeAssignmentTab() {
		loadAssignmentTable();
		adminView.getAssignedCoursesTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting())
				onAssignmentRowSelected();
		});
	}

	//load assignments from database
	private void loadAssignmentTable() {
		DefaultTableModel assignModel = getTableModel(adminView.getAssignedCoursesTable());
		assignModel.setRowCount(0);
		for (AssignedCourse assignedCourse : teacherCourseDAO.getAllAssignments()) {
			assignModel.addRow(new Object[] { assignedCourse.getId(), assignedCourse.getTeacherId(),
					assignedCourse.getCourseCode() });
		}
	}

	//get user row selection
	private void onAssignmentRowSelected() {
		int row = adminView.getAssignedCoursesTable().getSelectedRow();
		if (row < 0) {
			clearAssignmentFields();
			return;
		}
		DefaultTableModel assignedModel = getTableModel(adminView.getAssignedCoursesTable());
		adminView.getAssignTeacherIdField().setText(assignedModel.getValueAt(row, 1).toString());
		adminView.getAssignCourseCodeField().setText((String) assignedModel.getValueAt(row, 2));
	}

	//create assignment functionality
	private void handleCreateAssignment() {
	    // Get user input fields
	    String tid = adminView.getAssignTeacherIdField().getText().trim();
	    String code = adminView.getAssignCourseCodeField().getText().trim();
	    
	    // Input validation
	    if (tid.isEmpty() || code.isEmpty()) {
	        showErrorDialog("Teacher ID and Course Code are required.");
	        return;
	    }
	    
	    try {
	        int teacherId = Integer.parseInt(tid);
	        
	        // Check if the user exists and has a teacher role
	        User user = directoryDAO.getUserById(teacherId);
	        if (user == null) {
	            showErrorDialog("User with ID " + teacherId + " does not exist.");
	            return;
	        }
	        
	        if (!"teacher".equals(user.getRoleType())) {
	            showErrorDialog("User with ID " + teacherId + " is not a teacher.");
	            return;
	        }
	        
	        // Also verify that the course exists
	        Course course = courseDAO.getCourseByCode(code);
	        if (course == null) {
	            showErrorDialog("Course with code " + code + " does not exist.");
	            return;
	        }
	        
	        // Proceed with assignment
	        if (!teacherCourseDAO.assignCourse(teacherId, code)) {
	            showErrorDialog("Failed to create assignment.");
	        }
	    } catch (NumberFormatException ex) {
	        showErrorDialog("Invalid Teacher ID format.");
	    }
	    
	    // Update table
	    loadAssignmentTable();
	    clearAssignmentFields();
	}

	//update functionality
	private void handleUpdateAssignment() {
	    // Get user selection
	    int row = adminView.getAssignedCoursesTable().getSelectedRow();
	    if (row < 0) {
	        showErrorDialog("Please select an assignment to update.");
	        return;
	    }
	    
	    DefaultTableModel assignedModel = getTableModel(adminView.getAssignedCoursesTable());
	    int assignmentId = (int) assignedModel.getValueAt(row, 0);
	    
	    // Get user input fields
	    String tid = adminView.getAssignTeacherIdField().getText().trim();
	    String code = adminView.getAssignCourseCodeField().getText().trim();
	    
	    // Input validation
	    if (tid.isEmpty() || code.isEmpty()) {
	        showErrorDialog("Teacher ID and Course Code are required.");
	        return;
	    }
	    
	    try {
	        int teacherId = Integer.parseInt(tid);
	        
	        // Check if the user exists and has a teacher role
	        User user = directoryDAO.getUserById(teacherId);
	        if (user == null) {
	            showErrorDialog("User with ID " + teacherId + " does not exist.");
	            return;
	        }
	        
	        if (!"teacher".equals(user.getRoleType())) {
	            showErrorDialog("User with ID " + teacherId + " is not a teacher.");
	            return;
	        }
	        
	        // Also verify that the course exists
	        Course course = courseDAO.getCourseByCode(code);
	        if (course == null) {
	            showErrorDialog("Course with code " + code + " does not exist.");
	            return;
	        }
	        
	        // Proceed with assignment update
	        boolean unassigned = teacherCourseDAO.unassignById(assignmentId);
	        boolean assigned = teacherCourseDAO.assignCourse(teacherId, code);
	        if (!unassigned || !assigned) {
	            showErrorDialog("Failed to update assignment.");
	        }
	    } catch (NumberFormatException ex) {
	        showErrorDialog("Invalid ID format.");
	    }
	    
	    loadAssignmentTable();
	    clearAssignmentFields();
	}
	
	//delete functionality
	private void handleDeleteAssignment() {
		
		//get user selection
		int row = adminView.getAssignedCoursesTable().getSelectedRow();
		if (row < 0) {
			showErrorDialog("Please select an assignment to delete.");
			return;
		}
		DefaultTableModel assignedModel = getTableModel(adminView.getAssignedCoursesTable());
		int assignmentId = (int) assignedModel.getValueAt(row, 0);
		
		//get user choice and deletion confirmation
		int choice = JOptionPane.showConfirmDialog(adminView, "Delete assignment ID " + assignmentId + "?",
				"Confirm Delete", JOptionPane.YES_NO_OPTION);
		
		//delete assignment
		if (choice == JOptionPane.YES_OPTION) {
			if (!teacherCourseDAO.unassignById(assignmentId)) {
				showErrorDialog("Failed to delete assignment.");
			}
		}
		loadAssignmentTable();
		clearAssignmentFields();
	}

	//get the table model
	private DefaultTableModel getTableModel(JTable table) {
		return (DefaultTableModel) table.getModel();
	}

	//clear fields
	private void clearAssignmentFields() {
		adminView.getAssignTeacherIdField().setText("");
		adminView.getAssignCourseCodeField().setText("");
	}

	//show error dialog based on message string
	private void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(adminView, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
